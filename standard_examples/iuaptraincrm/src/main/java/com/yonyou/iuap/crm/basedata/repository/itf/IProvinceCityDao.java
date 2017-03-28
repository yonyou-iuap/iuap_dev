package com.yonyou.iuap.crm.basedata.repository.itf;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.ProvinceCityVO;
import com.yonyou.iuap.crm.basedata.entity.TmCountryVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;

public interface IProvinceCityDao {
	Page<TmCountryVO> getCountry(String condition, SQLParameter sqlParameter,
			PageRequest pageRequest) throws DAOException;

	Page<ProvinceCityVO> getprovince(String condition, SQLParameter sqlParameter,
			PageRequest pageRequest) throws DAOException;

	Page<ProvinceCityVO> getCity(String condition, SQLParameter sqlParameter,
			PageRequest pageRequest) throws DAOException;
	
	public List<ProvinceCityVO> queryProvinces(String condition)
			throws DAOException;
	
}
