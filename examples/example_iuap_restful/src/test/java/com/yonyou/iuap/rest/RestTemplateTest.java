package com.yonyou.iuap.rest;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration(locations = { "classpath:applicationContext.xml","file:src/main/webapp/WEB-INF/spring-mvc.xml" })
public class RestTemplateTest {
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private RestTemplate template;
	
	@Before
	public void setup() {
	}
	
/*	@Test
	public void testRestTemplate() {
		String url = "https://www.example.com/zoos";
		//String url = "https://repo1.maven.org/maven2/com/aliyun/mns/aliyun-sdk-mns/";
		//String url = "http://fanyi.baidu.com/translate?aldtype=16047&query=Derivation&keyfrom=baidu&smartresult=dict&lang=auto2zh#en/zh/Pseudo";
		ResponseEntity<String> result = template.getForEntity(url, String.class);
		System.out.println(result.getBody());
		
	}*/
	
	@Test
	public void testHttps() {
		String url = "https://ec.yyuap.com/restful/api/demo/restservice";
		//String url = "https://repo1.maven.org/maven2/com/aliyun/mns/aliyun-sdk-mns/";
		//String url = "http://fanyi.baidu.com/translate?aldtype=16047&query=Derivation&keyfrom=baidu&smartresult=dict&lang=auto2zh#en/zh/Pseudo";
		ResponseEntity<String> result = template.postForEntity(url, null, String.class);
		System.out.println(result.getBody());
		Assert.notNull(result);
	}
	
/*	@Test
	public void testMobileLoginPost() {
		String url = "http://localhost:8080/iuap-quickstart/malogin/formLogin?username=zb&password=666666a";
		HttpHeaders requestHeaders = new HttpHeaders();
		//resttemplate方法调用时候，携带cookie
		requestHeaders.add("Cookie", "token=testtoken");
		HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
		ResponseEntity<String> rss = template.exchange(url, HttpMethod.POST, requestEntity, String.class, new Object[]{});
		System.out.println(rss.getBody());
		
		List<String> cookies = rss.getHeaders().get("Set-Cookie");
		for (String string : cookies) {
			System.err.println(string);
		}
		System.out.println(cookies);
		
		
	}*/
}
