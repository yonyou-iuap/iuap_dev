package com.yonyou.iuap.mq.mns;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.model.Message;

public class MnsSimpleListener extends AbstractMessageListener {
	static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MnsSimpleListener.class); // auto append.

	public MnsSimpleListener(String queueName, CloudAccount mnsAccount, int waitSeconds) {
		super(queueName, mnsAccount, waitSeconds);
	}

	/**
	 * onMessage处理的时间过长，有可能会导致MNS将此消息放入到inactive状态，其他监听会收到重复消息，
	 * 如果确认消费耗时，请修改队列配置--取出消息隐藏时长(秒)
	 */
	@Override
	public void onMessage(Message message) {
		LOGGER.info(message.getMessageBody());
		
		if(message.getMessageBody().contains("exception")){
			LOGGER.error(message.getMessageBody());
			throw new RuntimeException("test mq with exception！");
		}
	}

}
