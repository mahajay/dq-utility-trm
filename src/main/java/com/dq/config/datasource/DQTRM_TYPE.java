package com.dq.config.datasource;

import static com.dq.DQTRM.model.Dqtrm.DQTRM;

import org.jooq.Field;
import org.jooq.impl.TableImpl;

public enum DQTRM_TYPE {

	TM_ADDITIONAL_STATEMENT(DQTRM.TM_ADDITIONAL_STATEMENT),
	TM_AMENDMENT(DQTRM.TM_AMENDMENT),
	TM_DESIGN_ELEMENT (DQTRM.TM_DESIGN_ELEMENT),
	TM_DRAWING (DQTRM.TM_DRAWING),
	TM_EMPLOYEE_ASSIGNMENT (DQTRM.TM_EMPLOYEE_ASSIGNMENT),
	TM_MARK_TYPE (DQTRM.TM_MARK_TYPE),
	TM_PRIOR_REGISTRATION (DQTRM.TM_PRIOR_REGISTRATION),
	TM_PUBLICATION (DQTRM.TM_PUBLICATION),
	TM_RELATIONSHIP (DQTRM.TM_RELATIONSHIP),
	TM_RENEWAL (DQTRM.TM_RENEWAL),
	TRADEMARK (DQTRM.TRADEMARK);
	
	private DQTRM_TYPE(TableImpl<?> table) {
		jooqTable = table;
	}
	
	TableImpl<?> jooqTable;
	
	public TableImpl<?> getJooqTable(){
		return jooqTable;
	}

	@SuppressWarnings("unchecked")
	public <T> Field<T> field(String fieldName, Class<T> clazz) {
		return (Field<T>) jooqTable.field(fieldName.toUpperCase());
	}
}
