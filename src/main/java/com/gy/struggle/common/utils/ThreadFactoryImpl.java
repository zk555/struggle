package com.gy.struggle.common.utils;


import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * class_name: ThreadFactoryImpl
 * package: com.gy.struggle.common.utils
 * describe: TODO 自定义的ThreadFactory实现类
 * creat_user: zhaokai@
 * creat_date: 2019/6/10
 * creat_time: 10:54
 **/
public class ThreadFactoryImpl implements ThreadFactory{
	AtomicInteger index = new AtomicInteger(0);
	private String threadNamePrefix;
	private boolean isDaemon;
	
	public ThreadFactoryImpl(String threadNamePrefix,boolean isDaemon) {
		super();
		this.threadNamePrefix = threadNamePrefix;
		this.isDaemon = isDaemon;
	}
	
	
	@Override
	public Thread newThread(Runnable r) {
		
		Thread thread =  new Thread(r,threadNamePrefix+index.addAndGet(1));
		thread.setDaemon(isDaemon);
		return thread;
		
	}
	
	

}
