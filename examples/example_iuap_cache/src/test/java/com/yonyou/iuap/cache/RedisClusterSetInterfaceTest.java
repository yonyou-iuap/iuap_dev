package com.yonyou.iuap.cache;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.util.Assert;

import com.yonyou.iuap.cache.vo.ChildEntity;

/**测试注入接口时候结果 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
    "classpath:applicationContext-cacheCluster.xml"
   })
public class RedisClusterSetInterfaceTest  extends AbstractJUnit4SpringContextTests   {
	
	  @Autowired
	  private ICacheManager cacheClusterManager;
	  
	  
	  @Test
	  public void testAutowired()     {
		  cacheClusterManager.set("testCluster" ,"testCluster_value" )   ;
			 String value = cacheClusterManager.get("testCluster" ) ;
			 Assert.notNull(value);
			 System.out.println(value);
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
			for(int i=1 ;i<=100 ;i++ ){
				cacheClusterManager.set("test1011_" + i, "ValuetestRedisCluster" +i ) ;
				Object value = cacheClusterManager.get("test1011_" + i) ;
				 Assert.notNull(value);
			}
		}
		
		/** 获取 */
		@Test
		public void testRedisClusterGet(){
			for(int i=1 ;i<=100 ;i++ ){
				String value = (String)cacheClusterManager.get("test1011_" + i)   ;
				Assert.notNull(value);
				System.out.println(value);
			}
		}
		
		
		//删除
		@Test
		public void testRedisClusterDel(){
			for(int i=1 ;i<=100 ;i++ ){
				cacheClusterManager.removeCache("test1011_" + i)   ;
			}
		}
		  
		/** 测试简单 pojo 放入缓存*/
		@Test
		public void testClusterManagersetAndGet(){
			for(int i=1 ;i<=1 ;i++ ){
				ChildEntity ce = new ChildEntity() ;
				ce.setId("2-12");  ce.setName("输入名字");
				cacheClusterManager.set("testChild", ce);
				ChildEntity getObject = cacheClusterManager.get("testChild") ;
				Assert.notNull(getObject);
				System.out.println(getObject) ;
			}
		}
		
		
		@Test  //有效期测试
		public void testClusterManagerEX(){
			cacheClusterManager.setex("testex", "有效期测试value", 10);
		}
		
		@Test  //是否存在测试
		public void testClusterManagerExists(){
			boolean f = cacheClusterManager.exists("testChild");
			Assert.notNull(f);
			System.out.println("检查key值是否存在：" +f );
		}
		
		
		@Test  // 测试  过期时间
		public void testClusterManagerExpire(){
			cacheClusterManager.expire("testChild" ,10 );
		}
		
		@Test  // 测试   hashmap
		public void testClusterManagerHset(){
			  cacheClusterManager.hset("person", "user_code", "李逵");
			  cacheClusterManager.hset("person", "age", 11 );
			  cacheClusterManager.hset("person", "xuexing", "AB" );
			  cacheClusterManager.hset("person", "boss", "songjiang" );
				
			  String name =	cacheClusterManager.hget("person", "user_code") ;
			  int age =	cacheClusterManager.hget("person", "age") ;
			  String xue =	cacheClusterManager.hget("person", "xuexing") ;
			  String boss	= cacheClusterManager.hget("person", "boss") ;
			  Assert.notNull(name);
			  Assert.notNull(age);
			  Assert.notNull(xue);
			  Assert.notNull(boss);
			  System.out.println(  name +"  "+age   + " "+xue + "  " +  boss) ;
		}
		
		
		@Test  // 测试   hashmap  hmget
		public void testClusterManagerHmget(){
				cacheClusterManager.hset("person", "user_code", "李逵");
				cacheClusterManager.hset("person", "age", 11 );
				cacheClusterManager.hset("person", "xuexing", "AB" );
				cacheClusterManager.hset("person", "boss", "songjiang" );
				
				List<String> list=  cacheClusterManager.hmget("person" ,  "user_code", "age","xuexing","boss"   ) ;  ;  // .hmget("person" ,  "user_code"   ) ; 
				 Assert.notNull(list);
				System.out.println( list ) ;
			 
		}
}
