package com.dq.config.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.stereotype.Component;



@Component
@PropertySources({ @PropertySource("classpath:datasource-cfg.properties") })
public class DatabaseManager {

	private static final String SCHEMA = "schema.";
	private static final String PASSWORD = "password.";
	private static final String USERNAME = "username.";
	private static final String URL = "url.";
	private static final String DRIVER_CLASS = "driver-class.";
	private static final Map<String, DataSource> datasourceMap = new HashMap<>();

	@Resource
	public Environment env;
	
	@Autowired
    @Bean(name = "dataSource")
    public DataSource getDataSource(String dbType, String schemaName) {
		String dsKey = dbType + "_" + schemaName;
		DataSource ds = datasourceMap.get(dsKey);
		if(ds == null) {
			ds = generateDatasource(DB_TYPE.getDbType(dbType), schemaName);
			datasourceMap.put(dsKey, ds);
		}
		
		return ds;
	}
	
	@Bean(name="dbType")
	public String getDbType() {
		return DB_TYPE.MYSQL.getDbName();
	}
	@Bean(name="dbType")
	public String getSchemaName() {
		return "";
	}

	/**
     * Return transaction manager
	 * @param schemaName 
     * 
     * @return 
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager getTransactionManager(String dbType, String schemaName) {
        return new DataSourceTransactionManager(getDataSource(dbType, schemaName));
    }

    @Bean(name = "transactionAwareDataSource")
    public TransactionAwareDataSourceProxy getTransactionAwareDataSource(String dbType, String schemaName) {
        return new TransactionAwareDataSourceProxy(getDataSource(dbType, schemaName));
    }

    /**
     * Return connection provider
     * @param schemaName 
     * 
     * @return 
     */
    @Bean(name = "connectionProvider")
    public DataSourceConnectionProvider getConnectionProvider(String dbType, String schemaName) {
        return new DataSourceConnectionProvider(getTransactionAwareDataSource(dbType, schemaName));
    }

    /**
     * Return exception translator
     * 
     * @return 
     */
    @Bean(name = "exceptionTranslator")
    public ExceptionTranslator getExceptionTranslator() {
        return new ExceptionTranslator();
    }

    /**
     * Returns the DSL context configuration
     * @param schemaName 
     *
     * @return 
     */
    @Bean(name = "dslConfig")
    public org.jooq.Configuration getDslConfig(String dbType, String schemaName) {
        DefaultConfiguration config = new DefaultConfiguration();
        config.setSQLDialect(SQLDialect.FIREBIRD);
        config.setConnectionProvider(getConnectionProvider(dbType, schemaName));
        DefaultExecuteListenerProvider listenerProvider = 
          new DefaultExecuteListenerProvider(getExceptionTranslator());
        config.setExecuteListenerProvider(listenerProvider);
        return config;
    }

    /**
     * Return DSL context
     * @param schemaName 
     *
     * @return 
     */
    @Bean(name = "dsl")
    public DSLContext getDsl(String dbType, String schemaName) {
        org.jooq.Configuration config = this.getDslConfig(dbType, schemaName);
        return new DefaultDSLContext(config);
    }
    
    private DataSource generateDatasource(DB_TYPE dbType, String schemaName) {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		
		switch (dbType) {
		case MYSQL:
			ds.setDriverClassName(env.getProperty(DRIVER_CLASS+dbType.getDbName()));
			ds.setUrl(env.getProperty(URL+dbType.getDbName())+ "/" +schemaName);
			ds.setUsername(env.getProperty(USERNAME+dbType.getDbName()));
			ds.setPassword(env.getProperty(PASSWORD+dbType.getDbName()));
			//ds.setSchema(schemaName);
			break;

		default:
			break;
		}
		return ds;
	}
}
