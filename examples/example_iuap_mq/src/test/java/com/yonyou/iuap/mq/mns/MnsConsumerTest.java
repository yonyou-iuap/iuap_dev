package com.yonyou.iuap.mq.mns;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.category.UnStable;
import org.springside.modules.test.spring.SpringContextTestCase;

@Category(UnStable.class)
@DirtiesContext
@ContextConfiguration(
	locations = { 
		"classpath:applicationContext-test.xml"
	}
)
public class MnsConsumerTest extends SpringContextTestCase {
	
	@Resource(name="simpleListener")
	private MnsSimpleListener simpleListener;
	
	@Resource(name="otherSimpleListener")
	private MnsOtherSimpleListener otherSimpleListener;
	
	@Test
	public void queueMessage() throws InterruptedException {
		while(true){
			System.out.println("running...");
			Thread.sleep(2000L);
		}
	}

}
