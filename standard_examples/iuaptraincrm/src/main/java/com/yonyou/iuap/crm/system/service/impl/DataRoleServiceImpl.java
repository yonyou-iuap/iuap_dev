package com.yonyou.iuap.crm.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.common.utils.AppInvocationInfoProxy;
import com.yonyou.iuap.crm.common.utils.DictCode;
import com.yonyou.iuap.crm.ieop.security.entity.DefineRoleSaveVO;
import com.yonyou.iuap.crm.system.service.itf.IDataRoleService;

/**
 * 数据权限过滤条件生成器servcie
* <p>description：</p>
* @author 
* @created 2017年1月16日 下午8:33:27
* @version 1.0
 */
@Service
public class DataRoleServiceImpl implements IDataRoleService {
	@Autowired
    @Qualifier("baseDAO") public BaseDAO dao;
	@Autowired
	public AppBaseDao appdao;
	
	/**
	 * 根据用户ID获取数据权限过滤SQL条件
	* @author 
	* @date 2017年1月16日
	* @param userid
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.system.service.itf.IDataRoleService#getRoleSql(java.lang.String, java.lang.String)
	 */
	@Override
	public String getRoleSql(String userid) throws DAOException {
		return getRoleSql(userid,"pk_org","pk_dept","creator");
	}
	
	/**
	 * 根据用户ID和业务表的组织字段，获取数据权限过滤SQL条件
	* @author 
	* @date 2017年1月16日
	* @param userid
	* @param deptColumn
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.system.service.itf.IDataRoleService#getRoleSql(java.lang.String, java.lang.String)
	 */
	@Override
	public String getRoleSql(String userid,String deptColumn) throws DAOException {
		return getRoleSql(userid,"pk_org",deptColumn,"creator");
	}
	
	/**
	 * 根据业务表的组织字段名、部门字段名和用户字段名，生成数据过滤条件
	* @author 
	* @date 2017年1月17日
	* @param orgColumn
	* @param deptColumn
	* @param createrColumn
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.system.service.itf.IDataRoleService#getRoleSql(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getRoleSql(String orgColumn,String deptColumn, String createrColumn) throws DAOException {
		return getRoleSql(AppInvocationInfoProxy.getPk_User(),orgColumn,deptColumn,createrColumn);
	}


	/**
	 * 根据用户ID、业务表的组织字段名、部门字段名和用户字段名，生成数据过滤条件
	* @author name
	* @date 2017年1月16日
	* @param userid
	* @param orgColumn
	* @param deptColumn
	* @param createrColumn
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.system.service.itf.IDataRoleService#getRoleSql(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getRoleSql(String userid, String orgColumn,
			String deptColumn, String createrColumn) throws DAOException {
//		String sql = "select max(a.data_role) as data_role from ieop_role a, ieop_role_user b where a.id=b.role_id and b.user_id='"+userid+"'";
		String sql = "SELECT max(c.data_role) as data_role FROM ieop_user_position a, ieop_role_position b, ieop_role c WHERE a.user_id = '"+userid+"' AND a.position_id = b.position_id AND c.id = b.role_id";
		List<DefineRoleSaveVO> list = dao.queryByClause(DefineRoleSaveVO.class, sql);
		String dataRole = list.get(0).getDataRole();
		String pk_org = AppInvocationInfoProxy.getPk_Corp();//当前组织
		String pk_dept = AppInvocationInfoProxy.getPk_Dept();//当前部门
		String roleSql = "";
		//根据用户最大权限判断
		if(null!=dataRole){
			switch (dataRole) {
			case DictCode.DATA_ROLE_01://仅本人
				roleSql = " and "+createrColumn+" = '"+userid+"' ";
				break;
			case DictCode.DATA_ROLE_02://本岗位
				roleSql = " and 1=1 ";
				break;
			case DictCode.DATA_ROLE_03://本部门
				if(!deptColumn.equals("")){
					roleSql = " and "+deptColumn+"='"+pk_dept+"' ";
				}
				break;
			case DictCode.DATA_ROLE_04://本组织
				roleSql = " and "+orgColumn+" = '"+pk_org+"' ";
				break;
			case DictCode.DATA_ROLE_05://本组织及以下组织
				roleSql = " and 1=1 ";
				break;
			case DictCode.DATA_ROLE_06://全部
				roleSql = " and 1=1 ";
				break;
			}
		}
		return roleSql;
	}

}
