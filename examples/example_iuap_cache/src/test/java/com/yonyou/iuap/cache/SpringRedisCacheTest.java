package com.yonyou.iuap.cache;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import com.yonyou.iuap.cache.redis.CacheService;
import com.yonyou.iuap.cache.vo.ChildEntity;

public class SpringRedisCacheTest {
	
	private static ApplicationContext context;
	
	
	private CacheService cacheService ;
	
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml","classpath:applicationContext-cache.xml"});
		
		cacheService = (CacheService)context.getBean("cacheService");
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/** 综合测试，包含了查询和删除 */
	@Test
	public void testAllOper() throws InterruptedException{
		int i=1 ;
		while( true ){
			System.out.println("执行@cacheEnable查询操作======"  );
			Object value = cacheService.getFromDB("testSpringCache","fromDb_value") ; //只有第一次才去修改缓存
			System.out.println("the value is:   " + value );
			Assert.notNull(value);
			
			System.out.println("执行@cachePut查询操作======"  );
			cacheService.getFromDBEver("testSpringCache2", "every to modify") ; //每次都会去修改缓存
			System.out.println("########################"  );
			Thread.sleep(10000);
			
			if( i%5 == 0){ //运行五次后 ，清除缓存
				System.out.println("执行清除操作======"  );
				cacheService.removeCache("testSpringCache");
				cacheService.removeCache("testSpringCache2");
			}
			i++ ;
		}
	}
	
	

	/** 第一次执行向缓存中插入数据，以后如果缓存中能查到数据就，就不再去修改缓存数据 */
	//运行多次根据控制台信息看结果
	@Test
	public void testRedisCacheSet(){
		Object value = cacheService.getFromDB("testSpringCache","fromDb_value") ;
		System.out.println("the value is:   " + value );
		Assert.notNull(value);
	}
	
	/**测试没有指定缓存的key ,默认key方式 */
	@Test
	public void testRedisCacheSetNOKEY(){
		for(int i = 1; i<100; i++ ){
			Object value = cacheService.getFromDB_NOKEY("testSpringCacheN"+i ,"fromDb_value") ;
			System.out.println("the value is:   " + value );
			Assert.notNull(value);
		}
	}
	
	/**测试没有指定缓存的key ,并且默认key里面包含的 pojo对象 */
	@Test
	public void testRedisCacheSetNOKEYObject(){
		ChildEntity ch = new ChildEntity() ;
		ch.setId("123");
		Object value = cacheService.getFromDB_NOKEY("testSpringCacheNObj",ch) ;
		System.out.println("the value is:   " + value );
		Assert.notNull(value);
	}
	
	 
	
	/** 根据条件判读是否加入缓存 */
	@Test
	public void testRedisCacheSetCondition(){
		Object value = cacheService.getFromDBCondition("tesxxx","fromDb_value") ; // key的长度大于 4 ，才写入缓存
		System.out.println("the value is:   " + value );
		Assert.notNull(value);
	}
	
	/** 每次执行都去 修改缓存 */
	@Test
	public void testRedisCacheSetEver(){ 
		
		Object value = cacheService.getFromDBEver("testSpringCache","fromDb_value") ;
		System.out.println("the value is:   " + value );
	}
	 
	/** 清除对应的缓存*/
	@Test
	public void testRedisCacheRemove(){
		
	     cacheService.removeCache("testSpringCache") ;
		
	     System.out.println("test over ..");
		 
	}
	
	/** 清除所有的缓存*/
	@Test
	public void testRedisCacheRemoveAll(){
		
		cacheService.removeCacheAll( ) ;
		
		System.out.println("test over ..");
		
	}
	
	 
	/** 测试pojo对象，放入缓存*/
	@Test
	public void testRedisCacheSetObject(){
		ChildEntity child = new ChildEntity() ;
		child.setId("123_456");
		ChildEntity value = (ChildEntity)cacheService.getFromDB("testSpringCache_obj" ,child ) ;
		System.out.println("get value :" + value.getId() );
		Assert.notNull(value);
	}
	
	/** 测试  删除pojo对象*/
	@Test
	public void testRedisCacheRemoveObject(){
		ChildEntity child = new ChildEntity() ;
		child.setId("123_456");
		cacheService.removeCache("testSpringCache_obj" );
		
	}

	
	 /**测试 map */
	@Test
	public void testRedisCacheSetMap(){
		
		Map<String,String> map = new HashMap<String,String>() ;
		map.put("username", "谷艳昭") ;
		map.put("password", "123") ;
		 
		Map<String,String> value = (Map<String,String>)cacheService.getFromDB("testSpringCache_map" ,map ) ;
		
		System.out.println("get value :" + value.get("username") );
	}
	
	/**map里面有pojo对象*/
	@Test
	public void testRedisCacheSetMapObject(){
		
		Map<String,Object> map = new HashMap<String,Object>() ;
		ChildEntity child = new ChildEntity() ;
		child.setId("123_456");
		child.setName("谷艳昭");
		map.put("child", child) ;
		
		Map<String,String> value = (Map<String,String>)cacheService.getFromDB("testSpringCache_mapObject" ,map ) ;
		
		System.out.println("get value :" + value.get("child") );
	}
	 
  
	
	
	
	
}
