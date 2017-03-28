package com.yonyou.iuap.crm.basedata.service.itf;

import com.yonyou.iuap.crm.basedata.entity.TmCompetPriceVO;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;
/**
 * 竞品价格信息Service接口类
 * 定义了竞品价格信息的查询、保存和删除业务的接口
 * @author 
 * @date Nov 24, 2016
 */
public interface ICompetPriceService {
	/**
	 * 查询竞品价格信息，带分页
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param searchParams
	 * @param pageRequest
	 * @return
	 * @throws AppBusinessException
	 */
	public abstract Page<TmCompetPriceVO> getCompetPricesBypage(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException;

	/**
	 * 保存竞品价格信息
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @throws AppBusinessException
	 */
	public abstract void saveEntities(List<TmCompetPriceVO> entities)
			throws AppBusinessException;

	/**
	 * 删除竞品价格信息，逻辑删除
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entity
	 * @throws AppBusinessException
	 */
	public abstract void deleteCompetPriceByTS(TmCompetPriceVO entity)
			throws AppBusinessException;

	/**
	 * 删除竞品价格信息，物理删除
	 * 
	 * @author 
	 * @date Nov 21, 2016
	 * @param entities
	 * @throws AppBusinessException
	 */
	public abstract void deleteCompetPrice(List<TmCompetPriceVO> entities)
			throws AppBusinessException;
}
