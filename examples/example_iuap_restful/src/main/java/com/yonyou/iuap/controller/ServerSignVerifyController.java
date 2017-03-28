package com.yonyou.iuap.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "rest")
@Controller
public class ServerSignVerifyController {

	@RequestMapping(value = "getTest", method = RequestMethod.GET)
	public @ResponseBody Object getTestReturnJson(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();
		json.put("result", "get success");
		return json.toString();
	}

	@RequestMapping(value = "postTest", method = RequestMethod.POST)
	public @ResponseBody Object postTestReturnJson(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();
		json.put("result", "post success");
		return json.toString();
	}
}
