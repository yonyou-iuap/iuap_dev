package com.yonyou.iuap.crm.ieop.security.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.ieop.security.entity.DefineRoleSaveVO;
import com.yonyou.iuap.crm.ieop.security.service.IDefineRoleSaveService;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.uap.ieop.security.service.impl.BaseServiceImpl;

/**
 * <p>
 * Title: RoleService
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @project： security_shiro
 * @Company: Yonyou
 * @author zhangyaoc
 * @version 1.0
 * @since JDK 1.7.0_67
 * @date May 29, 2015 3:14:54 PM
 */
@Service
public class DefineRoleSaveService extends BaseServiceImpl<DefineRoleSaveVO,String> implements
		IDefineRoleSaveService {

	@Autowired
	protected AppBaseDao appDao;

	public Page<DefineRoleSaveVO> getExtRolePage(Map<String, Object> searchParams,
			PageRequest pageRequest) throws Exception{
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		//{page.size=10, params={roleCode=123, roleName=312, pkCorp=null}}
		if(null != searchParams.get("roleCode") && !searchParams.get("roleCode").toString().equals("")){
			condition.append(" and role_code like ? ");
			sqlParameter.addParam("%"+searchParams.get("roleCode").toString()+"%");
		}
		if(null != searchParams.get("roleName") && !searchParams.get("roleName").toString().equals("")){
			condition.append(" and role_name like ? ");
			sqlParameter.addParam("%"+searchParams.get("roleName").toString()+"%");
		}
		return appDao.findBypageWithDR(DefineRoleSaveVO.class, condition.toString(), sqlParameter, pageRequest);
	}

	@Override
	public DefineRoleSaveVO saveRole(DefineRoleSaveVO role) throws Exception {
		// TODO 自动生成的方法存根
		appDao.mergeWithChild(role, null);
		return role;
	}
	
}
