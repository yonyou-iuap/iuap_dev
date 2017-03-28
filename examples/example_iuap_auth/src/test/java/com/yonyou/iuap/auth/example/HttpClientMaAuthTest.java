package com.yonyou.iuap.auth.example;

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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.util.Assert;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

/**
 * 利用httpclient调用登录和rest服务的例子
 * <p>登录时候获取cookie，调用服务时候携带cookies</p>
 * 
 * @author liujmc
 */
public class HttpClientMaAuthTest {

	String testUrl = "http://localhost:8080/example_iuap_auth/springmvc/demo/test";
	String loginUrl = "http://localhost:8080/example_iuap_auth/malogin/formLogin";

	@Test
	public void testLogin() throws Exception {
		// 创建HttpClientBuilder
		// HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// CloseableHttpClient client = httpClientBuilder.build();
		
		// 直接创建client
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(loginUrl);
		HashMap<String,String> parameterMap = new HashMap<String,String>();
		
		String text = "111111a" ;
		byte[] hashPassword = Digests.sha1(text.getBytes()) ;
		String password = Encodes.encodeHex(hashPassword);
		
		parameterMap.put("username", "test001");
		parameterMap.put("password", password);
		
		UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(getParam(parameterMap), "UTF-8");
		httpPost.setEntity(postEntity);
		System.out.println("request line:" + httpPost.getRequestLine());
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
	
	@Test
	public void testRest() throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(testUrl);
		httpGet.addHeader("Cookie","token=bWEsLTEsR0R6ZHB6ZXJMRHdUSSsvN2F3MEtJQ0ZvWjJoL3MwYlBuWUdVamo5dG9LeHYrTm1SaVYrRTF3V1RFVWkvdzBUK1ZrdDZUQzVGbmF6SWxNekxVcFFiQVE9PQ");
		httpGet.addHeader("Cookie","u_usercode=test001");
		httpGet.addHeader("Cookie","u_logints=1464691715250");
		httpGet.addHeader("Cookie","tenantid=JHOFdSdP");
		
		System.out.println("request line:" + httpGet.getRequestLine());
		HttpResponse httpResponseDemo = client.execute(httpGet);
		printResponse(httpResponseDemo);
		Assert.notNull(httpResponseDemo);
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

	public static List<NameValuePair> getParam(Map parameterMap) {
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		Iterator it = parameterMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry parmEntry = (Entry) it.next();
			param.add(new BasicNameValuePair((String) parmEntry.getKey(), (String) parmEntry.getValue()));
		}
		return param;
	}
}