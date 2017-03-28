package com.yonyou.iuap.crm.basedata.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.crm.basedata.entity.ProvinceCityVO;
import com.yonyou.iuap.crm.basedata.entity.TmCountryVO;
import com.yonyou.iuap.crm.basedata.repository.itf.IProvinceCityDao;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
@Repository
public class ProvinceCityDaoImpl implements IProvinceCityDao {
	@Autowired
	private AppBaseDao baseDao;
	@Autowired
	@Qualifier("baseDAO") private  BaseDAO dao;
	
	/**
	* 查询省份数据
	* @author 
	* @date 2016年11月29日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.IProvinceCityDao#getprovince(java.lang.String, com.yonyou.iuap.persistence.jdbc.framework.SQLParameter, org.springframework.data.domain.PageRequest)
	*/
	@Override
	public Page<ProvinceCityVO> getprovince(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
		return baseDao.findBypageWithDR(ProvinceCityVO.class, condition, sqlParameter, pageRequest);
	
	}
	
	/**
	* 查询省份所属城市数据
	* @author 
	* @date 2016年11月29日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.IProvinceCityDao#getCity(java.lang.String, com.yonyou.iuap.persistence.jdbc.framework.SQLParameter, org.springframework.data.domain.PageRequest)
	*/
	@Override
	public Page<ProvinceCityVO> getCity(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
		
		if(sqlParameter.getCountParams()==0)
			sqlParameter = null;
		StringBuffer sqlBuffer = new StringBuffer("SELECT tcity.`name`, tcity.`code`, tcity.pk_region, tcity.pk_father FROM tm_region tprovince, tm_region tcity WHERE tcity.dr=0 and tprovince.dr=0");
		if(null!=condition && !condition.equals("")){
			sqlBuffer.append(condition);
		}
		sqlBuffer.append(" order by tcity.`code`");
		return dao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, ProvinceCityVO.class);
	
	}

	@Override
	public Page<TmCountryVO> getCountry(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
		// TODO 自动生成的方法存根
		return baseDao.findBypageWithDR(TmCountryVO.class, condition, sqlParameter, pageRequest);
		
	}
	
	/**
	 * 通过条件查询实体
	* @author 
	* @date 2016年12月15日
	* @param condition
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ISeriesDao#findListByClause(java.lang.String)
	 */
	@Override
	public List<ProvinceCityVO> queryProvinces(String condition)
			throws DAOException {
		return baseDao.findListByClauseWithDR(ProvinceCityVO.class, condition);	
	}
}
