package com.dq.service.data;

import java.util.List;
import java.util.Map;

public class DQResultData {

	Map<String, List<?>> columnData;
	int count;
	
	public Map<String, List<?>> getColumnData() {
		return columnData;
	}
	public void setColumnData(Map<String, List<?>> columnData) {
		this.columnData = columnData;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
