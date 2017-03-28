package com.yonyou.iuap.crm.basedata.repository.impl;

import com.yonyou.iuap.crm.basedata.entity.TmCompetPriceVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.basedata.repository.itf.ICompetPriceDao;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * 竞品价格信息DAO接口实现类
 * 实现了竞品价格信息的查询、保存和删除业务的数据访问
 * @author 
 * @date Nov 24, 2016
 */
@Repository
public class CompetPriceDaoImpl implements ICompetPriceDao {
	@Autowired
	private AppBaseDao baseDao;

	/**
	 * 根据条件查询竞品价格信息列表
	 * 
	 * @author 
	 * @date Dec 30, 2016
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 */
	@Override
	public Page<TmCompetPriceVO> getCompetPricesBypage(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
		return baseDao.findBypageWithDR(TmCompetPriceVO.class, condition,
				sqlParameter, pageRequest);
	}

	/**
	 * 保存竞品价格信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetPriceDao#saveCompetPrices(java.util.List)
	 */
	@Override
	@Transactional
	public void saveCompetPrices(List<TmCompetPriceVO> entities)
			throws DAOException {
		baseDao.batchSaveWithPK(entities);
	}

	/**
	 * 删除竞品价格信息，逻辑删除
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetPriceDao#deleteCompetPriceByTS(com.yonyou.iuap.crm.basedata.entity.TmCompetPriceVO)
	 */
	@Override
	@Transactional
	public void deleteCompetPriceByTS(TmCompetPriceVO entity)
			throws DAOException {
		baseDao.deleteWithDR(entity);
	}

	/**
	 * 删除竞品价格信息，物理删除
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetPriceDao#deleteCompetPrice(List)
	 */
	@Override
	@Transactional
	public void deleteCompetPrice(List<TmCompetPriceVO> entities) throws DAOException {
		baseDao.batchDeleteWithDR(entities);
	}

	/**
	 * 更新竞品价格信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see ICompetPriceDao#updateCompetPrice(List)
	 */
	@Override
	@Transactional
	public void updateCompetPrice(List<TmCompetPriceVO> entity) throws DAOException {
		baseDao.batchUpdate(entity);
	}
}
