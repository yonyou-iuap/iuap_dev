package com.yonyou.iuap.example.disconf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.disconf.client.common.annotations.DisconfUpdateService;
import com.yonyou.iuap.disconf.client.common.update.IDisconfUpdate;
import com.yonyou.iuap.utils.PropertyUtil;

@Service
@DisconfUpdateService(confFileKeys = {"application.properties"})
public class DisConfigUpdateCallback implements IDisconfUpdate {
	
	private static final Logger LOG = LoggerFactory.getLogger(DisConfigUpdateCallback.class);

    public void reload() throws Exception {
    	PropertyUtil.reload();
    	LOG.info("DisConfigUpdateCallback called! application.properties has changed!");
    	
    }
}