package com.yonyou.iuap.ws.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.yonyou.iuap.ws.entity.DemoEntity;

@WebService  
public interface SoapWebServiceDemo {
	
	@WebMethod(operationName = "soaptest")  
    public DemoEntity getEntityInfo();

}


