package com.yonyou.iuap.mq.rabbitmq;

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
		"classpath:applicationContext-mq-consumer-test.xml"
	}
)
public class RabbitMqConsumerTest extends SpringContextTestCase {
	
	@Autowired
	private MqTestListener listener;
	
	@Autowired
	private MqTestOtherListener otherlistener;
	
	@Autowired
	private MqTestFanoutListener fanoutListener;
	
	@Test
	public void queueMessage() throws InterruptedException {
		while(true){
			//System.out.println("MenuAckMqTestListener:" + MenuAckMqTestListener.num);
			System.out.println("MqTestListener:" + MqTestListener.num);
			System.out.println("MqTestOtherListener:" + MqTestOtherListener.num);
			Thread.sleep(2000L);
		}
	}

	@Test
	public void fanoutQueueMessage() throws InterruptedException {
		while(true){
			System.out.println(MqTestFanoutListener.num);
			Thread.sleep(2000L);
		}
	}
}
