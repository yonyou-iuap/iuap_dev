package com.yonyou.iuap.event.local.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yonyou.iuap.event.local.BusinessEvent;
import com.yonyou.iuap.event.local.BusinessException;
import com.yonyou.iuap.event.local.LocalEventDispatcher;
import com.yonyou.iuap.event.local.common.SpringContextUtil;

public class LocalEventDispatcherTest {

	public static void main(String[] args) throws BusinessException{
		start();
		BusinessEvent businessEvent = new BusinessEvent("USER", "ADD_BEFORE", "tenantCode-000", System.currentTimeMillis());
		LocalEventDispatcher.fireEvent(businessEvent);
	}
	
	private static void start(){
		SpringContextUtil springContextUtil = new SpringContextUtil();
		springContextUtil.setApplicationContext(new ClassPathXmlApplicationContext("classpath:eventLocal-applicationContext.xml"));
	}
}
