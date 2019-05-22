package com.dq.service.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static com.dq.DQTRAM.model.Dqtram.DQTRAM;
import static org.hamcrest.Matchers.anyOf;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.SelectField;
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
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DQSpringBootApplication.class})
public class DataServiceTest {

	@Autowired
	DatabaseManager dbManager;
	
	@Autowired
	DataService service;
	
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
	
	@Ignore
	@Test
	public void testSelectFieldQuery() throws SQLException {
		String schemaName = "DQTRAM";
		DataSource ds = dbManager.getDataSource("mysql", schemaName );
		assertNotNull(ds);
		Connection conn = ds.getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record rec =  create.selectOne()
		.from(DQTRAM.TRAM_AM)
		.join(DQTRAM.TRAM_WP).on(DQTRAM_TYPE.TRAM_WP.field("wp_ser_num", String.class)
				.eq(DQTRAM_TYPE.TRAM_AM.field("am_ser_num", String.class)))
		.fetchAny();
		
		Row row = rec.valuesRow();
		for (Field<?> field : row.fields()) {
			System.out.println(field);
		}
	}
	
	@Test
	public void testGetData() throws Exception {
		String request = "{\"selectColumns\":{\"TRAM_WP\":[{\"name\":\"WP_WIPO_CD\",\"type\":\"java.lang.String\",\"table\":\"TRAM_WP\"},{\"name\":\"WP_RSN\",\"type\":\"java.lang.Integer\",\"table\":\"TRAM_WP\"}],\"TRAM_AM\":[{\"name\":\"AM_SER_NUM\",\"type\":\"java.lang.Integer\",\"table\":\"TRAM_AM\"}]},\"tables\":[\"TRAM_AM\",\"TRAM_WP\"],\"joins\":[{\"joinType\":\"INNER\",\"from\":{\"name\":\"WP_SER_NUM\",\"type\":\"java.lang.Integer\",\"table\":\"TRAM_WP\"},\"to\":{\"name\":\"AM_SER_NUM\",\"type\":\"java.lang.Integer\",\"table\":\"TRAM_AM\"},\"condition\":\"EQ\"}]}";
		Gson gson = new Gson();
		DQQueryData leftData = gson.fromJson(request, DQQueryData.class);
		DQResultData result = service.executeQUery("mysql", "DQTRAM", leftData, null, 1, 50);
		System.out.println("Result count - "+result.getCount());
		System.out.println("Result Data ----------- ");
		for (String columnName : result.getColumnData().keySet()) {
			System.out.println("Column - "+columnName);
			List<?> columnList = result.getColumnData().get(columnName);
			for (Object obj : columnList) {
				System.out.println(obj);
			}
		}
		
	}
}
