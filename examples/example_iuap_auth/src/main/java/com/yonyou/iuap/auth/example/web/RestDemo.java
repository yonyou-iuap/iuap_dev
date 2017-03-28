package com.yonyou.iuap.auth.example.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yonyou.iuap.auth.session.SessionManager;
import com.yonyou.iuap.cache.CacheManager;
import com.yonyou.iuap.context.InvocationInfoProxy;

@Controller
@RequestMapping(value = "/springmvc/demo")
public class RestDemo {
	
	private static final Logger logger = LoggerFactory.getLogger(RestDemo.class);
	
	@Autowired
	private SessionManager sessionMgr;
	
	@Autowired
	private CacheManager cacheMgr;
	
	@RequestMapping(value="test", method=RequestMethod.GET)  
	public @ResponseBody Object testrestful(HttpServletRequest request, HttpServletResponse response) { 
		JSONObject result = new JSONObject();
		
		String sid = InvocationInfoProxy.getToken();
		logger.info("session id is:" + sid);
		
		Object count = sessionMgr.getSessionCacheAttribute(sid, "test_count");
		if(count == null){
			sessionMgr.putSessionCacheAttribute(sid, "test_count", 1);
			count = 1;
		} else {
			int scount = (int)count;
			scount ++;
			sessionMgr.putSessionCacheAttribute(sid, "test_count", scount);
		}
		logger.info("count is：" + count);
		result.put("count", count);
		
		result.put("flag", "success");
		result.put("msg", "测试成功!");
		
		return result;
	}
	
	
	@RequestMapping(value = "testContext", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String testContext(HttpServletRequest request, HttpServletResponse response) {
		logger.info("InvocationInfoProxy.getTenantid()  ====== " + InvocationInfoProxy.getTenantid());
		logger.info("InvocationInfoProxy.getLocale() ====== " + InvocationInfoProxy.getLocale());
		logger.info(" InvocationInfoProxy.getUserid()  ======= " + InvocationInfoProxy.getUserid());
		// 业务逻辑处理
		return "success";
	}
	
	@RequestMapping(value="upload")  
	public @ResponseBody String uploadfile(HttpServletRequest request) throws IOException {
		
		logger.info("test info log for context.");
		logger.info("current tenantid is {}.", InvocationInfoProxy.getTenantid());
		
		// 判断 request 是否有文件上传,即多部分请求
		if ((request != null && ServletFileUpload.isMultipartContent(request))) {
			/*
			ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) request;
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
			MultipartHttpServletRequest multiRequest = multipartResolver.resolveMultipart((HttpServletRequest) shiroRequest.getRequest());
			*/
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Map<String, List<MultipartFile>> fileMap = multiRequest.getMultiFileMap();
		}
		return "success";
	}
	
}


