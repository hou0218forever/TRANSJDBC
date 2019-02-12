package com.hzb.entity;

/**
 * @author: houzhibo
 * @date: 2019年1月30日 上午11:52:43
 * @describe: 账户对象
 */
public class Account {

	private int id;
	private String name;
	private Integer money;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

}
