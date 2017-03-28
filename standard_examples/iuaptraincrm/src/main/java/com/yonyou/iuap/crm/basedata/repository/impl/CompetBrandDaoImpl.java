package com.yonyou.iuap.crm.basedata.repository.impl;

import com.yonyou.iuap.crm.basedata.entity.TmCompetBrandExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmCompetBrandVO;
import com.yonyou.iuap.crm.basedata.entity.TmCompetbrandSalesVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.basedata.repository.itf.ICompetBrandDao;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 竞品信息DAO接口实现类 实现了竞品信息的查询、更新、发布、撤销、关闭、保存和删除业务的数据访问
 * 
 * @author 
 * @date Nov 24, 2016
 */
@Repository
public class CompetBrandDaoImpl implements ICompetBrandDao {
	@Autowired
	private AppBaseDao baseDao;

	/**
	 * 根据主键查询竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param pk_competbrand
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetBrandDao#getCompetBrand(java.lang.String)
	 */
	@Override
	public TmCompetBrandVO getCompetBrand(String pk_competbrand)
			throws DAOException {
		TmCompetBrandVO vo = baseDao.findByIdWithDR(TmCompetBrandVO.class,
				pk_competbrand);
		return vo;
	}

	/**
	 * 根据条件查询竞品信息列表
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetBrandDao#getCompetBrandsBypage(java.lang.String,
	 *      com.yonyou.iuap.persistence.jdbc.framework.SQLParameter,
	 *      org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<TmCompetBrandVO> getCompetBrandsBypage(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
		return baseDao.findBypageWithDR(TmCompetBrandVO.class, condition,
				sqlParameter, pageRequest);
	}

	/**
	 * 根据条件查询竞品信息列表
	 * 
	 * @author 
	 * @date Nov 28, 2016
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetBrandDao#getCompetBrandsExtBypage(java.lang.String,
	 *      com.yonyou.iuap.persistence.jdbc.framework.SQLParameter,
	 *      org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<TmCompetBrandExtVO> getCompetBrandsExtBypage(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
		return baseDao.findBypageWithDR(TmCompetBrandExtVO.class, condition,
				sqlParameter, pageRequest);
	}
	/**
	 * 根据条件查询竞品信息列表，支持表连接，带分页
	 *
	 * @author 
	 * @date Nov 28, 2016
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @param sql
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetBrandDao#getCompetBrandsExtBypage(String, SQLParameter, PageRequest, String)
	 */
	@Override
	public Page<TmCompetBrandExtVO> getCompetBrandsExtBypage(String condition, SQLParameter sqlParameter, PageRequest pageRequest, String sql) throws DAOException {
		return baseDao.findBypageWithJoin(TmCompetBrandExtVO.class, condition, sqlParameter, pageRequest, sql);
	}

	/**
	 * 保存竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetBrandDao#saveCompetBrand(com.yonyou.iuap.crm.basedata.entity.TmCompetBrandVO)
	 */
	@Override
	@Transactional
	public String saveCompetBrand(TmCompetBrandVO entity) throws DAOException {
		baseDao.saveWithPK(entity);
		return entity.getPk_competbrand();
	}

	/**
	 * 删除竞品信息，逻辑删除
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetBrandDao#deleteCompetBrandByTS(com.yonyou.iuap.crm.basedata.entity.TmCompetBrandVO)
	 */
	@Override
	@Transactional
	public void deleteCompetBrandByTS(TmCompetBrandVO entity)
			throws DAOException {
		/*
		 * String sql = "update tm_competbrand set dr=1 where pk_competbrand='"
		 * + id + "'";
		 */
		baseDao.deleteWithDR(entity);
	}

	/**
	 * 删除竞品信息，物理删除
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetBrandDao#deleteCompetBrand(com.yonyou.iuap.crm.basedata.entity.TmCompetBrandVO)
	 */
	@Override
	@Transactional
	public void deleteCompetBrand(TmCompetBrandVO entity) throws DAOException {
		baseDao.delete(entity);
	}

	/**
	 * 发布竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetBrandDao#publishCompetBrand(com.yonyou.iuap.crm.basedata.entity.TmCompetBrandVO)
	 */
	@Override
	@Transactional
	public void publishCompetBrand(TmCompetBrandVO entity) throws DAOException {
		entity.setVstatus("20211002");
		baseDao.update(entity, "vstatus");
	}

	/**
	 * 撤销已发布的竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetBrandDao#revokeCompetBrand(com.yonyou.iuap.crm.basedata.entity.TmCompetBrandVO)
	 */
	@Override
	@Transactional
	public void revokeCompetBrand(TmCompetBrandVO entity) throws DAOException {
		entity.setVstatus("20211003");
		baseDao.update(entity, "vstatus");
	}

	/**
	 * 关闭竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetBrandDao#closeCompetBrand(com.yonyou.iuap.crm.basedata.entity.TmCompetBrandVO)
	 */
	@Override
	@Transactional
	public void closeCompetBrand(TmCompetBrandVO entity) throws DAOException {
		entity.setVstatus("20211004");
		baseDao.update(entity, "vstatus");
	}

	/**
	 * 更新竞品信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetBrandDao#updateCompetBrand(com.yonyou.iuap.crm.basedata.entity.TmCompetBrandVO)
	 */
	@Override
	@Transactional
	public void updateCompetBrand(TmCompetBrandVO entity) throws DAOException {
		baseDao.update(entity);
	}

	/**
	 * 根据条件查找竞品信息，不带分页
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param condition
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetBrandDao#findListByClauseWithDR(String)
	 */
	@Override
	public List<TmCompetBrandVO> findListByClauseWithDR(String condition)
			throws DAOException {
		return baseDao.findListByClauseWithDR(TmCompetBrandVO.class, condition);
	}

	/**
	 * 获取电池电量枚举信息
	 * 
	 * @author 
	 * @date Dec 6, 2016
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @param sql
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetBrandDao#getModelBattery(java.lang.String,
	 *      com.yonyou.iuap.persistence.jdbc.framework.SQLParameter,
	 *      org.springframework.data.domain.PageRequest, java.lang.String)
	 */
	@Override
	public Page<TmModelVO> getModelBattery(String condition, SQLParameter sqlParameter,
			PageRequest pageRequest, String sql) throws DAOException {
		return baseDao.findBypageWithJoin(TmModelVO.class, condition, sqlParameter,
				pageRequest, sql);
	}

	/**
	 * 批量保存竞品信息
	 * 
	 * @author 
	 * @date Dec 23, 2016
	 * @param entities
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetBrandDao#saveCompetBrand(java.util.List)
	 */
	@Override
	public void saveCompetBrand(List<TmCompetBrandVO> entities)
			throws DAOException {
		baseDao.batchSaveWithPK(entities);
	}

	/**
	 * 获竞品品牌的销量信息，数据主要来源于商用车上牌信息
	 * 
	 * @author 
	 * @date Dec 27, 2016
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @param sql
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ICompetBrandDao#getCompetBrandSalesVolume(java.lang.String,
	 *      com.yonyou.iuap.persistence.jdbc.framework.SQLParameter,
	 *      org.springframework.data.domain.PageRequest, java.lang.String)
	 */
	@Override
	public Page<TmCompetbrandSalesVO> getCompetBrandSalesVolume(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest,String sql)
			throws DAOException {
		return baseDao.findBypageWithJoin(TmCompetbrandSalesVO.class,
				condition, sqlParameter, pageRequest, sql);
	}
}
