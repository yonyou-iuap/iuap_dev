package com.yonyou.iuap.crm.dept.service.itf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springside.modules.utils.Clock;

import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.crm.corp.entity.BdCorpVO;
import com.yonyou.iuap.crm.dept.entity.BdDeptVO;

public interface IBdDeptService {

	public abstract void setClock(Clock clock);

	public abstract Page<BdDeptVO> getBdDeptsBypage(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws BusinessException;

	public abstract JSONObject saveEntity(BdDeptVO entity, String pk_corp)
			throws BusinessException;

	public abstract JSONObject saveEntityForNormal(BdDeptVO entity,
			String pk_corp) throws BusinessException;

	public abstract String updateEntity(BdDeptVO entity)
			throws BusinessException;

	public abstract void deleteBdDept(BdDeptVO entity) throws BusinessException;

	public abstract void deleteBdDeptById(String pk_dept)
			throws BusinessException;

	public abstract BdDeptVO getBdDept(String pk_dept) throws BusinessException;

	public abstract List<BdDeptVO> getBdDepts() throws BusinessException;

	public abstract List<BdDeptVO> getBdDeptnames(String sql)
			throws BusinessException;

	public abstract List<BdDeptVO> getBdDeptbycorp(String pk_corp)
			throws BusinessException;

	public abstract String queryBdDeptByDept(String pk_dept)
			throws BusinessException;

	public abstract List<BdDeptVO> getBdSubDepts(String wherestr)
			throws BusinessException;

	public abstract String stopBdDept(String pk_dept) throws BusinessException;

	// 返回组织
	public abstract List<HashMap> getBdDeptsListMap(String wherestr)
			throws BusinessException;

	public abstract String startBdDept(String pk_dept) throws BusinessException;

	public abstract Page<BdDeptVO> getBdDeptsByDept(
			Map<String, Object> searchParams, PageRequest pageRequest,
			String pk_dept) throws BusinessException;

	public abstract Page<BdDeptVO> getBdDept(Map<String, Object> searchParams,
			PageRequest pageRequest, String pk_dept) throws BusinessException;

	public abstract List<BdDeptVO> getdplist(String deptname, String pk_corp,
			String sql) throws BusinessException;

	public abstract Page<BdCorpVO> getBdCorpsBypages(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws BusinessException;

}