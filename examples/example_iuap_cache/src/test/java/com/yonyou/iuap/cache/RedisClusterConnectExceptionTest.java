package com.yonyou.iuap.cache;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;

import com.yonyou.iuap.cache.cluster.IUAPJedisCluster;


/** 模拟连接池异常 */
public class RedisClusterConnectExceptionTest {
	
	private static ApplicationContext context;
	
	private static GenericObjectPoolConfig genericObjectPoolConfig ;
	
	
	private static IUAPJedisCluster iUAPJedisCluster ;
	
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml","classpath:applicationContext-cache.xml"});
//		jedisCluster = (JedisCluster)context.getBean("jedisCluster");
		genericObjectPoolConfig = (GenericObjectPoolConfig)context.getBean("genericObjectPoolConfig");
		Set<HostAndPort> haps =   getHaps() ;
		
		iUAPJedisCluster = new   IUAPJedisCluster(haps, 20000, 6 ,genericObjectPoolConfig);
	}
	
	public Set<HostAndPort>  getHaps(){
		  Set<HostAndPort> haps = new HashSet<HostAndPort>();
		  
		  HostAndPort hap = new HostAndPort("192.168.22.129", 7000);
		  haps.add(hap);
		  HostAndPort hap1 = new HostAndPort("192.168.22.129", 7001);
		  haps.add(hap1);
		  HostAndPort hap2 = new HostAndPort("192.168.22.129", 7002);
		  haps.add(hap2);
		  HostAndPort hap3 = new HostAndPort("192.168.22.129", 7003);
		  haps.add(hap3);
		  HostAndPort hap4 = new HostAndPort("192.168.22.129", 7004);
		  haps.add(hap4);
		  HostAndPort hap5 = new HostAndPort("192.168.22.129", 7005);
		  haps.add(hap5);
		  
		  return haps ;
		  
	}

	@After
	public void tearDown() throws Exception {
	}

	
	/**模拟连接异常，只有当   为100 整数倍 的时候才执行 正常操作，其他时候抛出异常 */
	@Test
	public void testClusterManagerset(){
		for(int i =0 ;i<100000 ;i++){
			try{
				iUAPJedisCluster.set( String.valueOf(i)  , "testConnectException" +i) ;
			}catch(Exception e){
				System.out.println("捕获模拟异常"+i);
			}
			
		}
	}
	 
	

	
}
