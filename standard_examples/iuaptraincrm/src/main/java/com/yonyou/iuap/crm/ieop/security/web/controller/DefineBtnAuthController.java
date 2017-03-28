package com.yonyou.iuap.crm.ieop.security.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.crm.ieop.security.service.IDefineFunctionActivityService;
import com.yonyou.iuap.crm.ieop.security.service.IDefineFunctionService;
import com.yonyou.iuap.iweb.exception.BusinessException;
import com.yonyou.uap.ieop.security.cache.RbacCacheManager;
import com.yonyou.uap.ieop.security.entity.ExtFunction;
import com.yonyou.uap.ieop.security.entity.Function;
import com.yonyou.uap.ieop.security.entity.FunctionActivity;
import com.yonyou.uap.ieop.security.service.IFunctionActivityService;
import com.yonyou.uap.ieop.security.web.BaseController;

/**
 * 对于url的方式如何获取？
 * @author fanchj1
 *
 */
@Controller
@RequestMapping("/security/authBtn")
public class DefineBtnAuthController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
//	@Autowired
//	private IDefineFunctionService funcService;
	
//	@Autowired
//	private IDefineFunctionActivityService actionService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	@ResponseBody
	public Object handleBtnGroups(@RequestParam(value="funcCode") String funcCode)
			throws Exception {
//		if(!StringUtils.isEmpty(funcUrl) && funcUrl.startsWith("pages")){
//			funcUrl = funcUrl.replace("pages", "");
//		}
//		ExtFunction func = funcService.getFuncByFuncCode(funcCode);
		List<Function> funcList = RbacCacheManager.getInstance().getFunclist();
		Function func = null;
		if(funcList!=null && !funcList.isEmpty()){
			for(Function temp:funcList){
				if(temp.getFuncCode().equals(funcCode)){
					func = temp;
				}
			}
		}else{
			throw new BusinessException("没有缓存功能节点数据");
		}
		if(func == null || StringUtils.isEmpty(func.getFuncUrl())){
			throw new BusinessException("您查找的功能节点不存在");
		}
		/**
		 * 默认没有启用按钮权限，显示所有注册的按钮；
		 */
//		List<FunctionActivity> actions = actionService.getFuncActivityByFuncID(func.getId());
		String tenantId = InvocationInfoProxy.getTenantid();
		String sysId = InvocationInfoProxy.getSysid();
		List<FunctionActivity> actions = RbacCacheManager.getInstance().getFuncActivity(tenantId, sysId, func.getId());
		List<FunctionActivity> returnList = new ArrayList<FunctionActivity>();
		if((SecurityUtils.getSubject().hasRole("admin")) || func.getEnableAction().equals("N")){
			returnList = actions;
//			return actions;
		}else{
			if(actions.isEmpty()){
				throw new BusinessException("没有注册按钮资源");
			}
			List<FunctionActivity> temp = new ArrayList<FunctionActivity>();
			for(int i=0; i<actions.size(); i++){
				FunctionActivity btn = actions.get(i);
				if (isValidButton(btn)) {
					temp.add(btn);
				}
			}
			returnList = temp;
//			return temp;
		}
		
		List<String> funList = new ArrayList<String>();
		if(null != returnList){
			for(FunctionActivity activityVO : returnList){
				funList.add(activityVO.getActivityName());
			}
		}
		return funList;
	}
	
	private boolean isValidButton(FunctionActivity action) {

		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser != null && "Y".equalsIgnoreCase(action.getIsactive())) {
			return currentUser.isPermitted(action.getActivityCode());
		}
		return false;

	}
}
