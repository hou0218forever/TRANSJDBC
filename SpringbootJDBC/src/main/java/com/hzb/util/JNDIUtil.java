package com.hzb.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JNDIUtil {
	public static Connection getConnection(String jndi) {
		DataSource datasource = null;
		Connection connection = null;
		try {
			Context context = new InitialContext();
			Context envContext = null;
			try {
				envContext = (Context) context.lookup("java:/comp/env");
			} catch (Exception e1) {
				// 无法通过java:方式获得换用/comp/env的方式
				try {
					envContext = (Context) context.lookup("/comp/env");
				} catch (Exception fff) {
					e1.printStackTrace();
				}
			}
			// 如果数据源的名称不为空的话使用指定的数据源的名称来获取数据库连接对象
			if (jndi != null) {
				datasource = (DataSource) envContext.lookup(jndi);
			} else {
				datasource = (DataSource) envContext.lookup("sqlserver/default");
			}
			connection = datasource.getConnection();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return connection;
	}

	public static void main(String[] args) throws SQLException, NamingException {
		Connection connection = getConnection("java:comp/env/jdbc/mysql");
		System.out.println(connection);
	}
}
