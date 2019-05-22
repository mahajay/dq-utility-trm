package com.dq.service.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectField;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOnStep;
import org.jooq.TableLike;
import org.jooq.TableOnConditionStep;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dq.config.datasource.DB_TYPE;
import com.dq.config.datasource.DQTRAM_TYPE;
import com.dq.config.datasource.DQTRM_TYPE;
import com.dq.config.datasource.DQUtilityException;
import com.dq.config.datasource.DatabaseManager;
import com.dq.config.datasource.JOIN_TYPE;
import com.dq.service.metadata.Column;
import com.dq.service.metadata.Table;
import com.google.gson.Gson;

@Service
public class DataService {

	@Autowired
	DatabaseManager dbManager;
	
	public DQResultData executeQUery(String dbTypeName, String schemaName, 
			DQQueryData leftData, DQQueryData rightData, int pageNumber, int recordCount) throws SQLException, ClassNotFoundException {
		DataSource ds = dbManager.getDataSource(dbTypeName, schemaName);
		DB_TYPE dbType = DB_TYPE.getDbType(dbTypeName);
		if(ds == null) {
			throw new DQUtilityException("No datasource found for database "+dbTypeName+" and schema - "+schemaName);
		}
		DQResultData resultData = new DQResultData();
		Connection conn = ds.getConnection();
		DSLContext create = DSL.using(conn, dbType.getJooqSqlDialect());
		List<SelectField<?>> selectFields = generateSelectionFields(leftData.selectColumns, schemaName);
		int index = 1;
		TableOnConditionStep<?> tableOnConditionStep = null;
		for (Join joinColumn : leftData.getJoinColumns()) {
			if("DQTRAM".equals(schemaName)) {
				TableImpl<?> fromTble = DQTRAM_TYPE.valueOf(joinColumn.from.getTable()).getJooqTable();
				TableImpl<?> toTble = DQTRAM_TYPE.valueOf(joinColumn.to.getTable()).getJooqTable();
				if(tableOnConditionStep == null) {
					tableOnConditionStep = fromTble.join(toTble).on(buildJoinCondition(schemaName, joinColumn.from, joinColumn.to));	
				}else {
					tableOnConditionStep.innerJoin(fromTble.join(toTble).on(buildJoinCondition(schemaName, joinColumn.from, joinColumn.to)));
				}
			}
			if("DQTRM".equals(schemaName)) {
				TableImpl<?> fromTble = DQTRM_TYPE.valueOf(joinColumn.from.getTable()).getJooqTable();
				TableImpl<?> toTble = DQTRM_TYPE.valueOf(joinColumn.to.getTable()).getJooqTable();
				if(tableOnConditionStep == null) {
					tableOnConditionStep = fromTble.join(toTble).on(buildJoinCondition(schemaName, joinColumn.from, joinColumn.to));	
				}else {
					tableOnConditionStep.innerJoin(fromTble.join(toTble).on(buildJoinCondition(schemaName, joinColumn.from, joinColumn.to)));
				}
			}
		}
		
		
		SelectJoinStep<?> selectJoinStep = create.select(selectFields)
		.from(tableOnConditionStep);
		
		System.out.println("SQL - "+selectJoinStep.getSQL());
		//List<Field<?>> resultFileds = buildResultFields(schemaName, leftData.getSelectColumns());
		
		selectJoinStep.limit(recordCount).offset(pageNumber);
	
		int count = retrieveCount(tableOnConditionStep, create);
		resultData.setCount(count);
		if(rightData == null) {
			resultData.columnData = new HashMap<>();
			for (SelectField<?> record : selectFields) {
				if(!resultData.columnData.containsKey(record.getName())) {
					resultData.columnData.put(record.getName(), selectJoinStep.fetch(record.getName()));
				}
			}
			
			//.join(addJoins(schemaName, leftData));
			// If the rightData is null, just return the from here.
			return resultData;
		}
		/*
		List<SelectField<?>> rightSelectFields = generateSelectionFields(rightData.selectColumns, schemaName);
		List<TableLike<?>> rightTables = generateFromClause(rightData.tables, schemaName);
		SelectJoinStep<?> rightJoinStep = create.select(rightSelectFields)
				.from(rightTables);
		rightJoinStep.join(addJoins(schemaName, rightData));
			
		//List<Field<?>> resultFileds = buildResultFields(schemaName, leftData.getSelectColumns());
		selectJoinStep.leftJoin(rightJoinStep);*/
		
		return resultData;
	}

	private int retrieveCount(TableOnConditionStep<?> tableOnConditionStep, DSLContext create) {
				
		SelectConditionStep<Record1<Integer>> countStep = create.selectCount()
		.from(tableOnConditionStep).where(true);
		
		
		return countStep.fetchOne(0, Integer.class);
	}
	
	private List<Field<?>> buildResultFields(String schemaName, Map<String, List<Column>> selectColumns) {
		List<Field<?>> fieldList = new ArrayList<>();
		if("DQTRAM".equals(schemaName)) {
			
		}
		if("DQTRM".equals(schemaName)) {
			
		}
		return null;
	}

	private TableOnConditionStep<Record> addJoins(String schemaName, DQQueryData leftData) {
		TableOnConditionStep<Record> tableOnStep = null;
		for (Join join : leftData.joins) {
			if("DQTRAM".equals(schemaName)) {
				DQTRAM_TYPE dqType = DQTRAM_TYPE.valueOf(join.from.getTable());
				DQTRAM_TYPE toDqType = DQTRAM_TYPE.valueOf(join.to.getTable());
				org.jooq.Table<?> fromTable = dqType.getJooqTable();
				org.jooq.Table<?> toTable = toDqType.getJooqTable();
				
				Condition condition = buildJoinCondition(schemaName, join.from, join.to);
				tableOnStep = fromTable.join(toTable).on(condition);
				
			}
			if("DQTRM".equals(schemaName)) {
				DQTRM_TYPE dqType = DQTRM_TYPE.valueOf(join.from.getTable());
				DQTRM_TYPE toDqType = DQTRM_TYPE.valueOf(join.to.getTable());
				org.jooq.Table<?> fromTable = dqType.getJooqTable();
				org.jooq.Table<?> toTable = toDqType.getJooqTable();
				
				Condition condition = buildJoinCondition(schemaName, join.from, join.to);
				tableOnStep = fromTable.join(toTable).on(condition);
			}
		}
		return tableOnStep;
	}

	private Condition buildJoinCondition(String schemaName, Column from, Column to) {
		Condition condition = null;
		if(!from.getType().equals(to.getType())) {
			throw new DQUtilityException("Incorrect join condition from "+from+" to "+to);
		}
		if("DQTRAM".equals(schemaName)) {
			DQTRAM_TYPE fromDqType = DQTRAM_TYPE.valueOf(from.getTable());
			DQTRAM_TYPE toDqType = DQTRAM_TYPE.valueOf(to.getTable());
			if(from.getType().contains("String")) {
				condition = ((Field<String>)fromDqType.field(from.getName(), String.class))
								.eq((Field<String>)toDqType.field(to.getName(), String.class));
			}
			if(from.getType().contains("Integer")) {
				condition = ((Field<Integer>)fromDqType.field(from.getName(), Integer.class))
						.eq((Field<Integer>)toDqType.field(to.getName(), Integer.class));
			}
			if(from.getType().contains("Double")) {
				condition = ((Field<Double>)fromDqType.field(from.getName(), Double.class))
						.eq((Field<Double>)toDqType.field(to.getName(), Double.class));
			}
		}
		if("DQTRM".equals(schemaName)) {
			DQTRAM_TYPE fromDqType = DQTRAM_TYPE.valueOf(from.getTable());
			DQTRAM_TYPE toDqType = DQTRAM_TYPE.valueOf(to.getTable());
			if(from.getType().contains("String")) {
				condition = ((Field<String>)fromDqType.field(from.getName(), String.class))
								.eq((Field<String>)toDqType.field(to.getName(), String.class));
			}
			if(from.getType().contains("Integer")) {
				condition = ((Field<Integer>)fromDqType.field(from.getName(), Integer.class))
						.eq((Field<Integer>)toDqType.field(to.getName(), Integer.class));
			}
			if(from.getType().contains("Double")) {
				condition = ((Field<Double>)fromDqType.field(from.getName(), Double.class))
						.eq((Field<Double>)toDqType.field(to.getName(), Double.class));
			}	
		}
		return condition;
	}

	private List<Condition> genrateWhereCondition(List<Join> joins, String schemaName) {
		List<Condition> conditions = new ArrayList<>();
			for (Join join : joins) {
				Condition condition = DSL.trueCondition();
				//condition.
			}
		return conditions;
	}

	/*private List<TableLike<?>> generateFromClause(List<String> tables, String schemaName) {
		List<TableLike<?>> tableLikes = new ArrayList<>();
			for (String tableName : tables) {
				if("DQTRAM".equals(schemaName)) {
					DQTRAM_TYPE dqType = DQTRAM_TYPE.valueOf(tableName);
					dqType.getJooqTable().join(name)
					
					tableLikes.add(dqType.getJooqTable());
				}
				
				if("DQTRM".equals(schemaName)) {
					DQTRM_TYPE dqType = DQTRM_TYPE.valueOf(tableName);
					tableLikes.add(dqType.getJooqTable());
				}
			}
		return tableLikes;
	}*/

	private List<SelectField<?>> generateSelectionFields(Map<String, List<Column>> selectColumns, String schemaName) throws ClassNotFoundException {
		List<SelectField<?>> selectFieldList = new ArrayList<>();
		if(MapUtils.isNotEmpty(selectColumns)) {
			for (String tableName : selectColumns.keySet()) {
				if("DQTRAM".equals(schemaName)) {
					DQTRAM_TYPE dqType = DQTRAM_TYPE.valueOf(tableName);
					for (Column column : selectColumns.get(tableName)) {
						selectFieldList.add(dqType.field(column.getName(), Class.forName(column.getType())));
					}
				}		
			}
		}
		return selectFieldList;
	}
	
	/*public static void main(String[] args) throws SQLException, ClassNotFoundException {
	DataService service = new DataService();
	DQQueryData leftData = new DQQueryData();
	Map<String, List<Column>> columnMap = new HashedMap<>();
	List<Column> amColumns = new ArrayList<>();
	Column amSerNum = new Column();
	amSerNum.setName("AM_SER_NUM");
	amSerNum.setTable("TRAM_AM");
	amSerNum.setType("java.lang.Integer");
	amColumns.add(amSerNum );
	columnMap.put("TRAM_AM", amColumns );
	
	List<Column> wpColumns = new ArrayList<>();
	Column wpColumn = new Column();
	wpColumn.setName("WP_WIPO_CD");
	wpColumn.setTable("TRAM_WP");
	wpColumn.setType("java.lang.String");
	wpColumns.add(wpColumn );
	
	wpColumn = new Column();
	wpColumn.setName("WP_RSN");
	wpColumn.setTable("TRAM_WP");
	wpColumn.setType("java.lang.Integer");
	wpColumns.add(wpColumn );
	columnMap.put("TRAM_WP", wpColumns );
	
	List<String> tableList = new ArrayList<>();
	tableList.add("TRAM_AM");
	tableList.add("TRAM_WP");
	
	List<Join> joinColumnList = new ArrayList<>();
	Join join = new Join();
	Table tramWpTable = new Table();
	tramWpTable.setTableName("TRAM_WP");
	tramWpTable.setSchema("DQTRAM");
	join.setCondition(com.dq.config.datasource.Condition.EQ);
	join.setJoinType(JOIN_TYPE.INNER);
	join.setTo(amSerNum );
	join.setFrom(wpColumn );
	joinColumnList.add(join );
	
	leftData.setSelectColumns(columnMap );
	leftData.setTables(tableList);
	leftData.setJoinColumns(joinColumnList);
	
	Gson gson = new Gson();
	System.out.println("JSON - "+gson.toJson(leftData));
	
	service.executeQUery("mysql", "DQTRAM", leftData , null, 1, 10);
}*/
}
