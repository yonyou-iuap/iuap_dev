package com.yonyou.iuap.crm_mobile.common.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm_mobile.common.utils.ReferRestTemplate;

@RequestMapping({"/macore"})
@Controller
public class MobileServiceController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private ReferRestTemplate template;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/commonService", method = RequestMethod.POST)
	public @ResponseBody Object getIntRefData(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String,String> params) throws Exception{
		Map resultmap = new HashMap();
		String opr = params.get("requestopr");
		String url = params.get("requesturl");
		url = "http://10.54.134.5:8060/crm"+url;
		String responseType = params.get("responseType");
		String urlVariables = params.get("requestparam");
		String requestcallback = params.get("requestcallback");
		String sessionObj = params.get("sessionObj");
		String deviceinfo = params.get("deviceinfo");
		logger.error("Mobile Invoker Serivce url is {} and opr is {} and param is {} and responsetype is {} and requestcallback is {}",url,opr,urlVariables,responseType,requestcallback);
		logger.error("Mobile Deviceinfo is {}", deviceinfo);
		logger.error("Mobile sessionObj is {}", sessionObj);
		logger.error("Mobile url is {}", url);
		logger.error("Mobile responseType is {}", responseType);
		AppInvocationInfoProxy.setExtendAttribute("sessionObj", sessionObj);
		Map args = new HashMap();
		if(StringUtils.isNotEmpty(urlVariables)){
			JSONObject urlVariablesObj = new JSONObject();
			JSONObject appSessionObj = urlVariablesObj.parseObject(urlVariables);
			for(String keyitem : appSessionObj.keySet()){				
				args.put(keyitem, appSessionObj.get(keyitem));
				logger.error("args key={},name={}", keyitem, appSessionObj.get(keyitem));
			}
		}
		Object result = null;
		try{
			if(opr.equals("get")){
				result = this.template.getForObject(url, Class.forName(responseType),args);
			}else if(opr.equals("post")){
				result = this.template.postForObject(url, args, Class.forName(responseType), new Object[0]);
			}else if(opr.equals("delete")){
				this.template.delete(url, args);
			}
			resultmap.put("requestcallback", requestcallback);
			resultmap.put("status", "success");
			resultmap.put("data", result);
		}catch(RestClientException | ClassNotFoundException e){
			logger.error("Mobile Service {} Invoker Error",url);
			logger.error(e.getMessage(), e);
			resultmap.put("status", "failed");
			resultmap.put("data", "");
			if(e instanceof HttpStatusCodeException){
				HttpStatusCodeException err = (HttpStatusCodeException) e;
				response.setStatus(err.getStatusCode().value());
			}else if(e instanceof UnknownHttpStatusCodeException){
				UnknownHttpStatusCodeException err = (UnknownHttpStatusCodeException) e;
				response.setStatus(err.getRawStatusCode());
			}else if(e instanceof ResourceAccessException){
				ResourceAccessException err = (ResourceAccessException) e;
				response.setStatus(500);
			}else{
				response.setStatus(500);
			}
		}
		return resultmap;
	}
	
}
