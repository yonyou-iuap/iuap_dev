package com.yonyou.iuap.ws;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.junit.Test;
import org.springframework.util.Assert;

import com.yonyou.iuap.ws.entity.DemoEntity;
import com.yonyou.iuap.ws.soap.SoapWebServiceDemo;

public class SoapWebServiceTest {
	
	public SoapWebServiceDemo creatClient() {
		String address = "http://localhost:8080/iuap-webservice-example/cxf/soap/SoapWebServiceDemo";

		JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
		proxyFactory.setAddress(address);
		proxyFactory.setServiceClass(SoapWebServiceDemo.class);
		SoapWebServiceDemo accountWebServiceProxy = (SoapWebServiceDemo) proxyFactory.create();
		((BindingProvider) accountWebServiceProxy).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
		Client client = ClientProxy.getClient(accountWebServiceProxy);
		HTTPConduit conduit = (HTTPConduit) client.getConduit();
		HTTPClientPolicy policy = conduit.getClient();
		policy.setReceiveTimeout(600000);
		return accountWebServiceProxy;
	}
	
	@Test
	public void demoSoapTest() {
		SoapWebServiceDemo demoService = creatClient();
		DemoEntity demo = demoService.getEntityInfo();
		System.out.println(demo);
		Assert.notNull(demo);
	}

}
