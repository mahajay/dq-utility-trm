package com.dq.service.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.hamcrest.Matchers.anyOf;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.dq.boot.DQSpringBootApplication;
import com.dq.config.datasource.DQTRAM_TYPE;
import com.dq.config.datasource.DatabaseManager;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DQSpringBootApplication.class})
public class DataServiceTest {

	@Autowired
	DatabaseManager dbManager;
	
	@Ignore
	@Test
	public void testDynamicQuery() throws Exception {
		String schemaName = "DQTRAM";
		DataSource ds = dbManager.getDataSource("mysql", schemaName );
		assertNotNull(ds);
		Connection conn = ds.getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record record = create.selectOne()
		.from(DQTRAM_TYPE.TRAM_AM.getJooqTable()).fetchOne();
		System.out.println("Record - 1 "+record.get(1));
		System.out.println("Record - 2 "+record.get(2));
		System.out.println("Record - 3 "+record.get(3));
		System.out.println("Record - 4 "+record.get(4));
		System.out.println("Record - 5 "+record.get(5));
		
	}
}
