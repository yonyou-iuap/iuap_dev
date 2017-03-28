package com.yonyou.iuap.persistence.springjdbc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import com.yonyou.iuap.entity.demo.DemoEntity;
import com.yonyou.iuap.repository.demo.DemoEntityJdbcDao;

public class DemoJdbcDaoTest {
public static ApplicationContext context;
	
	private DemoEntityJdbcDao dao;
	
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml","classpath:applicationContext-persistence.xml"});
		dao = context.getBean(DemoEntityJdbcDao.class);
	}
	
	@Test
	public void testSpringJdbc() throws Exception {
		
		DemoEntity demo = dao.queryById("12b8755a-b4b9-43bf-aeaa-f18a4f43eaa1");
		
		Assert.notNull(demo);
	}
		
	
}
