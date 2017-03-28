package com.yonyou.iuap.persistence.springjdbc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yonyou.iuap.service.demo.JdbcTemplateService;

public class JdbcTemplateTest {
	
	public static ApplicationContext context;
	
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml","classpath:applicationContext-persistence.xml"});
	}
	
	@Test
	public void testSpringJdbc() throws Exception {
		JdbcTemplateService service = context.getBean(JdbcTemplateService.class);
		service.lockTest();
	}
		
}
