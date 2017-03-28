package com.yonyou.iuap.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/demo")
public class RestfulControllerDemo {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="cross_domain_rest", method=RequestMethod.GET)  
	public @ResponseBody Object testCrossDomainRest(HttpServletRequest request, HttpServletResponse response) { 
		response.setHeader("Access-Control-Allow-Origin","*"); 
		JSONObject json = new JSONObject();
		logger.info("client call me ... ");
		json.put("result", "success");
		return json.toString();
	}
	
	@RequestMapping(value="restservice", method=RequestMethod.POST)  
	public @ResponseBody Object restservice(HttpServletRequest request, HttpServletResponse response) { 
		JSONObject json = new JSONObject();
		logger.info("client call me ... ");
		json.put("result", "success");
		return json.toString();
	}
	
}
