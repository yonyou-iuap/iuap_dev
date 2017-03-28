package com.yonyou.iuap.lock.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yonyou.iuap.context.ContextHolder;
import com.yonyou.iuap.lock.zkpool.ZkPool;

public class CustomServletContextListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent sce) {
    }

	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		ContextHolder.setContext(wac);
		GenericObjectPoolConfig config = (GenericObjectPoolConfig)ContextHolder.getContext().getBean("zkPoolConfig");
		ZkPool.initPool(config);
	}

}