package com.yonyou.iuap.disconf.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * restAPI 登录接口示例
 */
public class LoginRestAPITest {
	
	protected static final Logger LOG = LoggerFactory.getLogger(LoginRestAPITest.class);
	
	private static final String loginUrl = "http://localhost:8080/iuap-disconfweb/api/login/formLogin";
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLogin() throws Exception {
		
		// 直接创建client
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(loginUrl);
		HashMap<String,String> parameterMap = new HashMap<String,String>();
		
		parameterMap.put("name", "test");
		parameterMap.put("password", "test");
		parameterMap.put("remember", "1");
		
		UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(getParam(parameterMap), "UTF-8");
		httpPost.setEntity(postEntity);
		LOG.info("request line:" + httpPost.getRequestLine());
		try {
			// 执行post请求
			HttpResponse httpResponse = client.execute(httpPost);
			printResponse(httpResponse);
			Assert.notNull(httpResponse);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭流并释放资源
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static List<NameValuePair> getParam(Map parameterMap) {
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		Iterator it = parameterMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry parmEntry = (Entry) it.next();
			param.add(new BasicNameValuePair((String) parmEntry.getKey(), (String) parmEntry.getValue()));
		}
		return param;
	}
	
	public static void printResponse(HttpResponse httpResponse) throws ParseException, IOException {
		// 获取响应消息实体
		HttpEntity entity = httpResponse.getEntity();
		// 响应状态
		System.out.println("status:" + httpResponse.getStatusLine());
		System.out.println("headers:");
		HeaderIterator iterator = httpResponse.headerIterator();
		while (iterator.hasNext()) {
			System.out.println("\t" + iterator.next());
		}
		// 判断响应实体是否为空
		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
			System.out.println("response length:" + responseString.length());
			System.out.println("response content:" + responseString.replace("\r\n", ""));
		}
	}
}