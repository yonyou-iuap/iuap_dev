package com.yonyou.iuap.crm.basedata.service.itf;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.crm.basedata.entity.SeriesExtVO;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;

/**
 * 车系信息服务接口
 * 
 * @author 
 * @date 2016年11月22日
 * @version 1.0
 */
public interface ISeriesService {

	/**
	 * 查询车系信息11
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param parammap
	 * @param pageRequest
	 * @return
	 * @throws AppBusinessException
	 */
	@Transactional
	public Page<SeriesExtVO> getSeriesBypage(Map<String, Object> parammap,
			PageRequest pageRequest) throws AppBusinessException;

	/**
	 * 保存车系信息 TODO description
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param mainvo
	 * @return
	 * @throws AppBusinessException
	 */
	@Transactional
	public String saveSeries(SeriesExtVO mainvo) throws AppBusinessException;

	/**
	 * 逻辑删除车系信息
	 * 
	 * @author 
	 * @date 2016年11月22日
	 * @param entity
	 * @throws AppBusinessException
	 */
	@Transactional
	public abstract void deleteSeriesByIdTS(SeriesExtVO entity)
			throws AppBusinessException;

	/**
	 * 通过ID获取车系VO TODO description
	 * 
	 * @author 
	 * @date 2016年11月30日
	 * @param seriesId
	 * @return
	 * @throws AppBusinessException
	 */
	@Transactional
	public SeriesExtVO getSeriesById(String seriesId)
			throws AppBusinessException;

	/**
	 * 更新车系信息 TODO description
	 * 
	 * @author 
	 * @date 2016年11月30日
	 * @param entity
	 * @return
	 * @throws AppBusinessException
	 */
	@Transactional
	public void updateEntity(SeriesExtVO entity) throws AppBusinessException;

	/**
	 * 通过车系编码查找车系 TODO description
	 * 
	 * @author 
	 * @date 2016年12月1日
	 * @param entity
	 * @param condition
	 * @throws AppBusinessException
	 */
	@Transactional
	public void findListByClauseWithDR(SeriesExtVO entity, String condition)
			throws AppBusinessException;

	/**
	 * 通过条件查询实体 TODO description
	 * 
	 * @author 
	 * @date 2016年12月13日
	 * @param condition
	 * @return
	 * @throws AppBusinessException
	 */
	public List<SeriesExtVO> findListByClause(String condition)
			throws AppBusinessException;

	/**
	 * 通过车辆类别主键带品牌名称 TODO description
	 * 
	 * @author 
	 * @date 2016年12月27日
	 * @param sql
	 * @return
	 * @throws AppBusinessException
	 */
	public List<Map<String, String>> getIntRefData(String sql)
			throws AppBusinessException;

	/**
	 * 手机端查询车系信息
	 * 
	 * @param pk_vehicleclassify
	 *            类别pk
	 * @return
	 * @throws AppBusinessException
	 */
	@Transactional
	public List<Map<String, Object>> querySeries(String pk_vehicleclassify)
			throws AppBusinessException;

	/**
	 * 手机端查询车系外观图片信息
	 * 
	 * @param pk_series
	 *            车系pk
	 * @return
	 * @throws AppBusinessException
	 */
	@Transactional
	public List<Map<String, Object>> queryOutTmeries(String pk_series)
			throws AppBusinessException;

	/**
	 * 手机端查询车系内饰图片信息
	 * 
	 * @param pk_series
	 *            车系pk
	 * @return
	 * @throws AppBusinessException
	 */
	@Transactional
	public List<Map<String, Object>> queryInTmeries(String pk_series)
			throws AppBusinessException;

	/**
	 * 手机端查询车系缩略图片信息
	 * 
	 * @param pk_series
	 *            车系pk
	 * @return
	 * @throws AppBusinessException
	 */
	@Transactional
	public List<Map<String, Object>> queryThumbTmeries(String pk_series)
			throws AppBusinessException;

}
