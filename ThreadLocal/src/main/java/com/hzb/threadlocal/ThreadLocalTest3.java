package com.hzb.threadlocal;
 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
 
public class ThreadLocalTest3 {
 
	private static ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<SimpleDateFormat>();
 
//如果不想要下面的t1.set则可以通过这种方式构造ThreadLocal.
//	private static ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<SimpleDateFormat>() {
//		public SimpleDateFormat initialValue() {
//			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		}
//	};
	private static final SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static ReentrantLock lock = new ReentrantLock();
 
	public static class ParseDate implements Runnable {
 
		int i = 0;
 
		public ParseDate(int i) {
			this.i = i;
		}
 
		// @Override
		// public void run() {
		// try {
		// lock.lock();
		// Date date = sm.parse("2018-8-12 17:30:" + i + "");
		// System.out.println(i + "--" + date);
		// lock.unlock();
		// Thread.sleep(1000);
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
 
		// @Override
		// public void run() {
		// try {
		// synchronized (ParseDate.class) {
		// Date date = sm.parse("2018-8-12 17:30:"+i+"");
		// System.out.println(i+"--"+date);
		// }
		// Thread.sleep(1000);
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
 
		@Override
		public void run() {
			if (t1.get() == null)
				t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
 
			try {
				Date date = t1.get().parse("2018-8-12 17:30:" + i + "");
				System.out.println(i + "--" + date);
				Thread.sleep(1000);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
 
	}
 
	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(10);
 
		for (int i = 0; i < 30; i++)
			es.execute(new ParseDate(i));
	}
 
}
