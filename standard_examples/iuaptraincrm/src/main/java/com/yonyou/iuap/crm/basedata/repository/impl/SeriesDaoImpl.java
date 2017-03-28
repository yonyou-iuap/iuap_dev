package com.yonyou.iuap.crm.basedata.repository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.crm.basedata.entity.SeriesExtVO;
import com.yonyou.iuap.crm.basedata.entity.SeriesVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.processor.BeanListProcessor;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;
import com.yonyou.iuap.crm.basedata.repository.itf.ISeriesDao;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;

/**
 * 
 * 车系信息Dao实现
 * 
 * @author 
 * @date 2016年11月22日
 * @version 1.0
 */
// 用于标注数据访问组件，即DAO组件
@Repository
public class SeriesDaoImpl implements ISeriesDao {
	@Autowired
	private AppBaseDao appbaseDao;

	/**
	 * 车系信息列表查询显示带分页 查询所以车系的信息，当dr=011
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return
	 * @throws DAOException
	 */
	@Override
	public Page<SeriesExtVO> getSeriesBypage(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
		String sqlJoin = "SELECT series.*,brand.pk_brand  FROM tm_series series "
				+ "LEFT JOIN tm_vehicleclassify vehicleclass ON (vehicleclass.pk_vehicleclassify=series.pk_vehicleclassify AND vehicleclass.dr=0) "
				+ "LEFT JOIN tm_brand brand ON (brand.pk_brand=vehicleclass.pk_brand AND brand.dr = 0) "
				+ "WHERE series.dr=0 ";
		// 将dr=1的过滤
		return appbaseDao.findBypageWithJoin(SeriesExtVO.class, condition, sqlParameter, pageRequest, sqlJoin);
		

	}

	/**
	 * 保存车系信息 新增和修改在一起保存，根据是否有Pk_series来判断该执行什么操作
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param pavo
	 * @return
	 * @throws DAOException
	 * @throws
	 */
	@Override
	public String saveSeries(SeriesExtVO pavo, String type) throws DAOException {
		String msg = "";
		if ("1".equals(type)) {
			appbaseDao.saveWithPK(pavo);
			msg = "保存成功！";
		} else {
			appbaseDao.update(pavo);
			msg = "修改成功！";
		}
		return msg;
	}

	/**
	 * 逻辑删除 逻辑删除设置dr=1
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param entity
	 * @throws DAOException
	 */
	@Override
	public void deleteSeriesByIdTS(SeriesExtVO entity) throws DAOException {
		appbaseDao.deleteWithDR(entity);
	}

	/**
	 * 通过id获取车系
	 * 
	 * @author 
	 * @date 2016年11月29日
	 * @param seriesId
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ISeriesDao#getSeriesById(java.lang.String)
	 */

	@Override
	public SeriesExtVO getSeriesById(String seriesId) throws DAOException {
		return appbaseDao.findById(SeriesExtVO.class, seriesId);
	}

	/**
	 * 更新车系信息
	 * 
	 * @author 
	 * @date 2016年12月1日
	 * @param entity
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ISeriesDao#updateSeriesVo(com.yonyou.iuap.crm.basedata.entity.SeriesExtVO)
	 */
	@Override
	public void updateSeriesVo(SeriesExtVO entity) throws DAOException {
		// TODO 自动生成的方法存根
		appbaseDao.update(entity);
	}

	/**
	 * 通过车系编码查找车系
	 * 
	 * @author 
	 * @date 2016年12月1日
	 * @param entity
	 * @param condition
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ISeriesDao#findListByClauseWithDR(com.yonyou.iuap.crm.basedata.entity.SeriesExtVO,
	 *      java.lang.String)
	 */
	@Override
	public List<SeriesExtVO> findListByClauseWithDR(SeriesExtVO entity,
			String condition) throws DAOException {
		return appbaseDao.findListByClauseWithDR(SeriesExtVO.class, condition);
	}

	/**
	 * 通过条件查询实体
	 * 
	 * @author 
	 * @date 2016年12月13日
	 * @param condition
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ISeriesDao#findListByClause(java.lang.String)
	 */
	@Override
	public List<SeriesExtVO> findListByClause(String condition)
			throws DAOException {
		return appbaseDao.findListByClauseWithDR(SeriesExtVO.class, condition);
	}

	/**
	 * 通过条件查询实体
	 * 
	 * @author 
	 * @date 2016年12月21日
	 * @param class1
	 * @param condition
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ISeriesDao#findListByClauseWithDR(java.lang.Class,
	 *      java.lang.String)
	 */
	@Override
	public List<SeriesVO> findListByClauseWithDR(Class<SeriesExtVO> class1,SQLParameter sqlParameter,
			String condition) throws DAOException {
		// TODO 自动生成的方法存根
		String tableName = FastBeanHelper.getTableName(SeriesVO.class);
		StringBuffer sqlBuffer = new StringBuffer("select * from "+tableName+" where ");
		sqlBuffer.append(condition);
		sqlBuffer.append(" and dr=0 order by ts desc");
		return appbaseDao.queryForList( sqlBuffer.toString(),sqlParameter,new BeanListProcessor(SeriesExtVO.class));
	}

	/**
	 * 通过车辆类别主键带品牌名称
	 * 
	 * @author 
	 * @date 2016年12月27日
	 * @param sql
	 * @return
	 * @throws DAOException
	 *             (non-Javadoc)
	 * @see com.yonyou.iuap.crm.basedata.repository.itf.ISeriesDao#getIntRefData(java.lang.String)
	 */
	@Override
	public List<Map<String, String>> getIntRefData(String sql)
			throws DAOException {
		// TODO 自动生成的方法存根
		return appbaseDao.findForList(sql, new MapListProcessor());
	}

	/**
	 * 手机端查询车系信息
	 * 
	 * @param pk_vehicleclassify
	 *            类别pk
	 * @return
	 * @throws AppBusinessException
	 */
	@Override
	public List<Map<String, Object>> querySeries(String pk_vehicleclassify)
			throws DAOException {
		// TODO 自动生成的方法存根
		StringBuffer sqlBuffer = new StringBuffer(
				"select * from tm_series where dr=0  and pk_vehicleclassify='"
						+ pk_vehicleclassify + "'");
		return appbaseDao.queryList(sqlBuffer.toString());
	}

	@Override
	public List<Map<String, Object>> queryOutTmeries(String pk_series)
			throws DAOException {
		// TODO 自动生成的方法存根
		StringBuffer sqlBuffer = new StringBuffer(
				"select * from pub_filesystem where groupname='tmseries-out' and filepath='"
						+ pk_series + "'");
		return appbaseDao.queryList(sqlBuffer.toString());
	}

	@Override
	public List<Map<String, Object>> queryInTmeries(String pk_series)
			throws DAOException {
		// TODO 自动生成的方法存根
		StringBuffer sqlBuffer = new StringBuffer(
				"select * from pub_filesystem where groupname='tmseries-in' and filepath='"
						+ pk_series + "'");
		return appbaseDao.queryList(sqlBuffer.toString());
	}

	@Override
	public List<Map<String, Object>> queryThumbTmeries(String pk_series)
			throws DAOException {
		// TODO 自动生成的方法存根
		StringBuffer sqlBuffer = new StringBuffer(
				"select * from pub_filesystem where groupname='tmseries-thumb' and filepath='"
						+ pk_series + "'  order by uploadtime desc");
		return appbaseDao.queryList(sqlBuffer.toString());
	}

}
