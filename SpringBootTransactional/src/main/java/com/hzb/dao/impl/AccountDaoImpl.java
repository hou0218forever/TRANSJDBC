package com.hzb.dao.impl;

import org.springframework.stereotype.Repository;
import com.hzb.base.BaseDao;
import com.hzb.dao.interfaces.AccountDao;
import com.hzb.entity.Account;

@Repository
public class AccountDaoImpl extends BaseDao<Account> implements AccountDao {

	@Override
	public void addAccount(String name, int money) {
//		String sql = "insert account(name,money) values(?,?);";
//		JdbcTemplate jt = new JdbcTemplate(AppConfig.datasource);
//		jt.update(sql, name, money);
		Account account = new Account();
		account.setMoney(money);
		account.setName(name);
		sqlSession.insert("addAccount", account);
	}

	
	//@Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.NESTED, readOnly = false, rollbackFor = RuntimeException.class)
	@Override
	public void updateAccount(String name, int money, boolean isbuy) {
//		String sql = "update account set money=money+? where name=?";
//		if (isbuy) {
//			sql = "update account set money=money-? where name=?";
//		}
//		JdbcTemplate jt = new JdbcTemplate(AppConfig.datasource);
//		jt.update(sql, money, name);

		Account account = new Account();
		account.setMoney(money);
		account.setName(name);
		sqlSession.update("updateAccount", account);
	}

}
