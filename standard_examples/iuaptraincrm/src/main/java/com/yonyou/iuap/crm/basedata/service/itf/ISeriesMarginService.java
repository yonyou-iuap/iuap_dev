package com.yonyou.iuap.crm.basedata.service.itf;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springside.modules.utils.Clock;

import com.yonyou.iuap.crm.basedata.entity.SeriesMarginExtVO;
import com.yonyou.iuap.crm.basedata.entity.SeriesMarginVO;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;

public interface ISeriesMarginService {
	public abstract void setClock(Clock clock);

	/**
	 * 分页查询
	* @author 
	* @date 2016年11月22日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	 */
	public abstract Page<SeriesMarginExtVO> getSeriesMarginsBypage(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws  AppBusinessException;

	/**
	 * 新增
	* @author 
	* @date 2016年11月22日
	* @param entity
	* @return
	* @throws AppBusinessException
	 */
	public abstract String saveEntity(SeriesMarginVO entity)
			throws  AppBusinessException;
	
	/**
	 * 批量新增
	* @author 
	* @date 2016年12月1日
	* @param voList
	* @throws AppBusinessException
	 */
	public abstract void saveEntitys(List<SeriesMarginVO> voList)
			throws  AppBusinessException;

	/**
	 * 修改
	* @author 
	* @date 2016年11月22日
	* @param entity
	* @return
	* @throws AppBusinessException
	 */
	public abstract String updateEntity(SeriesMarginVO entity)
			throws  AppBusinessException;

	/**
	 * 根据PK删除单条数据
	* @author 
	* @date 2016年11月22日
	* @param pk_seriesmargin
	* @throws AppBusinessException
	 */
	public abstract void deleteSeriesMarginById(String pk_seriesmargin)
			throws AppBusinessException;

	/**
	 * 批量删除
	* @author 
	* @date 2016年11月22日
	* @param entitys
	* @return
	* @throws AppBusinessException
	 */
	public Map<String, Object> removeSeriesMargins(List<SeriesMarginVO> entitys)
			throws AppBusinessException;

	/**
	 * 导入数据新增与修改
	* @author 
	* @date 2016年12月1日
	* @param voList
	* @throws AppBusinessException
	 */
	public void importEntitys(List<SeriesMarginVO> voList) throws AppBusinessException;

}
