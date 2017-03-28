
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : iuaptraincrm
*
* @File name : ICustomerInfoService.java
*
* @author 
*
* @Date : 2016年12月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月8日    name    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.iuap.crm.basedata.service.itf;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.TmCustomerVehicleExt2VO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerVehicleExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerVehicleVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomercontactorVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;


/**
 * 客户信息维护Service接口
 * @author 
 * @date 2016年12月8日
 */	
public interface ICustomerInfoService {
	
	
	/**
	 * 根据主键获取客户信息
	* TODO
	* @author 
	* @date 2016年12月19日
	* @param pk
	* @return
	* @throws DAOException
	 */
	public TmCustomerinfoVO getCustomerinfo(String pk)
			throws AppBusinessException;
	
	
	/**
	 * 获取客户信息分页列表
	* @author 
	* @date 2016年12月12日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	 */
	public Page<TmCustomerinfoVO> getCustomerinfoByPages(Map<String, Object> searchParams,
			 PageRequest pageRequest) throws AppBusinessException;
	
	/**
	 * 获取客户扩展信息分页列表
	* @author 
	* @date 2016年12月12日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	 */
	public Page<TmCustomerinfoExtVO> getCustomerinfoExtByPages(Map<String, Object> searchParams,
			 PageRequest pageRequest) throws AppBusinessException;
	
	
	/**
	 * 获取客户联系人信息
	* @author 
	* @date 2016年12月12日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	 */
	public Page<TmCustomercontactorVO> getCustomercontactorByPages(Map<String, Object> searchParams,
			 PageRequest pageRequest) throws AppBusinessException;
	
	/**
	 *  获取客户车辆分页列表信息
	* @author 
	* @date 2016年12月12日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	 */
	public Page<TmCustomerVehicleVO> getCustomerVehicleByPages(Map<String, Object> searchParams,
			 PageRequest pageRequest) throws AppBusinessException;
	
	/**
	 * 获取客户车辆扩展信息分页列表
	* @author 
	* @date 2016年12月12日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	 */
	public Page<TmCustomerVehicleExt2VO> getCustomerVehicleExt2ByPages(String pkCustomer,
			 PageRequest pageRequest) throws AppBusinessException;
	
	
	
	/**
	 * 保存客户信息和其联系人信息
	* @author 
	* @date 2016年12月23日
	* @param pavo
	* @param mpvos
	* @return
	* @throws AppBusinessException
	 */
	public void saveCustomerinfo(TmCustomerinfoVO pavo,List<TmCustomercontactorVO> mpvos) throws AppBusinessException;
	
	/**
	 * 保存客户信息
	* @author 
	* @date 2016年12月12日
	* @param vo
	* @return
	* @throws AppBusinessException
	 */
	public String saveCustomerinfo(TmCustomerinfoVO vo) throws AppBusinessException;
	
	
	
	/**
	 * 保存客户联系人信息
	* @author 
	* @date 2016年12月12日
	* @param vo
	* @return
	* @throws AppBusinessException
	*/
	public String saveCustomercontactor(TmCustomercontactorVO vo) throws AppBusinessException;
	
	
	/**
	 * 合并客户信息
	* @author 
	* @date 2017年1月3日
	* @param vo
	* @throws AppBusinessException
	 */
	public void mergeCustomerinfo(Map<String, Object> main)
			throws AppBusinessException;
			
	/**
	* 更新客户信息
	* @author 
	* @date 2016年12月12日
	* @param vo
	* @throws AppBusinessException
	*/
	public void updateCustomerinfo(TmCustomerinfoVO vo) throws AppBusinessException;
	
	
	/**
	 * 批量更新客户信息
	* @author 
	* @date 2016年12月30日
	* @param entityLst
	* @throws AppBusinessException
	 */
	public void batchUpdateCustomerinfo(List<TmCustomerinfoVO> entityLst) throws AppBusinessException;
	
	/**
	 * 删除客户联系人信息
	* @author 
	* @date 2016年12月12日
	* @param vo
	* @throws AppBusinessException
	*/
	public void deleteCustomercontactor(TmCustomercontactorVO vo) throws AppBusinessException;
	
	
	/**
	 * 批量删除客户联系人信息
	* @author 
	* @date 2016年12月12日
	* @param voList
	* @throws AppBusinessException
	*/
	public void deleteBatchCustomercontactor(List<TmCustomercontactorVO> voList) throws AppBusinessException;
	
	/**
	 *  获取车辆列表信息
	* @author 
	* @date 2017年01月16日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	 */
	public Page<TmCustomerVehicleExtVO> getVehicleExtByPages(Map<String, String> searchParams,
			 PageRequest pageRequest) throws AppBusinessException;
	
}
