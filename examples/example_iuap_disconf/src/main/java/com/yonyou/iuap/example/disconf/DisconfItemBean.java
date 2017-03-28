package com.yonyou.iuap.example.disconf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.yonyou.iuap.disconf.client.common.annotations.DisconfItem;

public class DisconfItemBean {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value(value = "2000d")
	private Double testitem;
	
    @DisconfItem(key = "testitem")
	public Double getTestitem() {
		return testitem;
	}

	public void setTestitem(Double testitem) {
		this.testitem = testitem;
		logger.info("dynamic property testitem in disconf has changed! new value is {}!", testitem);
	}
}