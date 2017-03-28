package com.yonyou.iuap.crm.ieop.security.repository.jdbc;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.uap.ieop.security.entity.ExtFunction;
import com.yonyou.uap.ieop.security.entity.RolePermission;
import com.yonyou.uap.ieop.security.repository.base.BaseJdbcDao;

/**
* <p>Title: DefineRoleFuncJdbcDao</p>
* <p>Description: 功能角色关联DAO </p>
* @project：security_shiro 
* @Company: Yonyou
* @author zhangyaoc
* @version 1.0   
* @since JDK 1.7.0_67
* @date 2015年5月29日 下午2:53:28 
*/
@Repository
public class DefineRoleFuncJdbcDao extends  BaseJdbcDao<RolePermission> {
	
	/**
	 * 根据角色ID删除角色功能关系
	* TODO description
	* @author 
	* @date 2016年12月8日
	* @param roleId
	* @throws DAOException
	 */
	public void deleteBatchRoleFunction(String roleId) throws DAOException{
		String sql = "delete from ieop_role_permission where role_id='"+roleId+"'";
		this.getJdbcTemplate().execute(sql);
	}
	
	/**
	 * 查询功能列表信息
	* @author 
	* @date 2016年12月14日
	* @param sql
	* @return
	* @throws AppBusinessException
	* (non-Javadoc)
	 */
	public List<ExtFunction> getFuncListWithCode(String sql)
			throws AppBusinessException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" select * from ieop_function where id in (").append(sql).append(") ");
		List<ExtFunction> result = (List<ExtFunction>) this.getJdbcTemplate().query(sqlBuf.toString(), new Object[] {}, BeanPropertyRowMapper.newInstance(ExtFunction.class));
		if(null!=result){
		    return result;
		}
		return null;
	}
	
	/**
	 * 獲取有權限的功能
	 * add by fanchj1 2016/10/16 含不需要分配的功能节点
	 */
	public List<ExtFunction> findAllFuncByRoleId(String roleId){
//		String sql ="select f.* from ieop_function f,ieop_role_permission p where f.id = p.permission_id and p.role_id=? and p.permission_type='func' and f.isactive='Y'";
		String sql ="(select f.* from ieop_function f,ieop_role_permission p where f.id = p.permission_id and p.role_id = ? and p.permission_type = 'func' and f.isactive = 'Y') union ( select * from ieop_function fc where fc.iscontrol = 'N' and fc.isactive = 'Y' )";
		List<ExtFunction> result = (List<ExtFunction>) this.getJdbcTemplate().query(sql, new Object[] { roleId }, BeanPropertyRowMapper.newInstance(ExtFunction.class));
		return result;
	} 
	
	/**
	 * 獲取該角色對應的功能節點的分配記錄
	 * @param roleId
	 * @param funcId
	 * @return
	 */
	public List<RolePermission> findRolePermActionByRoleIdAndFuncId(String roleId, String funcId){
		String sql ="select p.* from ieop_function_activity a,ieop_role_permission p where a.id = p.permission_id and p.role_id=? and a.func_id=? and p.permission_type='action' and a.isactive='Y'";
		List<RolePermission> result = (List<RolePermission>) this.getJdbcTemplate().query(sql, new Object[] { roleId, funcId }, BeanPropertyRowMapper.newInstance(RolePermission.class));
		return result;
	} 
}
