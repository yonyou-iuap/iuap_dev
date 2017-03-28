package com.yonyou.iuap.crm_mobile.common.utils;

import java.io.IOException;
import java.net.URI;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;

public class ReferRestTemplate extends RestTemplate {

	public ReferRestTemplate() {
	}

	public ReferRestTemplate(ClientHttpRequestFactory requestFactory) {
		super(requestFactory);
	}

	protected ClientHttpRequest createRequest(URI url, HttpMethod method)
			throws IOException {
		ClientHttpRequest request = super.createRequest(url, method);
		
		String sessionStr = (String) AppInvocationInfoProxy.getExtendAttribute("sessionObj");
//		String[] names = new String[]{"token","u_usercode","u_logints","pk_corp","userCNName","pk_user","tenantid","pk_dept"};
//		Cookie[] cookies = this.generateCookies(names,sessionStr);
		
		StringBuilder sb = new StringBuilder();
		if(!StringUtils.isEmpty(sessionStr)){
			JSONObject sessionObj = new JSONObject();
			JSONObject appSessionObj = sessionObj.parseObject(sessionStr);
			if (appSessionObj != null) {
				for (String name : appSessionObj.keySet()) {
					sb.append(name).append("=")
					.append(appSessionObj.get(name)).append(";");
				}
			}
		}

		request.getHeaders().add("Cookie",
				"SERVERID=Mobile_Server;" + sb.toString());
		request.getHeaders().add("X-Requested-With","XMLHttpRequest");
		return request;
	}
	
//	public Cookie[] generateCookies(String[] names,String sessionStr){
//		JSONObject sessionObj = new JSONObject();
//		JSONObject appSessionObj = sessionObj.parseObject(sessionStr);
//		List<Cookie> cookies = new ArrayList();
//		String domain = null;
//		String path = "/";
//		for(int i=0; i<names.length; i++){
//			try {
//				Cookie cookieitem = new Cookie(names[i], URLEncoder.encode(appSessionObj.getString(names[i]), "UTF-8"));
//				if (org.apache.commons.lang.StringUtils.isNotEmpty(domain))
//					cookieitem.setDomain(domain);
//				cookieitem.setPath(path);
//				cookies.add(cookieitem);			
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
//		return (Cookie[])cookies.toArray(new Cookie[0]);
//	}
}
