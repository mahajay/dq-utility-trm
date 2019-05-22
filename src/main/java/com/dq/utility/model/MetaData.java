package com.dq.utility.model;

import com.dq.config.datasource.DB_TYPE;
import com.dq.service.metadata.Column;

public class MetaData {
	DB_TYPE dbType;
	String schema;
	String table;
	Column[] columns;
	String[] joinColumns;
	
	public DB_TYPE getDbType() {
		return dbType;
	}
	public void setDbType(DB_TYPE dbType) {
		this.dbType = dbType;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public Column[] getColumns() {
		return columns;
	}
	public void setColumns(Column[] columns) {
		this.columns = columns;
	}
	public String[] getJoinColumns() {
		return joinColumns;
	}
	public void setJoinColumns(String[] joinColumns) {
		this.joinColumns = joinColumns;
	}
	
	
}
