package com.yonyou.iuap.crm.common.listener;

import java.security.KeyPair;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yonyou.iuap.cache.CacheManager;
import com.yonyou.iuap.context.ContextHolder;
import com.yonyou.iuap.lock.zkpool.ZkPool;
import com.yonyou.iuap.security.utils.RSAUtils;
import com.yonyou.iuap.utils.PropertyUtil;

public class CustomServletContextListener implements ServletContextListener {
	
	private static String keypairKey = "YYQC_KEY_PAIR";

    public void contextDestroyed(ServletContextEvent arg0) {
    }

    public void contextInitialized(ServletContextEvent event) {
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        ContextHolder.setContext(wac);

        if ("false".equals(PropertyUtil.getPropertyByKey("usezookeeper"))) {
            return;
        }
        try {
        	GenericObjectPoolConfig config = (GenericObjectPoolConfig)ContextHolder.getContext().getBean("zkPoolConfig");
    		ZkPool.initPool(config);
            
            // 初始化keypair
            CacheManager cm = (CacheManager)wac.getBean("cacheManager");
            if(cm.exists(keypairKey)){
            	KeyPair kp = (KeyPair)cm.get(keypairKey);
            	RSAUtils.setKeyPair(kp);
            	System.out.println("get keypair from redis ...");
            } else {
            	KeyPair gk = RSAUtils.generateKeyPair();
            	cm.set(keypairKey, gk);
            	System.out.println("generate keypair and put it into redis ...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}