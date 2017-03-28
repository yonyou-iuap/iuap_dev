package com.yonyou.iuap.security.digest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yonyou.iuap.security.rest.common.AuthConstants;
import com.yonyou.iuap.security.rest.common.Credential;
import com.yonyou.iuap.security.rest.common.SignProp;
import com.yonyou.iuap.security.rest.exception.UAPSecurityException;
import com.yonyou.iuap.security.rest.factory.ClientSignFactory;
import com.yonyou.iuap.security.rest.utils.ClientCredentialGenerator;
import com.yonyou.iuap.security.rest.utils.PostParamsHelper;
import com.yonyou.iuap.security.rest.utils.SignPropGenerator;

/**
 * 请求端签名示例，使用HttpClient发送Http请求
 * 
 * testDiffSign示例：一个请求端保存有多个服务端的签名凭证
 * 
 * testGetSign示例：http get请求签名过程 
 * testPostSign1示例：http post请求签名过程
 * testPostSign2示例：http post请求签名过程，考虑了参数一对多值的情况 
 * testPostSign3示例：http post请求签名过程，考虑了参数一对多值的情况（与testPostSign2略有区别）
 *
 */
public class ClientSignTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSign() throws MalformedURLException, UAPSecurityException {
		String ts = System.currentTimeMillis() + "";
		String url = "http://172.20.6.48:8080/ecmgr/rest/testrest?username=admin&appId=3c8242c9467d059f9b718a890c9932ca"
				+ "&ts=" + ts;
		SignProp prop = SignPropGenerator.genSignProp(url);
		System.out.println(prop);
		//getSigner时候传入提供服务的类型，以查找不同的客户端证书
		String sign = ClientSignFactory.getSigner("bpm").sign(prop);
		url += "&" + AuthConstants.PARAM_DIGEST + "=" + sign;
		System.out.println(sign);
		System.out.println(url);
	}
	
	/**
	 * 一个请求端保存有多个服务端的签名凭证。
	 * 对不同服务端生成的签名凭证使用标识符进行区分，如authfile_ali.txt，authfile_baidu.txt
	 * 需要在application.properties中配置多个签名凭证的路径，中间用逗号隔开，如： #客户端身份文件路径
	 * client.credential.path=D:/authfile_ali.txt, D:/authfile_baidu.txt
	 * 
	 * 运行该测试用例需修改配置文件 application.properties
	 * 
	 * @throws MalformedURLException
	 * @throws UAPSecurityException
	 */
	@Test
	public void testDiffSign() throws MalformedURLException, UAPSecurityException {
		String ts = System.currentTimeMillis() + "";
		String url = "http://172.20.6.48:8080/ecmgr/rest/testrest?username=admin&appId=3c8242c9467d059f9b718a890c9932ca"
				+ "&ts=" + ts;
		SignProp prop = SignPropGenerator.genSignProp(url);
		System.out.println(prop);
		String sign1 = ClientSignFactory.getSigner("ali").sign(prop);
		System.err.println("使用_ali的凭证签名结果：" + sign1);
		String sign2 = ClientSignFactory.getSigner("baidu").sign(prop);
		System.err.println("使用_baidu的凭证签名结果：" + sign2);
	}

	/**
	 * HttpClient Get请求签名
	 * 
	 * @throws ParseException
	 * @throws IOException
	 * @throws UAPSecurityException
	 */
	@Test
	public void testGetSign() throws ParseException, IOException,
			UAPSecurityException {
		// 1、发生请求时，生成时间戳ts，用于服务端判断请求是否超时
		String ts = System.currentTimeMillis() + "";
		// 2、将ts加入到查询字符串中
		String url = "http://localhost:8080/example_iuap_restful/rest/getTest?"
				+ "username=admin&appId=3c8242c9467d059f9b718a890c9932ca"
				+ "&ts=" + ts + "&x=测试";

		// 3、使用HttpClient发起http请求
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);

		// 4、使用完整的URL得到SignProp签名对象
		SignProp prop = SignPropGenerator.genSignProp(encode(url, "utf-8"));
		System.out.println(prop);
		// 5、根据SignProp对象生成签名字符串，getSigner时候传入提供服务的类型，以查找不同的客户端证书
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
	 * HttpClient Post请求签名，最常规的用法
	 * 
	 * @throws UAPSecurityException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@Test
	public void testPostSign1() throws UAPSecurityException,
			ClientProtocolException, IOException {
		// 1、发生请求时，生成时间戳ts，用于服务端判断请求是否超时
		String ts = System.currentTimeMillis() + "";
		// 2、将ts加入到查询字符串中
		String url = "http://localhost:8080/example_iuap_restful/rest/postTest?"
				+ "username=admin&appId=3c8242c9467d059f9b718a890c9932ca"
				+ "&ts=" + ts;

		// 3、使用HttpClient发起http请求
		HttpClient httpClient = new DefaultHttpClient();
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
		if(httpPost.getEntity().isChunked() || httpPost.getEntity().getContentLength()<0) {
			signProp.setContentLength(-1);
		} else {
			signProp.setContentLength(httpPost.getEntity().getContentLength());
		}
		// 7、根据SignProp对象构造签名字符串，getSigner时候传入提供服务的类型，以查找不同的客户端证书
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
	 * HttpClient Post请求签名，处理一对多值的用法
	 * 
	 * @throws UAPSecurityException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@Test
	public void testPostSign2() throws UAPSecurityException,
			ClientProtocolException, IOException {
		// 1、发生请求时，生成时间戳ts，用于服务端判断请求是否超时
		String ts = System.currentTimeMillis() + "";
		// 2、将ts加入到查询字符串中
		String url = "http://localhost:8080/example_iuap_restful/rest/postTest?"
				+ "username=admin&appId=3c8242c9467d059f9b718a890c9932ca"
				+ "&ts=" + ts;

		// 3、使用HttpClient发起http请求
		HttpClient httpClient = new DefaultHttpClient();
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
		if(httpPost.getEntity().isChunked() || httpPost.getEntity().getContentLength()<0) {
			signProp.setContentLength(-1);
		} else {
			signProp.setContentLength(httpPost.getEntity().getContentLength());
		}
		// 7、根据SignProp对象构造签名字符串，getSigner时候传入提供服务的类型，以查找不同的客户端证书
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
	 */
	@Test
	public void testPostSign3() throws UAPSecurityException,
			ClientProtocolException, IOException {
		// 1、发生请求时，生成时间戳ts，用于服务端判断请求是否超时
		String ts = System.currentTimeMillis() + "";
		// 2、将ts加入到查询字符串中
		String url = "http://localhost:8080/example_iuap_restful/rest/postTest?"
				+ "username=admin&appId=3c8242c9467d059f9b718a890c9932ca"
				+ "&ts=" + ts;

		// 3、使用HttpClient发起http请求
		HttpClient httpClient = new DefaultHttpClient();
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
		if(httpPost.getEntity().isChunked() || httpPost.getEntity().getContentLength()<0) {
			signProp.setContentLength(-1);
		} else {
			signProp.setContentLength(httpPost.getEntity().getContentLength());
		}
		// 7、根据SignProp对象构造签名字符串，getSigner时候传入提供服务的类型，以查找不同的客户端证书
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
	 * 只对url中的中文进行编码
	 * 
	 * @param url
	 * @param charset
	 * @return
	 */
	public String encode(String url, String charset) {
		try {
			Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]").matcher(url);
			while (matcher.find()) {
				String tmp = matcher.group();
				url = url.replaceAll(tmp,
						java.net.URLEncoder.encode(tmp, charset));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}

	public static void main(String[] args) throws UAPSecurityException {
		Credential c = ClientCredentialGenerator
				.loadCredential("/security/authfile.txt");
		System.out.println(c.getKey());
	}

}
