package com.yonyou.iuap.ws;

import org.junit.Test;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.yonyou.iuap.ws.entity.DemoEntity;

public class RestWebServiceTest {
	
	private static String resourceUrl = "http://localhost:8080/iuap-webservice-example/cxf/jaxrs/restws/resttest";
    
	private RestTemplate restTemplate = new RestTemplate();
	
	@Test
	public void demoJaxrsTest() {
		DemoEntity demo = restTemplate.getForObject(resourceUrl, DemoEntity.class, 1L);
		System.out.println(demo);
		Assert.notNull(demo);
	}

}
