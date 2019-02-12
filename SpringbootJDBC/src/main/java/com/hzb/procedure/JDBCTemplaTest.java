package com.hzb.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author 作者：houzhibo
 * @version 创建时间：2019年1月28日 上午10:51:39
 *          类说明：query查询方法中带有回调接口有三个：ResultSetExtractor、RowCallbackHandler、RowMapper
 *          使用ResultSetExtractor时，一般是直接new
 *          ResultSetExtractor()，然后在extractData(ResultSet
 *          rs)方法中实现自己的内容，最后返回Object结果。 使用RowCallbackHandler时，也是new
 *          RowCallbackHandler()，然后在processRow(ResultSet
 *          rs)方法中实现自己代码，并且将内容保存在上下文变量中，因为此方法没有返回类型（void） 使用RowMapper时，new
 *          RowMapper(), 然后在mapRow(ResultSet rs, int rowNum)实现自己代码，并返回Object结果。
 *          ResultSetExtractor一次处理多个结果，而RowCallbackHandler、RowMapper只处理单行结果
 */
public class JDBCTemplaTest {

	static int i = 0;
	static int j = 0;
	static int k = 0;

	public static void main(String[] args) {
		System.out.println("ok");
		try {
			BasicDataSource datasource = new BasicDataSource();
			datasource.setDriverClassName("com.mysql.jdbc.Driver");
			datasource.setUrl("jdbc:mysql://127.0.0.1:3306/test?useSSL=false");
			datasource.setUsername("root");
			datasource.setPassword("123");

			JdbcTemplate jt = new JdbcTemplate(datasource);

			// query1(jt);//
			// PreparedStatementCreator、PreparedStatementSetter、ResultSetExtractor
			// query2(jt);// PreparedStatementCreator、ResultSetExtractor
			// query3(jt);// createPreparedStatement、RowCallbackHandler
			// query4(jt);// PreparedStatementCreator、RowMapper
			// query5(jt);// ResultSetExtractor
			// query6(jt);// ResultSetExtractor
			// query7(jt);// PreparedStatementSetter、ResultSetExtractor
			// query8(jt);// ResultSetExtractor
			// query9(jt);// PreparedStatementCallback
			// query10(jt);// PreparedStatementCallback
			// query11(jt, 2, 3);// CallableStatementCreator、CallableStatementCallback
			// query12(jt, 1);// CallableStatementCreator、CallableStatementCallback
			// query13(jt, 1);// RowCallbackHandler
			// query14(jt);// jdbcTemplate.update
			// query15(jt, 2, 4);// jdbcTemplate.update
			query16(jt);// jdbcTemplate.update
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 杭州银行项目 jdbcTemplate.queryForObject
	 */
	private static void query16(JdbcTemplate jt) {
		String sql = "select now() from dual ";
		Date name = jt.queryForObject(sql, Date.class);
		System.out.println(name);
	}

	/**
	 * 杭州银行项目 分页查询 jdbcTemplate.queryForObject
	 */
	private static void query15(JdbcTemplate jt, Integer pageNo, Integer pageSize) {
		Integer start = (pageNo - 1) * pageSize + 1;
		Integer end = pageNo * pageSize;
		List<Object> inputParamValueList = new ArrayList<Object>();
		inputParamValueList.add(start);
		inputParamValueList.add(end);

		String totalNumToExecute = "select count(1) from user where id BETWEEN ? and ?";// 调用的sql
		String totalNum = jt.queryForObject(totalNumToExecute, inputParamValueList.toArray(), String.class);
		System.out.println(totalNum);

		final List<User> list = new ArrayList<User>();
		String querySqlToExecute = "select * from user where id BETWEEN ? and ?";// 调用的sql

		jt.query(querySqlToExecute, inputParamValueList.toArray(), new RowCallbackHandler() {
			public void processRow(ResultSet resultSet) throws SQLException {
				User user = new User();
				user.setId(resultSet.getInt(1));
				user.setName(resultSet.getString(2));
				user.setPass(resultSet.getString(3));
				user.setNote(resultSet.getString(4));
				list.add(user);
			}
		});
		System.out.println(list);
	}

	/**
	 * 杭州银行项目插入数据 jdbcTemplate.update
	 */
	private static void query14(JdbcTemplate jt) {
		String querySqlToExecute = "insert into user (name,pass,note) values (?,?,?)";// 调用的sql
		List<Object> inputParamValueList = new ArrayList<Object>();
		inputParamValueList.add("sys");
		inputParamValueList.add("123");
		inputParamValueList.add("测试");
		int count = jt.update(querySqlToExecute, new Object[] { "sys", "123", "测试" });
		System.out.println(count);
	}

	/**
	 * 杭州银行项目RowCallbackHandler接口
	 */
	private static void query13(JdbcTemplate jt, final Integer id) {
		String querySqlToExecute = "select * from user where id=?";// 调用的sql
		final List<User> list = new ArrayList<User>();
		List<Object> inputParamValueList = new ArrayList<Object>();
		inputParamValueList.add(id);
		jt.query(querySqlToExecute, inputParamValueList.toArray(), new RowCallbackHandler() {
			public void processRow(ResultSet resultSet) throws SQLException {
				User user = new User();
				user.setId(resultSet.getInt(1));
				user.setName(resultSet.getString(2));
				user.setPass(resultSet.getString(3));
				user.setNote(resultSet.getString(4));
				list.add(user);
			}
		});
		System.out.println(list);
	}

	/**
	 * 杭州银行项目中 有返回值的存储过程（结果集）CallableStatementCreator、CallableStatementCallback
	 */
	private static void query12(JdbcTemplate jt, final Integer parameter) {
		List<User> list1 = jt.execute(new CallableStatementCreator() {
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				String storedProc = "{call user_select(?)}";// 调用的sql
				CallableStatement cs = con.prepareCall(storedProc);
				cs.setInt(1, parameter);// 设置输入参数的值
				// cs.registerOutParameter(2, Types.VARCHAR);// 注册输出参数的类型
				return cs;
			}
		}, new CallableStatementCallback<List<User>>() {
			public List<User> doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.execute();
				ResultSet resultSet = cs.getResultSet();
				List<User> list = new ArrayList<User>();
				while (resultSet.next()) {
					User user = new User();
					user.setId(resultSet.getInt(1));
					user.setName(resultSet.getString(2));
					user.setPass(resultSet.getString(3));
					user.setNote(resultSet.getString(4));
					list.add(user);
				}
				return list;// 获取输出参数的值
			}
		});
		System.out.println(list1);
	}

	/**
	 * 杭州银行项目中 有返回值的存储过程（非结果集）CallableStatementCreator、CallableStatementCallback
	 */
	private static void query11(JdbcTemplate jt, final Integer a, final Integer b) {
		Integer sum = jt.execute(new CallableStatementCreator() {
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				String storedProc = "{CALL pr_add(?,?)}";// 调用的sql
				CallableStatement cs = con.prepareCall(storedProc);
				cs.setInt(1, a);// 设置输入参数的值
				cs.setInt(2, b);// 设置输入参数的值
				// cs.registerOutParameter(2, Types.VARCHAR);// 注册输出参数的类型
				return cs;
			}
		}, new CallableStatementCallback<Integer>() {
			public Integer doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.execute();
				ResultSet resultSet = cs.getResultSet();
				Integer sum = 0;
				while (resultSet.next()) {
					sum = resultSet.getInt(1);
				}
				return sum;
			}
		});
		System.out.println(sum);
	}

	/**
	 * 批量更新
	 */
	private static void query10(JdbcTemplate jt) {
		String sql = "update user set name = '2' where id = ?";
		int[] n = jt.execute(sql, new PreparedStatementCallback<int[]>() {
			public int[] doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, "zhangsan");
				ps.addBatch();
				ps.setString(1, "lisi");
				ps.addBatch();
				return ps.executeBatch();
			}
		});
		System.out.println(n);
	}

	/**
	 * 批量查询
	 */
	private static void query9(JdbcTemplate jt) {
		List<User> list1 = new ArrayList<User>();
		String sql = "select * from user where id<?";
		list1 = (List<User>) jt.execute(sql, new PreparedStatementCallback<List<User>>() {
			public List<User> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, 20);
				ResultSet rs = ps.executeQuery();
				User user = new User();
				List<User> list = new ArrayList<User>();
				while (rs.next()) {
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setPass(rs.getString("pass"));
					user.setNote(rs.getString("note"));
					list.add(user);
				}
				return list;
			}
		});
		System.out.println(list1);
	}

	/**
	 * 返回值为int
	 */
	private static void query8(JdbcTemplate jt) {
		int count = jt.queryForObject("select count(*) from user", null, Integer.class);
		System.out.println(count);

	}

	/**
	 * 条件查询
	 */
	private static void query7(JdbcTemplate jt) {
		List<User> list1 = new ArrayList<User>();
		String sql = "select * from user where id<? and name=?";
		list1 = (List<User>) jt.query(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, 20);
				ps.setString(2, "lisi");
			}
		}, new ResultSetExtractor<List<User>>() {
			public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<User> list = new ArrayList<User>();
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setPass(rs.getString("pass"));
					user.setNote(rs.getString("note"));
					list.add(user);
				}
				return list;
			}
		});
		System.out.println(list1);
	}

	/**
	 * 条件查询
	 */
	private static void query6(JdbcTemplate jt) {
		List<User> list1 = new ArrayList<User>();
		String sql = "select * from user where id<? and name=?";
		list1 = (List<User>) jt.query(sql, new Object[] { 15, "zhangsan" }, new ResultSetExtractor<List<User>>() {
			public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<User> list = new ArrayList<User>();
				while (rs.next()) {
					User userinfo = new User();
					userinfo.setId(rs.getInt("id"));
					userinfo.setName(rs.getString("name"));
					userinfo.setPass(rs.getString("pass"));
					userinfo.setNote(rs.getString("note"));
					list.add(userinfo);
				}
				return list;
			}
		});
		System.out.println(list1);

	}

	/**
	 * 条件查询
	 */
	private static void query5(JdbcTemplate jt) {
		List<User> list1 = new ArrayList<User>();
		String sql = "select * from user where id<20";
		list1 = (List<User>) jt.query(sql, new ResultSetExtractor<List<User>>() {
			public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<User> list = new ArrayList<User>();
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setPass(rs.getString("pass"));
					user.setNote(rs.getString("note"));
					list.add(user);
				}
				return list;
			}
		});
		System.out.println(list1);

	}

	/**
	 * 执行计划
	 */
	public static void query4(JdbcTemplate jt) {

		List<User> list1 = new ArrayList<User>();
		list1 = jt.query(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				System.out.println("i=" + (++i));
				String sql = "select * from user where id<?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, 20);
				return ps;
			}
		}, new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				System.out.println("j=" + (++j));
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPass(rs.getString("pass"));
				user.setNote(rs.getString("note"));
				return user;
			}
		});

		System.out.println(list1);

	}

	/**
	 * 执行计划
	 */
	public static void query3(JdbcTemplate jt) {
		final List<User> list = new ArrayList<User>();
		jt.query(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				System.out.println("i=" + (++i));
				String sql = "select * from user where id = ?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, 2);
				return ps;
			}
		}, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				System.out.println("j=" + (++j));
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPass(rs.getString("pass"));
				user.setNote(rs.getString("note"));
				list.add(user);
			}
		});
		System.out.println(list);
	}

	public static void query2(JdbcTemplate jt) {
		User u = new User();
		u = (User) jt.query(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				System.out.println("i=" + (++i));
				String sql = "select * from user where id=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, 2);
				return ps;
			}
		}, new ResultSetExtractor<User>() {
			public User extractData(ResultSet rs) throws SQLException, DataAccessException {
				System.out.println("j=" + (++j));
				User user = new User();
				while (rs.next()) {
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setPass(rs.getString("pass"));
					user.setNote(rs.getString("note"));

				}
				return user;
			}
		});
		System.out.println(u);
	}

	public static void query1(JdbcTemplate jt) {
		User user = new User();
		user = (User) jt.query(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				System.out.println("i=" + (++i));
				String sql = "select * from user where id=?";
				return con.prepareStatement(sql);
			}
		}, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				System.out.println("j=" + (++j));
				ps.setInt(1, 1);
			}
		}, new ResultSetExtractor<User>() {
			public User extractData(ResultSet rs) throws SQLException, DataAccessException {
				System.out.println("k=" + (++k));
				User user = new User();
				while (rs.next()) {
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setPass(rs.getString("pass"));
					user.setNote(rs.getString("note"));

				}
				return user;
			}
		});
		System.out.println(user);
	}

}
