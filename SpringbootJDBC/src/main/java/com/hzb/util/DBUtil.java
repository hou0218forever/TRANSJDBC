package com.hzb.util;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DBUtil {
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8";
	private static final String USER = "root";
	private static final String PASSWORD = "123";
	private static Connection connection = null;
	static {
		try {
			// 第一步，加载驱动，此处加载MySQL驱动，如加载Oracle驱动则输入com.jdbc.driver.OracalDriver
			Class.forName("com.mysql.jdbc.Driver");
			// 第二步，获得数据库连接,请注意这里获得的连接对象类型是java.sql.Connection
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 获取数据库连接对象的静态方法
	public static Connection getConnection() {
		if (connection != null) {
			System.out.println("获得数据库连接！");
			return connection;
		}
		System.out.println("连接失败！");
		return null;
	}

	public static void main(String[] args) {
		Connection connection = getConnection();
		System.out.println(connection);
	}
}
