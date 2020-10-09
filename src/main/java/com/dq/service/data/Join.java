package com.dq.service.data;

import com.dq.config.datasource.Condition;
import com.dq.config.datasource.JOIN_TYPE;
import com.dq.service.metadata.Column;
import com.dq.service.metadata.Table;

public class Join {

	JOIN_TYPE joinType;
	Column from;
	Column to;
	Condition condition;
	
	public JOIN_TYPE getJoinType() {
		return joinType;
	}
	public void setJoinType(JOIN_TYPE joinType) {
		this.joinType = joinType;
	}
	public Column getFrom() {
		return from;
	}
	public void setFrom(Column from) {
		this.from = from;
	}
	public Column getTo() {
		return to;
	}
	public void setTo(Column to) {
		this.to = to;
	}
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	@Override
	public String toString() {
		return "Join [joinType=" + joinType + ", from=" + from + ", to=" + to + ", condition=" + condition + "]";
	}
	
}
