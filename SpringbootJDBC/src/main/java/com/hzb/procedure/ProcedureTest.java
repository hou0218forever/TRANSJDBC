package com.hzb.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * @author 作者：houzhibo
 * @version 创建时间：2019年1月28日 上午10:47:32 类说明：调用存储过程
 * 
 */
public class ProcedureTest {

	public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://127.0.0.1:3306/test";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "123";

	public static void main(String[] args) throws Exception {
		test1(0);
		test2(1, 2);
	}

	public static void test1(Integer parameter) throws Exception {
		Class.forName(DRIVER_CLASS);
		Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		String sql = "{CALL user_select(?)}"; // 调用存储过程
		CallableStatement cstm = connection.prepareCall(sql); // 实例化对象cstm
		cstm.setInt(1, parameter); // 存储过程输入参数
		// cstm.registerOutParameter(1, Types.INTEGER); // 设置返回值类型 即返回值
		cstm.execute(); // 执行存储过程
		ResultSet resultSet = cstm.getResultSet();
		while (resultSet.next()) {
			int id = resultSet.getInt(1);
			String name = resultSet.getString(2);
			String pass = resultSet.getString(3);
			String note = resultSet.getString(4);
			System.out.println("id:" + id + ";name:" + name + ";pass:" + pass + ";note:" + note);
		}
		cstm.close();
		connection.close();
	}

	public static void test2(Integer a, Integer b) throws Exception {
		Class.forName(DRIVER_CLASS);
		Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		String sql = "{CALL pr_add(?,?)}"; // 调用存储过程
		CallableStatement cstm = connection.prepareCall(sql); // 实例化对象cstm
		cstm.setInt(1, a); // 存储过程输入参数
		cstm.setInt(2, b); // 存储过程输入参数
		// cstm.registerOutParameter(1, Types.INTEGER); // 设置返回值类型 即返回值
		cstm.execute(); // 执行存储过程
		ResultSet resultSet = cstm.getResultSet();
		while (resultSet.next()) {
			int sum = resultSet.getInt(1);
			System.out.println("sum:" + sum);
		}

		cstm.close();
		connection.close();
	}

}
