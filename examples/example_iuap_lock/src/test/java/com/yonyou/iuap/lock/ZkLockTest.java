package com.yonyou.iuap.lock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.curator.framework.CuratorFramework;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.yonyou.iuap.lock.exception.LockException;
import com.yonyou.iuap.lock.zklock.AbstractLockAction;
import com.yonyou.iuap.lock.zkpool.ZkPool;

@ContextConfiguration(
	locations = { 
		"classpath:applicationContext-lock.xml"
	}
)
public class ZkLockTest extends AbstractJUnit4SpringContextTests {

	@Before
	public void setUp() throws Exception {
		GenericObjectPoolConfig config = (GenericObjectPoolConfig)this.applicationContext.getBean("zkPoolConfig");
		ZkPool.initPool(config);
		CuratorFramework[] array = new CuratorFramework[10];
		for (int i = 0; i < array.length; i++) {
			array[i] = ZkPool.getClient();
		}
		
		for (int i = 0; i < array.length; i++) {
			ZkPool.returnClient(array[i]);
		}
	}
	
	@Test
	public void testMutexZkLock() {
		
		// 锁的路径，路径需要业务上唯一，并保证路径中没有特殊字符，如“/”
		final String id = "businesslockpath_test";
		try {
			LockTemplate.execute(new AbstractLockAction(id) {
				@Override
				public void doInLock() throws LockException {
					System.out.println("do business lock!");
					System.out.println("加锁业务执行!");
				}
			});
			System.out.println("加锁业务完成!");
		} catch (LockException e) {
			System.err.println("加锁业务失败!");
			System.err.println(e.getLockErrorType());
		}
		
	}
	
	@Test
	public void testWithThreads() throws InterruptedException {
		long t1 = System.currentTimeMillis();
		int size = 0;
		String[] resources = {
			"resource01","resource02","resource03","resource04","resource05",
			"resource06","resource07","resource08","resource09","resource10"
		};
		int threadNum = 200;
		while (size < 10) {
			ExecutorService service = Executors.newFixedThreadPool(threadNum);
			for (int i = 0; i < threadNum; ++i) {
				final int ai = i % 10;
				final String lockid = resources[ai];
				Callable<Void> task = new Callable<Void>() {
					@Override
					public Void call() throws Exception {
						for (int j = 0; j < 100; j++) {
							doWorkInLock();
						}
						return null;
					}

					private void doWorkInLock() {
						long time1 = System.currentTimeMillis();
						final String id = "businesslockpath_test_" + lockid;
						try {
							LockTemplate.execute(new AbstractLockAction(id) {
								@Override
								public void doInLock() throws LockException {
									//System.out.println("do business lock!");
									try {
										Thread.sleep(20L);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									System.out.println("加锁业务执行!");
								}
							});
							System.out.println("加锁业务完成!");
						} catch (LockException e) {
							System.err.println("加锁业务失败!");
							//System.err.println(e.getLockErrorType());
							//e.printStackTrace();
						}
						long time2 = System.currentTimeMillis();
						System.out.println("一次加解锁消耗时间：" + (time2 - time1));
					}
				};
				service.submit(task);
			}
			// 用于关闭启动线程，如果不调用该语句，jvm不会关闭
			service.shutdown();
			// 用于等待子线程结束，再继续执行下面的代码
			service.awaitTermination(10, TimeUnit.MINUTES);
			System.out.println("----success end----:" + ++size);
		}
		long t2 = System.currentTimeMillis();
		System.err.println("耗时:" + (t2-t1) + " -- " + size);
	}
	
	// 重入性测试
	@Test
	public void testThreadReEntry1() {
		String lockPath = "testlock";
		try {
			LockTemplate.execute(new AbstractLockAction(lockPath) {
				@Override
				public void doInLock() throws LockException {
					System.out.println("加锁业务1执行!");
					testThreadReEntry2();
				}
			});
		} catch (LockException e) {
			System.err.println("加锁业务1失败!");
		}
	}
	
	@Test
	public void testThreadReEntry2() {
		String lockPath = "testlock";
		try {
			LockTemplate.execute(new AbstractLockAction(lockPath) {
				@Override
				public void doInLock() throws LockException {
					System.out.println("加锁业务2执行!");
				}
			});
		} catch (LockException e) {
			System.err.println("加锁业务2失败!");
		}
	}
}
