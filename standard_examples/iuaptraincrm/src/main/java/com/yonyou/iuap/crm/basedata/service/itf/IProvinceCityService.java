
package com.yonyou.iuap.crm.basedata.service.itf;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.ProvinceCityVO;
import com.yonyou.iuap.crm.basedata.entity.TmCountryVO;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;

public interface IProvinceCityService {
	Page<TmCountryVO> getCountry(Map<String, Object> searchParams,
			PageRequest pageRequest) throws AppBusinessException;

	Page<ProvinceCityVO> getProvince(Map<String, Object> searchParams,
			PageRequest pageRequest) throws AppBusinessException;

	Page<ProvinceCityVO> getCity(Map<String, Object> searchParams,
			PageRequest pageRequest) throws AppBusinessException;
	
	public List<ProvinceCityVO> queryProvinces(String condition)
			throws AppBusinessException;
	
	public List<ProvinceCityVO> queryCities(String condition)
			throws AppBusinessException;
	

}
