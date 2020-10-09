package com.dq.service.metadata;

public class Column {

	private String name;
	private String type;
	private String table;
	private String schema;
	
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
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	@Override
	public String toString() {
		return "Column [name=" + name + ", type=" + type + ", table=" + table + ", schema=" + schema + "]";
	}
	
}
