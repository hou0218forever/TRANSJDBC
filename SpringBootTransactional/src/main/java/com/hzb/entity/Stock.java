package com.hzb.entity;

/**
 * @author: houzhibo
 * @date: 2019年1月30日 上午11:53:22
 * @describe:股票对象
 */
public class Stock {

	private int id;
	private String name;
	private Integer count;

	public Stock() {
		super();
	}

	public Stock(int id, String name, Integer count) {
		super();
		this.id = id;
		this.name = name;
		this.count = count;
	}

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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
