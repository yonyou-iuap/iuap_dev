package formulatest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 模拟表单Post请求,返回Json格式数据
 * 
 * @author liuxing
 *
 */
public class RestHttpRequestUtil {
	private static Logger logger = LoggerFactory.getLogger(RestHttpRequestUtil.class);
	
	private static String serverIp = "http://localhost:8080/iuap-formula-service/formula/execute";

	public static Object doPost(String param) {

		String urlStr = getServer();
		StringBuffer sb = new StringBuffer("");
		URL url = null;
		try {
			HttpURLConnection conn;
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			if (conn instanceof HttpURLConnection) {
				((HttpURLConnection) conn).setChunkedStreamingMode(2048);
			}
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			if (param != null) {

				conn.getOutputStream().write(param.getBytes());
			}
			// 获取返回结果
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String lines;
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			String error = conn.getHeaderField("error");
			if (error != null && !"".equals(error)) {
				String decode = URLDecoder.decode(error, "utf-8");
				// throw new RestException(decode);
			}
		} catch (MalformedURLException e) {
			// throw new RestException("连接异常");
//			logger.error("xxxx");
		
		} catch (IOException e) {
			// throw new RestException("连接异常");
			logger.error("xxx");
		}

		String result = sb.toString();
		Object json = null;
		try {
			if (!StringUtils.isEmpty(result)) {
				json = JSONObject.fromObject(sb.toString());
			}
			Object value = ((JSONObject) json).get("f");
			String s = value.toString();
		} catch (Exception e) {
			try {
				if (!StringUtils.isEmpty(result)) {
					Object obj = JSONArray.fromObject(sb.toString());
					if (obj != null) {
						JSONArray json1 = (JSONArray) obj;
						if (json1 != null) {
							for (int i = 0; i < json1.size(); i++) {
								JSONObject userJson = (JSONObject) json1.get(i);
								Object value = userJson.get("f");
								String s = value.toString();
							}
						}
					}
				}

			} catch (Exception e1) {
				
			}
		}

		return result;
	}

	public static Object doPost(String requestUrl, Map<String, String> params) {
		// if (getServer() == null)
		// throw new RestException("服务器地址异常！！");
		String urlStr = "http://" + getServer() + requestUrl;
		StringBuffer sb = new StringBuffer("");
		URL url = null;
		try {
			HttpURLConnection conn;
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			if (conn instanceof HttpURLConnection) {
				((HttpURLConnection) conn).setChunkedStreamingMode(2048);
			}
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			if (params != null && params.size() > 0) {
				StringBuffer paramBulider = new StringBuffer();
				for (String key : params.keySet()) {
					paramBulider.append(key);
					paramBulider.append("=");
					paramBulider.append(
							key == null || key.length() == 0 ? "" : URLEncoder.encode(params.get(key), "UTF-8"));
					paramBulider.append("&");
				}
				String paramStr = paramBulider.substring(0, paramBulider.length() - 1);
				conn.getOutputStream().write(paramStr.getBytes());
			}
			// 获取返回结果
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String lines;
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			String error = conn.getHeaderField("error");
			if (error != null && !"".equals(error)) {
				String decode = URLDecoder.decode(error, "utf-8");
				// throw new RestException(decode);
			}
		} catch (MalformedURLException e) {
			// throw new RestException("连接异常");
			
		} catch (IOException e) {
			// throw new RestException("连接异常");
			
		}

		String result = sb.toString();
		Object json = null;
		try {
			if (!StringUtils.isEmpty(result)) {
				json = JSONObject.fromObject(sb.toString());
			}
		} catch (Exception e) {
			try {
				if (!StringUtils.isEmpty(result)) {
					json = JSONArray.fromObject(sb.toString());
				}
			} catch (Exception e1) {
				// throw new RestException("连接异常");
				
			}
		}
		return json;
	}

	private static String getServer() {

		return serverIp;

	}
}
