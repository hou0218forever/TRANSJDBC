package com.hzb.aspect;

public class MyAspect {
	public void myBefore(){
		System.out.println("方法执行之前执行");
	}

	public void myAfter(){
		System.out.println("方法执行之后执行");
	}
}
