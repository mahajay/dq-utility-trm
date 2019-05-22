package com.dq.service.data;

import java.util.List;
import java.util.Map;

import com.dq.service.metadata.Column;

public class DQQueryData {

	Map<String, List<Column>> selectColumns;
	List<String> tables;
	List<Join> joins;

	public Map<String, List<Column>> getSelectColumns() {
		return selectColumns;
	}
	public void setSelectColumns(Map<String, List<Column>> selectColumns) {
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
	
	
}