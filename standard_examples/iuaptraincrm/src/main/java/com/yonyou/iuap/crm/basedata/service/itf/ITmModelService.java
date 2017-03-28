package com.yonyou.iuap.crm.basedata.service.itf;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.crm.basedata.entity.TmCompetBrandVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmModelVO;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;


/**
* TODO 车型service接口
* @author name
* @date 2016年11月21日
*/
	
public interface ITmModelService {
	
	
	/**
	* TODO 通过id获取实体
	* @author name
	* @date 2016年11月21日
	* @param id
	* @return
	* @throws AppBusinessException
	*/
		
	public TmModelVO getEntityById(String id) throws AppBusinessException;
	
	
	/**
	* TODO 逻辑删除
	* @author name
	* @date 2016年11月21日
	* @param id
	* @throws AppBusinessException
	*/
		
	@Transactional
	public void deleteById(String id) throws AppBusinessException;
	
	
	
	/**
	* TODO 逻辑删除
	* @author 
	* @date 2016年11月21日
	* @param entity
	* @throws AppBusinessException
	*/
		
	@Transactional
	public void deleteEntity(TmModelVO entity) throws AppBusinessException;
	
	
	/**
	* TODO 批量删除
	* @author 
	* @date 2016年11月21日
	* @param ids
	* @throws AppBusinessException
	*/
		
	@Transactional
	public void batchDelete(List<String> ids) throws AppBusinessException;
	
	public void batchDeleteEntity(List<TmModelVO> entitys) throws AppBusinessException;
	
	
	/**
	* TODO 保存
	* @author name
	* @date 2016年11月21日
	* @param entity
	* @return
	* @throws AppBusinessException
	*/
		
	@Transactional
	public TmModelVO saveEntity(TmModelVO entity) throws AppBusinessException;

	
	/**
	* 获取分页列表
	* @author name
	* @date 2016年11月21日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	*/
		
	public Page<TmModelExtVO> getTmModelPage(Map<String, Object> searchParams, PageRequest pageRequest) throws AppBusinessException;
	
	
	/**
	* 获取车型的竞品
	* @author name
	* @date 2016年11月21日
	* @param entity
	* @return
	* @throws AppBusinessException
	*/
		
	public List<TmCompetBrandVO> getTmCompetByModel(TmModelVO entity) throws AppBusinessException;
	
	/**
	* TODO 启用-停用
	* @author 
	* @date 2016年11月30日
	* @param option
	* @param vo
	* @throws AppBusinessException
	*/
	@Transactional
	public void startOrStop(Boolean flag,List<TmModelExtVO> vos) throws AppBusinessException;
	
	
	/**根据条件查询车型
	* TODO description
	* @author 
	* @date 2016年12月8日
	* @param condition
	* @return
	* @throws AppBusinessException
	*/
		
	public List<TmModelVO> getModelByCondition(String condition) throws AppBusinessException;
	
	/**手机端根据车型id查询车型
	* TODO description
	* @date 2016年12月8日
	* @param condition
	* @return
	* @throws AppBusinessException
	*/
		
	public List<Map<String,Object>> queryModelInfo(String pk_series) throws AppBusinessException;

}
