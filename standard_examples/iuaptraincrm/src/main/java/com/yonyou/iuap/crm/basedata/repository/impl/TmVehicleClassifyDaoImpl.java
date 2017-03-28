package com.yonyou.iuap.crm.basedata.repository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.processor.BeanListProcessor;
import com.yonyou.iuap.crm.basedata.repository.itf.ITmVehicleClassifyDao;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;

/**
 * 
 * 车辆分类Dao实现
 * 
 * @author 
 * @date 2016年11月21日
 */
@Repository
public class TmVehicleClassifyDaoImpl implements ITmVehicleClassifyDao {
	@Autowired
	private AppBaseDao baseDao;

	
	/**
	 * 根据查询条件， 获取车辆分类信息扩展并分页
	* @author 
	* @date 2016年12月7日
	* @param sqlJoin
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ITmVehicleClassifyDao#getTmVehicleClassifysExtBypage(java.lang.String, java.lang.String, com.yonyou.iuap.persistence.jdbc.framework.SQLParameter, org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<TmVehicleClassifyExtVO> getTmVehicleClassifysExtBypage(
			String sqlJoin, String condition, SQLParameter sqlParameter,
			PageRequest pageRequest) throws DAOException {
		return baseDao.findBypageWithJoin(TmVehicleClassifyExtVO.class,
				condition, sqlParameter, pageRequest, sqlJoin);

	}

	/*
	 * 分页获取车辆分类信息
	 * @author 
	 * @date 2016年11月21日
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return
	 * @throws DAOException (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ITmVehicleClassifyVoDao#
	 * getTmVehicleClassifysBypage(java.lang.String,
	 * com.yonyou.iuap.persistence.jdbc.framework.SQLParameter,
	 * org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<TmVehicleClassifyVO> getTmVehicleClassifysBypage(
			String condition, SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
		return baseDao.findBypageWithDR(TmVehicleClassifyVO.class, condition,
				sqlParameter, pageRequest);

	}

	/**
	 * 获取车辆分类实体
	 * 
	 * @author 
	 * @date 2016年11月21日
	 * @param id
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ITmVehicleClassifyDao#getTmVehicleClassifyVo(java.lang.String)
	 */
	@Override
	public TmVehicleClassifyVO getTmVehicleClassifyVo(String id)
			throws DAOException {
		return baseDao.findById(TmVehicleClassifyVO.class, id);
	}

	/**
	 * 保存车辆分类实体
	 * 
	 * @author 
	 * @date 2016年11月21日
	 * @param entity
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ITmVehicleClassifyDao#saveTTmVehicleClassifyVo(com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO)
	 */
	@Override
	public String saveTmVehicleClassifyVo(TmVehicleClassifyVO entity)
			throws DAOException {
		baseDao.saveWithPK(entity);
		return entity.getPk_vehicleclassify();
	}

	/**
	 * 更新车辆分类实体
	 * 
	 * @author 
	 * @date 2016年11月21日
	 * @param entity
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ITmVehicleClassifyDao#updateTmVehicleClassifyVo(com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO)
	 */
	@Override
	public void updateTmVehicleClassifyVo(TmVehicleClassifyVO entity)
			throws DAOException {
		baseDao.update(entity);
	}
	
	/**
	* @author 
	* @date 2016年12月30日
	* @param entityLst
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ITmVehicleClassifyDao#batchUpdateTmVehicleClassifyVo(java.util.List)
	*/
		
	@Override
	public void batchUpdateTmVehicleClassifyVo(
			List<TmVehicleClassifyVO> entityLst) throws DAOException {
		
		baseDao.batchUpdate(entityLst);
	}
	

	/**
	 * 删除车辆分类实体
	 * 
	 * @author 
	 * @date 2016年11月21日
	 * @param entity
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ITmVehicleClassifyDao#deleteTmVehicleClassifyVo(com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO)
	 */
	@Override
	public void deleteTmVehicleClassifyVo(TmVehicleClassifyVO entity)
			throws DAOException {
		baseDao.delete(entity);
	}

	/**
	 * 根据Id， 产出车辆分类实体
	 * 
	 * @author 
	 * @date 2016年11月21日
	 * @param id
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ITmVehicleClassifyDao#deleteById(java.lang.String)
	 */
	@Override
	public void deleteById(String id) throws DAOException {
		TmVehicleClassifyVO entity = new TmVehicleClassifyVO();
		entity.setPk_vehicleclassify(id);
		baseDao.delete(entity);
	}

	
	/**
	* 手机端获取车辆分类信息
	* @date 2016年11月24日
	* @return
	 * @throws DAOException 
	 */
	public  List<Map<String,Object>> queryTmVehicleClassifys() throws  DAOException{
		StringBuffer sqlBuffer = new StringBuffer("select * from tm_vehicleclassify where dr=0 ");
		return baseDao.queryList(sqlBuffer.toString());
	}

	/**
	* @author 
	* @date 2017年1月6日
	* @param sql
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.basedata.repository.itf.ITmVehicleClassifyDao#findVehicleClassifyByClause(java.lang.String)
	*/
		
	@Override
	public List<TmVehicleClassifyVO> findVehicleClassifyByClause(String sqlCondition, SQLParameter sqlParameter)
			throws DAOException {
		
//		String tableName = FastBeanHelper.getTableName(entityClass);
		StringBuffer sqlBuffer = new StringBuffer("select * from tm_vehicleclassify where ");
		sqlBuffer.append(sqlCondition);
		sqlBuffer.append(" and dr=0 order by ts desc");
		
		return baseDao.queryForList(sqlBuffer.toString(), sqlParameter, new BeanListProcessor(TmVehicleClassifyVO.class));
	}

}
