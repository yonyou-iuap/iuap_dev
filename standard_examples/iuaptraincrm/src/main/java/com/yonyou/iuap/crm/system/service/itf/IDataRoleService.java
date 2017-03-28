package com.yonyou.iuap.crm.system.service.itf;

import com.yonyou.iuap.persistence.bs.dao.DAOException;

/**
 * 数据权限控制
* <p>description：</p>
* @author 
* @created 2017年1月16日 下午8:30:41
* @version 1.0
 */
public interface IDataRoleService {
	/**
	 * 根据用户ID获取数据权限过滤SQL条件
	* TODO description
	* @author 
	* @date 2017年1月16日
	* @param userid  用户ID
	* @return
	* @throws DAOException
	 */
	public String getRoleSql(String userid) throws DAOException;
	/**
	 * 根据用户ID和业务表的组织字段，获取数据权限过滤SQL条件
	* TODO description
	* @author 
	* @date 2017年1月16日
	* @param userid  用户ID
	* @param deptColumn  业务表中的部门字段
	* @return
	* @throws DAOException
	 */
	public String getRoleSql(String userid,String deptColumn) throws DAOException;
	
	/**
	 * 根据用户ID、业务表的组织字段名、部门字段名和用户字段名，生成数据过滤条件
	* TODO description
	* @author 
	* @date 2017年1月16日
	* @param userid
	* @param orgColumn
	* @param deptColumn
	* @param createrColumn
	* @return
	* @throws DAOException
	 */
	public String getRoleSql(String userid,String orgColumn,String deptColumn,String createrColumn) throws DAOException;
	
	/**
	 * 根据业务表的组织字段名、部门字段名和用户字段名，生成数据过滤条件
	* TODO description
	* @author 
	* @date 2017年1月17日
	* @param orgColumn
	* @param deptColumn
	* @param createrColumn
	* @return
	* @throws DAOException
	 */
	public String getRoleSql(String orgColumn,String deptColumn,String createrColumn) throws DAOException;
}
