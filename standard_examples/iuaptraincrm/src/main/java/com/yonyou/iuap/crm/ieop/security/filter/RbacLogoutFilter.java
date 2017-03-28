package com.yonyou.iuap.crm.ieop.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.iuap.auth.shiro.LogoutFilter;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.uap.ieop.security.cache.RbacCacheManager;

public class RbacLogoutFilter extends LogoutFilter {

	@Override
	protected void doLogout(HttpServletRequest request,
			HttpServletResponse response) {
		super.doLogout(request, response);
		String tenantId = InvocationInfoProxy.getTenantid();
		String sysId = InvocationInfoProxy.getSysid();
		String userId = AppInvocationInfoProxy.getPk_User();
		RbacCacheManager.getInstance().logout(tenantId, sysId, userId);
	}

	
}
