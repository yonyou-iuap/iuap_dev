package com.yonyou.iuap.mq.service;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.category.UnStable;
import org.springside.modules.test.spring.SpringContextTestCase;

@Category(UnStable.class)
@DirtiesContext
@ContextConfiguration(
	locations = { 
		"classpath:applicationContext-test.xml",
		"classpath:applicationContext-mq-provider-test.xml"
	}
)
public class MqServiceTest extends SpringContextTestCase {

	@Autowired
	private IMqService mqService;
	
	//rabbitmq,spring 配置文件中配置mqService为com.yonyou.iuap.mq.rabbit.RabbitMqService
	@Test
	public void queueMessage1() throws Exception {
		String qName = "iuap-direct-exchange";
		String key = "simple_queue_key";
		String msg = "iuap mq msg test! ";
		
		mqService.sendMsg(qName, key, msg);
	}
	
	//mns，spring 配置文件中配置mqService，com.yonyou.iuap.mq.mns.AliyunMnsService
	@Test
	public void queueMessage2() throws Exception {
		String qName = "testali-mq-01";
		String key = null;
		String msg = "iuap mq msg test! ";
		mqService.sendMsg(qName, key, msg);
	}
	
	@Test
	public void testTopicMns() throws Exception {
		String topicName = "test-topic-01";
		String msg = "topic message test! ";
		
		mqService.publishMsg(topicName, msg);
	}
	

}
