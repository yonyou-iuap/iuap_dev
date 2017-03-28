package com.yonyou.iuap.mq.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;

@Service
public class MenuAckMqTestListener implements ChannelAwareMessageListener{

	private static Logger logger = LoggerFactory.getLogger(MenuAckMqTestListener.class);
	
	public static int num;

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		num ++;
		String msg = new String(message.getBody());
		logger.info("MQ ======== mq MqTestListener :" + msg);
		
		try {
			if(num < 5){
				Integer.parseInt("test");
			}
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
		} catch (Exception e) {
			logger.error("consume message error!",e);
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}
		
	}

}
