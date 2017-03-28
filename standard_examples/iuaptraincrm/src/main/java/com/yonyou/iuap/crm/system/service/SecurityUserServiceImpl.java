package com.yonyou.iuap.crm.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.uap.ieop.security.entity.SecurityUser;
import com.yonyou.uap.ieop.security.service.ISecurityUserService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.user.entity.ExtIeopUserVO;
import com.yonyou.iuap.crm.user.service.itf.IExtIeopUserService;

/***
 * 引用  yonyou-security-core.jar的工程，需要实现  ISecurityUserService此接口，用来做用户查询
 */
@Service("appSecurityUserService")
public class SecurityUserServiceImpl implements ISecurityUserService{
	
	@Autowired
	IExtIeopUserService userService;
	
	public SecurityUser findUserByLoginName(String loginName) throws AppBusinessException {

		List<ExtIeopUserVO> user_list;
		try {
			user_list = userService
					.getBdUserByCodeAndName(loginName, "", "");
		} catch (Exception e) {
			throw new AppBusinessException("查询用户出错",e);
		}
		if (user_list != null && user_list.size() > 0) {
			ExtIeopUserVO user = user_list.get(0);
			SecurityUser shiroUser = new SecurityUser(user.getId().toString(),
					user.getLoginName(), user.getName());
			return shiroUser;
		}
		return null;
	}
}
