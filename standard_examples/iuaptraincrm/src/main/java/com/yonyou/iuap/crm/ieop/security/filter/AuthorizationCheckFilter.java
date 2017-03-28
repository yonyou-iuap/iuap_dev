package com.yonyou.iuap.crm.ieop.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.uap.ieop.security.cache.RbacCacheManager;
import com.yonyou.uap.ieop.security.entity.Function;
import com.yonyou.uap.ieop.security.entity.FunctionActivity;

/**
 * 权限过滤filter
 * 
 * @author zhangyaoc
 * 
 *         按钮权限如果也要在filter里做权限过滤需要重新处理功能节点的url和对应按钮的code 将url对应的controller中的
 * @RequestMapping(value = "/security/role_function")
 *                       其中的value注册为funCode，按钮对应controller中某一具体方法的value
 *                       然后将FunctionActivity对象中存储的funcode和activity_code作为校验权限的标志
 */
public class AuthorizationCheckFilter implements Filter {
	
//	private IExtFunctionService funcService = null;
//	private IExtFunctionActivityService activityService = null;

	@Override
	public void destroy() {

	}

	private Logger logger = LoggerFactory
			.getLogger(AuthorizationCheckFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest re = (HttpServletRequest)request;
//		System.out.println( SecurityUtils.getSubject().getPrincipal());
//		System.out.println(re.getRequestURI());
		
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		boolean isPermissioin = true;
		if (StringUtils.isEmpty(username) || SecurityUtils.getSubject().hasRole("admin")||re.getRequestURI().contains(".do")) {
			chain.doFilter(request, response);
		} else {
			try {
				HttpServletRequest httpRequest = (HttpServletRequest) request;
				String uri = httpRequest.getRequestURI();
				
				/**
				 * 1.验证功能有没有权限
				 */
				List<Function> functions = new ArrayList<Function>();
				isPermissioin = this.authFunctionPermission(uri,functions);
				Function currentFunction = null;
				if(functions.size() > 0){
					currentFunction = functions.get(0);
				}
				/**
				 * 2.验证按钮有没有权限
				 * 对于有权限的功能进行启用按钮权限验证
				 */
				if(currentFunction!=null && isPermissioin==true && currentFunction.isEnableAction()){
					isPermissioin = this.authFunctionActivityPermission(uri,currentFunction);
				}
			} catch (Exception e) {
				logger.error("Authentication Filter Error.", e.getMessage());
			}
			if (isPermissioin) {
				/** 有执行方法或权限不拦截 */
				chain.doFilter(request, response);
			} else {
				/**
				 * 抛出无权限异常,具体无权的处理你们可以自己改下
				 * 拦截无权异常NoPermissionExcepiton，将信息回写无权状态
				 */
				  HttpServletResponse  resp = (HttpServletResponse) response;
				  resp.setCharacterEncoding("utf-8");
				  resp.setHeader("errorStatus", "306");
				  resp.setHeader("errorInfo", "NoPermission!");
				  
				  resp.setStatus(306);
		          JSONObject json = new JSONObject();
		          json.put("msg", "NoPermission!");
		          resp.getWriter().write(json.toString());
		          logger.error("Authentication Filter Error.{}用户没有权限操作{}",SecurityUtils.getSubject().getPrincipal(),re.getRequestURI());
//				  throw new NoPermissionExcepiton("该用户没有权限执行此操作！");
			}
		}

	}
	
	/**
	 * 2.功能权限校验
	 * 默认功能权限中存储的URL受权限保护，如当前访问url不在功能注册表范围内，则不需要权限-- 则直接运行 
	 * @throws Exception 
	 */
	public boolean authFunctionPermission(String uri,List<Function> functions) throws Exception{
//		List<Function> funcList = funcService.findAll();
		List<Function> funcList = RbacCacheManager.getInstance().getFunclist();
		if(funcList!=null && !funcList.isEmpty()){
			/**
			 * 1.获取所有有效的带功能URL的功能节点
			 */
			Map<String, Function> authFunctionMap = new HashMap<String, Function>();
			for (Function func : funcList) {
				if ("N".equalsIgnoreCase(func.getIsleaf())//是功能节点
						&& func.getFuncUrl() != null
						&& func.getFuncUrl().length() != 0
						&& func.getFuncCode() != null) {
					authFunctionMap.put(func.getFuncUrl(), func);
				}
			}
			
			Subject currentUser = SecurityUtils.getSubject();
			for (String funUri : authFunctionMap.keySet()) {
				if (uri.contains(funUri) && authFunctionMap.get(funUri) != null) {
					Function currentFunction=authFunctionMap.get(funUri);
					functions.add(currentFunction);
					String funcode = authFunctionMap.get(funUri)
							.getFuncCode();
					/**
					 * isactive含未启用的功能（则不能访问）
					 * 实际对于没有启用的功能节点不应该分配，对于已经分配的，这里也可以处理；【补充说明，没有分配的通过isPermitted校验也是不通过的】
					 */
					if("N".equalsIgnoreCase(currentFunction.getIsactive())){
						logger.error("Authentication Filter Error.{}用户操作{}功能已经停用{}",SecurityUtils.getSubject().getPrincipal(),currentFunction.getFuncName(),funUri);
						return false;
					}
					/**
					 * iscontrol没开启业务活动权限的默认能够访问
					 */
					if("N".equalsIgnoreCase(currentFunction.getIscontrol())){
						return true;
					}else if (!currentUser.isPermitted(funcode)){
						/** 当前登录人 具有权限 */
						logger.error("Authentication Filter Error.{}用户没有权限操作{}功能{}",SecurityUtils.getSubject().getPrincipal(),currentFunction.getFuncName(),funUri);
						return false;
					}else{
						return true;
					}
				}
			}
		}
		/**
		 * 对于没有注册的功能的URL处理：
		 * 有权限，不拦截
		 */
		return true;
	}
	
	/**
	 * 3.按钮权限校验
	 * 必须先预置按钮数据
	 * @throws Exception 
	 */
	public boolean authFunctionActivityPermission(String uri,Function function) throws Exception{
//		List<FunctionActivity> activityList = activityService.getFuncActivityByFuncID(function.getId());
		String sysId = InvocationInfoProxy.getSysid();
		String tenantId = InvocationInfoProxy.getTenantid();
		List<FunctionActivity> activityList = activityList= RbacCacheManager.getInstance().getFuncActivity(tenantId, sysId, function.getId());
		if (activityList != null && activityList.size() > 0) {
			Subject currentUser = SecurityUtils.getSubject();
			for (FunctionActivity action : activityList) {
				String actionCode = action.getActivityCode();
				if (StringUtils.isEmpty(actionCode)) {
					continue;
				}
//				String actionUrl = action.getFuncCode()+ "/"
//						+ action.getActivityCode();
				String actionUrl = actionCode;
				/**
				 * 当前访问的按钮操作已经注册，但当前登录人不 具有按钮权限 
				 */
				if (uri.contains(actionUrl)) {
					if("N".equalsIgnoreCase(action.getIsactive())){
						logger.error("Authentication Filter Error.{}用户操作{}功能{}按钮已经停用{}",SecurityUtils.getSubject().getPrincipal(),function.getFuncName(),action.getActivityName(),actionUrl);
						return false;
					}
					if(!currentUser.isPermitted(actionUrl)){
						logger.error("Authentication Filter Error.{}用户没有权限操作{}功能{}按钮{}",SecurityUtils.getSubject().getPrincipal(),function.getFuncName(),action.getActivityName(),actionUrl);
						return false;
					}
					return true;
				}
			}
		}
		/**
		 * 没有注册的按钮的数据，不拦截
		 */
		return true;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
//		WebApplicationContext wac = ContextLoader
//				.getCurrentWebApplicationContext();
//		funcService = (IExtFunctionService) wac.getBean(IExtFunctionService.class);
//		activityService = (IExtFunctionActivityService) wac.getBean(IExtFunctionActivityService.class);
	}

}
