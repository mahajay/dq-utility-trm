package com.dq.trm.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dq.config.datasource.DB_TYPE;
import com.dq.service.metadata.ColumnData;
import com.dq.service.metadata.Metadata;
import com.dq.service.metadata.MetadataService;
import com.dq.utility.model.MetaData;

@Controller
//@RequestMapping(value="/dq-utility")
public class MetaDataController {

	@Autowired
	MetadataService metaService;
	
	
	@RequestMapping(value="/db_list", method=RequestMethod.GET)
	@ResponseBody
	public List<Metadata> getDatabases(){
		List<Metadata> dbList = new ArrayList<>();
		for (DB_TYPE dbType : DB_TYPE.values()) {
			Metadata data = new Metadata();
			data.setType("database");
			data.setValue(dbType.getDbName());
			dbList.add(data);
		}
		return dbList;
	}
	
	@RequestMapping(value = "/schema/{dbType}",method = RequestMethod.GET)
	@ResponseBody
	public List<Metadata> getDbSchema(@PathVariable String dbType) throws SQLException{
		List<Metadata> schemas = metaService.getDBSchemaList(dbType);
		CollectionUtils.filter(schemas, new Predicate<Metadata>() {

			@Override
			public boolean evaluate(Metadata data) {
				return !(data.getValue().equalsIgnoreCase("information_schema") ||
						data.getValue().equalsIgnoreCase("mysql") ||
						data.getValue().equalsIgnoreCase("performance_schema") ||
						data.getValue().equalsIgnoreCase("sys"));
			}
		});
		return schemas;
	}
	
	@RequestMapping(value = "/schema/{dbType}/{schema}",method = RequestMethod.GET)
	@ResponseBody
	public List<String> getTableList(@PathVariable String dbType, @PathVariable String schema) throws SQLException{
		return metaService.getTableNames(dbType, schema);
	}
	
	@RequestMapping(value = "/schema/{dbType}/{schema}/{table}",method = RequestMethod.GET)
	@ResponseBody
	public ColumnData getColumnList(@PathVariable String dbType, @PathVariable String schema, @PathVariable String table) throws SQLException{
		ColumnData data = new ColumnData();
		data.setTable(table);
		data.setColumns(metaService.getColumnNames(dbType, schema, table));
		return data;
	}
	
	@RequestMapping(value="showColumns", method=RequestMethod.POST)
	public ModelAndView getColumns(@ModelAttribute("SpringWeb") MetaData tableData,
			ModelMap map) throws SQLException {
		System.out.println("Inside getColumns method ....");
		List<String> columns = metaService.getColumnNames(tableData.getDbType().getDbName(), tableData.getSchema(), tableData.getTable());
		for (String srcColumn : columns) {
			System.out.println("srcColumn - "+srcColumn);	
		}
		
		tableData.setColumns(columns.toArray(new String[columns.size()]));
		map.addAttribute("table", tableData.getTable());
		map.addAttribute("columns", tableData.getColumns());
		map.addAttribute("dbType", tableData.getDbType().getDbName());
		map.addAttribute("schema", tableData.getSchema());
		ModelAndView modelAndView = new ModelAndView("columns", "command", tableData);
		  return modelAndView;
	}
	
/*	@RequestMapping(value="showDQCounts", method=RequestMethod.POST)
	public ModelAndView showDQCounts(@ModelAttribute("SpringWeb")  DQData dqData,
			ModelMap map) throws SQLException {
		DQData resultData = dataService.getDQResultCount(dqData);
		map.addAttribute("dqCountMap", resultData.getQueryCount());
		map.addAttribute("queryType", "");
		ModelAndView modelAndView = new ModelAndView("dqcounts", "command", resultData);
		  return modelAndView;
	}
	
	@RequestMapping(value="showDQResults", method=RequestMethod.POST)
	public ModelAndView showDQResults(@ModelAttribute("SpringWeb")  DQData dqData,
			ModelMap map, HttpServletRequest request) throws SQLException {
		System.out.println("queryType ----- "+request.getParameter("queryType"));
		dqData.setQueryType(request.getParameter("queryType"));
		DQData resultData = dataService.getDQResultData(dqData);
		for (String str : resultData.getResultData().keySet()) {
			Map<Integer, List<String>> result = resultData.getResultData().get(str);
			System.out.println("Result size - "+result.size());
			for (int index : result.keySet()) {
				List<String> dataList = result.get(index);
				System.out.println("dataList sieze - "+dataList.size());
				for (String string : dataList) {
					System.out.print(string + ", ");	
				}
				
			}
		}
		map.addAttribute("resultData", resultData.getResultData());
		map.addAttribute("fieldList", resultData.getFields());
		ModelAndView modelAndView = new ModelAndView("dqresults", "command", resultData);
		  return modelAndView;
	}*/
}
