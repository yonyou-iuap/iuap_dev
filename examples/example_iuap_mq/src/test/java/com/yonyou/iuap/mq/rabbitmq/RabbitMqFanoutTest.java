package com.yonyou.iuap.mq.rabbitmq;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.category.UnStable;
import org.springside.modules.test.spring.SpringContextTestCase;

import com.yonyou.iuap.mq.rabbit.RabbitMQProducer;

@Category(UnStable.class)
@DirtiesContext
@ContextConfiguration(
	locations = { 
		"classpath:applicationContext-test.xml",
		"classpath:applicationContext-mq-provider-test.xml"
	}
)
public class RabbitMqFanoutTest extends SpringContextTestCase {

	@Autowired
	private RabbitMQProducer mqSender;

	public void setMqSender(RabbitMQProducer mqSender) {
		this.mqSender = mqSender;
	}

	@Test
	public void fanoutMessage() throws InterruptedException {
		long t1 = System.currentTimeMillis();
		int msgCount = 10;
		for (int i = 0; i < msgCount; i++) {
			mqSender.publishMsg("iuap-fanout-exchange", new TestMq("testmq fanout " + i, "name" + i));
		}
		long t2 = System.currentTimeMillis();
		System.out.println("发布订阅方式发送消息" + msgCount + "个消耗时间(毫秒)" + (t2 - t1));
	}
	
	class TestMq {
		private String id;

		private String name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public TestMq(String id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

	}

}
