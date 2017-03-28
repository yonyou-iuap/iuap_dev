package com.yonyou.iuap.ws.rest;

import java.util.Date;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.yonyou.iuap.ws.entity.DemoEntity;


@Path(value = "/restws")   
public class RestFulWebServiceDemo {
	
	@GET  
    @Path(value = "/resttest")  
	@Produces({"application/json", "text/xml"})
	public DemoEntity entityInfo(){
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
