package com.yonyou.iuap.example.disconf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.disconf.client.common.annotations.DisconfUpdateService;
import com.yonyou.iuap.disconf.client.common.update.IDisconfUpdate;

@Service
@DisconfUpdateService(itemKeys = {"testitem"})
public class DisconfItemUpdateCallback implements IDisconfUpdate {
	
	private static final Logger LOG = LoggerFactory.getLogger(DisconfItemUpdateCallback.class);

    public void reload() throws Exception {
    	LOG.info("DisconfItemUpdateCallback called! testitem has changed!");
    }
}