package com.yonyou.iuap.system.web.index;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
		
		//如果有多语环境，跳转到指定语种的静态资源
		//return "forward:/index." + InvocationInfoProxy.getLocale()+".html";
		
		return "forward:/index.html";
	}
}
