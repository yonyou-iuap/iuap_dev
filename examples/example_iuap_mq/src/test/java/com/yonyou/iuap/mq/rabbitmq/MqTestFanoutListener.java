package com.yonyou.iuap.mq.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class MqTestFanoutListener implements MessageListener{

	private static Logger logger = LoggerFactory.getLogger(MqTestFanoutListener.class);
	
	public static int num;
	
	@Override
	public void onMessage(Message message) {
		num ++;
		try {
			logger.info("MQ ======== mq listener :" + new String(message.getBody()));
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			logger.error("onMessage error!",e);
		}
	}

}
