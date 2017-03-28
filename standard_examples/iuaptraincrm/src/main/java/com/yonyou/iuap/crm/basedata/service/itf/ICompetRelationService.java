package com.yonyou.iuap.crm.basedata.service.itf;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.TmModelCompetExtVO;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;

/**
 * 竞品对比车型信息Service接口类 定义了竞品-对比关系的保存和删除业务的接口
 * 
 * @author 
 * @date Dec 2, 2016
 */
public interface ICompetRelationService {
	/**
	 * 保存竞品-对比关系
	 * 
	 * @author 
	 * @date Dec 2, 2016
	 * @param entities
	 * @throws AppBusinessException
	 */
	public abstract void saveCompetRelation(List<TmModelCompetExtVO> entities)
			throws AppBusinessException;

	/**
	 * 删除竞品-对比关系，逻辑删除
	 * 
	 * @author 
	 * @date Dec 2, 2016
	 * @param searchParams
	 * @throws AppBusinessException
	 */
	public abstract void deleteCompetRelation(Map<String, Object> searchParams)
			throws AppBusinessException;

	/**
	 * 获取某个竞品下对比车型列表信息
	 * 
	 * @author 
	 * @date Dec 2, 2016
	 * @param entities
	 * @throws AppBusinessException
	 */
	public abstract Page<TmModelCompetExtVO> getRelatedModels(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException;

}
