package com.yonyou.iuap.mq.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class MqTestOtherListener implements MessageListener{

	private static Logger logger = LoggerFactory.getLogger(MqTestOtherListener.class);
	
	public static int num;
	
	@Override
	public void onMessage(Message message) {
		num ++;
		logger.info("MQ ======== mq MqTestOtherListener :" + new String(message.getBody()));
	}

}
