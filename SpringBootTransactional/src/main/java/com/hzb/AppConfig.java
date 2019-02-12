package com.hzb;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan({ "com.hzb" })
public class AppConfig {

	public static BasicDataSource datasource;
	static {
		datasource = new BasicDataSource();
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setUrl("jdbc:mysql://127.0.0.1:3306/test?useSSL=false");
		datasource.setUsername("root");
		datasource.setPassword("123");
	}

	@SuppressWarnings("static-access")
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(DataSource.class)
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(this.datasource);
	}

}
