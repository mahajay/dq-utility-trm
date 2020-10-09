package com.dq.service.data;

import com.dq.config.datasource.Condition;
import com.dq.service.metadata.Column;

public class Where {

	Column column;
	Condition condition;
	String value;
	
	public Column getColumn() {
		return column;
	}
	public void setColumn(Column column) {
		this.column = column;
	}
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Where [column=" + column + ", condition=" + condition + ", value=" + value + "]";
	}
}
