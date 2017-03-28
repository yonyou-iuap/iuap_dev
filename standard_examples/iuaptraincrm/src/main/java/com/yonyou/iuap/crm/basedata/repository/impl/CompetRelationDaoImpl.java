package com.yonyou.iuap.crm.basedata.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.crm.basedata.entity.TmCompetRelationVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelCompetExtVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.basedata.repository.itf.ICompetRelationDao;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;

/**
 * 竞品对比车型信息DAO接口实现类  TODO description
 * 
 * @author 
 * @date Dec 2, 2016
 */
@Repository
public class CompetRelationDaoImpl implements ICompetRelationDao {
	@Autowired
	private AppBaseDao baseDao;

	/**
	 * 保存竞品
	 * 
	 * @author 
	 * @date Dec 2, 2016
	 * @param entities
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetRelationDao#saveCompetRelation(List)
	 */
	@Override
	@Transactional
	public void saveCompetRelation(List<TmCompetRelationVO> entities)
			throws DAOException {
		baseDao.batchSaveWithPK(entities);
	}

	/**
	 * 删除竞品
	 * 
	 * @author 
	 * @date Dec 2, 2016
	 * @param entities
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetRelationDao#deleteCompetRelation(java.util.List)
	 */
	@Override
	@Transactional
	public void deleteCompetRelation(List<TmCompetRelationVO> entities)
			throws DAOException {
		baseDao.batchDeleteWithDR(entities);
	}

	/**
	 * 根据条件查询竞品
	 * 
	 * @author 
	 * @date Dec 5, 2016
	 * @param condition
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetRelationDao#findCompetRelationByCause(java.lang.String)
	 */
	@Override
	public List<TmCompetRelationVO> findCompetRelationByCause(String condition)
			throws DAOException {
		return baseDao.findListByClauseWithDR(TmCompetRelationVO.class,
				condition);
	}

	/**
	 * 根据条件查询竞品
	 * 
	 * @author 
	 * @date Dec 23, 2016
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @param sql
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetRelationDao#queryModelExtPage(java.lang.String,
	 *      com.yonyou.iuap.persistence.jdbc.framework.SQLParameter,
	 *      org.springframework.data.domain.PageRequest, java.lang.String)
	 */
	@Override
	public Page<TmModelCompetExtVO> queryModelExtPage(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest,String sql)
			throws DAOException {
		return baseDao.findBypageWithJoin(TmModelCompetExtVO.class, condition,
				sqlParameter, pageRequest,sql);
	}

}
