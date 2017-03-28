package com.yonyou.iuap.lock;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.curator.framework.CuratorFramework;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.yonyou.iuap.lock.zkpool.ZkPool;

@ContextConfiguration(
	locations = { 
		"classpath:applicationContext-lock.xml"
	}
)
public class DisLockTest extends AbstractJUnit4SpringContextTests {
	
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
	public void testDisLock() throws InterruptedException {
		final String lockpath = "iuapdislockpath_test";
		long time3 = 0;
		long time1 = 0;
		long time2 = 0;
		long time4 = 0;
		long time6 = 0;
		boolean islocked = false;
		
		try {
			time1 = System.currentTimeMillis();
			islocked = DistributedLock.lock(lockpath);
			if(islocked){
				time2 = System.currentTimeMillis();
				time3 = time2 - time1;
				System.out.println("加锁成功，耗用时间为:" + time3 + " 毫秒!");
			}
		} catch (Exception e) {
			time4 = System.currentTimeMillis();
			time3 = time4 - time1;
			System.out.println("锁服务调用失败，耗用时间为:" + time3 + " 毫秒!");
		} finally {
			if(islocked){
				long t7 = System.currentTimeMillis();
				DistributedLock.unlock(lockpath);
				time6 = System.currentTimeMillis() - t7;
				System.out.println("解锁需要的时间:" + time6 + " 毫秒!");
			} else {
				System.err.println("加锁失败!");
			}
		}
	}
	
	@Test
	public void testDisLockCodeExample1() throws InterruptedException {
		String lockpath = "iuapdislockpath_test";
		boolean islocked = false;
		try {
			islocked = DistributedLock.lock(lockpath);
			if(islocked){
				System.out.println("加锁1成功，业务处理!");
				
				//再次加锁，测试重入性
				testDisLockCodeExample2();
			}
		} catch (Exception e) {
			System.out.println("加锁1操作异常!");
		} finally {
			if(islocked){
				DistributedLock.unlock(lockpath);
				System.out.println("解锁1成功!");
			} 
		}
	}
	
	@Test
	public void testDisLockCodeExample2() throws InterruptedException {
		String lockpath = "iuapdislockpath_test";
		boolean islocked = false;
		try {
			islocked = DistributedLock.lock(lockpath);
			if(islocked){
				System.out.println("加锁2成功，业务处理!");
			}
		} catch (Exception e) {
			System.out.println("加锁2操作异常!");
		} finally {
			if(islocked){
				DistributedLock.unlock(lockpath);
				System.out.println("解锁2成功!");
			} 
		}
	}
	
	@Test
	public void testBatchDisLockCodeExample() throws InterruptedException {
		String lockpaths[] = {"iuapdislockpath_test1","iuapdislockpath_test2","iuapdislockpath_test3"};
		boolean islocked = false;
		try {
			islocked = DistributedLock.lock(lockpaths);
			if(islocked){
				System.out.println("批量加锁成功，业务处理!");
			} else {
				System.out.println("批量加锁失败!");
			}
		} catch (Exception e) {
			System.out.println("加锁操作异常!");
		} finally {
			if(islocked){
				DistributedLock.unlock(lockpaths);
				System.out.println("批量解锁成功!");
			} 
		}
	}

}
