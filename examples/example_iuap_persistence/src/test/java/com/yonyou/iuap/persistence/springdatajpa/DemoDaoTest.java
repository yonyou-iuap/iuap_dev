package com.yonyou.iuap.persistence.springdatajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.yonyou.iuap.entity.demo.DemoEntity;
import com.yonyou.iuap.repository.demo.DemoEntityJpaDao;

@ContextConfiguration(
	locations = { 
		"classpath:applicationContext.xml",
		"classpath:applicationContext-persistence.xml"
	}
)
@TransactionConfiguration(defaultRollback=false)
public class DemoDaoTest extends SpringTransactionalTestCase {

	@Autowired
	private DemoEntityJpaDao dao;
	
	@Autowired
	private JdbcTemplate jt;
	
	@Test
	public void testDeleteById() throws Exception {
		
		DemoEntity demo = new DemoEntity();
	    demo.setId("testsavebyjdbc02");
		demo.setCode("code_test");
		demo.setMemo("memo_test");
		demo.setIsdefault("Y");
		demo.setName("name_test");
		dao.save(demo);
		
		dao.delete("testsavebyjdbc02");
		
		Assert.isNull(dao.findOne("testsavebyjdbc02"));
		
	}
	
	
}
