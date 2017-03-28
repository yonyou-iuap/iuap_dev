package com.yonyou.iuap.persistence.oid;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yonyou.iuap.context.ContextHolder;
import com.yonyou.iuap.persistence.utils.IDGenerator;

public class TestIdGenerator {
	
	public static ApplicationContext context;
	
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml","classpath:applicationContext-cache.xml","classpath:applicationContext-oid.xml"});
		ContextHolder.setContext(context);
	}
	
	@Test
	public void testUUIDGenerator(){
		String uuid = IDGenerator.generateObjectID(null);
		System.out.println(uuid);
	}
	
	/**
	 * 修改配置文件，设置id生成类型和对应的初始值
	 * 
	 * idtype=redis
	 * IUAP_PRIMARY_MYMODULE_START_VALUE=10000
	 */
	@Test
	public void testRedisGenerator(){
		String module = "MYMODULE";
		String id = IDGenerator.generateObjectID(module);
		System.out.println(id);
	}
	
	
	
	@Test
	public void testCustomGenerator(){
		String id = IDGenerator.generateObjectID("custom");
		System.out.println(id);
	}
	
	/**
	 * 修改配置文件，设置id生成类型
	 * 
	 * idtype=snowflake
	 */
	@Test
	public void testSnowFlake(){
		//OID_WORKERID,系统属性或者环境变量中设置workid
		//System.setProperty("OID_WORKERID", "1");
		
		for (int i = 0; i < 100000; i++) {
			String id = IDGenerator.generateObjectID(null);
			System.out.println(id);
		}
	}
	
	@Test
	public void testUapOid(){
		// 八位的字符，与租户方案中的schema命名规范保持一致
		String defautlSchema = "uapcloud";
		for (int i = 0; i < 100; i++) {
			String id = IDGenerator.generateObjectID(defautlSchema);
			System.out.println(id);
		}
		defautlSchema = "tenant01";
		for (int i = 0; i < 100; i++) {
			String id = IDGenerator.generateObjectID(defautlSchema);
			System.out.println(id);
		}
	}
	
}
