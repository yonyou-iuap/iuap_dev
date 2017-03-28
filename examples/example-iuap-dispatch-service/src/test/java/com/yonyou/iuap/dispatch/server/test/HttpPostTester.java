package com.yonyou.iuap.dispatch.server.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.iuap.generic.adapter.IContants;

public class HttpPostTester {
	
	private static Logger logger = LoggerFactory.getLogger(HttpPostTester.class);
	
	public static void main(String[] args) {
		HttpPostTester tester = new HttpPostTester();
		// 	tester.testHttpPost();
		tester.testHttpPostByJson();
//		tester.testHttpPostofCornExpresByJson();
	}

	public String testHttpPostByJson() {
		String urlStr = "http://localhost:8080/iuap-dispatch-service/dispatchserver/add.do";

//		String taskConfig = "{\"replace\":true,\"recallConfig\":{\"data\":{},\"option\":{\"url\":\"http://localhost:8080/iuap-dispatch-server/dispatchserver/pause.do\"},\"recallType\":\"HTTP\"},\"taskConfig\":{\"triggerType\":\"SimpleTrigger\",\"jobCode\":\"22b511e8-1b80-4f4d-b65e-48f52d8aa682\",\"groupCode\":\"simpleTaskGroup\",\"startDate\":1463813876403,\"endDate\":null,\"priority\":0,\"timeConfig\":{\"interval\":2,\"intervalType\":\"SECOND\",\"isForever\":false,\"repeatCount\":1}},\"note\":\"note\"}";

		
		String taskConfig = "{\"replace\":true,\"recallConfig\":{\"data\":{},\"option\":{\"url\":\"http://localhost:8080/iuap-dispatch-service/dispatchserver/pause.do\"},\"recallType\":\"HTTP\"},\"taskConfig\":{\"triggerType\":\"SimpleTrigger\",\"jobCode\":\"22b511e8-1b80-4f4d-b65e-48f52d8aa682\",\"groupCode\":\"simpleTaskGroup\",\"startDate\":1463813876403,\"endDate\":null,\"priority\":0,\"timeConfig\":{\"interval\":2,\"intervalType\":\"SECOND\",\"isForever\":false,\"repeatCount\":1}},\"note\":\"note\"}";
		
		
		// tenantid,userid,sysid 要通过cookie传入
		String cookies = getCookieString("tenantTest", "sysTest", "userTest");
		
		
		

		StringBuffer sb = new StringBuffer();
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

			conn.setRequestProperty(IContants.COOKIES, cookies);
			conn.getOutputStream().write(taskConfig.getBytes());

			// 获取返回结果
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String lines;
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			String error = conn.getHeaderField("error");

		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		String result = sb.toString();

		logger.error("the return msg is " + result);
		return result;

	}
	
	public String testHttpPostofCornExpresByJson() {
		String urlStr = "http://localhost:8080/iuap-dispatch-service/dispatchserver/add.do";

//		String taskConfig = "{\"replace\":true,\"recallConfig\":{\"data\":{},\"option\":{\"url\":\"http://localhost:8080/iuap-dispatch-server/dispatchserver/pause.do\"},\"recallType\":\"HTTP\"},\"taskConfig\":{\"cronExpress\":\"* */1 * * *\",\"jobCode\":\"22b511e8-1b80-4f4d-b65e-48f52d8aa682\",\"groupCode\":\"simpleTaskGroup\",\"startDate\":1463813876403,\"endDate\":null,\"priority\":0,\"timeConfig\":{\"interval\":2,\"intervalType\":\"SECOND\",\"isForever\":false,\"repeatCount\":1}},\"note\":\"note\"}";
		String taskConfigCorn = "{\"replace\":true, \"recallConfig\":{\"data\":{\"serverName\":\"Windows 2003\"},\"option\":{\"url\":\"http://localhost:8080/iuap-dispatch-service/dispatchserver/pause.do\"},\"recallType\":\"HTTP\"}, \"taskConfig\":{\"cronExpress\":\"* */1 * * * ?\",\"groupCode\":\"cronTaskGroup\",\"jobCode\":\"cronTask\",\"priority\":0,\"triggerType\":\"CronTrigger\"}}";
		// tenantid,userid,sysid 要通过cookie传入
		String cookies = getCookieString("tenantTest", "sysTest", "userTest");

		StringBuffer sb = new StringBuffer();
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

			conn.setRequestProperty(IContants.COOKIES, cookies);
			conn.getOutputStream().write(taskConfigCorn.getBytes());

			// 获取返回结果
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String lines;
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			String error = conn.getHeaderField("error");

		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		String result = sb.toString();

		logger.error("the return msg is " + result);
		return result;

	}

	// public void testHttpPost() {
	// String url =
	// "http://localhost:8080/iuap-dispatch-server/dispatchserver/add.do";
	//
	// Map<String, Object> params = new HashMap<String, Object>();
	//
	// TimeConfig timeConfig =
	// TimeConfig.getInstance().withIntervalInSeconds(2).withRepeatCount(1);
	// Date startDate = new Date(System.currentTimeMillis() - 10 * 1000);
	//
	// SimpleTaskConfig taskConfig = new
	// com.yonyou.iuap.dispatch.SimpleTaskConfig(
	// UUID.randomUUID().toString()/* 任务名 */, "simpleTaskGroup"/* 任务组名 */,
	// timeConfig);
	// taskConfig.setStartDate(startDate);
	//
	// params.put("note", "note");
	// params.put(Contants.TASK_CONFIG, taskConfig.toString());
	// params.put(Contants.RECALL_CONFIG, RecallConfig.getDefault().toString());
	// params.put(Contants.REPLACE_FLAG, true);
	//
	//
	// Map<String, String> cookies = new HashMap<String, String>();
	// cookies.put(IContants.TENANTID, "tenantTest");
	// cookies.put(IContants.SYSID, "sysTest");
	// cookies.put(IContants.USERID, "userTest");
	//
	//
	//
	// String msg = send(url, params,cookies);
	//
	//
	// logger.error("the return msg is " + msg);
	// }

	// public String send(String url, Map<String, Object>
	// data,Map<String,String> cookies ) {
	// String msg = null;
	// try {
	//
	// if (url == null) {
	// return "operate type is error";
	// }
	//
	// String result = com.yonyou.iuap.dispatch.server.common.HttpUtil.send(url,
	// JSON.toJSONString(data), cookies);
	// if (result == null) {
	// return "send to server error";
	// }
	// JSONObject returnMsg = JSON.parseObject(result);
	// boolean success = returnMsg.getBoolean("success");
	// if (success) {
	// return "ok";
	// }
	// msg = returnMsg.getString("error");
	// } catch (Exception e) {
	// msg = "交换信息格式不正确";
	//
	// }
	// return msg;
	// }

	private static String getCookieString(String tenantid, String sysid, String userid) {

		StringBuffer st = new StringBuffer();
		st.append(IContants.TENANTID);
		st.append("=");
		st.append(tenantid);
		st.append(";");
		st.append(IContants.SYSID);
		st.append("=");
		st.append(sysid);
		st.append(";");
		st.append(IContants.USERID);
		st.append("=");
		st.append(userid);

		return st.toString();

	}

}
