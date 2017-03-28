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
import com.yonyou.iuap.cache.redis.ClusterCacheService;
import com.yonyou.iuap.cache.vo.ChildEntity;

public class SpringRedisCacheClusterTest {
	
	private static ApplicationContext context;
	
	
	
	
	private ClusterCacheService cacheService ;
	
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml","classpath:applicationContext-cacheCluster.xml"});
		cacheService = (ClusterCacheService)context.getBean("clusterCacheService");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/** 综合测试  redis集群存储内容，包含了查询和删除 */
	@Test
	public void testAllOper() throws InterruptedException{
		int i=1 ;
		while( true ){
			System.out.println("执行@cacheEnable查询操作======"  );
			Object value = cacheService.getFromDB("testSpringClusterCache","fromDb_value") ; //只有第一次才去修改缓存
			System.out.println("the value is:   " + value );
			Assert.notNull(value);
			
			System.out.println("执行@cachePut查询操作======"  );
			cacheService.getFromDBEver("testSpringClusterCache2", "every to modify") ; //每次都会去修改缓存
			System.out.println("########################"  );
			Thread.sleep(10000);
			
			if( i%5 == 0){ //运行五次后 ，清除缓存
				System.out.println("执行清除操作======"  );
				cacheService.removeCache("testSpringClusterCache");
				cacheService.removeCache("testSpringClusterCache2");
			}
			i++ ;
		}
	}

	/** 第一次执行向缓存中插入数据，以后如果缓存中能查到数据就，就不再去修改缓存数据 */
	//运行多次根据控制台信息看结果
	@Test
	public void testRedisCacheSet(){
		Object value = cacheService.getFromDB("testSpringClusterCache","fromDb_value") ;
		Assert.notNull(value);
		System.out.println(value);
		
	}
	
	/**  插入多条数据 */
	@Test
	public void testRedisCacheSetMul(){
		for(int i=1 ; i<=100 ;i++){
			
			Object value = cacheService.getFromDB("testM"+i,"fromDb_value") ;
			Assert.notNull(value);
		}
		
	}
	 
	//删除缓存
	@Test
	public void testRedisCacheRemove(){
	     cacheService.removeCache("testSpringClusterCache") ;
	     System.out.println("test over ..");
		 
	}
	
	
	@Test
	public void testRedisCacheRemoveAll(){
		
		cacheService.removeCacheAll( ) ;
		
		System.out.println("test over ..");
		
	}
	
 
	
	/** 测试pojo对象放入缓存 */
	@Test
	public void testRedisCacheSetObject(){
		ChildEntity child = new ChildEntity() ;
		child.setId("123_456");
		
		ChildEntity value = (ChildEntity)cacheService.getFromDB("testSpringClusterpojo" ,child ) ;
		Assert.notNull(value);
		System.out.println("get value :" + value.getId() );
	}
	//执行方法后删除缓存
	@Test
	public void testRedisCacheRemoveObject(){
		cacheService.removeCache("testSpringClusterpojo");
	}
	//执行方法前删除缓存
	@Test
	public void testRedisCacheRemoveObjectBefore(){
		cacheService.removeCacheBefore("testSpringClusterpojo");
	}

	 
	@Test
	public void testRedisCacheSetMap(){
		
		Map<String,String> map = new HashMap<String,String>() ;
		map.put("username", "谷艳昭") ;
		map.put("password", "123") ;
		Map<String,String> value = (Map<String,String>)cacheService.getFromDB("testSpringClusterCache_map" ,map ) ;
		Assert.notNull(value);
		System.out.println("get value :" + value.get("username") );
		
		cacheService.removeCache("testSpringClusterCache_map");
	}
	
	@Test
	public void testRedisCacheSetMapObject(){
		
		Map<String,Object> map = new HashMap<String,Object>() ;
		ChildEntity child = new ChildEntity() ;
		child.setId("123_456");
		child.setName("谷艳昭");
		map.put("child", child) ;
		Map<String,ChildEntity> value = (Map<String,ChildEntity>)cacheService.getFromDB("testSpringCache_mapObject" ,map ) ;
		Assert.notNull(value);
		System.out.println("get value :" + value.get("child").getName() );
		
		cacheService.removeCache("testSpringCache_mapObject");
	}
	
	
	public String set( String key ,String value ){
		
		System.out.println("test spring cache ,query from db ..");
		
		return "first" ;
		
	}
	
	

  
	
	
	
	
}
