package com.hzb.base;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author: houzhibo
 * @date: 2019年1月30日 下午12:07:05
 * @describe:
 */
public class BaseJDBCTemplate {
	
	private static volatile BaseJDBCTemplate singleton;

	private BaseJDBCTemplate() {
	}

	public static BaseJDBCTemplate getInstance() {
		if (singleton == null) {
			synchronized (BaseJDBCTemplate.class) {
				if (singleton == null) {
					singleton = new BaseJDBCTemplate();
				}
			}
		}
		return singleton;
	}

	static JdbcTemplate jt;
	public static BasicDataSource datasource ;
	static {
		datasource = new BasicDataSource();
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setUrl("jdbc:mysql://127.0.0.1:3306/test?useSSL=false");
		datasource.setUsername("root");
		datasource.setPassword("123");
		jt = new JdbcTemplate(datasource);
	}

	public JdbcTemplate getJDBCTemplate() {
		return jt;
	}
}
