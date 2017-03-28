package com.yonyou.iuap.auth.example.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yonyou.iuap.auth.session.SessionManager;
import com.yonyou.iuap.context.InvocationInfoProxy;

@Controller
@RequestMapping(value = "/")
public class IndexController {

	private Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private SessionManager sessionMgr;

	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest req) {
		String cuser = null;
		if (SecurityUtils.getSubject().getPrincipal() != null)
			cuser = (String) SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("cusername", cuser == null ? "" : cuser);
		logger.debug("current user is {}", cuser);

		try {
			logger.info("sessions:" + sessionMgr.countOnlineSessions());
			logger.info("users:" + sessionMgr.countOnlineUsers());
		} catch (Exception e) {
			logger.error("", e);
		}
		logger.info("locale:{}, timezone:{}, real:{}|{}", InvocationInfoProxy.getLocale(), InvocationInfoProxy.getTimeZone(), req.getLocale().toString());
		return "index";
	}
}