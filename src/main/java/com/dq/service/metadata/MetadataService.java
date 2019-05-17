package com.dq.service.metadata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import com.dq.config.datasource.DB_TYPE;
import com.dq.config.datasource.DatabaseManager;

@Service
public class MetadataService {
	
	@Autowired
	DatabaseManager dbManager;
	
	public List<String> getSupprotedDBNames(){
		List<String> dbList = new ArrayList<>();
		for (DB_TYPE dbType : DB_TYPE.values()) {
			dbList.add(dbType.getDbName());
		}
		return dbList;
	}
	
	public List<String> getDbConnectionAttributes(String dbName){
		DB_TYPE dbType = DB_TYPE.getDbType(dbName);
		if(dbType == null) {
			return new ArrayList<>();
		}
		return dbType.getAttributes();
	}
	
	public List<Metadata> getDBSchemaList(String dbType) throws SQLException {
		List<Metadata> schemaList = new ArrayList<>();
		DataSource ds = dbManager.getDataSource(dbType, "");
		Connection con = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			rs = con.getMetaData().getCatalogs();
			while (rs.next()) {
				Metadata data = new Metadata();
				data.setType("schema");
				data.setValue(rs.getString("TABLE_CAT"));
				schemaList.add(data);
			} 
		} finally {
			try {
				if(rs != null)  rs.close();
				if(con!=null) con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return schemaList;
	}
	
	public List<String> getTableNames(String dbType, String schemaName) throws SQLException{
		List<String> tableNames = new ArrayList<>();
		DataSource ds = dbManager.getDataSource(dbType, schemaName);
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbSchemaSelectionStr = "";
		String tableSelectionStr = "";
		try {
			con = ds.getConnection();
			if(dbType.equals(DB_TYPE.MYSQL.getDbName())) {
				dbSchemaSelectionStr = "use "+schemaName;
				tableSelectionStr = "SHOW TABLES";
			}
			/*pstmt = con.prepareStatement(dbSchemaSelectionStr);
			pstmt.executeQuery();*/
			rs = con.createStatement().executeQuery(tableSelectionStr);
			while(rs.next()) {
				tableNames.add(rs.getString(1));
			}
			
		} finally {
			if(rs != null) rs.close();
			if(con != null) con.close();
		}
		return tableNames;
	}
	
	public List<String> getColumnNames(String dbType, String schemaName, String tableName) throws SQLException{
		List<String> columnNames = new ArrayList<>();
		DataSource ds = dbManager.getDataSource(dbType, schemaName);
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbSchemaSelectionStr = "";
		String columnSelectionStr = "";
		try {
			con = ds.getConnection();
			if(dbType.equals(DB_TYPE.MYSQL.getDbName())) {
				dbSchemaSelectionStr = "use "+schemaName;
				columnSelectionStr = "SHOW COLUMNS FROM %s";
			}
			pstmt = con.prepareStatement(dbSchemaSelectionStr);
			pstmt.executeQuery();
			rs = con.createStatement().executeQuery(String.format(columnSelectionStr, tableName));
			while(rs.next()) {
				columnNames.add(rs.getString(1));
			}
			
		} finally {
			if(rs != null) rs.close();
			if(con != null) con.close();
		}
		return columnNames;
	}
}
