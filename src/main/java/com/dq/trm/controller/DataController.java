package com.dq.trm.controller;

import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dq.config.datasource.JOIN_TYPE;
import com.dq.service.data.DQCompareQueryData;
import com.dq.service.data.DQQueryData;
import com.dq.service.data.DQResultData;
import com.dq.service.data.DataService;
import com.dq.service.data.Join;
import com.dq.trm.controller.dto.DQCompareResultData;

@Controller
public class DataController {

	@Autowired
	DataService dataService;

	@RequestMapping(value = "/database/{dbType}/executeSource", method = RequestMethod.POST)
	public @ResponseBody DQResultData executeSourceQuery(@RequestBody DQQueryData data,
			@PathVariable String dbType, HttpServletRequest request) {
		
		int pageNumber = request.getParameter("pageNumber") == null ? 1 : Integer.parseInt(request.getParameter("pageNumber"));
		int recordCount = request.getParameter("records") == null ? 50 : Integer.parseInt(request.getParameter("records"));
		
		if(data.getPageNumber() == 0 ) {
			data.setPageNumber(pageNumber);
		}
		if(data.getRecordCount() == 0) {
			data.setRecordCount(recordCount);
		}
		
		DQResultData result = null;
		try {
			result = dataService.executeQUery(dbType, data, null, null, null);
		} catch (ClassNotFoundException | SQLException e) {
			result = new DQResultData();
			Map<String, List<?>> errorResult = new HashMap<>();
			List<String> errList = new ArrayList<>();
			errList.add(e.getMessage());
			errorResult.put("Error", errList );
			result.setColumnData(errorResult );
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject(result);
		return result;	
	}
	
	@RequestMapping(value = "/database/{dbType}/executeComapre", method = RequestMethod.POST)
	public @ResponseBody DQResultData executeCompareQuery(@RequestBody DQCompareQueryData compareData,
			@PathVariable String dbType, HttpServletRequest request) throws Exception {
		
		int pageNumber = request.getParameter("pageNumber") == null ? 1 : Integer.parseInt(request.getParameter("pageNumber"));
		int recordCount = request.getParameter("records") == null ? 50 : Integer.parseInt(request.getParameter("records"));
		System.out.println("Compare data - "+compareData);
		DQQueryData leftData = compareData.getSourceData();
		DQQueryData rightData = compareData.getTargetData();
		
		if(leftData.getPageNumber() == 0 ) {
			leftData.setPageNumber(pageNumber);
		}
		if(leftData.getRecordCount() == 0) {
			leftData.setRecordCount(recordCount);
		}
		
		return dataService.executeQUery(dbType, leftData, rightData, compareData.getSrcTgtJoins(), compareData.getSrcTgtWheres());
	}
}
