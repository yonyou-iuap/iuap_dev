package com.iuap.log.security.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iuap.log.security.entities.SecurityLog;
import com.iuap.log.security.utils.SecurityLogUtil;

@Controller
@RequestMapping("/testSecuritylog")
public class SecuritylogController {

	@RequestMapping(method = RequestMethod.GET, value="/testSavelog")
	public @ResponseBody Map<String,Object> savelog(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		SecurityLog log = new SecurityLog();
		log.setContentDes("content");
		log.setNotice("测试123");
		log.setTimestamp(new Date());
		SecurityLogUtil.saveLog(log);
		returnMap.put("success", "true");
		return returnMap;
	}
}
