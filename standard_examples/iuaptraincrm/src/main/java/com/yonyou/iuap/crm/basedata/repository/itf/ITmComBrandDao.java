package com.yonyou.iuap.crm.basedata.repository.itf;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.basedata.entity.TmComBrandVO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;

public interface ITmComBrandDao {
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
	public Page<TmComBrandVO> getTmBrandsBypage(String condition, SQLParameter arg1,
			PageRequest pageRequest) throws DAOException;
	
	/**
	 * 根据Id， 获取品牌信息实体
	* @author 
	* @date 2016年11月24日
	* @param id
	* @return
	* @throws DAOException
	 */
	public TmComBrandVO getTmComBrandVO(String id) throws DAOException;
	
	/**
	 * 保存品牌信息实体
	* @author 
	* @date 2016年11月24日
	* @param entity
	* @return
	* @throws DAOException
	 */
	public String saveTmComBrandVO(TmComBrandVO entity) throws DAOException;
	
	/**
	 * 更新相应的品牌信息实体
	* @author 
	* @date 2016年11月24日
	* @param entity
	* @throws DAOException
	 */
	public void updateTmComBrandVO(TmComBrandVO entity) throws DAOException;
	
	/**
	 * 删除品牌信息实体
	* @author 
	* @date 2016年11月24日
	* @param entity
	* @throws DAOException
	 */
	public void deleteTmComBrandVO(TmComBrandVO entity) throws DAOException;

	/**
	 * 根据Id， 删除该品牌实体
	* @author 
	* @date 2016年11月24日
	* @param id
	* @throws DAOException
	 */
	public void deleteById(String id) throws DAOException;
	
	/**
	 * 检查数据库中该列是否有相同的值  
	* @author 
	* @date 2016年11月24日
	* @param column
	* @param value
	* @return
	* @throws DAOException
	 */
	public boolean check(String column, String value) throws DAOException ;

	/**
	 * 查询所有竞品列表，以List形式返回 
	 * 
	 * @author 
	 * @date Dec 23, 2016
	 * @return
	 */
	public List<TmComBrandVO> findAllBrands() throws DAOException;

}
