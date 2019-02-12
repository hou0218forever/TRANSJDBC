package com.hzb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.hzb.dao.impl.AccountDaoImpl;
import com.hzb.dao.impl.StockDaoImpl;
import com.hzb.exception.BuyStockException;
import com.hzb.service.interfaces.BuyStockService;
/**
 * isolation 隔离级别
 * propagation 传播行为
 */
@Service
public class BuyStockServiceImpl implements BuyStockService {

	@Autowired
	private AccountDaoImpl accountDaoImpl;

	@Autowired
	private StockDaoImpl stockDaoImpl;

	@Override
	public void addAccount(String accountname, int money) {
		accountDaoImpl.addAccount(accountname, money);
	}

	@Override
	public void addStock(String stockname, int amount) {
		stockDaoImpl.addStock(stockname, amount);
	}

	/**
	 * isolation 四种隔离级别
	* DEFAULT ：这是默认值，表示使用底层数据库的默认隔离级别。对大部分数据库而言，通常这值就是： READ_COMMITTED 。 
	* READ_UNCOMMITTED ：该隔离级别表示一个事务可以读取另一个事务修改但还没有提交的数据。该级别不能防止脏读和不可重复读，因此很少使用该隔离级别。 
	* READ_COMMITTED ：该隔离级别表示一个事务只能读取另一个事务已经提交的数据。该级别可以防止脏读，这也是大多数情况下的推荐值。 
	* REPEATABLE_READ ：该隔离级别表示一个事务在整个过程中可以多次重复执行某个查询，并且每次返回的记录都相同。即使在多次查询之间有新增的数据满足该查询，这些新增的记录也会被忽略。该级别可以防止脏读和不可重复读。 
	* SERIALIZABLE ：所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会用到该级别。 
	 */
	/**
	 * propagation 七种传播行为
	 * REQUIRED 支持当前事务，如果当前没有事务，就新建一个事务
	 * SUPPORTS 支持当前事务，如果当前没有事务，就以非事务方式执行
	 * MANDATORY 必须在一个事务中运行。也就是说，他必须被一个父事务调用。否则，他就要抛出异常。
	 * REQUIRES_NEW 新建事务，如果当前存在事务，把当前事务挂起 开启一个新的事物父类异常不影响子类，子类异常会影响父类
	 * NOT_SUPPORTED 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
	 * NEVER 以非事务方式执行，如果当前存在事务，则抛出异常
	 * Nested的事务和他的父事务是相依的，他的提交是要等和他的父事务一块提交的
	 */
	@Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = RuntimeException.class)
	@Override
	public void buyStock(String accountname, int money, String stockname, int amount) throws BuyStockException {
		boolean isBuy = true;
		accountDaoImpl.updateAccount(accountname, money, isBuy);
		stockDaoImpl.updateStock(stockname, amount, isBuy);
		if (isBuy == true) {
			throw new BuyStockException("10001", "购买股票发生异常");
		}
	}

}
