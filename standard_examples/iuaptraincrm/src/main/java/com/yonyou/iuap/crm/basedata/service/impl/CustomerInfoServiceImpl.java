/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : iuaptraincrm
 *
 * @File name : CustomerInfoServiceImpl.java
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

package com.yonyou.iuap.crm.basedata.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.crm.basedata.entity.TmCustomerVehicleExt2VO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerVehicleExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerVehicleVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomercontactorVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoVO;
import com.yonyou.iuap.crm.basedata.repository.itf.ICustomerInfoDao;
import com.yonyou.iuap.crm.basedata.service.itf.ICustomerInfoService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.common.utils.CheckUtil;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;
import com.yonyou.iuap.persistence.vo.pub.util.StringUtil;

/**
 * 客户信息维护Service实现
 * 
 * @author 
 * @date 2016年12月8日
 */
@Service
public class CustomerInfoServiceImpl implements ICustomerInfoService {

	@Autowired
	private ICustomerInfoDao customerinfoDao;
//	@Autowired
//	private ITrackInfoDao trackDao;
	@Autowired
	@Qualifier("baseDAO") private BaseDAO baseDao;

	/**
	 * 根据主键获取客户信息
	 * 
	 * @author 
	 * @date 2016年12月19日
	 * @param pk
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICustomerInfoService#getCustomerinfo(java.lang.String)
	 */
	@Override
	public TmCustomerinfoVO getCustomerinfo(String pk)
			throws AppBusinessException {
		try {
			return customerinfoDao.getTmCustomerinfoVO(pk);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}

	}

	/**
	 * 获取客户信息分页列表
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICustomerInfoService#getCustomerinfoByPages(java.lang.String,
	 *      com.yonyou.iuap.persistence.jdbc.framework.SQLParameter,
	 *      org.springframework.data.domain.PageRequest)
	 */
	@Override
	@Transactional
	public Page<TmCustomerinfoVO> getCustomerinfoByPages(
			Map<String, Object> parammap, PageRequest pageRequest)
			throws AppBusinessException {

		StringBuffer condition = new StringBuffer(); // 查询条件
		SQLParameter sqlParameter = new SQLParameter(); // 查询参数
		// 查询条件： 客户编码、 客户名称、 省份、 城市、 状态
		// vcustomerno vcustomername pk_province pk_city vstatus
		if (null != parammap.get("vcustomerno")
				&& !parammap.get("vcustomerno").toString().equals("")) {
			condition.append(" and vcustomerno like ? ");
			sqlParameter.addParam("%" + parammap.get("vcustomerno").toString()
					+ "%");
		}
		if (null != parammap.get("vcustomername")
				&& !parammap.get("vcustomername").toString().equals("")) {
			condition.append(" and vcustomername like ? ");
			sqlParameter.addParam("%"
					+ parammap.get("vcustomername").toString() + "%");
		}
		if (null != parammap.get("pk_province")
				&& !parammap.get("pk_province").toString().equals("")) {
			condition.append(" and pk_province like ? ");
			sqlParameter.addParam("%" + parammap.get("pk_province").toString()
					+ "%");
		}
		if (null != parammap.get("pk_city")
				&& !parammap.get("pk_city").toString().equals("")) {
			condition.append(" and pk_city like ? ");
			sqlParameter.addParam("%" + parammap.get("pk_city").toString()
					+ "%");
		}
		if (null != parammap.get("vstatus")
				&& !parammap.get("vstatus").toString().equals("")) {
			condition.append(" and vstatus like ? ");
			sqlParameter.addParam("%" + parammap.get("vstatus").toString()
					+ "%");
		}

		try {
			return customerinfoDao.getCustomerinfoByPages(condition.toString(),
					sqlParameter, pageRequest);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 获取客户扩展信息分页列表
	 * 
	 * @author 
	 * @date 2016年12月9日
	 * @param searchParams
	 * @param pageRequest
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICustomerInfoService#getCustomerinfoExtByPages(java.util.Map,
	 *      org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<TmCustomerinfoExtVO> getCustomerinfoExtByPages(
			Map<String, Object> parammap, PageRequest pageRequest)
			throws AppBusinessException {

		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		// 查询条件： 客户编码、 客户名称、 省份、 城市、 状态
		if (null != parammap.get("vcustomerno")
				&& !parammap.get("vcustomerno").toString().equals("")) {
			condition.append(" and vcustomerno like ? ");
			sqlParameter.addParam("%" + parammap.get("vcustomerno").toString()
					+ "%");
		}
		if (null != parammap.get("vcustomername")
				&& !parammap.get("vcustomername").toString().equals("")) {
			condition.append(" and vcustomername like ? ");
			sqlParameter.addParam("%"
					+ parammap.get("vcustomername").toString() + "%");
		}
		if (null != parammap.get("pk_province")
				&& !parammap.get("pk_province").toString().equals("")) {
			condition.append(" and pk_province like ? ");
			sqlParameter.addParam("%" + parammap.get("pk_province").toString()
					+ "%");
		}
		if (null != parammap.get("pk_city")
				&& !parammap.get("pk_city").toString().equals("")) {
			condition.append(" and pk_city like ? ");
			sqlParameter.addParam("%" + parammap.get("pk_city").toString()
					+ "%");
		}
		if (null != parammap.get("vstatus")
				&& !parammap.get("vstatus").toString().equals("")) {
			condition.append(" and vstatus like ? ");
			sqlParameter.addParam("%" + parammap.get("vstatus").toString()
					+ "%");
		}

		// 联合查询， 获取客户信息中的省份名和城市名
		String sqlJoin = "select c.*, p.name as provincename, t.name as cityname from tm_customerinfo c left join tm_region p on c.pk_province=p.pk_region "
				+ "left join tm_region t on c.pk_city=t.pk_region where c.dr=0 ";

		try {

			return customerinfoDao.getCustomerinfoExtByPages(sqlJoin,
					condition.toString(), sqlParameter, pageRequest);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 获取客户联系人信息
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICustomerInfoService#getCustomercontactorByPages(java.lang.String,
	 *      com.yonyou.iuap.persistence.jdbc.framework.SQLParameter,
	 *      org.springframework.data.domain.PageRequest)
	 */

	@Override
	@Transactional
	public Page<TmCustomercontactorVO> getCustomercontactorByPages(
			Map<String, Object> parammap, PageRequest pageRequest)
			throws AppBusinessException {

		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();

		if (null != parammap.get("pk_customerinfo")
				&& !parammap.get("pk_customerinfo").toString().equals("")) {
			condition.append(" and pk_customerinfo like ? ");
			sqlParameter.addParam("%"
					+ parammap.get("pk_customerinfo").toString() + "%");
		}
		try {
			return customerinfoDao.getCustomercontactorByPages(
					condition.toString(), sqlParameter, pageRequest);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 获取客户车辆分页列表信息
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICustomerInfoService#getCustomerVehicleByPages(java.lang.String,
	 *      com.yonyou.iuap.persistence.jdbc.framework.SQLParameter,
	 *      org.springframework.data.domain.PageRequest)
	 */
	@Override
	@Transactional
	public Page<TmCustomerVehicleVO> getCustomerVehicleByPages(
			Map<String, Object> parammap, PageRequest pageRequest)
			throws AppBusinessException {

		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();

		if (null != parammap.get("pk_customerinfo")
				&& !parammap.get("pk_customerinfo").toString().equals("")) {
			condition.append(" and pk_customerinfo like ? ");
			sqlParameter.addParam("%"
					+ parammap.get("pk_customerinfo").toString() + "%");
		}
		try {
			return customerinfoDao.getCustomerVehicleByPages(
					condition.toString(), sqlParameter, pageRequest);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 保存客户信息和其联系人信息
	 * 
	 * @author 
	 * @date 2016年12月23日
	 * @param pavo
	 * @param mpvos
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICustomerInfoService#saveCustomerinfo(com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoVO,
	 *      java.util.List)
	 */
	@Override
	@Transactional
	public void saveCustomerinfo(TmCustomerinfoVO pavo,
			List<TmCustomercontactorVO> mpvos) throws AppBusinessException {
		// 生成当前时间
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		String datestr = df.format(d);
		try {
			pavo.setStatus(VOStatus.UPDATED);
			pavo.setModifier(AppInvocationInfoProxy.getPk_User()); // @BUG
			pavo.setModifiedtime(datestr);
			for (TmCustomercontactorVO proModelPreVO : mpvos) {
				if (null == proModelPreVO.getPk_customercontactor()
						|| proModelPreVO.getPk_customercontactor().equals("")) {
					proModelPreVO.setCreationtime(datestr);
					proModelPreVO.setStatus(VOStatus.NEW);
				} else {
					proModelPreVO.setStatus(VOStatus.UPDATED);
					pavo.setModifier(AppInvocationInfoProxy.getPk_User());
					proModelPreVO.setModifiedtime(datestr);
				}

			}

			customerinfoDao.saveCustomerinfoWithContactor(pavo, mpvos);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 保存客户信息
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param vo
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICustomerInfoService#saveCustomerinfo(com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoVO)
	 */
	@Override
	@Transactional
	public String saveCustomerinfo(TmCustomerinfoVO vo)
			throws AppBusinessException {

		try {
			String pk = AppTools.generatePK();
			vo.setPk_customerinfo(pk);
			vo.setDr(0);

			return customerinfoDao.saveCustomerinfo(vo);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 保存客户联系人信息
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param vo
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICustomerInfoService#saveCustomercontactor(com.yonyou.iuap.crm.basedata.entity.TmCustomercontactorVO)
	 */
	@Override
	@Transactional
	public String saveCustomercontactor(TmCustomercontactorVO vo)
			throws AppBusinessException {
		try {
			String pk = AppTools.generatePK();
			vo.setPk_customercontactor(pk);
			vo.setDr(0);

			return customerinfoDao.saveCustomercontactor(vo);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 更新客户信息
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param vo
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICustomerInfoService#updateCustomerinfo(com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoVO)
	 */
	@Override
	@Transactional
	public void updateCustomerinfo(TmCustomerinfoVO vo)
			throws AppBusinessException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			vo.setModifier(AppInvocationInfoProxy.getPk_User());
			vo.setModifiedtime(sdf.format(new Date()));

			customerinfoDao.updateCustomerinfo(vo);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 合并客户信息
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param vo
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICustomerInfoService#updateCustomerinfo(com.yonyou.iuap.crm.basedata.entity.TmCustomerinfoVO)
	 */
	@Override
	@Transactional
	public void mergeCustomerinfo(Map<String, Object> main)
			throws AppBusinessException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, Object> selected = (Map<String, Object>) main
					.get("main");
			TmCustomerinfoVO vo = new TmCustomerinfoVO(); // 主键
			CheckUtil.mapToBean(selected, vo);
			List<Map<String, Object>> nonSelected = (List<Map<String, Object>>) main
					.get("sub");

			String pkCustomerinfo = vo.getPk_customerinfo();
			// 逐个合并客户关系
			for (int i = 0; i < nonSelected.size(); i++) {

				// 1 - 找到需要被合并的客户
				TmCustomerinfoVO tmp = new TmCustomerinfoVO();
				CheckUtil.mapToBean(nonSelected.get(i), tmp);

				// 2 - 更新该客户下的车辆信息
				List<TmCustomerVehicleVO> vehicleLst = customerinfoDao
						.getCustomerVehicles(tmp.getPk_customerinfo());
				for (int j = 0; j < vehicleLst.size(); j++) {
					vehicleLst.get(j).setPk_customerinfo(pkCustomerinfo);
				}
				customerinfoDao.batchUpdateCustomerVehicle(vehicleLst);

				// 3 - 更新该客户下的联系人信息 （注意联系人合并： 名字、电话相同作校验）
				List<TmCustomercontactorVO> contactorLst = customerinfoDao
						.getCustomercontactors(tmp.getPk_customerinfo());
				for (int j = 0; j < contactorLst.size(); j++) {
					contactorLst.get(j).setPk_customerinfo(pkCustomerinfo);
				}
				customerinfoDao.batchUpdateCustomerContactor(contactorLst);

				// 4 - 更新销售线索中的客户信息
//				TrackInfoVO tv = trackDao.getEntityById(tmp.getPk_trackinfo());
//				if (tv != null) {
//					tv.setPk_customer(pkCustomerinfo);
//					trackDao.saveTrackForCst(tv);
//				}

				// 5 - 逻辑删除该客户
				customerinfoDao.deleteCustomerinfo(tmp);
			}
			// TODO （注意联系人合并： 名字、电话相同作校验）
			// TODO modify other vo?
			// vo.setModifier(AppInvocationInfoProxy.getPk_User());
			// vo.setModifiedtime(sdf.format(new Date()));
			//
			// // 更新
			// customerinfoDao.updateCustomerinfo(vo);

			// 合并重复联系人
			// 相同联系人准则： 姓名和电话 ？

		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 批量更新客户信息
	 * 
	 * @author 
	 * @date 2016年12月30日
	 * @param entityLst
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICustomerInfoService#batchUpdateCustomerinfo(java.util.List)
	 */

	@Override
	@Transactional
	public void batchUpdateCustomerinfo(List<TmCustomerinfoVO> entityLst)
			throws AppBusinessException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {

			for (TmCustomerinfoVO vo : entityLst) {
				vo.setModifier(AppInvocationInfoProxy.getPk_User());
				vo.setModifiedtime(sdf.format(new Date()));
			}

			customerinfoDao.batchUpdateCustomerinfo(entityLst);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 删除客户联系人信息
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param vo
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICustomerInfoService#deleteCustomercontactor(com.yonyou.iuap.crm.basedata.entity.TmCustomercontactorVO)
	 */

	@Override
	@Transactional
	public void deleteCustomercontactor(TmCustomercontactorVO vo)
			throws AppBusinessException {
		try {
			customerinfoDao.deleteCustomercontactor(vo);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 批量删除客户联系人信息
	 * 
	 * @author 
	 * @date 2016年12月8日
	 * @param voList
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICustomerInfoService#deleteBatchCustomercontactor(java.util.List)
	 */
	@Override
	@Transactional
	public void deleteBatchCustomercontactor(List<TmCustomercontactorVO> voList)
			throws AppBusinessException {
		try {
			customerinfoDao.deleteBatchCustomercontactor(voList);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}

	}

	@Override
	public Page<TmCustomerVehicleExtVO> getVehicleExtByPages(
			Map<String, String> searchParams, PageRequest pageRequest)
			throws AppBusinessException {
		// TODO 自动生成的方法存根
		SQLParameter sp = new SQLParameter();
		String sql = "select t3.vbillno as vprojectno,t3.vcontractno as vcontractno,t2.vpoorderno as poorderno,t1.vbillno as transno,\n"
				+ "t2.vcertificateplace as vcertificateplace,cus.vcustomername as vcustomername,dept.deptname as deptname,\n"
				+ "tc.* from tm_customervehicle tc \n"
				+ "left join\n"
				+ "(select ti.vvin,ta.pk_transport_apply,ta.vbillno from tm_transport_items ti left join tm_transport_apply ta on ti.pk_transport_apply=ta.pk_transport_apply) t1\n"
				+ "on tc.vvin=t1.vvin \n"
				+ "left join \n"
				+ "(select pv.vvin,pv.vcertificateplace,po.pk_poorderinfo,po.vpoorderno,po.pk_projectapply from pro_poorderinfo_vin pv left join pro_poorderinfo po on pv.pk_poorderinfo=po.pk_poorderinfo) t2\n"
				+ "on tc.vvin=t2.vvin \n"
				+ "left join\n"
				+ "(select pa.pk_projectapply,pa.vbillno,co.vcontractno,co.pk_sale_dept from pro_projectapply pa left join sale_contract co on pa.pk_projectapply=co.pk_projectapply) t3\n"
				+ "on t2.pk_projectapply = t3.pk_projectapply\n"
				+ "left join tm_customerinfo cus\n"
				+ "on tc.pk_customerinfo=cus.pk_customerinfo\n"
				+ "left join bd_dept dept\n"
				+ "on t3.pk_sale_dept = dept.pk_dept\n" + "WHERE 1=1 ";
		if (null != searchParams.get("vprojectno")
				&& !searchParams.get("vprojectno").toString().equals("")) {
			sql += " and t3.vbillno = ? ";
			sp.addParam(searchParams.get("vprojectno"));
		}
		if (null != searchParams.get("vcontractno")
				&& !searchParams.get("vcontractno").toString().equals("")) {
			sql += " and t3.vcontractno = ? ";
			sp.addParam(searchParams.get("vcontractno"));
		}
		if (null != searchParams.get("vpoorderno")
				&& !searchParams.get("vpoorderno").toString().equals("")) {
			sql += " and t2.vpoorderno = ? ";
			sp.addParam(searchParams.get("vpoorderno"));
		}
		if (null != searchParams.get("transno")
				&& !searchParams.get("transno").toString().equals("")) {
			sql += " and t1.vbillno = ? ";
			sp.addParam(searchParams.get("transno"));
		}
		if (null != searchParams.get("pk_customerinfo")
				&& !searchParams.get("pk_customerinfo").toString().equals("")) {
			sql += " and tc.pk_customerinfo = ? ";
			sp.addParam(searchParams.get("pk_customerinfo"));
		}
		if (null != searchParams.get("vvin")
				&& !searchParams.get("vvin").toString().equals("")) {
			sql += " and tc.vvin = ? ";
			sp.addParam(searchParams.get("vvin"));
		}
		// "	and t3.vbillno='PA-002' \n" +
		// "	and t3.vcontractno='' \n" +
		// "	and t2.vpoorderno='' \n" +
		// "	and t1.vbillno\n" +
		// "	and tc.vvin='vin0003'";
		sql += " order by tc.ts desc";
		try {
			return baseDao.queryPage(sql, sp, pageRequest,
					TmCustomerVehicleExtVO.class);
		} catch (DAOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 获取客户车辆扩展信息
	 * @author 
	 * @date 2017年1月20日
	 * @param searchParams
	 * @param pageRequest
	 * @return
	 * @throws AppBusinessException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.service.itf.ICustomerInfoService#getCustomerVehicleExt2ByPages(java.util.Map,
	 *      org.springframework.data.domain.PageRequest)
	 */

	@Override
	public Page<TmCustomerVehicleExt2VO> getCustomerVehicleExt2ByPages(
			String pkCustomer, PageRequest pageRequest)
			throws AppBusinessException {
		SQLParameter sqlParameter = new SQLParameter();

		if (StringUtil.isEmpty(pkCustomer)) {
			return null;
		}
		
		sqlParameter.addParam(pkCustomer);

		// 联合查询， 获取车辆信息及其车型、车系
		String sqlJoin = " select a.*, tm.vmodelname,ts.vseriesname from (select * from tm_customervehicle where pk_customerinfo=? and dr=0) a left join tm_model tm on a.pk_model=tm.pk_model left join tm_series ts on tm.pk_series= ts.pk_series ";

		try {

			return customerinfoDao.getCustomerVehicleEx2ByPages(sqlJoin,
					null , sqlParameter, pageRequest);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

}
