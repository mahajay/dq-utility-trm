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
import org.jooq.SQLDialect;

public enum DB_TYPE {
			MYSQL("mysql", SQLDialect.MYSQL);
		private List<String> attributes = new ArrayList<>();
		private static Map<String, DB_TYPE> dbTypeMap = new HashMap<>();
		String dbName;
		SQLDialect dialect;
			DB_TYPE(String name, SQLDialect dialect){
				dbName = name;
				this.dialect = dialect;
			}
			
			public String getDbName() {
				return dbName;
			}
			
			public List<String> getAttributes(){
				return attributes;
			}
			
			public static DB_TYPE getDbType(String dbName) {
				return dbTypeMap.get(dbName);
			}
			
			public SQLDialect getJooqSqlDialect() {
				return dialect;
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
