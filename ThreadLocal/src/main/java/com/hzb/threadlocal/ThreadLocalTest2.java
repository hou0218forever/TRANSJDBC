package com.hzb.threadlocal;

/**
 * @author: houzhibo
 * @date: 2019年2月13日 下午2:22:12
 * @describe:
 */
public class ThreadLocalTest2 {
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			int n=i;
			Thread.sleep(200);
			new Thread(new Runnable() {
				@Override
				public void run() {
					Double d = Math.random() * 10;
					ThreadLocalDemo.getThreadInstance().setName("name" + n);
					new A().get();
					new B().get();
				}
			}).start();
		}
	}

	static class A {
		public void get() {
			System.out.println(ThreadLocalDemo.getThreadInstance().getName());
		}
	}

	static class B {
		public void get() {
			System.out.println(ThreadLocalDemo.getThreadInstance().getName());
		}
	}

}

class ThreadLocalDemo {
	/* 把线程相关的部分内聚到 类里面 相当于map 每个类是对应key */
	private static ThreadLocal<ThreadLocalDemo> t = new ThreadLocal<ThreadLocalDemo>();

	private ThreadLocalDemo() {
	}

	public static ThreadLocalDemo getThreadInstance() {
		ThreadLocalDemo threadLocalDemo = t.get();
		if (null == threadLocalDemo) {// 当前线程无绑定的对象时，直接绑定一个新的对象
			threadLocalDemo = new ThreadLocalDemo();
			t.set(threadLocalDemo);
		}
		return threadLocalDemo;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
