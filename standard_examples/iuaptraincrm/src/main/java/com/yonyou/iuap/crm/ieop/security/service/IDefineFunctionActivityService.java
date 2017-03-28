package com.yonyou.iuap.crm.ieop.security.service;

import java.util.List;

import com.yonyou.uap.ieop.security.entity.FunctionActivity;

public interface IDefineFunctionActivityService {

	/**
	 * 获取用户所有业务活动权限
	 * 
	 * @param userId
	 * @return
	 */
	public abstract List<FunctionActivity> getFuncActivityByFuncID(String funcID) throws Exception;
}
