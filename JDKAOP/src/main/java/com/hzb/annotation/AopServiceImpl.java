package com.hzb.annotation;

public class AopServiceImpl implements AopService {

	@Override
	public void add() {
		System.out.println("增加");
	}

	@Override
	public void delete() {
		System.out.println("删除");
	}

	@Override
	public void find() {
		System.out.println("查找");
	}
}
