package com.dq.service.metadata;

public class Column {

	private String name;
	private String type;
	private String table;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	
	@Override
	public String toString() {
		return "Column [name=" + name + ", type=" + type + ", table=" + table + "]";
	}	
}
