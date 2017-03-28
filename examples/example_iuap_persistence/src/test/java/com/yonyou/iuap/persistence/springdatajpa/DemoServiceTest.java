package com.yonyou.iuap.persistence.springdatajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.yonyou.iuap.entity.demo.DemoEntity;
import com.yonyou.iuap.service.demo.DemoService;

@ContextConfiguration(
	locations = { 
		"classpath:applicationContext.xml",
		"classpath:applicationContext-persistence.xml"
	}
)
@TransactionConfiguration(defaultRollback=true)
public class DemoServiceTest extends SpringTransactionalTestCase {
	
	@Autowired
	private DemoService service;
	
	@Test
	public void testQueryById() throws Exception {
				
		DemoEntity demo2 = service.getDemoById("12b8755a-b4b9-43bf-aeaa-f18a4f43eaa1");
		Assert.notNull(demo2);
		
	}
	
	@Test
	public void testSave() throws Exception {
		
		DemoEntity demo = new DemoEntity();
		//demo.setId(UUID.randomUUID().toString());
		demo.setCode("code_test");
		demo.setMemo("memo_test");
		demo.setIsdefault("Y");
		demo.setName("name_test");
		
		DemoEntity savedDemo = service.saveEntity(demo);
		System.out.println(savedDemo.getId());
		Assert.notNull(service.getDemoById(savedDemo.getId()));
	}
	
	@Test
	public void testDelete() throws Exception {
		
		service.deleteById("12b8755a-b4b9-43bf-aeaa-f18a4f43eaa1");
		DemoEntity demo=service.getDemoById("12b8755a-b4b9-43bf-aeaa-f18a4f43eaa1");
		Assert.isNull(demo);
	}

}
