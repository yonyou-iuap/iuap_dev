
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : iuaptraincrm
*
* @File name : ICustomerInfoDao.java
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
	
package com.yonyou.iuap.crm.basedata.repository.itf;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.TmCustomerVehicleExt2VO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerVehicleVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomercontactorVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;

/**
 * 客户信息维护Dao接口
 * @author 
 * @date 2016年12月8日
 */
	
public interface ICustomerInfoDao {
	
	
	/**
	 * 根据pk， 获取客户信息
	* @author 
	* @date 2016年12月30日
	* @param pk
	* @return
	* @throws DAOException
	 */
	public TmCustomerinfoVO getTmCustomerinfoVO(String pk) throws DAOException;
	
	/**
	 *  获取客户信息分页列表
	* @author 
	* @date 2016年12月12日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	 */
	public Page<TmCustomerinfoVO> getCustomerinfoByPages(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException;
	
	/**
	 * 获取客户扩展信息分页列表
	* @author 
	* @date 2016年12月12日
	* @param sqlJoin
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	 */
	public Page<TmCustomerinfoExtVO> getCustomerinfoExtByPages(String sqlJoin, String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException;
	
	
	
	
	/**
	 * 获取客户联系人信息
	* @author 
	* @date 2016年12月12日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	 */
	public Page<TmCustomercontactorVO> getCustomercontactorByPages(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException;
	
	
	/**
	 * 获取客户联系人信息
	* @author 
	* @date 2016年12月12日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	 */
	public List<TmCustomercontactorVO> getCustomercontactors(String pkCustomerinfo) throws DAOException;
	
	
	/**
	 * 获取客户车辆分页列表信息
	* @author 
	* @date 2016年12月12日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	 */
	public Page<TmCustomerVehicleVO> getCustomerVehicleByPages(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException;
	
	/**
	 * 获取客户车辆扩展分页列表信息
	* @author 
	* @date 2016年12月12日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	 */
	public Page<TmCustomerVehicleExt2VO> getCustomerVehicleEx2ByPages(String sqlJoin,
			String condition, SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException;
	
	
	/**
	 * 获取客户车辆分页列表信息
	* @author 
	* @date 2016年12月12日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	 */
	public List<TmCustomerVehicleVO> getCustomerVehicles(String pkCustomerinfo) throws DAOException;
	
	
	
	/**
	 * 保存客户信息和联系人信息
	* @author 
	* @date 2016年12月16日
	* @param pavo
	* @param mpvos
	* @return
	* @throws DAOException
	 */
	public void saveCustomerinfoWithContactor(TmCustomerinfoVO pavo,List<TmCustomercontactorVO> mpvos) throws DAOException;
	
	/**
	 * 保存客户信息
	* @author 
	* @date 2016年12月12日
	* @param vo
	* @return
	* @throws DAOException
	 */
	public String saveCustomerinfo(TmCustomerinfoVO vo) throws DAOException;
	
	/**
	 * 保存客户联系人信息
	* @author 
	* @date 2016年12月12日
	* @param vo
	* @return
	* @throws DAOException
	 */
	public String saveCustomercontactor(TmCustomercontactorVO vo) throws DAOException;
	
	/**
	 * 更新客户信息
	* @author 
	* @date 2016年12月12日
	* @param vo
	* @throws DAOException
	 */
	public void updateCustomerinfo(TmCustomerinfoVO vo) throws DAOException;
	
	
	/**
	 * 批量更新客户车辆信息
	* @author 
	* @date 2016年12月12日
	* @param vo
	* @throws DAOException
	 */
	public void batchUpdateCustomerVehicle(List<TmCustomerVehicleVO> entityLst) throws DAOException;
	
	/**
	 * 批量更新客户联系人
	* @author 
	* @date 2016年12月12日
	* @param vo
	* @throws DAOException
	 */
	public void batchUpdateCustomerContactor(List<TmCustomercontactorVO> entityLst) throws DAOException;
	
	/**
	 * 批量更新客户信息
	* @author 
	* @date 2016年12月30日
	* @param entityLst
	* @throws DAOException
	 */
	public void batchUpdateCustomerinfo(List<TmCustomerinfoVO> entityLst) throws DAOException;
	
	/**
	 * 删除客户联系人信息
	* @author 
	* @date 2016年12月12日
	* @param vo
	* @throws DAOException
	 */
	public void deleteCustomercontactor(TmCustomercontactorVO vo) throws DAOException;
	
	
	/**
	 * 删除客户信息
	* @author 
	* @date 2016年12月20日
	* @param vo
	* @throws DAOException
	 */
	public void deleteCustomerinfo(TmCustomerinfoVO vo) throws DAOException;
	
	/**
	 * 批量删除客户联系人信息
	* @author 
	* @date 2016年12月12日
	* @param voList
	* @throws DAOException
	 */
	public void deleteBatchCustomercontactor(List<TmCustomercontactorVO> voList) throws DAOException;
	
	
	
}
