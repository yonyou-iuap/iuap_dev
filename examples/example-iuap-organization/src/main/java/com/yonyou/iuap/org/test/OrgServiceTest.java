package com.yonyou.iuap.org.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yonyou.iuap.org.entity.OrgEntity;
import com.yonyou.iuap.org.service.IOrgService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:organization-applicationContext.xml" })
public class OrgServiceTest {
	
	private static Logger logger = LoggerFactory.getLogger(OrgServiceTest.class);

	@Autowired
	private IOrgService orgService;
	
	@Test
	public void testOrgSevice(){
		OrgEntity orgEntity = orgService.queryById("1");
		logger.error(orgEntity.getCode());
	}
}
