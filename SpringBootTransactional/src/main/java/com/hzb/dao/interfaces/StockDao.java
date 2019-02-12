package com.hzb.dao.interfaces;

public interface StockDao {

	void addStock(String sname, int count);

	void updateStock(String sname, int count, boolean isbuy);

}
