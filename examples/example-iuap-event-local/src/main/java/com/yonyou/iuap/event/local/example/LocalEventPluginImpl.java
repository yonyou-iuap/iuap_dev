package com.yonyou.iuap.event.local.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.iuap.event.local.BusinessEvent;
import com.yonyou.iuap.event.local.IBussinessListener;

public class LocalEventPluginImpl implements IBussinessListener{
	
	private static Logger logger = LoggerFactory.getLogger(LocalEventPluginImpl.class);
	/**
	 * 事件响应
	 */
	public void doAction(BusinessEvent businessEvent) {
		logger.error("doAction, time="+(System.currentTimeMillis()-Long.valueOf(String.valueOf(businessEvent.getUserObject())))+",event="+businessEvent);
	}
	
	public void doBackAction(BusinessEvent businessEvent) {
		logger.error("doBackAction, event="+businessEvent);
	}
}
