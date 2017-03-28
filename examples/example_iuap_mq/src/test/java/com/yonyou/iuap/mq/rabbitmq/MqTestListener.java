package com.yonyou.iuap.mq.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class MqTestListener implements MessageListener{

	private static Logger logger = LoggerFactory.getLogger(MqTestListener.class);
	
	public static int num;
	
	@Override
	public void onMessage(Message message) {
		num ++;
		logger.info("MQ ======== mq MqTestListener :" + new String(message.getBody()));
		throw new RuntimeException("test");
	}

}
