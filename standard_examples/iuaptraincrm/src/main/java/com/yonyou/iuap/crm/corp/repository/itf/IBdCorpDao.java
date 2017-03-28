package com.yonyou.iuap.crm.corp.repository.itf;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.uap.billcode.model.BillCodeRuleVO;
import com.yonyou.iuap.crm.corp.entity.BdCorpVO;

public interface IBdCorpDao {
    /*查询组织*/
	public BdCorpVO getBdCorp(String pk_corp) throws DAOException;
	/*查询组织*/
	public List<BdCorpVO> getBdCorps(String wherestr) throws DAOException;
	/*查询组织*/
	public List<HashMap> getBdCorpsListMap(String wherestr) throws DAOException;	
	/*组织修改保存*/
	public void updateBdCorp(BdCorpVO entity) throws DAOException;
	
	public void updateBdCorpByCondition(String condition) throws DAOException;
	/*组织新增保存*/
	public String saveBdCorp(BdCorpVO entity) throws DAOException;
	/*组织删除*/
	public void deleteBdCorp(BdCorpVO entity) throws DAOException;
	/*组织查询带分页*/
	public Page<BdCorpVO> getBdCorpsBypage(String pk_corp, SQLParameter arg1,
			PageRequest pageRequest) throws DAOException;
	/*组织删除*/
	public void deleteBdCorpById(String pk_corp) throws DAOException;
	/* 逻辑删除，非物理删除 */
	public void deleteBdCorpByIdTS(String pk_corp) throws DAOException;
	/*增加保存，并返回主健*/
	public String saveBdCorpWithPK(BdCorpVO entity) throws DAOException;
	/*该组织是否被部门引用*/
	public String queryDeptByCorp(String pk_corp) throws DAOException;	
	/*停用*/
	public void stopCorp(String pk_corp)  throws DAOException;
	/*启用*/
	public void startCorp(String pk_corp,String def1) throws DAOException;
}
