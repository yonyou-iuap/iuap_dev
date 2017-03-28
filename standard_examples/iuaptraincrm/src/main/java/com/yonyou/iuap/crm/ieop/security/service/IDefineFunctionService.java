package com.yonyou.iuap.crm.ieop.security.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.uap.ieop.security.entity.ExtFunction;
import com.yonyou.uap.ieop.security.service.IBaseService;

/**
 * <p>
 * Title: IFunctionService
 * </p>
 * <p>
 * Description:功能服务
 * </p>
 * 
 * @project： security_shiro
 * @Company: Yonyou
 * @author zhangyaoc
 * @version 1.0
 * @since JDK 1.7.0_67
 * @date May 29, 2015 3:15:44 PM
 */
public interface IDefineFunctionService extends IBaseService<ExtFunction, String> {

	/**
	 * @param searchParams
	 * @param pageRequest
	 * @return
	 */
	public abstract Page<ExtFunction> getDemoPage(
			Map<String, Object> searchParams, PageRequest pageRequest) throws AppBusinessException;
	
	
	  public abstract ExtFunction getFuncRoot()
			    throws Exception;
	
	/**
	 * 根据用户查询根功能
	 * 
	 * @param userId
	 * @return
	 */
	public abstract ExtFunction getFuncRootByUser(String userId) throws AppBusinessException;
	
	/**
	 * 功能查询参照过滤
	* TODO description
	* @author 
	* @date 2016年12月9日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	 */
	public Page<ExtFunction> getDemoPageForRef(Map<String, Object> searchParams,
			PageRequest pageRequest) throws AppBusinessException ;
}