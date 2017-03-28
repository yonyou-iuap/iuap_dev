package com.yonyou.iuap.crm.basedata.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.crm.basedata.entity.ProvinceCityVO;
import com.yonyou.iuap.crm.basedata.entity.TmCountryVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.basedata.repository.itf.IProvinceCityDao;
import com.yonyou.iuap.crm.basedata.service.itf.IProvinceCityService;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
@Service
public class ProvinceCityServiceImpl implements IProvinceCityService {
	@Autowired
	private IProvinceCityDao provinceCityDao;
	
	/**
	* 查询省份数据
	* @author 
	* @date 2016年11月28日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.service.itf.IProvinceCityService#getProvince(java.util.Map, org.springframework.data.domain.PageRequest)
	*/
	@Override
	public Page<ProvinceCityVO> getProvince(
			Map<String, Object> searchParams, PageRequest pageRequest)
					throws AppBusinessException {
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		condition.append("and pk_father='~'");
		if(!searchParams.isEmpty()){
			condition.append(" ").append("and (`code` like").append(" ").append("?").append(" ").append("||").append(" `name` like").append(" ").append(" ? )");
			sqlParameter.addParam("%"+searchParams.get("condition")+ "%");
			sqlParameter.addParam("%"+searchParams.get("condition")+ "%");
		}
		

		try {
			return provinceCityDao.getprovince(condition.toString(), sqlParameter, pageRequest);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}
	
	/**
	* 根据省份查询城市
	* @author 
	* @date 2016年11月29日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.service.itf.IProvinceCityService#getCity(java.util.Map, org.springframework.data.domain.PageRequest)
	*/
	@Override
	public Page<ProvinceCityVO> getCity(
			Map<String, Object> searchParams, PageRequest pageRequest)
					throws AppBusinessException {
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		condition.append(" and tcity.pk_father = tprovince.pk_region");
		condition.append(" and tprovince.pk_father = '~' ");
		if(!searchParams.isEmpty()){
			if(null!=searchParams.get("provincepk")&&!"".equals(searchParams.get("provincepk"))){
				condition.append(" and tprovince.pk_region = ?");
				sqlParameter.addParam(searchParams.get("provincepk"));
			}
			if(null!=searchParams.get("condition")&&!"".equals(searchParams.get("condition"))){
				condition.append(" ").append("and (tcity.`code` like").append(" ").append("?").append(" ").append("||").append(" tcity.`name` like").append(" ").append(" ? )");
				sqlParameter.addParam("%"+searchParams.get("condition")+ "%");
				sqlParameter.addParam("%"+searchParams.get("condition")+ "%");
			}
		}
		try {
			return provinceCityDao.getCity(condition.toString(), sqlParameter, pageRequest);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	@Override
	public Page<TmCountryVO> getCountry(Map<String, Object> searchParams,
			PageRequest pageRequest) throws AppBusinessException {
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		condition.append("and 1=1 ");
		if(!searchParams.isEmpty()){
			condition.append(" ").append("and (`vcountrycode` like").append(" ").append("?").append(" ").append("||").append(" `vcountryname` like").append(" ").append(" ? )");
			sqlParameter.addParam("%"+searchParams.get("condition")+ "%");
			sqlParameter.addParam("%"+searchParams.get("condition")+ "%");
		}
		

		try {
			return provinceCityDao.getCountry(condition.toString(), sqlParameter, pageRequest);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}

	/**
	 * 查询省市
	* @author 
	* @date 2016年12月15日
	* @param condition
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.service.itf.IProvinceCityService#queryProvinces(java.lang.String)
	 */
	@Override
	public List<ProvinceCityVO> queryProvinces(String condition)
			throws AppBusinessException {
		try{
			return provinceCityDao.queryProvinces(condition);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}
	
	/**
	 * 查询所有城市
	* @author 
	* @date 2016年12月15日
	* @param condition
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.service.itf.IProvinceCityService#queryProvinces(java.lang.String)
	 */
	@Override
	public List<ProvinceCityVO> queryCities(String condition)
			throws AppBusinessException {
		try{
			return provinceCityDao.queryProvinces(condition);
		} catch (DAOException e) {
			throw new AppBusinessException(e.getMessage());
		}
	}
}
