package com.yonyou.iuap.crm.system.web.index;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.uap.ieop.security.cache.RbacCacheManager;

/**
 * 工程首页跳转示例，对应shiro配置文件中的映射，项目中根据需求修改
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {
	
	private Logger logger = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model) {
		String cuser = null;
		if (SecurityUtils.getSubject().getPrincipal() != null)
			cuser = (String) SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("cusername", cuser==null?"":cuser);
		logger.debug("current user is {}",cuser);
		
		return "forward:/index.html";
	}
	
	@RequestMapping(value="/authLogin",method = RequestMethod.GET)
	public @ResponseBody String authLogin(ModelMap model,HttpServletRequest request) {
		String cuser = null;
		if (AppInvocationInfoProxy.getUserCNName() != null)
			cuser = AppInvocationInfoProxy.getUserCNName();
		String tenantId = InvocationInfoProxy.getTenantid();
		String sysId = InvocationInfoProxy.getSysid();
		String userId = AppInvocationInfoProxy.getPk_User();
		RbacCacheManager.getInstance().login(tenantId, sysId, userId);
		return cuser;
	}
}
