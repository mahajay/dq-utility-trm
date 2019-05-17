package com.dq.service.metadata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.Matchers.anyOf;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.dq.boot.DQSpringBootApplication;
import com.dq.config.datasource.DB_TYPE;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DQSpringBootApplication.class})
public class MetaServiceTest {

	@Autowired
	MetadataService service;
	
	@Test
	public void testGetSupportedDbNames() {
		List<String> supportedDbs = service.getSupprotedDBNames();
		for (String dbName : supportedDbs) {
			assertSame(DB_TYPE.getDbType(dbName), DB_TYPE.MYSQL);
		}
	}
	
	@Test
	public void testGetAttributes() {
		List<String> attributes = service.getDbConnectionAttributes(DB_TYPE.MYSQL.getDbName());
		for (String attribute : attributes) {
			assertNotNull(attribute);
			System.out.println("attribute - "+attribute);
		}
	}
	
	@Test
	public void testGetDBSchemaList() throws SQLException {
		List<Metadata> metaDataLst = service.getDBSchemaList(DB_TYPE.getDbType("mysql").getDbName());
		assertNotNull(metaDataLst);
		assertTrue(metaDataLst.size() > 0);
		List<String> schemaNames = new ArrayList<>();
		for (Metadata metadata : metaDataLst) {
			schemaNames.add(metadata.getValue());
		}
	}
	
	@Test
	public void testGetTableNames() throws SQLException {
		List<String> tables = service.getTableNames("mysql", "DQTRAM");
		assertNotNull(tables);
		assertTrue(tables.size() > 0);
		for (String table : tables) {
			System.out.println("Table name from the schema - "+table);
		}
	}
	
	@Test
	public void testGetColumnNames()  throws SQLException  {
		String schemaName = "DQDEV";
		String dbType = "mysql";
		List<String> tables = service.getTableNames(dbType, schemaName);
		assertNotNull(tables);
		assertTrue(tables.size() > 0);
		String tableName = tables.get(0);
		List<String> columns = service.getColumnNames(dbType, schemaName, tableName);
		assertNotNull(columns);
		assertTrue(columns.size() > 0);
	}
}
