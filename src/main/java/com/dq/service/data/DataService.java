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
import org.jooq.Param;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.SelectField;
import org.jooq.SelectJoinStep;
import org.jooq.TableOnConditionStep;
import org.jooq.TableOnStep;
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
	
	private static final String DQTRAM = "DQTRAM";
	private static final String DQTRM = "DQTRM";
	
	private static final Map<String, Map<String, TableImpl<?>>> jooqTableMap = new HashMap<>(); 
	
	static {
		jooqTableMap.put(DQTRAM, new HashMap<>());
		for (DQTRAM_TYPE type : DQTRAM_TYPE.values()) {
			jooqTableMap.get(DQTRAM).put(type.name(), type.getJooqTable());
		}
		jooqTableMap.put(DQTRM, new HashMap<>());
		for (DQTRM_TYPE type : DQTRM_TYPE.values()) {
			jooqTableMap.get(DQTRM).put(type.name(), type.getJooqTable());
		}
	}
	
	public DQResultData executeQUery(String dbTypeName,
			DQQueryData leftData, DQQueryData rightData, List<Join> outsideJoins, 
			List<Where> outsideWhereList) throws SQLException, ClassNotFoundException {
		System.out.println("Request - "+leftData);
		DataSource ds = dbManager.getDataSource(dbTypeName, leftData.getSchema());
		DB_TYPE dbType = DB_TYPE.getDbType(dbTypeName);
		if(ds == null) {
			throw new DQUtilityException("No datasource found for database "+dbTypeName+" and schema - "+leftData.getSchema());
		}

		Connection conn = ds.getConnection();
		DSLContext create = DSL.using(conn, dbType.getJooqSqlDialect());
		List<SelectField<?>> selectLeftFields = generateSelectionFields(leftData.selectColumns, leftData.getSchema());
		TableOnConditionStep<?> tableOnConditionStep = generateFromTableOnConditionStep(leftData);
		
		SelectJoinStep<?> selectLeftJoinStep = create.select(selectLeftFields)
		.from(tableOnConditionStep);
		
		if(rightData == null) {
			
			return retrieveResultData(selectLeftFields, selectLeftJoinStep, selectLeftJoinStep, create,
					leftData.getRecordCount(), leftData.getPageNumber());
		}
		
		ds = dbManager.getDataSource(dbTypeName, rightData.getSchema());
		conn = ds.getConnection();
		create = DSL.using(conn, dbType.getJooqSqlDialect());
		
		List<SelectField<?>> selectRightFields = generateSelectionFields(rightData.selectColumns, rightData.getSchema());
		tableOnConditionStep = generateFromTableOnConditionStep(rightData);
		
		SelectJoinStep<?> selectRightJoinStep = create.select(selectRightFields)
		.from(tableOnConditionStep);

		org.jooq.Table<?> leftTable = selectLeftJoinStep.asTable();
		org.jooq.Table<?> rightTable = selectRightJoinStep.asTable();
		
		//leftTable.join(rightTable).on(((Field<String>)leftTable.field("")).eq((Field<String>) rightTable.field(""))))
		Condition outsideJoinCondition = DSL.trueCondition();
		TableOnStep<Record> joinTableStep = null;
		JOIN_TYPE selectedJoin = null;
		for (Join outJoin : outsideJoins) {
			selectedJoin = outJoin.joinType;
			if(joinTableStep == null) {
				joinTableStep = setupOutOuterJoin(leftTable, rightTable, joinTableStep, outJoin);	
			}
			
			outsideJoinCondition.and(buildJoinCondition(outJoin.from.getSchema(), outJoin.from, outJoin.to, outJoin.condition));
		}
		
		joinTableStep.on(outsideJoinCondition);
		Condition whereCondition = genrateWhereCondition(outsideWhereList);
		TableOnConditionStep<Record> finalStepWithCondition = joinTableStep.on(whereCondition);
		
		SelectJoinStep<?> finalJoinStep = create.select()
		.from(finalStepWithCondition);
		
		selectRightFields.addAll(selectLeftFields);
		
		DQResultData finalResult = retrieveResultData(selectRightFields, finalJoinStep, selectLeftJoinStep, create, leftData.getRecordCount(), leftData.getPageNumber());
		SelectJoinStep<?> innerJoin = selectLeftJoinStep.innerJoin(selectRightJoinStep).on(DSL.trueCondition());
		/*int srcCnt = retrieveCount(selectLeftJoinStep, create);
		int trgtCnt = retrieveCount(selectRightJoinStep, create);
		int innerCnt = retrieveCount(innerJoin, create);
		if(selectedJoin == JOIN_TYPE.INNER) {
			finalResult.setCount(innerCnt);
		}
		if(selectedJoin == JOIN_TYPE.LEFT_OUTER) {
			finalResult.setCount(srcCnt - innerCnt);
		}
		if(selectedJoin == JOIN_TYPE.RIGHT_OUTER) {
			finalResult.setCount(trgtCnt - innerCnt);
		}*/
		return finalResult;
	}

	private TableOnStep<Record> setupOutOuterJoin(org.jooq.Table<?> leftTable, org.jooq.Table<?> rightTable,
			TableOnStep<Record> joinTableStep, Join outJoin) {
		switch (outJoin.joinType) {
		case INNER:
			joinTableStep = leftTable.join(rightTable);
			break;
		case LEFT :
			joinTableStep = leftTable.leftJoin(rightTable);
			break;
		case LEFT_OUTER:
			joinTableStep = leftTable.leftOuterJoin(rightTable);
			break;
		case RIGHT:
			joinTableStep = leftTable.rightJoin(rightTable);
			break;
		case RIGHT_OUTER:
			joinTableStep = leftTable.rightOuterJoin(rightTable);
			break;
		case FULL_OUTER:
			joinTableStep = leftTable.fullOuterJoin(rightTable);
			break;
			
		default:
			break;
		}
		return joinTableStep;
	}

	private DQResultData retrieveResultData(List<SelectField<?>> selectLeftFields,
			SelectJoinStep<?> selectJoinStep, SelectJoinStep<?> selectSrcJoinStep, DSLContext create, int recCount, int page) {
		System.out.println("Final - Query - "+selectJoinStep);
		DQResultData resultData = new DQResultData();
		int count = retrieveCount(selectSrcJoinStep, create);
		System.out.println("Total Retrieved records - "+count);
		resultData.setCount(count );
		selectJoinStep.limit(recCount).offset(page);
		resultData.columnData = new HashMap<>();
		for (SelectField<?> record : selectLeftFields) {
			if(!resultData.columnData.containsKey(record.getName())) {
				resultData.columnData.put(record.getName(), selectJoinStep.fetch(record.getName()));
			}
		}
		
		return resultData;
	}

	private TableOnConditionStep<?> generateFromTableOnConditionStep(DQQueryData queryData) {
		TableOnConditionStep<?> tableOnConditionStep = null;

		for (Join joinColumn : queryData.getJoinColumns()) {
			String fromTable = joinColumn.from.getTable().toUpperCase();
			String toTable = joinColumn.to.getTable().toUpperCase();
			String schemaName = queryData.schema.toUpperCase();
			
			TableImpl<?> fromTble = jooqTableMap.get(schemaName).get(fromTable);
			TableImpl<?> toTble = jooqTableMap.get(schemaName).get(toTable);
			
			if (tableOnConditionStep == null) {
				tableOnConditionStep = fromTble.join(toTble)
						.on(buildJoinCondition(schemaName, joinColumn.from, joinColumn.to, com.dq.config.datasource.Condition.EQ));
			} else {
				tableOnConditionStep.innerJoin(
						fromTble.join(toTble).on(buildJoinCondition(schemaName, joinColumn.from, joinColumn.to, com.dq.config.datasource.Condition.EQ)));
			}
		}
		return tableOnConditionStep;
	}

	private int retrieveCount(SelectJoinStep<?> finalJoinStep, DSLContext create) {
		System.out.println("Count - Query - "+finalJoinStep);
			return	create.fetchCount(finalJoinStep);
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
			TableImpl<?> fromTable = jooqTableMap.get(schemaName).get(join.from.getTable());
			TableImpl<?> toTable = jooqTableMap.get(schemaName).get(join.to.getTable());
			Condition condition = buildJoinCondition(schemaName, join.from, join.to, com.dq.config.datasource.Condition.EQ);
			tableOnStep = fromTable.join(toTable).on(condition);
			
			/*if("DQTRAM".equals(schemaName)) {
				DQTRAM_TYPE dqType = DQTRAM_TYPE.valueOf(join.from.getTable());
				DQTRAM_TYPE toDqType = DQTRAM_TYPE.valueOf(join.to.getTable());
				org.jooq.Table<?> fromTable = dqType.getJooqTable();
				org.jooq.Table<?> toTable = toDqType.getJooqTable();
				
				Condition condition = buildJoinCondition(schemaName, join.from, join.to, com.dq.config.datasource.Condition.EQ);
				tableOnStep = fromTable.join(toTable).on(condition);
				
			}
			if("DQTRM".equals(schemaName)) {
				DQTRM_TYPE dqType = DQTRM_TYPE.valueOf(join.from.getTable());
				DQTRM_TYPE toDqType = DQTRM_TYPE.valueOf(join.to.getTable());
				org.jooq.Table<?> fromTable = dqType.getJooqTable();
				org.jooq.Table<?> toTable = toDqType.getJooqTable();
				
				Condition condition = buildJoinCondition(schemaName, join.from, join.to, com.dq.config.datasource.Condition.EQ);
				tableOnStep = fromTable.join(toTable).on(condition);
			}*/
		}
		return tableOnStep;
	}

	private Condition buildJoinCondition(String schemaName, Column from, Column to, com.dq.config.datasource.Condition joinCondition) {
		Condition condition = DSL.trueCondition();
		/*if(!from.getType().equals(to.getType())) {
			throw new DQUtilityException("Incorrect join condition from "+from+" to "+to);
		}*/
		TableImpl<?> fromTable = jooqTableMap.get(from.getSchema()).get(from.getTable());
		TableImpl<?> toTable = jooqTableMap.get(to.getSchema()).get(to.getTable());
		Field<String> fromStringField = null;
		Field<String> toStringField = null;
		Field<Integer> fromIntField = null;
		Field<Integer> toIntField = null;
		if(joinCondition == com.dq.config.datasource.Condition.EQ) {
			if(!from.getType().equals(to.getType())) {
				if(from.getType().contains("String")) {
					fromIntField = ((Field<String>)fromTable.field(from.getName(), String.class)).cast(Integer.class);
					toIntField = (Field<Integer>)toTable.field(to.getName(), Integer.class);
				}else if(to.getType().contains("String")) {
					toIntField = ((Field<String>)toTable.field(to.getName(), String.class)).cast(Integer.class);
					fromIntField = (Field<Integer>)fromTable.field(from.getName(), Integer.class);
				}
				condition = fromIntField.eq(toIntField);
			}else {
				if(from.getType().contains("String")) {
					condition = ((Field<String>)fromTable.field(from.getName(), String.class))
									.eq((Field<String>)toTable.field(to.getName(), String.class));
				}
				if(from.getType().contains("Integer")) {
					condition = ((Field<Integer>)fromTable.field(from.getName(), Integer.class))
							.eq((Field<Integer>)toTable.field(to.getName(), Integer.class));
				}
				if(from.getType().contains("Double")) {
					condition = ((Field<Double>)fromTable.field(from.getName(), Double.class))
							.eq((Field<Double>)toTable.field(to.getName(), Double.class));
				}
			}	
		}
		
		
		/*if(joinCondition == com.dq.config.datasource.Condition.EQ) {
			if(from.getType().contains("String")) {
				condition = ((Field<String>)fromTable.field(from.getName(), String.class))
								.eq((Field<String>)toTable.field(to.getName(), String.class));
			}
			if(from.getType().contains("Integer")) {
				condition = ((Field<Integer>)fromTable.field(from.getName(), Integer.class))
						.eq((Field<Integer>)toTable.field(to.getName(), Integer.class));
			}
			if(from.getType().contains("Double")) {
				condition = ((Field<Double>)fromTable.field(from.getName(), Double.class))
						.eq((Field<Double>)toTable.field(to.getName(), Double.class));
			}
		}*/
		
		return condition;
	}

	private Condition genrateWhereCondition(List<Where> outsideWhereList) {
		Condition outsideWhereCondition = DSL.trueCondition();
		for (Where where : outsideWhereList) {
			TableImpl<?> table = jooqTableMap.get(where.column.getSchema()).get(where.column.getTable());
			String whereFieldName = where.getColumn().getName();
			String value = where.value;
			switch (where.condition) {
			case EQ:
				if(where.column.getType().contains("Integer")) {
					outsideWhereCondition.and(((Field<Integer>)table.field(whereFieldName, Integer.class))
							.eq(Integer.parseInt(value)));	
				}
				if(where.column.getType().contains("Double")) {
					outsideWhereCondition.and(((Field<Double>)table.field(whereFieldName, Double.class))
							.eq(Double.parseDouble(value)));
				}
				else {
					outsideWhereCondition.and(((Field<String>)table.field(whereFieldName, String.class))
							.eq(value));
				}
				
				break;

			case NEQ:
				if(where.column.getType().contains("Integer")) {
					outsideWhereCondition.and(((Field<Integer>)table.field(whereFieldName, Integer.class))
							.ne(Integer.parseInt(value)));	
				}
				if(where.column.getType().contains("Double")) {
					outsideWhereCondition.and(((Field<Double>)table.field(whereFieldName, Double.class))
							.ne(Double.parseDouble(value)));
				}
				else {
					outsideWhereCondition.and(((Field<String>)table.field(whereFieldName, String.class))
							.ne(value));
				}
				
				break;

			case GT:
				if(where.column.getType().contains("Integer")) {
					outsideWhereCondition.and(((Field<Integer>)table.field(whereFieldName, Integer.class))
							.gt(Integer.parseInt(value)));	
				}
				if(where.column.getType().contains("Double")) {
					outsideWhereCondition.and(((Field<Double>)table.field(whereFieldName, Double.class))
							.gt(Double.parseDouble(value)));
				}
								
				break;
				
			case GE:
				if(where.column.getType().contains("Integer")) {
					outsideWhereCondition.and(((Field<Integer>)table.field(whereFieldName, Integer.class))
							.ge(Integer.parseInt(value)));	
				}
				if(where.column.getType().contains("Double")) {
					outsideWhereCondition.and(((Field<Double>)table.field(whereFieldName, Double.class))
							.ge(Double.parseDouble(value)));
				}				
				break;

			case LT:
				if(where.column.getType().contains("Integer")) {
					outsideWhereCondition.and(((Field<Integer>)table.field(whereFieldName, Integer.class))
							.lt(Integer.parseInt(value)));	
				}
				if(where.column.getType().contains("Double")) {
					outsideWhereCondition.and(((Field<Double>)table.field(whereFieldName, Double.class))
							.lt(Double.parseDouble(value)));
				}
				
				break;
			case LE:
				if(where.column.getType().contains("Integer")) {
					outsideWhereCondition.and(((Field<Integer>)table.field(whereFieldName, Integer.class))
							.le(Integer.parseInt(value)));	
				}
				if(where.column.getType().contains("Double")) {
					outsideWhereCondition.and(((Field<Double>)table.field(whereFieldName, Double.class))
							.le(Double.parseDouble(value)));
				}
				
				break;
			case IS:
				
				outsideWhereCondition.and(((Field<String>)table.field(whereFieldName, String.class))
						.eq(value));
				
				break;
			default:
				break;
			}
		}
		return outsideWhereCondition;
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

	private List<SelectField<?>> generateSelectionFields(List<Column> selectColumns, String schemaName)
			throws ClassNotFoundException {
		List<SelectField<?>> selectFieldList = new ArrayList<>();

		for (Column column : selectColumns) {
			TableImpl<?> table = jooqTableMap.get(column.getSchema()).get(column.getTable());
			selectFieldList.add(table.field(column.getName(), Class.forName(column.getType())));
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
	amSerNum.setSchema("DQTRAM");
	amColumns.add(amSerNum );
	columnMap.put("TRAM_AM", amColumns );
	
	List<Column> wpColumns = new ArrayList<>();
	Column wpColumn = new Column();
	wpColumn.setName("WP_WIPO_CD");
	wpColumn.setTable("TRAM_WP");
	wpColumn.setType("java.lang.String");
	wpColumn.setSchema("DQTRAM");
	wpColumns.add(wpColumn );
	
	wpColumn = new Column();
	wpColumn.setName("WP_RSN");
	wpColumn.setTable("TRAM_WP");
	wpColumn.setType("java.lang.Integer");
	wpColumns.add(wpColumn );
	wpColumn.setSchema("DQTRAM");
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
	leftData.setSchema("DQTRAM");
	
	Gson gson = new Gson();
	System.out.println("JSON - "+gson.toJson(leftData));
	
	//service.executeQUery("mysql", "DQTRAM", leftData , null, 1, 10,null,null,null);
	service.executeQUery("mysql", leftData, null, null, null, null);
}*/
	
}
