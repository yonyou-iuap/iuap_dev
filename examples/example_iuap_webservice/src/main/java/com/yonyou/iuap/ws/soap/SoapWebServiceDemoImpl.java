package com.yonyou.iuap.ws.soap;

import java.util.Date;
import java.util.UUID;

import com.yonyou.iuap.ws.entity.DemoEntity;

public class SoapWebServiceDemoImpl implements SoapWebServiceDemo{

	public DemoEntity getEntityInfo() {
		DemoEntity entity=new DemoEntity();
		entity.setCount(10);
		entity.setFlag(1);
		entity.setId(UUID.randomUUID().toString());
		entity.setIsdefault(true);
		entity.setName("username");
		entity.setPrice(100);
		entity.setStartDate(new Date());
		return entity;
	}

}
