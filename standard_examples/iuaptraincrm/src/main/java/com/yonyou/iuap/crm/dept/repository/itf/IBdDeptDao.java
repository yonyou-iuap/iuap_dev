package com.yonyou.iuap.crm.dept.repository.itf;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.corp.entity.BdCorpVO;
import com.yonyou.iuap.crm.dept.entity.BdDeptVO;

public interface IBdDeptDao {

	public BdDeptVO getBdDept(String pk_dept) throws DAOException;
	
	public List<BdDeptVO> getBdDepts(String wherestr) throws DAOException;
	
	public List<BdDeptVO> getBdDeptsbyc(String wherestr, String pk_corp) throws DAOException;
	
	public List<BdDeptVO> getBdDeptnum(String wherestr) throws DAOException;

	public List<HashMap> getBdDeptsListMap(String wherestr) throws DAOException;
	
	public List<HashMap> getBdDeptsListMapbyc(String wherestr, String pk_corp) throws DAOException;
	
	public List<BdDeptVO> getBdDeptByCodeAndName(String deptcode, String deptname) throws DAOException;
	
	public void updateBdDept(BdDeptVO entity) throws DAOException;
	
	public void updateBdDeptByCondition(String condition) throws DAOException;
	
	public String saveBdDept(BdDeptVO entity) throws DAOException;
	
	public void deleteBdDept(BdDeptVO entity) throws DAOException;
	
	public Page<BdDeptVO> getBdDeptsBypage(String condition, SQLParameter arg1,
			PageRequest pageRequest) throws DAOException;

	public void deleteBdDeptById(String pk_dept) throws DAOException;

	public void deleteBdDeptByIdTS(String pk_dept) throws DAOException;

	public String saveBdDeptWithPK(BdDeptVO entity) throws DAOException;
	
	public String queryDeptByDept(String pk_dept) throws DAOException;	
	/*停用*/
	public void stopDept(String pk_dept)  throws DAOException;
	/*启用*/
	public void startDept(String pk_dept) throws DAOException;

	public Page<BdDeptVO> getBdDept(String condition, SQLParameter arg1,
			PageRequest pageRequest) throws DAOException;

	List<BdDeptVO> getBdDeptcorp(String pk_corp) throws DAOException;

	public Page<BdCorpVO> getBdCorpsBypage(String pk_corp, SQLParameter arg1,
			PageRequest pageRequest) throws DAOException;
}
