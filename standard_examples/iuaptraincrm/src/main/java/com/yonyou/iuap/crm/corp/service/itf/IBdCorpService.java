package com.yonyou.iuap.crm.corp.service.itf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.crm.corp.entity.BdCorpVO;

public interface IBdCorpService {

	public abstract Page<BdCorpVO> getCorpsBypage(
			Map<String, Object> searchParams, PageRequest pageRequest);

	public abstract Page<BdCorpVO> getCorpsByCorp(
			Map<String, Object> searchParams, PageRequest pageRequest,
			String pk_corp) throws BusinessException;

	public abstract Page<BdCorpVO> getCorpsBypages(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws BusinessException;

	public abstract JSONObject saveEntity(BdCorpVO entity)
			throws BusinessException;

	public abstract JSONObject saveEntityForNormal(BdCorpVO entity)
			throws BusinessException;

	public abstract JSONObject saveEntityForException(BdCorpVO entity)
			throws BusinessException;

	public abstract void deleteBdCorp(BdCorpVO entity)
			throws BusinessException;

	public abstract void deleteBdCorpById(String pk_corp)
			throws BusinessException;

	public abstract BdCorpVO getBdCorp(String pk_corp)
			throws BusinessException;

	// 返回所有组织
	public abstract List<BdCorpVO> getBdCorps() throws BusinessException;

	// 返回组织
	public abstract List<HashMap> getBdCorpsListMap(String wherestr)
			throws BusinessException;

	public abstract String queryDeptByCorp(String pk_corp)
			throws BusinessException;

	public abstract String stopCorp(String pk_corp) throws BusinessException;

	// 返回组织机构的下级，根据条件
	public abstract List<BdCorpVO> getBdSubCorps(String wherestr)
			throws BusinessException;

	public abstract String startCorp(String pk_corp, String def1)
			throws BusinessException;

}