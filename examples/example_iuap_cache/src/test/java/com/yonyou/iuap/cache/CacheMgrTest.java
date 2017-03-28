package com.yonyou.iuap.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;
import org.springside.modules.nosql.redis.JedisTemplate;
import org.springside.modules.nosql.redis.JedisTemplate.PipelineAction;
import org.springside.modules.nosql.redis.JedisTemplate.PipelineActionNoResult;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.yonyou.iuap.cache.vo.ChildEntity;
import com.yonyou.iuap.cache.vo.MasterEntity;

public class CacheMgrTest {
	
	private static ApplicationContext context;
	
	private static CacheManager cacheManager;
	
	private static JedisTemplate jedisTemplate;
	
	//private static JedisShardedTemplate jedisShardedTemplate;
	
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml","classpath:applicationContext-cache.xml"});
		cacheManager = (CacheManager)context.getBean("cacheManager");
		jedisTemplate = (JedisTemplate)context.getBean("jedisTemplate");
		//jedisShardedTemplate = (JedisShardedTemplate)context.getBean("jedisShardedTemplate");
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 普通存取删除测试,get,set,remove,exists
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSimpleCache() throws Exception {
		String key = "testKey";
		cacheManager.set(key, "test");
		
		Assert.isTrue(cacheManager.exists(key));
		
		String cacheValue = (String)cacheManager.get(key);
		System.out.println(cacheValue);
		
		cacheManager.removeCache(key);
		
		Assert.isNull(cacheManager.get(key));
	}
	
	/**
	 * hashmap测试,hget,hset,hdel,hexist
	 * 
	 * @throws Exception
	 */
	@Test
	public void testHashOperate() throws Exception {
		String key = "test_hkey";
		
		String filed_name_01 = "filed_name_01";
		String filed_name_02 = "filed_name_02";
		
		cacheManager.hset(key, filed_name_01, "filed_value_01");
		cacheManager.hset(key, filed_name_02, "filed_value_02");
		
		Assert.isTrue("filed_value_01".equals(cacheManager.hget(key, filed_name_01)));
		Assert.isTrue("filed_value_02".equals(cacheManager.hget(key, filed_name_02)));
		
		Assert.isTrue(cacheManager.hexists(key, filed_name_01));
		
		cacheManager.hdel(key, filed_name_01);
		Assert.isNull(cacheManager.hget(key, filed_name_01));
		
		
	}
	
	@Test
	public void testExpire() throws InterruptedException {
		String key = "test_expire_key";
		String value = "test_value";
		
		int timeout = 5;
		cacheManager.setex(key, value, timeout);
		Assert.notNull(cacheManager.get(key));
		
		Thread.sleep(6000L);
		
		Assert.isNull(cacheManager.get(key));
	}
	
	/**
	 * 测试incr和decr
	 */
	@Test
	public void testIncrAndDecr() {
		String key = "test_key_incr";
		cacheManager.removeCache(key);
		Long num = 10L;
		cacheManager.initNumForIncr(key, num);
		
		long cacheNum = cacheManager.incr(key);
		Assert.isTrue(11L == cacheNum);
		System.out.println(cacheNum);
		
		cacheNum = cacheManager.incr(key);
		Assert.isTrue(12L == cacheNum);
		
		cacheNum = cacheManager.decr(key);
		Assert.isTrue(11L == cacheNum);
		
	}
	
	/**
	 * 测试缓存简单的pojo
	 */
	@Test
	public void testCacheObject() {
		MasterEntity t = new MasterEntity();
		t.setId("testid");
		t.setDefault(false);
		
		List<ChildEntity> cs = new ArrayList<ChildEntity>();
		for (int i = 0; i < 5; i++) {
			ChildEntity c = new ChildEntity();
			c.setId("testcid" + i);
			c.setName("testname" + i);
			cs.add(c);
		}
		t.setChildren(cs);
		
		cacheManager.set("objectkey", t);
		
		MasterEntity getObj = cacheManager.get("objectkey");
		System.out.println(getObj.getId());
		Assert.notNull(getObj);
		
	}
	
	@Test
	public void testPipeline(){
		cacheManager.setAndExpireInPipeline("test_pipeline", "test_pipeline_value", 20);
		String value=cacheManager.get("test_pipeline");
		System.out.println(value);
		Assert.notNull(value);
	}
	
	@Test
	public void testPipelineExecute(){
		PipelineActionNoResult actionNoResult= new PipelineActionNoResult(){
			@Override
			public void action(Pipeline Pipeline) {
				Pipeline.set("test_pipeline", "test test_pipeline");
				Pipeline.set("pipeline_entity","entity");
			}};
		
		PipelineAction action= new PipelineAction(){
			@Override
			public List<Object> action(Pipeline Pipeline) {
				List<Object> list = new ArrayList<Object>();
				list.add(Pipeline.get("test_pipeline").toString());
				list.add(Pipeline.get("pipeline_entity").toString());				
				return list;
			}};
			

				
		cacheManager.piplineExecute(actionNoResult);
		List<String> result = cacheManager.piplineExecute(action);
		for(int i=0;i<result.size();i++){
			System.out.println(result.get(i));
		}
		Assert.notNull(result);
	}
	
	@Test
	public void testExpireAt(){
		String key = "test_expireat";
		long ts = System.currentTimeMillis();
		long tsat = (ts / 1000) + 60;
		cacheManager.set(key, "test");
		
		Jedis jedis = jedisTemplate.getJedisPool().getResource();
		jedis.expireAt(key, tsat);
		String value=cacheManager.get(key);
		jedisTemplate.getJedisPool().returnResource(jedis);
		Assert.notNull(value);
		
	}
	
	@Test
	public void testWithThreads() throws InterruptedException {
		long t1 = System.currentTimeMillis();
		int size = 0;
		int threadNum = 20;
		while (size < 10) {
			ExecutorService service = Executors.newFixedThreadPool(threadNum);
			for (int i = 0; i < threadNum; ++i) {
				final int ai = i % 10;
				Callable<Void> task = new Callable<Void>() {
					@Override
					public Void call() throws Exception {
						String key = "testKey" + ai;
						cacheManager.set(key, "test");
						System.out.println(cacheManager.get(key));
						return null;
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
	
	@Test
	public void testBigSizeCache() throws Exception {
		for (int i = 0; i < 1000000; i++) {
			cacheManager.set("bWEsL"+ UUID.randomUUID() + i, UUID.randomUUID().toString()+UUID.randomUUID().toString());
			cacheManager.set("d2ViL"+ UUID.randomUUID() + i, UUID.randomUUID().toString()+UUID.randomUUID().toString());
			cacheManager.set("bWEsL"+ UUID.randomUUID() + i, UUID.randomUUID().toString()+UUID.randomUUID().toString());
		}
	}
	
	@Test
	public void testCount() throws Exception {
		long t1 = System.currentTimeMillis();
		Set<String> users = cacheManager.getJedisTemplate().getJedisPool().getResource().keys("bWEsL*");
		int c = users.size();
		int i = 0;
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println(string);
			System.out.println(i++);
		}
	    long t2 = System.currentTimeMillis();
	    
	    System.out.println(c);
	    System.out.println((t2 - t1));
	}
	
	@Test
	public void testLicense() throws Exception {
		Jedis jedis = jedisTemplate.getJedisPool().getResource();
		
		//init licdata
//		for (int i = 0; i < 31; i++) {
//			String id = UUID.randomUUID().toString();
//			jedis.set("licdata:" + i + ":" + "name" + i + "$" + id, new java.util.Date(System.currentTimeMillis()).toLocaleString());
//		}
		
		/*
		for (int i = 0; i < 1000; i++) {
			String id = UUID.randomUUID().toString();
			jedis.set("licdata:11:" + "name" + i + "$" + id, new java.util.Date(System.currentTimeMillis()).toLocaleString());
		}
		*/
		
		//模糊查询
		/*
		Set keyset = jedis.keys("licdata:11:name140*");
		System.out.println(keyset.size());
		for (Iterator iterator = keyset.iterator(); iterator.hasNext();) {
			String object = (String) iterator.next();
			System.out.println(object);
		}
		*/
		
		jedisTemplate.getJedisPool().returnResource(jedis);
	}
	
	@Test
	public void testSortedSet() throws Exception {
		Jedis jedis = jedisTemplate.getJedisPool().getResource();
		
		/*
		for (int i = 1000; i < 100000; i++) {
			jedis.zadd("licdata_11_keys", Double.valueOf(i+""), "content_" + i);
		}
		*/
		
		Set<String> keys = jedis.zrevrangeByScore("licdata_11_keys", 10000L, 0L, 50, 100);
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.err.println(string);
		}
		
		jedisTemplate.getJedisPool().returnResource(jedis);
	}
	
	@Test
	public void testPageBySortedSet() throws Exception {
		Jedis jedis = jedisTemplate.getJedisPool().getResource();
		
		System.out.println("总条数：" + jedis.zcount("licdata_11_keys", "0", "1000000"));
		
		int i = 0;
		int page = 1;
		while(i<100000){
			Set<String> keys = jedis.zrevrangeByScore("licdata_11_keys", 100000L, 0L, i, 100);
			Thread.sleep(10L);
			System.err.println("页数:" + page ++);
			i = i + 100;
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				System.err.println(key);
			}
		}
		
		jedisTemplate.getJedisPool().returnResource(jedis);
	}
}
