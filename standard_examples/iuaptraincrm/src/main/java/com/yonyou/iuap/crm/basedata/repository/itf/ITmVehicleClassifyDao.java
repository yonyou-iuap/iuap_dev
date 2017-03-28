package com.yonyou.iuap.crm.basedata.repository.itf;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyExtVO;
import com.yonyou.iuap.crm.basedata.entity.TmVehicleClassifyVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;

/**
 * 
 * 车辆分类Dao接口
 * 
 * @author 
 * @date 2016年11月21日
 */
public interface ITmVehicleClassifyDao {

	/**
	 * 根据查询条件， 获取车辆分类信息并分页
	 * @author 
	 * @date 2016年11月24日
	 * @param condition
	 * @param arg1
	 * @param pageRequest
	 * @return
	 * @throws DAOException
	 */
	public Page<TmVehicleClassifyVO> getTmVehicleClassifysBypage(
			String condition, SQLParameter arg1, PageRequest pageRequest)
			throws DAOException;

	/**
	* 根据查询条件， 获取车辆分类扩展信息并分页
	* @author 
	* @date 2016年12月7日
	* @param sqlJoin
	* @param string
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	 */
	public Page<TmVehicleClassifyExtVO> getTmVehicleClassifysExtBypage(
			String sqlJoin, String string, SQLParameter sqlParameter,
			PageRequest pageRequest) throws DAOException;

	/**
	 * 根据Id， 获取相应的车辆分类实体
	 * 
	 * @author 
	 * @date 2016年11月24日
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public TmVehicleClassifyVO getTmVehicleClassifyVo(String id)
			throws DAOException;

	/**
	 * 保存车辆分类实体
	 * 
	 * @author 
	 * @date 2016年11月24日
	 * @param entity
	 * @return
	 * @throws DAOException
	 */
	public String saveTmVehicleClassifyVo(TmVehicleClassifyVO entity)
			throws DAOException;

	/**
	 * 更新相应的车辆分类实体
	 * 
	 * @author 
	 * @date 2016年11月24日
	 * @param entity
	 * @throws DAOException
	 */
	public void updateTmVehicleClassifyVo(TmVehicleClassifyVO entity)
			throws DAOException;

	
	/**
	 * 批量更新车辆类别信息
	* @author 
	* @date 2016年12月30日
	* @param entityLst
	* @throws DAOException
	 */
	public void batchUpdateTmVehicleClassifyVo(List<TmVehicleClassifyVO> entityLst) throws DAOException;
	
	
	
	/**
	 * 删除相应的车辆分类实体
	 * 
	 * @author 
	 * @date 2016年11月24日
	 * @param entity
	 * @throws DAOException
	 */
	public void deleteTmVehicleClassifyVo(TmVehicleClassifyVO entity)
			throws DAOException;

	/**
	 * 根据Id， 删除车辆分类实体
	 * 
	 * @author 
	 * @date 2016年11月24日
	 * @param id
	 * @throws DAOException
	 */
	public void deleteById(String id) throws DAOException;

	
	/**
	* 手机端获取车辆分类信息
	* @date 2016年11月24日
	* @return
	* @throws AppBusinessException
	 * @throws DAOException 
	 */
	public  List<Map<String,Object>> queryTmVehicleClassifys()
			throws DAOException;
	
	/**
	 * 根据sql条件查找相关的车辆类别数据
	* @author 
	* @date 2017年1月6日
	* @param sql
	* @return
	* @throws DAOException
	 */
	public List<TmVehicleClassifyVO> findVehicleClassifyByClause(String sql, SQLParameter sqlParameter) throws DAOException;

}
