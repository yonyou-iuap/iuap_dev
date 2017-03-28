package com.yonyou.iuap.crm.basedata.repository.itf;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.TmBrandVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;

/**
 * 
* 品牌信息Dao接口
* @author 
* @date 2016年11月21日
 */
public interface ITmBrandDao {
	
	/**
	 * 根据ID， 获取品牌信息实体
	* @author 
	* @date 2016年11月24日
	* @param condition
	* @param arg1
	* @param pageRequest
	* @return
	* @throws DAOException
	 */
	public Page<TmBrandVO> getTmBrandsBypage(String condition, SQLParameter arg1,
			PageRequest pageRequest) throws DAOException;
	
	/**
	 * 根据Id， 获取品牌信息实体
	* @author 
	* @date 2016年11月24日
	* @param id
	* @return
	* @throws DAOException
	 */
	public TmBrandVO getTmBrandVo(String id) throws DAOException;
	
	/**
	 * 保存品牌信息实体
	* @author 
	* @date 2016年11月24日
	* @param entity
	* @return
	* @throws DAOException
	 */
	public String saveTmBrandVo(TmBrandVO entity) throws DAOException;
	
	/**
	 * 更新相应的品牌信息实体
	* @author 
	* @date 2016年11月24日
	* @param entity
	* @throws DAOException
	 */
	public void updateTmBrandVo(TmBrandVO entity) throws DAOException;
	
	/**
	 * 删除品牌信息实体
	* @author 
	* @date 2016年11月24日
	* @param entity
	* @throws DAOException
	 */
	public void deleteTmBrandVo(TmBrandVO entity) throws DAOException;

	/**
	 * 根据Id， 删除该品牌实体
	* @author 
	* @date 2016年11月24日
	* @param id
	* @throws DAOException
	 */
	public void deleteById(String id) throws DAOException;
	

	
	/**
	* 批量更新品牌实体
	* @author 
	* @date 2016年12月30日
	* @param entityLst
	* @throws DAOException
	*/
		
	void batchUpdateTmBrandVo(List<TmBrandVO> entityLst) throws DAOException;
}
