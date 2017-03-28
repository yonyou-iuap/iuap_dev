package com.yonyou.iuap.crm.basedata.repository.itf;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.SeriesExtVO;
import com.yonyou.iuap.crm.basedata.entity.SeriesVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;

/**
 * 车系信息Dao层接口
 * 
 * @author 
 * @date 2016年11月22日
 * @version 1.0
 */
public interface ISeriesDao {

	/**
	 * 查询所有车系信息列表 根据条件查询信息
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param condition
	 * @param sqlParameter
	 * @param pageRequest
	 * @return
	 * @throws DAOException
	 */
	@Transactional
	public Page<SeriesExtVO> getSeriesBypage(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException;

	/**
	 * 保存车系信息
	 * 
	 * @param mainvo
	 * @return
	 * @throws DAOException
	 */
	@Transactional
	public String saveSeries(SeriesExtVO mainvo, String type)
			throws DAOException;

	/**
	 * 逻辑删除，非物理删除
	 * 
	 * @param seriesvo
	 * @return
	 * @throws DAOException
	 */
	@Transactional
	public void deleteSeriesByIdTS(SeriesExtVO seriesvo) throws DAOException;

	/**
	 * 通过id获取车系
	 * 
	 * @author 
	 * @date 2016年11月29日
	 * @param seriesId
	 * @return
	 * @throws DAOException
	 */
	@Transactional
	public SeriesExtVO getSeriesById(String seriesId) throws DAOException;

	/**
	 * 更新车系信息 TODO description
	 * 
	 * @author 
	 * @date 2016年12月1日
	 * @param entity
	 * @throws DAOException
	 */
	@Transactional
	public void updateSeriesVo(SeriesExtVO entity) throws DAOException;

	/**
	 * 通过车系编码查找车系 TODO description
	 * 
	 * @author 
	 * @date 2016年12月1日
	 * @param entity
	 * @param condition
	 * @return
	 * @throws DAOException
	 */
	@Transactional
	public List<SeriesExtVO> findListByClauseWithDR(SeriesExtVO entity,
			String condition) throws DAOException;

	/**
	 * 通过条件查询实体 TODO description
	 * 
	 * @author 
	 * @date 2016年12月13日
	 * @param condition
	 * @return
	 * @throws DAOException
	 */
	public List<SeriesExtVO> findListByClause(String condition)
			throws DAOException;

	/**
	 * 通过条件查询实体 TODO description
	 * 
	 * @author 
	 * @date 2016年12月21日
	 * @param class1
	 * @param condition
	 * @return
	 * @throws DAOException
	 */
	public List<SeriesVO> findListByClauseWithDR(Class<SeriesExtVO> class1,SQLParameter sqlParameter,
			String condition) throws DAOException;

	/**
	 * 通过车辆类别主键带品牌名称 TODO description
	 * 
	 * @author 
	 * @date 2016年12月27日
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<Map<String, String>> getIntRefData(String sql)
			throws DAOException;

	/**
	 * 手机端查询车系信息
	 * 
	 * @param pk_vehicleclassify
	 *            类别pk
	 * @return
	 * @throws AppBusinessException
	 */
	public List<Map<String, Object>> querySeries(String pk_vehicleclassify)
			throws DAOException;

	/**
	 * 手机端查询车系外观图片信息
	 * 
	 * @param pk_series
	 *            车系pk
	 * @return
	 * @throws AppBusinessException
	 */
	public List<Map<String, Object>> queryOutTmeries(String pk_series)
			throws DAOException;

	/**
	 * 手机端查询车系内饰图片信息
	 * 
	 * @param pk_series
	 *            车系pk
	 * @return
	 * @throws AppBusinessException
	 */
	public List<Map<String, Object>> queryInTmeries(String pk_series)
			throws DAOException;

	/**
	 * 手机端查询车系缩略图片信息
	 * 
	 * @param pk_series
	 *            车系pk
	 * @return
	 * @throws AppBusinessException
	 */
	public List<Map<String, Object>> queryThumbTmeries(String pk_series)
			throws DAOException;

}
