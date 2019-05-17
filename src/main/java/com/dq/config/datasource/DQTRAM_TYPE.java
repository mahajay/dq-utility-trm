package com.dq.config.datasource;

import org.jooq.impl.TableImpl;
import com.dq.DQTRAM.model.tables.TramAm;
import com.dq.DQTRAM.model.tables.TramPr;
import com.dq.DQTRAM.model.tables.TramPx;
import com.dq.DQTRAM.model.tables.TramWp;

public enum DQTRAM_TYPE {
	
	TRAM_AM (new TramAm()),
	TRAM_PR(new TramPr()),
	TRAM_PX (new TramPx()),
	TRAM_WP(new TramWp());
	
	private DQTRAM_TYPE(TableImpl<?> table) {
		jooqTable = table;
	}
	
	TableImpl<?> jooqTable;
	
	public TableImpl<?> getJooqTable(){
		return jooqTable;
	}
}
