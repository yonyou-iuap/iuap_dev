package com.yonyou.iuap.crm.basedata.service.itf;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;

/**
 * 车辆分类服务接口
* @author 
* @date 2016年11月21日
 */
public interface ITmVehicleClassifyService {
	
	/**
	 * 根据查询条件， 获取车辆分类信息并分页
	* @author 
	* @date 2016年11月24日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	 */
	public  Page<TmVehicleClassifyVO> getTmVehicleClassifysBypage(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException;
	
	/**
	 * 根据查询条件， 获取车辆分类扩展信息并分页
	* @author 
	* @date 2016年11月28日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	 */
	public  Page<TmVehicleClassifyExtVO> getTmVehicleClassifysExtBypage(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws AppBusinessException;
	
	
	/**
	 * 保存车辆分类信息
	* @author 
	* @date 2016年11月24日
	* @param entity
	* @return
	* @throws AppBusinessException
	 */
	public String saveEntity(TmVehicleClassifyVO entity) throws AppBusinessException;
	
	/**
	 * 更新车辆分类信息 
	* @author 
	* @date 2016年11月24日
	* @param entity
	* @return
	* @throws AppBusinessException
	 */
	public String updateEntity(TmVehicleClassifyVO entity) throws AppBusinessException;
	
	/**
	 * 批量更新车辆类别信息 
	* @author 
	* @date 2016年12月30日
	* @param entityLst
	* @return
	* @throws AppBusinessException
	 */
	public String batchUpdateEntity(List<TmVehicleClassifyVO> entityLst) throws AppBusinessException;
	
	
	/**
	 * 删除车辆分类信息
	* @author 
	* @date 2016年11月24日
	* @param entity
	* @throws AppBusinessException
	 */
	public void deleteTmVehicleClassifyVo(TmVehicleClassifyVO entity) throws AppBusinessException;
		
	/**
	 * 根据ID, 获取车辆分类信息
	* @author 
	* @date 2016年11月24日
	* @param id
	* @return
	* @throws AppBusinessException
	 */
	public TmVehicleClassifyVO getTmVehicleClassifyVo(String id) throws AppBusinessException;
	
	/**
	 * 根绝ID, 删除车辆分类信息
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
	/**
	* 手机端获取车辆分类信息
	* @date 2016年11月24日
	* @return
	* @throws AppBusinessException
	 */
	public  List<Map<String,Object>> queryTmVehicleClassifys()
			throws AppBusinessException;
}
