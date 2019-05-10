package com.dq.config.datasource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public enum DB_TYPE {
			MYSQL("mysql");
			// RDS("rds")
		private List<String> attributes = new ArrayList<>();
		private static Map<String, DB_TYPE> dbTypeMap = new HashMap<>();
	
			DB_TYPE(String name){
				dbName = name;
			}
			String dbName;
			public String getDbName() {
				return dbName;
			}
			
			public List<String> getAttributes(){
				return attributes;
			}
			
			public static DB_TYPE getDbType(String dbName) {
				return dbTypeMap.get(dbName);
			}
			
			static {
				List<String> fileAttributes = null;
				try {
					fileAttributes = FileUtils.readLines(new File(DB_TYPE.class.getClassLoader().getResource("datasource-cfg.properties").getFile()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				for (DB_TYPE dbType : DB_TYPE.values()) {
					dbTypeMap.put(dbType.getDbName(), dbType);
					for (String attr : fileAttributes) {
						if(attr.toLowerCase().contains(dbType.getDbName().toLowerCase())) {
							dbType.attributes.add(attr);
						}
					}
				} 
			}
}
