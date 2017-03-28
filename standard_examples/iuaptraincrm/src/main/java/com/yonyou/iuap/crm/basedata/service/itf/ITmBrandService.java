package com.yonyou.iuap.crm.basedata.service.itf;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.TmBrandVO;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;

/**
 * 
* 品牌信息服务接口
* @author 
* @date 2016年11月21日
 */
public interface ITmBrandService {
	
	/**
	 * 根据查询条件， 获取品牌信息并分页 
	* @author 
	* @date 2016年11月24日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	 */
	public  Page<TmBrandVO> getTmBrandsBypage(
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
	public String saveEntity(TmBrandVO entity) throws AppBusinessException;
	
	/**
	 * 更新品牌信息
	* @author 
	* @date 2016年11月24日
	* @param entity
	* @return
	* @throws AppBusinessException
	 */
	public String updateEntity(TmBrandVO entity) throws AppBusinessException;
	
	/**
	 * 批量更新品牌信息
	* @author 
	* @date 2016年12月30日
	* @param entity
	* @return
	* @throws AppBusinessException
	 */
	public String batchUpdateEntity(List<TmBrandVO> entityLst) throws AppBusinessException;
	
	/**
	 * 删除品牌信息
	* @author 
	* @date 2016年11月24日
	* @param entity
	* @throws AppBusinessException
	 */
	public void deleteTmBrandVo(TmBrandVO entity) throws AppBusinessException;
	
	/**
	 * 获取品牌信息实体
	* @author 
	* @date 2016年11月24日
	* @param id
	* @return
	* @throws AppBusinessException
	 */
	public TmBrandVO getTmBrandVo(String id) throws AppBusinessException;
	
	/**
	 * 根据Id， 删除品牌信息实体
	* @author 
	* @date 2016年11月24日
	* @param id
	* @throws AppBusinessException
	 */
	public void deleteById(String id) throws AppBusinessException;
	
//	/**
//	 * 检查数据库中该列是否有相同的值 
//	* @author 
//	* @date 2016年11月24日
//	* @param column
//	* @param value
//	* @return
//	* @throws AppBusinessException
//	 */
//	public boolean checkPropertyDuplicated(String column, String value) throws AppBusinessException;
}
