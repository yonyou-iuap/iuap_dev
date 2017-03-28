package com.yonyou.iuap.auth.example;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

/**
 * 利用resttemplate调用登录和rest服务的例子
 * <p>登录时候获取cookie，调用服务时候携带cookies</p>
 * 
 * @author liujmc
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration(
	locations = { 
		"classpath:applicationContext.xml",
		"classpath:applicationContext-cache.xml",
		"classpath:applicationContext-shiro.xml",
		"file:src/main/webapp/WEB-INF/spring-mvc.xml" 
	}
)
public class RestTemplateMaAuthTest {
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private RestTemplate template;
	
	@Before
	public void setup() {
	}
	
	@Test
	public void testMobileLoginPost() {
		
		
		HttpHeaders requestHeaders = new HttpHeaders();
		HashMap<String, Object> params = new HashMap<String, Object>();
		String text = "111111a" ;
		byte[] hashPassword = Digests.sha1(text.getBytes()) ;
		String password = Encodes.encodeHex(hashPassword);
		params.put("username", "test001");
		params.put("password", password);
		
		String url = "http://localhost:8080/example_iuap_auth/malogin/formLogin?username=test001&password=" + password;
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
		ResponseEntity<String> rss = template.exchange(url, HttpMethod.POST, requestEntity, String.class, params);
		System.out.println(rss.getBody());
		List<String> cookies = rss.getHeaders().get("Set-Cookie");
		for (String string : cookies) {
			System.err.println(string);
		}
		System.out.println(cookies);
	}
	
	@Test
	public void testDemoRest(){
		String url = "http://localhost:8080/example_iuap_auth/springmvc/demo/test";
        
		HttpHeaders requestHeaders = new HttpHeaders();
		//resttemplate方法调用时候，携带cookie
		requestHeaders.add("Cookie","token=bWEsLTEsV05kaDJJaXJjY0E5MWNRSkdkMHlPa2RJc29UTVRIQTZ0WnpSMEh3eW1OazgrbytLcm04OUZCcDFDcEEzQzRaYWN4cE9yemVUcEFjSy9BNU0vbmtQdnc9PQ");
		requestHeaders.add("Cookie","u_usercode=test001");
		requestHeaders.add("Cookie","u_logints=1459225051918");
		requestHeaders.add("Cookie","tenantid=JHOFdSdP");
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
		ResponseEntity<String> rss = template.exchange(url, HttpMethod.GET, requestEntity, String.class, params);
		
		List<String> cookies = rss.getHeaders().get("Set-Cookie");
		for (String string : cookies) {
			System.err.println(string);
		}
        
		System.out.println(rss.getBody());
    }
	
	@Test   //模拟用户中心返回 成功的数据
	public void testJson(){
		String verifyResult = "{\"status\":1,\"msg\":\"认证成功\"}"  ;
    		JSONObject json = JSONObject.fromObject(verifyResult);
    		String status = String.valueOf(json.get("status"));
    		String message = json.getString("msg");
    		System.out.println(  message);
	}
	
	@Test   //模拟用户中心返回 成功的数据
	public void testMapJson(){
		  Map map = new HashMap() ;
	        map.put("status" , 1);
	        map.put("msg" , "认证成功");
	        map.put("tenant" , "qqq");
	        map.put("usertype" , 1);
	        
	        Map userMap = new HashMap() ;
	        userMap.put( "companyId" , "");
	        userMap.put("departmentId" , "");
	        userMap.put( "loginTs" ,0);
	        userMap.put("pwdstarttime" , "");
	        userMap.put( "registerDate" , "2016-02-02 10:10:05");
	        userMap.put("soureceId" , "");
	        userMap.put("systemId" , "");
	        userMap.put("tenantId" , "e74d9676-c615-4b74-a044-2d99905bb159");
	        userMap.put("typeId" , "超级管理员");
	        userMap.put("userAvator" , "");
	        userMap.put("userCode" , "zhangheng");
	        userMap.put("userEmail" , "zhangheng@yonyou.com");
	        userMap.put("userId" , "b7196a7f-04db-44b4-ac2a-5da9e1b9aab8");
	        userMap.put("userMobile" ,"");
	        userMap.put("userName" ,"zhangheng");
	        userMap.put( "userStates" , 1);
	        
	        map.put("user", userMap);
		System.out.println(  map.toString());
	}
	
	
	
}
