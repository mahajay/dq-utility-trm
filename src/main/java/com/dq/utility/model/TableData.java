package com.dq.utility.model;

import java.util.List;

import com.dq.config.datasource.DB_TYPE;

public class TableData {

	DB_TYPE dbType; // mysql/oracle/MS-SQL etc
	
	List<String> targetTableList; // "TRAM_AM", "TRAM_PR" etc.. or "TRADEMARK", "TM_AMMENDMENT" etc. 
	List<String> sourceTableList; // "TRAM_AM", "TRAM_PR" etc.. or "TRADEMARK", "TM_AMMENDMENT" etc.
}
