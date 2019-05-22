package com.dq.config.datasource;

import static com.dq.DQTRAM.model.Dqtram.DQTRAM;

import org.jooq.Field;
import org.jooq.impl.TableImpl;
import com.dq.DQTRAM.model.tables.TramAm;
import com.dq.DQTRAM.model.tables.TramPr;
import com.dq.DQTRAM.model.tables.TramPx;
import com.dq.DQTRAM.model.tables.TramWp;

public enum DQTRAM_TYPE {
	
	TRAM_AM (DQTRAM.TRAM_AM),
	TRAM_PR(DQTRAM.TRAM_PR),
	TRAM_PX (DQTRAM.TRAM_PX),
	TRAM_WP(DQTRAM.TRAM_WP);
	
	private DQTRAM_TYPE(TableImpl<?> table) {
		jooqTable = table;
	}
	
	TableImpl<?> jooqTable;
	
	public TableImpl<?> getJooqTable(){
		return jooqTable;
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> Field<T> field(String fieldName, Class<T> type){
		return (Field<T>) jooqTable.field(fieldName.toUpperCase());
	}
}
