package com.dq.service.metadata;

import java.util.List;

public class ColumnData {

	private String table;
	private List<Column> columns;
	
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
	
}
