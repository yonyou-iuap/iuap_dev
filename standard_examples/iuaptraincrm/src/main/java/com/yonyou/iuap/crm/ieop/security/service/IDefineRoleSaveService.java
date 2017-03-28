package com.yonyou.iuap.crm.ieop.security.service;


import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.ieop.security.entity.DefineRoleSaveVO;
import com.yonyou.uap.ieop.security.service.IBaseService;

/**
 * <p>
 * Title: IRoleService
 * </p>
 * <p>
 * Description:角色服务
 * </p>
 * 
 * @project： security_shiro
 * @Company: Yonyou
 * @author zhangyaoc
 * @version 1.0
 * @since JDK 1.7.0_67
 * @date May 29, 2015 3:16:00 PM
 */
public interface IDefineRoleSaveService extends IBaseService<DefineRoleSaveVO, String> {
	
	/**
	 * 保存角色
	 * 
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public DefineRoleSaveVO saveRole(DefineRoleSaveVO role) throws Exception;
	
	/**
	 * 
	* TODO description
	* @author name
	* @date 2017年1月3日
	* @param searchParams
	* @param pageRequest
	* @return
	 */
	public Page<DefineRoleSaveVO> getExtRolePage(Map<String, Object> searchParams,
			PageRequest pageRequest) throws Exception;

}