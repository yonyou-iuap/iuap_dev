package com.yonyou.iuap.crm.basedata.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.crm.basedata.entity.TmBrandVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.basedata.repository.itf.ITmBrandDao;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;

/**
 * 品牌信息Dao
* @author 
* @date 2016年11月21日
 */
@Repository
public class TmBrandDaoImpl implements ITmBrandDao{
	@Autowired
	private AppBaseDao baseDao;		
	
	
	
	/**
	 * 根据ID， 获取品牌信息实体
	* @author 
	* @date 2016年11月24日
	* @param id
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ITmBrandDao#getTmBrandVo(java.lang.String)
	 */
	@Override
	public TmBrandVO getTmBrandVo(String id) throws DAOException {
		return baseDao.findById(TmBrandVO.class, id);
	}

	/**
	 * 保存品牌信息实体
	* @author 
	* @date 2016年11月24日
	* @param entity
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ITmBrandDao#saveTmBrandVo(com.yonyou.iuap.crm.basedata.entity.TmBrandVO)
	 */
	@Override
	public String saveTmBrandVo(TmBrandVO entity) throws DAOException {
		baseDao.saveWithPK(entity);
		return entity.getPk_brand();
	}

	/**
	 * 更新相应的品牌信息
	* @author 
	* @date 2016年11月24日
	* @param entity
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ITmBrandDao#updateTmBrandVo(com.yonyou.iuap.crm.basedata.entity.TmBrandVO)
	 */
	@Override
	public void updateTmBrandVo(TmBrandVO entity) throws DAOException {
		baseDao.update(entity);
	}
	
	/**
	 * 批量更新品牌信息
	* @author 
	* @date 2016年12月30日
	* @param entityLst
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ITmBrandDao#batchUpdateTmBrandVo(java.util.List)
	 */
	@Override
	public void batchUpdateTmBrandVo(List<TmBrandVO> entityLst) throws DAOException {
		baseDao.batchUpdate(entityLst);
	}

	/**
	 * 删除相应的品牌信息
	* @author 
	* @date 2016年11月24日
	* @param entity
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ITmBrandDao#deleteTmBrandVo(com.yonyou.iuap.crm.basedata.entity.TmBrandVO)
	 */
	@Override
	public void deleteTmBrandVo(TmBrandVO entity) throws DAOException {
		baseDao.delete(entity);
	}

	/**
	 * 根据查询条件，获取品牌信息并分页
	* @author 
	* @date 2016年11月24日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ITmBrandDao#getTmBrandsBypage(java.lang.String, com.yonyou.iuap.persistence.jdbc.framework.SQLParameter, org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<TmBrandVO> getTmBrandsBypage(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException {
		
		return baseDao.findBypageWithDR(TmBrandVO.class, condition, sqlParameter, pageRequest);
	
	}

	/**
	 * 根据Id， 删除相应的品牌信息
	* @author 
	* @date 2016年11月24日
	* @param id
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ITmBrandDao#deleteById(java.lang.String)
	 */
	@Override
	public void deleteById(String id) throws DAOException {
		TmBrandVO entity = new TmBrandVO();
		entity.setPk_brand(id);
		baseDao.delete(entity);
	}
	
}
