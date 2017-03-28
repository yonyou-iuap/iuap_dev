package com.yonyou.iuap.cache.redis;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * @Cacheable(value=”testcache”,key="#key" , condition=”#userName.length()>2”)
 * @Cacheable 主要的参数  
 * value  缓存的名称，在 spring 配置文件中定义，必须指定至少一个
 * key    缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合
 * condition   缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存
 * 
 * 
 * @CachePut(value=”testcache”,condition=”#userName.length()>2”)
                 和 @Cacheable 不同的是，它每次都会触发真实方法的调用
                 
   @CachEvict   主要针对方法配置，能够根据一定的条件对缓存进行清空      
 */


public class ClusterCacheService {
	static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ClusterCacheService.class); // auto append.
	//测试 redisCluster
	
	 @Cacheable(value = "redisClusterCache", key = "#key")  
	 public Object getFromDB(String key, Object obj ){
			
			LOGGER.info("exeute getFromDb ....");
			return  obj ;
	 }
		 
	 @CachePut(value = "redisClusterCache", key = "#key")  
	 public Object getFromDBEver(String key, Object obj ){
		 
		 LOGGER.info("exeute getFromDb ....");
		 return  obj ;
	 }
		 
	 //执行方法后删除缓存 
	  @CacheEvict(value="redisClusterCache")
	  public void removeCache(String key ){
		 LOGGER.info("exeute  service remove cache ....");
	  }
	  //执行方法前删除缓存
	  @CacheEvict(value="redisClusterCache",beforeInvocation=true)
	  public void removeCacheBefore(String key ){
		  LOGGER.info("exeute  service remove cache ....");
	  }
		
		
		@CacheEvict(value="redisClusterCache",allEntries=true)
		public void removeCacheAll(  ){
			LOGGER.info("exeute  service remove all cache ....");
		}
	
		
		 @Cacheable(value = "redisClusterCache", key = "#key")  
		public Object getClusterInfo( String key  ){
			LOGGER.info("exeute  service ....");
			return "clusterinfo_value" ;
		}
	
		 @Cacheable(value = "redisClusterCache", key = "#key")  
		 public Object getClusterInfo( String key ,Object obj ){
			 LOGGER.info("exeute  service ....");
			 return obj ;
		 }
	 
	
}
