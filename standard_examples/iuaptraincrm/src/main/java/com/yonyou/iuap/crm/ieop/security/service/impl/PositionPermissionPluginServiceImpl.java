package com.yonyou.iuap.crm.ieop.security.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.yonyou.iuap.crm.ieop.security.service.IDefinePositionService;
import com.yonyou.uap.ieop.security.entity.RolePermission;
import com.yonyou.uap.ieop.security.service.IRolePermissionService;
import com.yonyou.uap.ieop.security.service.impl.BaseServiceImpl;

//@Service
public class PositionPermissionPluginServiceImpl extends BaseServiceImpl<RolePermission,String> implements IRolePermissionService{

	@Autowired
	protected IDefinePositionService positionService;
	
	public PositionPermissionPluginServiceImpl(){
		
	}
	
	
	
	public List<RolePermission> findAllRoleByFuncId(String funcId) {
		return null;
	}

	
	public List<RolePermission> findAllFuncByRoleId(String roleId) {
		return null;
	}

	
	public List<RolePermission> findAllFuncByUserId(String UserId) {
		return null;
	}
	
	public List<RolePermission> findAllFuncByUserIdAndType(String UserId,String type){
		return null;
	}
	
	public Page<RolePermission> getRoleFuncPage(Map<String, Object> searchParams,
			PageRequest pageRequest) {
		return null;
	}

	
	/**
	 * 根据用户及岗位及角色用户用户的资源
	 */
	public Map<String, List<RolePermission>> findAllPermWithTypeByUserId(
			String userId) {
		List<RolePermission> funcInUser=positionService.findAllFuncByUserId(userId);
		Map<String,List<RolePermission>>permissions=new HashMap<String, List<RolePermission>>();
		for(RolePermission perm:funcInUser){
			List<RolePermission> tempList=permissions.get(perm.getPermissionType());
			if(tempList==null){
				tempList=new ArrayList<RolePermission>();
				permissions.put(perm.getPermissionType(), tempList);
			}
			tempList.add(perm);
		}
		
		return permissions;
	}



	@Override
	public Specification<RolePermission> buildSpecification(
			Map<String, Object> paramMap) throws Exception {
		
		return null;
	}
	
}
