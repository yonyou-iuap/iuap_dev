package com.yonyou.iuap.cache;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.yonyou.iuap.cache.vo.ChildEntity;

public class RedisClusterSetTest {
	
	private static ApplicationContext context;
	
	private CacheClusterManager cacheClusterManager ;
	
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml","classpath:applicationContext-cacheCluster.xml"});
//		jedisCluster = (JedisCluster)context.getBean("jedisCluster");
		cacheClusterManager = (CacheClusterManager)context.getBean("cacheClusterManager");
	}

	@After
	public void tearDown() throws Exception {
	}

	
	//
	@Test
	public void testClusterManagerset(){
		 cacheClusterManager.set("testCluster" ,"testCluster_value" )   ;
		 String value = cacheClusterManager.get("testCluster" ) ;
		 Assert.notNull(value);
		 System.out.println(value);
	}
	
	/** 添加 */
	@Test
	public void testRedisClusterSeter(){
		try {
			for(int i=1 ;i<=100 ;i++ ){
				cacheClusterManager.set("test1011_" + i, "ValuetestRedisCluster" +i ) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 获取 */
	@Test
	public void testRedisClusterGet(){
		try {
			for(int i=1 ;i<=100 ;i++ ){
				String value = (String)cacheClusterManager.get("test1011_" + i)   ;
				System.out.println(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//删除
	@Test
	public void testRedisClusterDel(){
		try {
			for(int i=1 ;i<=100 ;i++ ){
				cacheClusterManager.removeCache("test1011_" + i)   ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	  
	/** 测试简单 pojo 放入缓存*/
	@Test
	public void testClusterManagersetAndGet(){
		try {
			for(int i=1 ;i<=1 ;i++ ){
				ChildEntity ce = new ChildEntity() ;
				ce.setId("2-12");  ce.setName("输入名字");
				cacheClusterManager.set("testChild", ce);
				ChildEntity getObject = cacheClusterManager.get("testChild") ;
				System.out.println(getObject) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test  //有效期测试
	public void testClusterManagerEX(){
		try {
			cacheClusterManager.setex("testex", "有效期测试value", 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test  //是否存在测试
	public void testClusterManagerExists(){
		try {
//			boolean f = cacheClusterManager.exists("testex");
			boolean f = cacheClusterManager.exists("testChild");
			System.out.println("检查key值是否存在：" +f );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test  // 测试  过期时间
	public void testClusterManagerExpire(){
		try {
			cacheClusterManager.expire("testChild" ,10 );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test  // 测试   hashmap
	public void testClusterManagerHset(){
		try {
			cacheClusterManager.hset("person", "user_code", "李逵");
			cacheClusterManager.hset("person", "age", 11 );
			cacheClusterManager.hset("person", "xuexing", "AB" );
			cacheClusterManager.hset("person", "boss", "songjiang" );
			
			
		  String name =	cacheClusterManager.hget("person", "user_code") ;
		  int age =	cacheClusterManager.hget("person", "age") ;
		  String xue =	cacheClusterManager.hget("person", "xuexing") ;
		  String boss	= cacheClusterManager.hget("person", "boss") ;
		  System.out.println(  name +"  "+age   + " "+xue + "  " +  boss) ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test  // 测试   hashmap  hmget
	public void testClusterManagerHmget(){
		try {
			cacheClusterManager.hset("person", "user_code", "李逵");
			cacheClusterManager.hset("person", "age", 11 );
			cacheClusterManager.hset("person", "xuexing", "AB" );
			cacheClusterManager.hset("person", "boss", "songjiang" );
			
//			List<Object> list = cacheClusterManager.hmget("person" ,  "user_code", "age","xuexing","boss"   ) ; 
			List<String> list=  cacheClusterManager.hmget("person" ,  "user_code", "age","xuexing","boss"   ) ;  ;  // .hmget("person" ,  "user_code"   ) ; 
			 
			System.out.println( list ) ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//判读 clusternode节点的主从
	@Test
	public void testJedisPool(){
		Map<String,JedisPool> map =  cacheClusterManager.getJedisCluster().getClusterNodes()  ;
		for(String k : map.keySet() ){
				 JedisPool jedisPool = map.get( k ) ;  
				 Jedis connection = jedisPool.getResource();   connection.clusterSlots().size() ;
				 String cnodes = connection.clusterNodes() ;
				 String[] nodes = cnodes.split("\n") ;
				 for(String n : nodes){
					if( n.contains("myself")  ){ 
						 if(n.contains("master")){
							 System.out.println(connection.getClient().getPort() + "  master" );
						 }else if(n.contains("slave")){
							 System.out.println(connection.getClient().getPort() + "  slave" );
						 }
					}
				 }
				
				 int count =  connection.keys("*").size()  ;
				 System.out.println(count);
			}
	}
	
	
	/** 测试单独节点 */
//	@Test
//	public void testNode(){
//		Map<String,JedisPool> map =  cacheClusterManager.getJedisCluster().getClusterNodes()  ;
//		for(String k : map.keySet() ){
//			JedisPool jedisPool = map.get( k ) ;  
//			Jedis connection = jedisPool.getResource();   connection.clusterSlots().size() ;
//			String cnodes = connection.clusterNodes() ;
//			String[] nodes = cnodes.split("\n") ;
//			for(String n : nodes){
//				if( n.contains("myself") && n.contains("master") ){ 
//					for(int i=1 ; i<4; i++ ){
//						connection.set("tnode"+i, "testnodeValue"+i) ;
//					}
//				}
//			}
//		}
//	}
	
	
	@Test
	public void testBytes(){
		String key ="rediskey" ;
		byte[] keyBytes = key.getBytes(Charset.forName("UTF-8"));
		System.out.println(keyBytes) ;
	}
	
	@Test
	public void testURLQuery(){
		String url = "192.168.22.129:7000,192.169.22.129:7001?timeout=20000&maxRedirections=6" ;
		URI uri; 
    	try {
			String paramters =  url.substring(url.indexOf("?") + 1 ) ;
			String ipAndPort = url.substring(0 , url.indexOf("?")  ) ;
			
			System.out.println("") ;
		} catch ( Exception e) {
			e.printStackTrace();
		}
		
	}
	

	
}
