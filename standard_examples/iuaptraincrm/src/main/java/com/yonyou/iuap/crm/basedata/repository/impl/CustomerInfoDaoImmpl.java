/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : iuaptraincrm
 *
 * @File name : CustomerInfoDaoImmpl.java
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

package com.yonyou.iuap.crm.basedata.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.crm.basedata.entity.TmCustomerVehicleExt2VO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerVehicleVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomercontactorVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.processor.BeanListProcessor;
import com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;

/**
 * 客户信息维护Dao实现类
 * 
 * @author 
 * @date 2016年12月8日
 */
@Repository
public class CustomerInfoDaoImmpl implements ICustomerInfoDao {
	@Autowired
	private AppBaseDao baseDao;

	/**
	 * @author 
	 * @date 2016年12月12日
	 * @param pk
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao#getTmCustomerinfoVO(java.lang.String)
	 */

	@Override
	public TmCustomerinfoVO getTmCustomerinfoVO(String pk) throws DAOException {
		return baseDao.findById(TmCustomerinfoVO.class, pk);
	}

	/**
	 * 获取客户信息分页列表
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return (non-Javadoc)
	 * @throws DAOException
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao#getCustomerinfoByPages(java.lang.String,
	 *      com.yonyou.iuap.persistence.jdbc.framework.SQLParameter,
	 *      org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<TmCustomerinfoVO> getCustomerinfoByPages(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
		return baseDao.findBypageWithDR(TmCustomerinfoVO.class, condition,
				sqlParameter, pageRequest);
	}

	/**
	 * 获取客户扩展信息分页列表
	 * 
	 * @author 
	 * @date 2016年12月9日
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao#getCustomerinfoExtByPages(java.lang.String,
	 *      com.yonyou.iuap.persistence.jdbc.framework.SQLParameter,
	 *      org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<TmCustomerinfoExtVO> getCustomerinfoExtByPages(String sqlJoin,
			String condition, SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
		return baseDao.findBypageWithJoin(TmCustomerinfoExtVO.class, condition,
				sqlParameter, pageRequest, sqlJoin);
	}

	/**
	 * 获取客户联系人信息
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return (non-Javadoc)
	 * @throws DAOException
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao#getCustomercontactorByPages(java.lang.String,
	 *      com.yonyou.iuap.persistence.jdbc.framework.SQLParameter,
	 *      org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<TmCustomercontactorVO> getCustomercontactorByPages(
			String condition, SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
		return baseDao.findBypageWithDR(TmCustomercontactorVO.class, condition,
				sqlParameter, pageRequest);
	}

	/**
	 * 获取客户车辆分页列表信息
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao#getCustomerVehicleByPages(java.lang.String,
	 *      com.yonyou.iuap.persistence.jdbc.framework.SQLParameter,
	 *      org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<TmCustomerVehicleVO> getCustomerVehicleByPages(
			String condition, SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
		return baseDao.findBypageWithDR(TmCustomerVehicleVO.class, condition,
				sqlParameter, pageRequest);

	}
	
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
	@Override
	public Page<TmCustomerVehicleExt2VO> getCustomerVehicleEx2ByPages(String sqlJoin,
			String condition, SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
	
		return baseDao.findBypageWithJoin(TmCustomerVehicleExt2VO.class, condition,
				sqlParameter, pageRequest, sqlJoin);
	}
	
	

	/**
	 * 保存客户信息和其联系人子表
	 * 
	 * @author 
	 * @date 2016年12月30日
	 * @param pavo
	 * @param mpvos
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao#saveCustomerinfoWithContactor(com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoVO,
	 *      java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public void saveCustomerinfoWithContactor(TmCustomerinfoVO pavo,
			List<TmCustomercontactorVO> mpvos) throws DAOException {
		
		baseDao.mergeWithChild(pavo, mpvos);
		
	}

	/**
	 * 保存客户信息
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param vo
	 * @return (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao#saveCustomerinfo(com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoVO)
	 */
	@Override
	public String saveCustomerinfo(TmCustomerinfoVO vo) throws DAOException {
		return baseDao.saveWithPK(vo);
	}

	/**
	 * 保存客户联系人信息
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param vo
	 * @return (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao#saveCustomercontactor(com.yonyou.iuap.crm.basedata.entity.TmCustomercontactorVO)
	 */
	@Override
	public String saveCustomercontactor(TmCustomercontactorVO vo)
			throws DAOException {
		return baseDao.saveWithPK(vo);
	}

	/**
	 * 更新客户信息
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param vo
	 * @return (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao#updateCustomerinfo(com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoVO)
	 */
	@Override
	public void updateCustomerinfo(TmCustomerinfoVO vo) throws DAOException {
		baseDao.update(vo);
	}

	/**
	 * 批量更新客户车辆信息
	 * 
	 * @author 
	 * @date 2017年1月3日
	 * @param vo
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao#batchUpdateCustomerVehicle(java.util.List)
	 */

	@Override
	public void batchUpdateCustomerVehicle(List<TmCustomerVehicleVO> entityLst)
			throws DAOException {
		baseDao.batchUpdate(entityLst);
	}

	/**
	 * 批量更新客户联系人信息
	 * 
	 * @author 
	 * @date 2017年1月3日
	 * @param vo
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao#batchUpdateCustomerContactor(java.util.List)
	 */

	@Override
	public void batchUpdateCustomerContactor(
			List<TmCustomercontactorVO> entityLst) throws DAOException {
		baseDao.batchUpdate(entityLst);
	}

	/**
	 * 批量更新客户信息
	 * 
	 * @author 
	 * @date 2016年12月30日
	 * @param entityLst
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao#batchUpdateCustomerinfo(java.util.List)
	 */

	@Override
	public void batchUpdateCustomerinfo(List<TmCustomerinfoVO> entityLst)
			throws DAOException {
		baseDao.batchUpdate(entityLst);
	}

	/**
	 * 删除客户信息
	 * 
	 * @author 
	 * @date 2016年12月20日
	 * @param vo
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao#deleteCustomerinfo(com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoVO)
	 */

	@Override
	public void deleteCustomerinfo(TmCustomerinfoVO vo) throws DAOException {
		baseDao.deleteWithDR(vo);
	}

	/**
	 * 删除客户联系人信息
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param vo
	 * @return (non-Javadoc)
	 * @throws DAOException
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao#deleteCustomercontactor(com.yonyou.iuap.crm.basedata.entity.TmCustomercontactorVO)
	 */
	@Override
	public void deleteCustomercontactor(TmCustomercontactorVO vo)
			throws DAOException {
		baseDao.deleteWithDR(vo);
	}

	/**
	 * 批量删除客户联系人信息
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param voList
	 * @return (non-Javadoc)
	 * @throws DAOException
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao#deleteBatchCustomercontactor(java.util.List)
	 */
	@Override
	public void deleteBatchCustomercontactor(List<TmCustomercontactorVO> voList)
			throws DAOException {
		baseDao.batchDeleteWithDR(voList);
	}

	/**
	 * @author 
	 * @date 2017年1月3日
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao#getCustomercontactors(java.lang.String,
	 *      com.yonyou.iuap.persistence.jdbc.framework.SQLParameter,
	 *      org.springframework.data.domain.PageRequest)
	 */

	@Override
	public List<TmCustomercontactorVO> getCustomercontactors(
			String pkCustomerinfo) throws DAOException {
		StringBuffer sql = new StringBuffer();
		SQLParameter para = new SQLParameter();

		sql.append("SELECT * ").append("FROM tm_customercontactor AS tc ")
				.append("WHERE tc.pk_customerinfo=?").append(" AND tc.dr=0");
		para.addParam(pkCustomerinfo);

		return baseDao.queryForList(sql.toString(), para,
				new BeanListProcessor(TmCustomercontactorVO.class));
	}

	/**
	 * @author 
	 * @date 2017年1月3日
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao#getCustomerVehicles(java.lang.String,
	 *      com.yonyou.iuap.persistence.jdbc.framework.SQLParameter,
	 *      org.springframework.data.domain.PageRequest)
	 */

	@Override
	public List<TmCustomerVehicleVO> getCustomerVehicles(String pkCustomerinfo)
			throws DAOException {
		StringBuffer sql = new StringBuffer();
		SQLParameter para = new SQLParameter();
		sql.append("SELECT * ").append("FROM tm_customervehicle AS tc ")
				.append("WHERE tc.pk_customerinfo=?").append(" AND tc.dr=0");
		para.addParam(pkCustomerinfo);
		return baseDao.queryForList(sql.toString(), para,
				new BeanListProcessor(TmCustomerVehicleVO.class));
	}

}
