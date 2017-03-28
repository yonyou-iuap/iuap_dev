package com.yonyou.iuap.crm.ieop.security.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService;
import com.yonyou.uap.ieop.security.service.IRolePluginService;

public class PositionRolePluginServiceImpl implements IRolePluginService
{	
	protected static Logger logger = LoggerFactory.getLogger(PositionRolePluginServiceImpl.class);
	@Autowired
	protected IDefinePositionService positionService;
	@Override
	public Set<String> findAllRoleByUserId(String userId) throws Exception {
		
		Set<String> roles =null;
		try {
			roles = positionService.findAllRoleByUserId(userId);
		} catch (Exception e) {
			logger.error("查询用户岗位角色异常.", e);
		}
		return roles;
	}

}
