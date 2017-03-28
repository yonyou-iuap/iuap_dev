package com.yonyou.iuap.crm.basedata.service.itf;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.TmComBrandVO;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;

public interface ITmComBrandService {
	/**
	 * 根据查询条件， 获取品牌信息并分页 
	* @author 
	* @date 2016年11月24日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	 */
	public  Page<TmComBrandVO> getTmBrandsBypage(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException;
	
	/**
	 * 保存品牌信息
	* @author 
	* @date 2016年11月24日
	* @param entity
	* @return
	* @throws AppBusinessException
	 */
	public String saveEntity(TmComBrandVO entity) throws AppBusinessException;
	
	/**
	 * 更新品牌信息
	* @author 
	* @date 2016年11月24日
	* @param entity
	* @return
	* @throws AppBusinessException
	 */
	public String updateEntity(TmComBrandVO entity) throws AppBusinessException;
	
	/**
	 * 删除品牌信息
	* @author 
	* @date 2016年11月24日
	* @param entity
	* @throws AppBusinessException
	 */
	public void deleteTmBrandVo(TmComBrandVO entity) throws AppBusinessException;
	
	/**
	 * 获取品牌信息实体
	* @author 
	* @date 2016年11月24日
	* @param id
	* @return
	* @throws AppBusinessException
	 */
	public TmComBrandVO getTmComBrandVo(String id) throws AppBusinessException;
	
	/**
	 * 根绝Id， 删除品牌信息实体
	* @author 
	* @date 2016年11月24日
	* @param id
	* @throws AppBusinessException
	 */
	public void deleteById(String id) throws AppBusinessException;
	
	/**
	 * 检查数据库中该列是否有相同的值 
	* @author 
	* @date 2016年11月24日
	* @param column
	* @param value
	* @return
	* @throws AppBusinessException
	 */
	public boolean checkPropertyDuplicated(String column, String value) throws AppBusinessException;

}
