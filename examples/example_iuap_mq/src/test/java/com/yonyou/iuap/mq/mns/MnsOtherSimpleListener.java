package com.yonyou.iuap.mq.mns;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.model.Message;

public class MnsOtherSimpleListener extends AbstractMessageListener {
	static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MnsOtherSimpleListener.class); // auto append.

	public MnsOtherSimpleListener(String queueName, CloudAccount mnsAccount, int waitSeconds) {
		super(queueName, mnsAccount, waitSeconds);
	}

	@Override
	public void onMessage(Message message) {
		LOGGER.info(message.getMessageBody());
	}

}
