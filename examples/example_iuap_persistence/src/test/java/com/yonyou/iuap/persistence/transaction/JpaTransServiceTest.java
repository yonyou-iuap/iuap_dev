package com.yonyou.iuap.persistence.transaction;

import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yonyou.iuap.entity.quotation.IpuQuotation;
import com.yonyou.iuap.service.trans.JpaTransService;

public class JpaTransServiceTest {
	
	public static ApplicationContext context;
	
	private JpaTransService service;
	
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml","classpath:applicationContext-persistence.xml"});
		service = context.getBean(JpaTransService.class);
	}
	
	@Test
	public void testTransInJpa() throws Exception {
		
	}
	
}
