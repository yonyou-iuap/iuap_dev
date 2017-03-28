package com.yonyou.iuap.crm.common.auth.realm;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.iuap.auth.session.SessionManager;
import com.yonyou.iuap.auth.shiro.StatelessRealm;
import com.yonyou.iuap.auth.shiro.StatelessToken;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;

public class ExtStatelessRealm extends StatelessRealm {

	 @Autowired
	 private SessionManager sessionManager;
	 
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken arg0) throws AuthenticationException {
		SimpleAuthenticationInfo info =  (SimpleAuthenticationInfo)super.doGetAuthenticationInfo(arg0);
		String token = (String)info.getCredentials();
		StatelessToken token1 = (StatelessToken)arg0;
		String uname = (String)token1.getPrincipal();
		String userCNName = (String)sessionManager.getSessionCacheAttribute(token, uname);
		String pk_user = (String)sessionManager.getSessionCacheAttribute(token, "pk_user");
		String pk_corp = (String)sessionManager.getSessionCacheAttribute(token, "pk_corp");
		String pk_dept = (String)sessionManager.getSessionCacheAttribute(token, "pk_dept");
		
		AppInvocationInfoProxy.setUserCNName(userCNName);
		AppInvocationInfoProxy.setPk_User(pk_user);
		AppInvocationInfoProxy.setPk_Corp(pk_corp);
		AppInvocationInfoProxy.setPk_Dept(pk_dept);
		return info;
	}

}
