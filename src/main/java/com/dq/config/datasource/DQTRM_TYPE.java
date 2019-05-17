package com.dq.config.datasource;

import org.jooq.impl.TableImpl;

import com.dq.DQTRM.model.tables.TmAdditionalStatement;
import com.dq.DQTRM.model.tables.TmAmendment;
import com.dq.DQTRM.model.tables.TmDesignElement;
import com.dq.DQTRM.model.tables.TmDrawing;
import com.dq.DQTRM.model.tables.TmEmployeeAssignment;
import com.dq.DQTRM.model.tables.TmMarkType;
import com.dq.DQTRM.model.tables.TmPriorRegistration;
import com.dq.DQTRM.model.tables.TmPublication;
import com.dq.DQTRM.model.tables.TmRelationship;
import com.dq.DQTRM.model.tables.TmRenewal;
import com.dq.DQTRM.model.tables.Trademark;

public enum DQTRM_TYPE {

	TM_ADDITIONAL_STATEMENT(new TmAdditionalStatement()),
	TM_AMENDMENT(new TmAmendment()),
	TM_DESIGN_ELEMENT (new TmDesignElement()),
	TM_DRAWING (new TmDrawing()),
	TM_EMPLOYEE_ASSIGNMENT (new TmEmployeeAssignment()),
	TM_MARK_TYPE (new TmMarkType()),
	TM_PRIOR_REGISTRATION (new TmPriorRegistration()),
	TM_PUBLICATION (new TmPublication()),
	TM_RELATIONSHIP (new TmRelationship()),
	TM_RENEWAL (new TmRenewal()),
	TRADEMARK (new Trademark());
	
	private DQTRM_TYPE(TableImpl<?> table) {
		jooqTable = table;
	}
	
	TableImpl<?> jooqTable;
	
	public TableImpl<?> getJooqTable(){
		return jooqTable;
	}
}
