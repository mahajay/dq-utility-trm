package com.dq.utility.model;

import com.dq.config.datasource.DB_TYPE;

public class MetaData {
	DB_TYPE dbType;
	String schema;
	String table;
	String[] columns;
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
	public String[] getColumns() {
		return columns;
	}
	public void setColumns(String[] columns) {
		this.columns = columns;
	}
	public String[] getJoinColumns() {
		return joinColumns;
	}
	public void setJoinColumns(String[] joinColumns) {
		this.joinColumns = joinColumns;
	}
	
	
}
