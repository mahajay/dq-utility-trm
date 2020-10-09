package com.dq.service.data;

import java.util.List;
import java.util.Map;

import com.dq.service.metadata.Column;

public class DQQueryData {

	List<Column> selectColumns;
	List<String> tables;
	List<Join> joins;
	List<Where> wheres;
	String schema;
	int pageNumber;
	int recordCount;

	public List<Column> getSelectColumns() {
		return selectColumns;
	}
	public void setSelectColumns(List<Column> selectColumns) {
		this.selectColumns = selectColumns;
	}
	public List<String> getTables() {
		return tables;
	}
	public void setTables(List<String> tables) {
		this.tables = tables;
	}
	public List<Join> getJoinColumns() {
		return joins;
	}
	public void setJoinColumns(List<Join> joinColumns) {
		this.joins = joinColumns;
	}
	public List<Join> getJoins() {
		return joins;
	}
	public void setJoins(List<Join> joins) {
		this.joins = joins;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public List<Where> getWheres() {
		return wheres;
	}
	public void setWheres(List<Where> wheres) {
		this.wheres = wheres;
	}
	@Override
	public String toString() {
		return "DQQueryData [selectColumns=" + selectColumns + ", tables=" + tables + ", joins=" + joins + ", wheres="
				+ wheres + ", schema=" + schema + ", pageNumber=" + pageNumber + ", recordCount=" + recordCount + "]";
	}
}