package com.hzb.aspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import com.hzb.annotation.AopService;
import com.hzb.annotation.AopServiceImpl;

/**
 * 动态代理
 */
public class MyBeanFactory {

	public static AopService getBean() {
		MyAspect myAspect = new MyAspect();
		AopService aopService = new AopServiceImpl();
		return (AopService) Proxy.newProxyInstance(MyBeanFactory.class.getClassLoader(),
				new Class[] { AopService.class }, new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						myAspect.myBefore();
						Object object = method.invoke(aopService, args);
						myAspect.myAfter();
						return object;
					}
				});
	}

	public static void main(String[] args) {
		AopService aopService = MyBeanFactory.getBean();
		aopService.add();
		aopService.delete();
		aopService.find();
	}
}
