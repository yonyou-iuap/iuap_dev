package com.yonyou.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.login.entity.User;
import com.yonyou.uap.ieop.security.entity.SecurityUser;
import com.yonyou.uap.ieop.security.service.ISecurityUserService;

/***
 * 引用  yonyou-security-core.jar的工程，需要实现  ISecurityUserService此接口，用来做用户查询
 */
@Service
public class SecurityUserServiceImpl implements ISecurityUserService{
	
	@Autowired
	AccountService userService;
	
	public SecurityUser findUserByLoginName(String loginName) throws Exception {
		
		User user = userService.findUserByLoginName(loginName);
		
		SecurityUser shiroUser=new SecurityUser(user.getId().toString(), user.getLoginName(), user.getName());
		
		return shiroUser;
	}
}
