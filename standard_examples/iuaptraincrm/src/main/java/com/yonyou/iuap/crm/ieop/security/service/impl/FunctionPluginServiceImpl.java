package com.yonyou.iuap.crm.ieop.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.iuap.crm.ieop.security.service.IDefineFunctionActivityService;
import com.yonyou.uap.ieop.security.entity.Function;
import com.yonyou.uap.ieop.security.entity.FunctionActivity;
import com.yonyou.uap.ieop.security.service.IFunctionPluginService;
import com.yonyou.uap.ieop.security.service.IFunctionService;

public class FunctionPluginServiceImpl implements IFunctionPluginService{
	@Autowired IFunctionService functionService;
	@Autowired IDefineFunctionActivityService functionActionService;
	@Override
	public List<Function> findAllFunction() throws Exception {
		// TODO Auto-generated method stub
		return functionService.findAll();
	}
	@Override
	public List<FunctionActivity> findFunctionActivity(String functionId) throws Exception {
		// TODO Auto-generated method stub
		return functionActionService.getFuncActivityByFuncID(functionId);
	}
	

}
