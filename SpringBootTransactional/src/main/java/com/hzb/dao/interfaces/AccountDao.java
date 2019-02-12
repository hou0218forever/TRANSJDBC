package com.hzb.dao.interfaces;

public interface AccountDao {

	void addAccount(String name, int money);

	void updateAccount(String name, int money, boolean isbuy);

}
