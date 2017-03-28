package com.yonyou.iuap.security.digest;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.yonyou.iuap.security.rest.common.AuthConstants;
import com.yonyou.iuap.security.rest.common.SignProp;
import com.yonyou.iuap.security.rest.exception.UAPSecurityException;
import com.yonyou.iuap.security.rest.factory.ClientSignFactory;
import com.yonyou.iuap.security.rest.utils.PostParamsHelper;
import com.yonyou.iuap.security.rest.utils.SignPropGenerator;

/**
 * 使用RestTemplate发送http请求
 * 
 * testGetSign示例：http get请求签名过程
 * testPostSign示例：http post请求签名过程
 * testRestHttpsGet()示例：https get请求签名过程
 * testRestHttpsPost()示例：https post请求签名过程
 *
 */
public class RestTemplateSignTest {

	/**
	 * RestTemplate Get请求签名
	 * 
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 * @throws UAPSecurityException
	 */
	@Test
	public void testGetSign() throws MalformedURLException,
			UnsupportedEncodingException, UAPSecurityException {
		// 1、初始化RestTemplate，或者直接在spring配置文件中进行配置
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(1000);// 设置超时
		requestFactory.setReadTimeout(1000);
		RestTemplate template = new RestTemplate(requestFactory);

		// 2、发生请求时，生成时间戳ts，用于服务端判断请求是否超时
		String ts = System.currentTimeMillis() + "";
		// 3、将ts加入到查询字符串中
		String url = "http://localhost:8080/example_iuap_restful/rest/getTest?"
				+ "username=admin&appId=3c8242c9467d059f9b718a890c9932ca"
				+ "&ts=" + ts + "&x=测试";

		// 4、使用完整的URL得到SignProp签名对象
		SignProp prop = SignPropGenerator.genSignProp(encode(url, "utf-8"));
		// 5、根据SignProp对象生成签名字符串
		String sign = ClientSignFactory.getSigner("bpm").sign(prop);

		// 6、将签名sign加入到http请求的header中
		HttpHeaders headers = new HttpHeaders();
		headers.add(AuthConstants.PARAM_DIGEST, sign);

		// 7、构造HttpEntity对象
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(
				null, headers);

		// 8、使用RestTemplate发送http get请求，并得到响应结果
		ResponseEntity<String> response = template.exchange(url,
				HttpMethod.GET, requestEntity, String.class, new Object[] {});
		System.out.println(response.getBody());
	}

	/**
	 * RestTemplate post请求签名
	 * 
	 * @throws MalformedURLException
	 * @throws UAPSecurityException
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testPostSign() throws MalformedURLException,
			UAPSecurityException, UnsupportedEncodingException {
		// 1、初始化RestTemplate，或者直接在spring配置文件中进行配置
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(1000);// 设置超时
		requestFactory.setReadTimeout(1000);
		RestTemplate template = new RestTemplate(requestFactory);

		// 2、发生请求时，生成时间戳ts，用于服务端判断请求是否超时
		String ts = System.currentTimeMillis() + "";
		String url = "http://localhost:8080/example_iuap_restful/rest/postTest?"
				+ "username=admin&appId=3c8242c9467d059f9b718a890c9932ca"
				+ "&ts=" + ts;

		// 3、 构造POST表单Map
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("k", "123");
		paramsMap.put("v", "456");
		paramsMap.put("w", "789");
		paramsMap.put("y", "测试");

		// 4、根据完整的URL、表单参数Map以及content-length的值生成SignProp对象
		SignProp signProp = SignPropGenerator.genSignProp(url);
		signProp.setPostParamsStr(PostParamsHelper.genParamsStrByMap(paramsMap));
		signProp.setContentLength(this.getContentLength(paramsMap));
		String sign = ClientSignFactory.getSigner("bpm").sign(signProp);

		// 5、将签名sign加入到http请求的header中
		HttpHeaders headers = new HttpHeaders();
		headers.add(AuthConstants.PARAM_DIGEST, sign);
		MediaType type = MediaType
				.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
		headers.setContentType(type);

		// 6、构造MultiValueMap对象
		MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<String, String>();
		for (String name : paramsMap.keySet()) {
			postParameters.add(name, paramsMap.get(name));
		}

		// 7、构造HttpEntity
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(
				postParameters, headers);

		// 8、使用RestTemplate发送http post请求，并得到响应结果
		ResponseEntity<String> response = template.exchange(url,
				HttpMethod.POST, requestEntity, String.class, new Object[] {});
		System.out.println(response);
	}

	/**
	 * 
	 * RestTemplate https get请求签名
	 * 
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 * @throws UAPSecurityException
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	@Test
	public void testRestHttpsGet() throws MalformedURLException,
			UnsupportedEncodingException, UAPSecurityException, 
			KeyManagementException, NoSuchAlgorithmException {
		// 1、初始化RestTemplate，或者直接在spring配置文件中进行配置，这部分代码只作参考，并非规范		
		SSLContext sslContext = SSLContexts.custom().build();
		HttpClient httpClient = HttpClients.custom().setSslcontext(sslContext).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		requestFactory.setConnectTimeout(1000);// 设置超时
		requestFactory.setReadTimeout(1000);
		RestTemplate template = new RestTemplate(requestFactory);

		// 2、发生请求时，生成时间戳ts，用于服务端判断请求是否超时
		String ts = System.currentTimeMillis() + "";
		// 3、将ts加入到查询字符串中
		String url = "https://ec.yyuap.com/restful/rest/getTest?"
				+ "username=admin&appId=3c8242c9467d059f9b718a890c9932ca"
				+ "&ts=" + ts;
		// 4、使用完整的URL得到SignProp签名对象
		SignProp prop = SignPropGenerator.genSignProp(url);
		// 5、根据SignProp对象生成签名字符串
		String sign = ClientSignFactory.getSigner("bpm").sign(prop);

		// 6、将签名sign加入到http请求的header中
		HttpHeaders headers = new HttpHeaders();
		headers.add(AuthConstants.PARAM_DIGEST, sign);

		// 7、构造HttpEntity对象
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(
				null, headers);

		// 8、使用RestTemplate发送https get请求，并得到响应结果
		ResponseEntity<String> response = template.exchange(url,
				HttpMethod.GET, requestEntity, String.class, new Object[] {});
		System.out.println(response.getBody());
	}

	/**
	 * RestTemplate https post请求签名
	 * 
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 * @throws UAPSecurityException
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	@Test
	public void testRestHttpsPost() throws MalformedURLException,
			UnsupportedEncodingException, UAPSecurityException,
			KeyManagementException, NoSuchAlgorithmException {
		// 1、初始化RestTemplate，或者直接在spring配置文件中进行配置，这部分代码只作参考，并非规范
		SSLContext sslContext = SSLContexts.custom().build();
		HttpClient httpClient = HttpClients.custom().setSslcontext(sslContext).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		requestFactory.setConnectTimeout(1000);// 设置超时
		requestFactory.setReadTimeout(1000);
		RestTemplate template = new RestTemplate(requestFactory);

		// 2、发生请求时，生成时间戳ts，用于服务端判断请求是否超时
		String ts = System.currentTimeMillis() + "";
		String url = "https://ec.yyuap.com/restful/rest/postTest?"
				+ "username=admin&appId=3c8242c9467d059f9b718a890c9932ca"
				+ "&ts=" + ts;

		// 3、 构造POST表单Map
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("k", "123");
		paramsMap.put("v", "456");
		paramsMap.put("w", "789");
		paramsMap.put("y", "测试");

		// 4、根据完整的URL、表单参数Map以及content-length的值生成SignProp对象
		SignProp signProp = SignPropGenerator.genSignProp(url);
		signProp.setPostParamsStr(PostParamsHelper.genParamsStrByMap(paramsMap));
		signProp.setContentLength(this.getContentLength(paramsMap));
		String sign = ClientSignFactory.getSigner("bpm").sign(signProp);

		// 5、将签名sign加入到http请求的header中
		HttpHeaders headers = new HttpHeaders();
		headers.add(AuthConstants.PARAM_DIGEST, sign);
		MediaType type = MediaType
				.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
		headers.setContentType(type);

		// 6、构造MultiValueMap对象
		MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<String, String>();
		for (String name : paramsMap.keySet()) {
			postParameters.add(name, paramsMap.get(name));
		}

		// 7、构造HttpEntity
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(
				postParameters, headers);

		// 8、使用RestTemplate发送http post请求，并得到响应结果
		ResponseEntity<String> response = template.exchange(url,
				HttpMethod.POST, requestEntity, String.class, new Object[] {});
		System.out.println(response);
	}

	/**
	 * 获得contentlength，包一个参数对应过个值得情况
	 * 
	 * @param paramsMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public long getContentLength(Map<String, ?> paramsMap)
			throws UnsupportedEncodingException {
		StringBuilder builder = new StringBuilder();
		for (String name : paramsMap.keySet()) {
			if (paramsMap.get(name) instanceof String[]) {
				for (String value : (String[]) paramsMap.get(name)) {
					builder.append(URLEncoder.encode(name, "utf-8"));
					builder.append('=');
					builder.append(URLEncoder.encode(value, "utf-8"));
					builder.append("&");
				}
				builder.delete(builder.length() - 1, builder.length());
			} else {
				builder.append(URLEncoder.encode(name, "utf-8"));
				builder.append('=');
				builder.append(URLEncoder.encode((String) paramsMap.get(name),
						"utf-8"));
			}
			builder.append('&');
		}
		builder.delete(builder.length() - 1, builder.length());
		byte[] bytes = builder.toString().getBytes("utf-8");

		return bytes.length;
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
}
