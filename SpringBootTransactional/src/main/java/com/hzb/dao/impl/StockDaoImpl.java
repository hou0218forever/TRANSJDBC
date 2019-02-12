package com.hzb.dao.impl;

import org.springframework.stereotype.Repository;
import com.hzb.base.BaseDao;
import com.hzb.dao.interfaces.StockDao;
import com.hzb.entity.Stock;
import com.hzb.exception.BuyStockException;

@Repository
public class StockDaoImpl extends BaseDao<Stock> implements StockDao {

	public void addStock(String sname, int count) {
//		JdbcTemplate jt = new JdbcTemplate(AppConfig.datasource);
//		String sql = "insert into stock(name,count) values(?,?)";
//		jt.update(sql, sname, count);
		Stock stock = new Stock();
		stock.setName(sname);
		stock.setCount(count);
		sqlSession.insert("addStock", stock);
	}
	/**
	 * REQUIRED 支持当前事务，如果当前没有事务，就新建一个事务
	 * SUPPORTS 支持当前事务，如果当前没有事务，就以非事务方式执行
	 * MANDATORY 必须在一个事务中运行。也就是说，他必须被一个父事务调用。否则，他就要抛出异常。
	 * REQUIRES_NEW 新建事务，如果当前存在事务，把当前事务挂起 开启一个新的事物父类异常不影响子类，子类异常会影响父类
	 * NOT_SUPPORTED 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
	 * NEVER 以非事务方式执行，如果当前存在事务，则抛出异常
	 * Nested的事务和他的父事务是相依的，他的提交是要等和他的父事务一块提交的
	 */
	// @Transactional(propagation = Propagation.NESTED, readOnly = false, rollbackFor = RuntimeException.class)
	public void updateStock(String sname, int count, boolean isbuy) {
//		String sql = "update stock set count = count-? where name = ?";
//		if (isbuy) {
//			sql = "update stock set count = count+? where name = ?";
//		}
//		JdbcTemplate jt = new JdbcTemplate(AppConfig.datasource);
//		jt.update(sql, count, sname);
//		throw new BuyStockException("更新股票发生异常");
		Stock stock = new Stock();
		stock.setName(sname);
		stock.setCount(count);
		sqlSession.update("updateStock", stock);
		throw new BuyStockException("10001","更新股票发生异常");
	}

}
