package com.yonyou.iuap.crm.psn.repository.itf;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.psn.entity.BdPsndocVO;

public interface IBdPsnDao {
	/* 查询人员,根据人员主键 */
	public BdPsndocVO queryPsnById(String pk_psndoc) throws DAOException;

	/* 查询人员,根据人员编码 */
	public BdPsndocVO queryPsnByCode(String psncode) throws DAOException;

	/* 查询人员列表,根据查询条件 where */
	public List<BdPsndocVO> queryPsnListByCondition(String wherestr)
			throws DAOException;

	/* 修改保存 */
	public void updatePsn(BdPsndocVO entity) throws DAOException;
	
	public void shiftDept(BdPsndocVO entity) throws DAOException;

	/* 新增保存 */
	public String savePsn(BdPsndocVO entity) throws DAOException;

	/* 删除 */
	public void deletePsn(BdPsndocVO entity) throws DAOException;

	/* 查询带分页 */
	public Page<BdPsndocVO> getPsnsBypage(String condition, SQLParameter arg1,
			PageRequest pageRequest) throws DAOException;

	/* 删除 */
	public void deletePsnById(String pk_psndoc) throws DAOException;

	/* 封存 */
	public void sealedPsn(String pk_psndoc) throws DAOException;

	void deletelist(List<BdPsndocVO> entity) throws DAOException;

	/* 解封 */
	public void unSealedPsn(String pk_psndoc) throws DAOException;

	public void startlist(List<BdPsndocVO> entity) throws DAOException;

	public void stoplist(List<BdPsndocVO> entity) throws DAOException;

	public void updatetel(String pk_psndoc, String psntel) throws DAOException;

	public void updateemail(String pk_psndoc, String mail) throws DAOException;

}
