package com.yonyou.iuap.security.digest;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yonyou.iuap.security.rest.common.AuthConstants;
import com.yonyou.iuap.security.rest.common.SignProp;
import com.yonyou.iuap.security.rest.exception.UAPSecurityException;
import com.yonyou.iuap.security.rest.factory.ClientSignFactory;
import com.yonyou.iuap.security.rest.utils.PostParamsHelper;
import com.yonyou.iuap.security.rest.utils.SignPropGenerator;

/**
 * 请求端签名https请求，使用HttpClient发送请求
 * 
 * testGetSign示例：https get请求签名过程 
 * testPostSign1示例：https post请求签名过程
 * testPostSign2示例：https post请求签名过程，考虑了参数一对多值的情况 
 * testPostSign3示例：https post请求签名过程，考虑了参数一对多值的情况（与testPostSign2略有不同）
 *
 */
public class HttpsClientSignTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * HttpClient https Get请求签名
	 * 
	 * @throws ParseException
	 * @throws IOException
	 * @throws UAPSecurityException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	@Test
	public void testGetSign() throws ParseException, IOException,
			UAPSecurityException, KeyManagementException,
			NoSuchAlgorithmException {
		// 1、发生请求时，生成时间戳ts，用于服务端判断请求是否超时
		String ts = System.currentTimeMillis() + "";
		// 2、将ts加入到查询字符串中
		String url = "http://ec.yyuap.com/restful/rest/getTest?"
				+ "username=admin&appId=3c8242c9467d059f9b718a890c9932ca"
				+ "&ts=" + ts;

		// 3、使用HttpClient发起https请求，这部分代码只作参考，并非规范
		SSLContext sslContext = SSLContexts.custom().build();
		HttpClient httpClient = HttpClients.custom().setSslcontext(sslContext).build();
		HttpGet httpGet = new HttpGet(url);

		// 4、使用完整的URL得到SignProp签名对象
		SignProp prop = SignPropGenerator.genSignProp(url);
		// 5、根据SignProp对象生成签名字符串
		String sign = ClientSignFactory.getSigner("bpm").sign(prop);

		// 6、将签名字符串加入到http请求的header中
		httpGet.setHeader(AuthConstants.PARAM_DIGEST, sign);

		// 7、发送http get请求，并得到响应结果
		HttpResponse response = httpClient.execute(httpGet);
		String result = "";
		if (response != null) {
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				result = EntityUtils.toString(resEntity, "UTF-8");
				System.out.println(result);
			}
		}
	}

	/**
	 * HttpClient https Post请求签名，最常规的用法
	 * 
	 * @throws UAPSecurityException
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	@Test
	public void testPostSign1() throws UAPSecurityException,
			ClientProtocolException, IOException, KeyManagementException,
			NoSuchAlgorithmException {
		// 1、发生请求时，生成时间戳ts，用于服务端判断请求是否超时
		String ts = System.currentTimeMillis() + "";
		// 2、将ts加入到查询字符串中
		String url = "https://ec.yyuap.com/restful/rest/postTest?"
				+ "username=admin&appId=3c8242c9467d059f9b718a890c9932ca"
				+ "&ts=" + ts;

		// 3、使用HttpClient发起https请求，这部分代码只作参考，并非规范
		SSLContext sslContext = SSLContexts.custom().build();
		HttpClient httpClient = HttpClients.custom().setSslcontext(sslContext).build();
		HttpPost httpPost = new HttpPost(url);

		// 4、 构造POST表单Map
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("k", "123");
		paramsMap.put("v", "456");
		paramsMap.put("w", "789");
		paramsMap.put("y", "测试");

		// 5、设置POST表单参数
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Iterator iterator = paramsMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> elem = (Entry<String, String>) iterator
					.next();
			list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
		}
		if (list.size() > 0) {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,
					"UTF-8");
			httpPost.setEntity(entity);
		}

		// 6、根据完整的URL、表单参数Map以及content-length的值生成SignProp对象
		SignProp signProp = SignPropGenerator.genSignProp(url);
		signProp.setPostParamsStr(PostParamsHelper.genParamsStrByMap(paramsMap));
		signProp.setContentLength(httpPost.getEntity().getContentLength());
		// 7、根据SignProp对象构造签名字符串
		String sign = ClientSignFactory.getSigner("bpm").sign(signProp);
		// 8、将签名字符串加入到http请求的header中
		httpPost.setHeader(AuthConstants.PARAM_DIGEST, sign);

		// 9、发送http post请求，并得到响应结果
		HttpResponse response = httpClient.execute(httpPost);
		String result = "";
		if (response != null) {
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				result = EntityUtils.toString(resEntity, "UTF-8");
				System.out.println(result);
			}
		}
	}

	/**
	 * HttpClient https Post请求签名，处理一对多值的用法
	 * 
	 * @throws UAPSecurityException
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	@Test
	public void testPostSign2() throws UAPSecurityException,
			ClientProtocolException, IOException, KeyManagementException,
			NoSuchAlgorithmException {
		// 1、发生请求时，生成时间戳ts，用于服务端判断请求是否超时
		String ts = System.currentTimeMillis() + "";
		// 2、将ts加入到查询字符串中
		String url = "https://ec.yyuap.com/restful/rest/postTest?"
				+ "username=admin&appId=3c8242c9467d059f9b718a890c9932ca"
				+ "&ts=" + ts;

		// 3、使用HttpClient发起https请求，这部分代码只作参考，并非规范
		SSLContext sslContext = SSLContexts.custom().build();
		HttpClient httpClient = HttpClients.custom().setSslcontext(sslContext).build();
		HttpPost httpPost = new HttpPost(url);

		// 4、 构造POST表单Map
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("k", "123");
		paramsMap.put("v", "456");
		paramsMap.put("w", "789,100"); // 参数w对应789和100两个值，
										// 例如，表单中的checkbox多选的情况下，就会出现这种参数

		// 5、设置POST表单参数
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Iterator iterator = paramsMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> elem = (Entry<String, String>) iterator
					.next();
			list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
		}
		if (list.size() > 0) {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,
					"UTF-8");
			httpPost.setEntity(entity);
		}

		// 6、根据完整的URL、表单参数Map以及content-length的值生成SignProp对象
		SignProp signProp = SignPropGenerator.genSignProp(url);
		signProp.setPostParamsStr(PostParamsHelper.genParamsStrByMap(paramsMap));
		signProp.setContentLength(httpPost.getEntity().getContentLength());
		// 7、根据SignProp对象构造签名字符串
		String sign = ClientSignFactory.getSigner("bpm").sign(signProp);
		// 8、将签名字符串加入到http请求的header中
		httpPost.setHeader(AuthConstants.PARAM_DIGEST, sign);

		// 9、发送http post请求，并得到响应结果
		HttpResponse response = httpClient.execute(httpPost);
		String result = "";
		if (response != null) {
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				result = EntityUtils.toString(resEntity, "UTF-8");
				System.out.println(result);
			}
		}
	}

	/**
	 * HttpClient Post请求签名，处理一对多值得做法
	 * 
	 * @throws UAPSecurityException
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	@Test
	public void testPostSign3() throws UAPSecurityException,
			ClientProtocolException, IOException, KeyManagementException,
			NoSuchAlgorithmException {
		// 1、发生请求时，生成时间戳ts，用于服务端判断请求是否超时
		String ts = System.currentTimeMillis() + "";
		// 2、将ts加入到查询字符串中
		String url = "https://ec.yyuap.com/restful/rest/postTest?"
				+ "username=admin&appId=3c8242c9467d059f9b718a890c9932ca"
				+ "&ts=" + ts;

		// 3、使用HttpClient发起https请求，这部分代码只作参考，并非规范
		SSLContext sslContext = SSLContexts.custom().build();
		HttpClient httpClient = HttpClients.custom().setSslcontext(sslContext).build();
		HttpPost httpPost = new HttpPost(url);

		// 4、 构造POST表单Map
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("k", "123");
		paramsMap.put("v", "456");
		paramsMap.put("w", "789,100"); // 参数w有两个值，用逗号分隔的字符串表示
		paramsMap.put("z", new String[] { "xx", "yy" }); // 参数z有两个值，用字符串数组表示

		// 5、设置POST表单参数
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Iterator iterator = paramsMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> elem = (Entry<String, Object>) iterator
					.next();
			if (elem.getValue() instanceof String[]) {
				// 对于参数z生成两个参数对，z-xx， z-yy
				String[] values = (String[]) elem.getValue();
				for (int i = 0; i < values.length; i++) {
					list.add(new BasicNameValuePair(elem.getKey(), values[i]));
				}
			} else {
				list.add(new BasicNameValuePair(elem.getKey(), (String) elem
						.getValue()));
			}
		}
		if (list.size() > 0) {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,
					"UTF-8");
			httpPost.setEntity(entity);
		}

		// 6、根据完整的URL、表单参数Map以及content-length的值生成SignProp对象
		SignProp signProp = SignPropGenerator.genSignProp(url);
		signProp.setPostParamsStr(PostParamsHelper.genParamsStrByMap(paramsMap));
		signProp.setContentLength(httpPost.getEntity().getContentLength());
		// 7、根据SignProp对象构造签名字符串
		String sign = ClientSignFactory.getSigner("bpm").sign(signProp);
		// 8、将签名字符串加入到http请求的header中
		httpPost.setHeader(AuthConstants.PARAM_DIGEST, sign);

		// 9、发送http post请求，并得到响应结果
		HttpResponse response = httpClient.execute(httpPost);
		String result = "";
		if (response != null) {
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				result = EntityUtils.toString(resEntity, "UTF-8");
				System.out.println(result);
			}
		}
	}
}
