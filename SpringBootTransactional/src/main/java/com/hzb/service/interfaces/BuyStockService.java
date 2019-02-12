package com.hzb.service.interfaces;

import com.hzb.exception.BuyStockException;

public interface BuyStockService {

	public void addAccount(String accountname, int money);

	public void addStock(String stockname, int amount);

	public void buyStock(String accountname, int money, String stockname, int amount)
			throws BuyStockException, BuyStockException;

}
