package com.yonyou.iuap.crm.ieop.security.service;

import java.util.List;

import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.uap.ieop.security.entity.ExtFunction;
import com.yonyou.uap.ieop.security.entity.FunctionActivity;

public interface IDefineRolePermissionService {
	
	public void createRoleFuncRelation(String roleId, String rolecode,
			String funcId) throws AppBusinessException;
	
	public List<ExtFunction> queryHasPermissionFunction(String roleid)
			throws Exception;
	
	public abstract void createActivityPermissionBatch(List<FunctionActivity> datas, String roleId,
			String rolecode, String funcId) throws Exception;
}
