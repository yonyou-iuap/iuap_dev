package com.yonyou.iuap.crm.ieop.security.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.crm.common.base.repository.AppBaseDao;
import com.yonyou.iuap.crm.common.utils.DictCode;
import com.yonyou.iuap.crm.ieop.security.entity.DefinePositionVO;
import com.yonyou.iuap.crm.ieop.security.entity.DefineRolePostitionVO;
import com.yonyou.iuap.crm.ieop.security.entity.DefineRoleSaveVO;
import com.yonyou.iuap.crm.ieop.security.entity.DefineUserPositionVO;
import com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.processor.BeanListProcessor;
import com.yonyou.iuap.persistence.jdbc.framework.processor.ColumnListProcessor;
import com.yonyou.iuap.persistence.jdbc.framework.processor.MapListProcessor;
import com.yonyou.uap.ieop.security.entity.RolePermission;
/**
 * 岗位数据库操作类
* <p>description：</p>
* @author 
* @created 2016年12月23日 上午9:27:59
* @version 1.0
 */
@Repository
public class DefinePositionDaoImpl implements IDefinePositionDao {
	@Autowired
	private AppBaseDao appBaseDao;
	@Autowired
	@Qualifier("baseDAO") private BaseDAO baseDao;

	/**
	 * 分页查询
	* @author 
	* @date 2016年12月23日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#getCountrysubsidyBypages(java.lang.String, com.yonyou.iuap.persistence.jdbc.framework.SQLParameter, org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<DefinePositionVO> getPositionBypages(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException {
		StringBuffer sqlBuffer = new StringBuffer("");
		
		sqlBuffer.append(" select a.* from ieop_position a where 1=1");
		if(condition!=null && !condition.isEmpty()){
			sqlBuffer.append(condition);
		}
		sqlBuffer.append(" order by a.ts desc");
		return baseDao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, DefinePositionVO.class);
	}

	/**
	 * 保存
	* @author 
	* @date 2016年12月23日
	* @param vo
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#save(com.yonyou.iuap.crm.ieop.security.entity.DefinePositionVO)
	 */
	@Override
	public String save(DefinePositionVO vo) throws DAOException {
		return appBaseDao.saveWithPK(vo);
	}

	/**
	 * 修改
	* @author 
	* @date 2016年12月23日
	* @param vo
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#modify(com.yonyou.iuap.crm.ieop.security.entity.DefinePositionVO)
	 */
	@Override
	public void modify(DefinePositionVO vo) throws DAOException {
		appBaseDao.update(vo);
	}
	
	/**
	 * 批量修改
	* @author 
	* @date 2016年12月25日
	* @param vo
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#modifyBatch(java.util.List)
	 */
	@Override
	public void modifyBatch(List<DefinePositionVO> vo) throws DAOException {
		appBaseDao.batchUpdate(vo);
	}

	/**
	 * 根据条件查询岗位信息
	* @author 
	* @date 2016年12月23日
	* @param condition
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#queryWithCondition(java.lang.String)
	 */
	@Override
	public List<DefinePositionVO> queryWithCondition(String condition)
			throws DAOException {
		StringBuffer sqlBuffer = new StringBuffer("");
		
		sqlBuffer.append(" select a.* from ieop_position a where 1=1");
		if(condition!=null && !condition.isEmpty()){
			sqlBuffer.append(condition);
		}
		sqlBuffer.append(" order by a.ts desc");
		
		List<DefinePositionVO> positionList = baseDao.queryForObject(sqlBuffer.toString(), new BeanListProcessor(DefinePositionVO.class));
		
		return positionList;
	}

	/**
	 * 根据岗位ID查询角色
	* @author 
	* @date 2016年12月23日
	* @param positionId
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#getRoleByPositionId(java.lang.String)
	 */
	@Override
	public List<DefineRoleSaveVO> getRoleByPositionId(String positionId)
			throws DAOException {
		StringBuffer sqlBuffer = new StringBuffer("");
		
		sqlBuffer.append(" select a.* from ieop_role a,ieop_role_position b where a.id=b.role_id ");
		if(positionId!=null && !positionId.isEmpty()){
			sqlBuffer.append(" and b.position_id = '").append(positionId).append("'");
		}
		sqlBuffer.append(" order by a.create_date desc");
		
		List<DefineRoleSaveVO> positionList = baseDao.queryForObject(sqlBuffer.toString(), new BeanListProcessor(DefineRoleSaveVO.class));
		
		return positionList;
	}

	/**
	 * 根据岗位ID，查询没有分配的角色
	* @author 
	* @date 2016年12月23日
	* @param positionId
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#queryRoleByPositionId(java.lang.String)
	 */
	@Override
	public List<DefineRoleSaveVO> queryRoleByPositionId(String positionId)
			throws DAOException {
		StringBuffer sqlBuffer = new StringBuffer("");
		
		sqlBuffer.append(" select a.* from ieop_role a where id not in (select b.role_id from ieop_role_position b where ");
		if(positionId!=null && !positionId.isEmpty()){
			sqlBuffer.append(" b.position_id = '").append(positionId).append("'");
		}
		sqlBuffer.append(") order by a.create_date desc");
		
		List<DefineRoleSaveVO> positionList = baseDao.queryForObject(sqlBuffer.toString(), new BeanListProcessor(DefineRoleSaveVO.class));
		
		return positionList;
	}
	
	/**
	 * 批量增加角色
	* @author 
	* @date 2016年12月23日
	* @param rolePositionList
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#saveRoleBatch(java.util.List)
	 */
	@Override
	public void saveRoleBatch(List<DefineRolePostitionVO> rolePositionList)
			throws DAOException {
		appBaseDao.batchSaveWithPK(rolePositionList);
	}
	
	/**
	 * 批量删除角色
	* @author 
	* @date 2016年12月23日
	* @param rolePositionList
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#deleteRoleBatch(java.util.List)
	 */
	@Override
	public void deleteRoleBatch(List<DefineRolePostitionVO> rolePositionList)
			throws DAOException {
		appBaseDao.batchDelete(rolePositionList);
	}
	
	/**
	 * 根据岗位ID，查询角色岗位关系
	* @author 
	* @date 2016年12月23日
	* @param positionId
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#quereRoleWithPositionId(java.lang.String)
	 */
	@Override
	public List<DefineRolePostitionVO> quereRoleWithPositionId(String positionId)
			throws DAOException {
		StringBuffer sqlBuffer = new StringBuffer("");
		
		sqlBuffer.append(" select a.* from ieop_role_position a where ");
		if(positionId!=null && !positionId.isEmpty()){
			sqlBuffer.append(" a.position_id = '").append(positionId).append("'");
		}
		
		List<DefineRolePostitionVO> positionList = baseDao.queryForObject(sqlBuffer.toString(), new BeanListProcessor(DefineRolePostitionVO.class));
		
		return positionList;
	}

	/**
	 * 根据用户ID查询岗位信息
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#getPositionWithUserId(java.lang.String)
	 */
	@Override
	public List<DefinePositionVO> getPositionWithUserId(String userId)
			throws DAOException {
		StringBuffer sqlBuffer = new StringBuffer("");
		
		sqlBuffer.append(" select a.* from ieop_position a,ieop_user_position b where a.id=b.position_id ");
		if(userId!=null && !userId.isEmpty()){
			sqlBuffer.append(" and b.user_id = '").append(userId).append("'");
		}
		
		List<DefinePositionVO> positionList = baseDao.queryForObject(sqlBuffer.toString(), new BeanListProcessor(DefinePositionVO.class));
		
		return positionList;
	}

	/**
	 * 根据用户ID查询未添加的岗位信息
	* @author name
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#queryPositionForUserAdd(java.lang.String)
	 */
	@Override
	public List<DefinePositionVO> queryPositionForUserAdd(String userId)
			throws DAOException {
		StringBuffer sqlBuffer = new StringBuffer("");
		
		sqlBuffer.append(" select a.* from ieop_position a where position_status='").append(DictCode.ALREADY_START_STATUS).append("' ");
		sqlBuffer.append("  and a.id not in (select b.position_id from ieop_user_position b where 1=1 ");
		if(userId!=null && !userId.isEmpty()){
			sqlBuffer.append(" and b.user_id = '").append(userId).append("'");
		}
		sqlBuffer.append(")");
		List<DefinePositionVO> positionList = baseDao.queryForObject(sqlBuffer.toString(), new BeanListProcessor(DefinePositionVO.class));
		
		return positionList;
	}

	/**
	 * 为用户批量增加岗位信息
	* @author 
	* @date 2016年12月26日
	* @param volist
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#addUserPosition(java.util.List)
	 */
	@Override
	public String addUserPosition(List<DefineUserPositionVO> volist)
			throws DAOException {
		appBaseDao.batchSave(volist);
		return null;
	}

	/**
	 * 为用户批量删除岗位信息
	* @author 
	* @date 2016年12月26日
	* @param volist
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#deleteUserPosition(java.util.List)
	 */
	@Override
	public String deleteUserPosition(List<DefineUserPositionVO> volist)
			throws DAOException {
		appBaseDao.batchDelete(volist);
		return null;
	}
	
	/**
	 * 根据用户Id查询岗位信息
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#quereyPositionWithUserId(java.lang.String)
	 */
	@Override
	public List<DefineUserPositionVO> quereyPositionWithUserId(String userId)
			throws DAOException {
		StringBuffer sqlBuffer = new StringBuffer("");
		
		sqlBuffer.append(" select a.* from ieop_user_position a where 1=1 ");
		if(userId!=null && !userId.isEmpty()){
			sqlBuffer.append(" and a.user_id = '").append(userId).append("'");
		}
		List<DefineUserPositionVO> positionList = baseDao.queryForObject(sqlBuffer.toString(), new BeanListProcessor(DefineUserPositionVO.class));
		
		return positionList;
	}

	/**
	 * 根据角色ID查询关联的岗位信息
	* @author 
	* @date 2016年12月26日
	* @param roleId
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#getPositionWithRoleId(java.lang.String)
	 */
	@Override
	public List<DefinePositionVO> getPositionWithRoleId(String roleId)
			throws DAOException {
		StringBuffer sqlBuffer = new StringBuffer("");
		
		sqlBuffer.append(" select a.* from ieop_position a,ieop_role_position b where a.id=b.position_id ");
		if(roleId!=null && !roleId.isEmpty()){
			sqlBuffer.append(" and b.role_id = '").append(roleId).append("'");
		}
		
		List<DefinePositionVO> positionList = baseDao.queryForObject(sqlBuffer.toString(), new BeanListProcessor(DefinePositionVO.class));
		
		return positionList;
	}

	/**
	 * 根据角色ID查询未关联的岗位信息
	* @author 
	* @date 2016年12月26日
	* @param roleId
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#queryPositionForRoleAdd(java.lang.String)
	 */
	@Override
	public List<DefinePositionVO> queryPositionForRoleAdd(String roleId)
			throws DAOException {
		StringBuffer sqlBuffer = new StringBuffer("");
		
		sqlBuffer.append(" select a.* from ieop_position a where position_status='").append(DictCode.ALREADY_START_STATUS).append("' ");
		sqlBuffer.append("  and a.id not in (select b.position_id from ieop_role_position b where 1=1 ");
		if(roleId!=null && !roleId.isEmpty()){
			sqlBuffer.append(" and b.role_id = '").append(roleId).append("'");
		}
		sqlBuffer.append(")");
		List<DefinePositionVO> positionList = baseDao.queryForObject(sqlBuffer.toString(), new BeanListProcessor(DefinePositionVO.class));
		
		return positionList;
	}

	/**
	 * 批量增加角色岗位关系
	* @author 
	* @date 2016年12月26日
	* @param rolePositionVOArr
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#addRolePosition(com.yonyou.iuap.crm.ieop.security.entity.DefineRolePostitionVO[])
	 */
	@Override
	public String addRolePosition(List<DefineRolePostitionVO> volist)
			throws DAOException {
		appBaseDao.batchSave(volist);
		return null;
	}

	/**
	 * 批量删除角色岗位关系
	* @author 
	* @date 2016年12月26日
	* @param rolePositionVOArr
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#deletePositionForRole(com.yonyou.iuap.crm.ieop.security.entity.DefineRolePostitionVO[])
	 */
	@Override
	public String deleteRolePosition(List<DefineRolePostitionVO> volist)
			throws DAOException {
		appBaseDao.batchDelete(volist);
		return null;
	}
	
	/**
	 * 根据角色ID查询角色和岗位的关系
	* @author 
	* @date 2016年12月26日
	* @param roleId
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#quereyPositionWithRoleId(java.lang.String)
	 */
	@Override
	public List<DefineRolePostitionVO> quereyPositionWithRoleId(String roleId)
			throws DAOException {
		StringBuffer sqlBuffer = new StringBuffer("");
		
		sqlBuffer.append(" select a.* from ieop_role_position a where 1=1 ");
		if(roleId!=null && !roleId.isEmpty()){
			sqlBuffer.append(" and a.role_id = '").append(roleId).append("'");
		}
		List<DefineRolePostitionVO> positionList = baseDao.queryForObject(sqlBuffer.toString(), new BeanListProcessor(DefineRolePostitionVO.class));
		
		return positionList;
	}
	
	/**
	 * 根据登录用户获取授权信息
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#getStringPermission(java.lang.String)
	 */
	@Override
	public Set<String> getStringPermission(String userId) throws DAOException {
		Set<String> permissionSet = new HashSet<String>();
		
		StringBuffer sqlBuffer = new StringBuffer("");
		
		sqlBuffer.append(" select c.permission_code from ieop_user_position a,ieop_role_position b,ieop_role_permission c ");
		sqlBuffer.append(" where a.position_id=b.position_id and b.role_id=c.role_id ");
		if(userId!=null && !userId.isEmpty()){
			sqlBuffer.append(" and a.user_id = '").append(userId).append("'");
		}
		List<Object> positionList = (List<Object>)baseDao.queryForObject(sqlBuffer.toString(), new ColumnListProcessor());
		if(null != positionList && positionList.size()>0){
			for(Object funcCode : positionList){
				if(null != funcCode && funcCode.toString().trim().length()>0){
					permissionSet.add(funcCode.toString().trim());
				}
			}
		}
		return permissionSet;
	}
	
	/**
	 * 根据登录用户获取角色授权信息
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#getStringPermission(java.lang.String)
	 */
	@Override
	public Set<String> findAllRoleByUserId(String userId) throws DAOException {
		Set<String> roleSet = new HashSet<String>();
		
		StringBuffer sqlBuffer = new StringBuffer("");
		
		sqlBuffer.append(" select b.role_code from ieop_user_position a, ieop_role_position b ");
		sqlBuffer.append(" where a.position_id = b.position_id ");
		if(userId!=null && !userId.isEmpty()){
			sqlBuffer.append(" and a.user_id = '").append(userId).append("'");
		}
		List<Object> roleList = (List<Object>)baseDao.queryForObject(sqlBuffer.toString(), new ColumnListProcessor());
		if(null != roleList && roleList.size()>0){
			for(Object roleCode : roleList){
				if(null != roleCode && roleCode.toString().trim().length()>0){
					roleSet.add(roleCode.toString().trim());
				}
			}
		}
		return roleSet;
	}

	/**
	 * 根据登录用户获取资源授权信息
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.ieop.security.repository.IDefinePositionDao#getStringPermission(java.lang.String)
	 */
	@Override
	public List<RolePermission> findAllFuncByUserId(String userId)
			throws DAOException {
		StringBuffer sqlBuffer = new StringBuffer("");
		sqlBuffer.append(" select c.* from ieop_user_position a,ieop_role_position b,ieop_role_permission c ");
		sqlBuffer.append(" where a.position_id=b.position_id and b.role_id=c.role_id ");
		if(userId!=null && !userId.isEmpty()){
			sqlBuffer.append(" and a.user_id = '").append(userId).append("'");
		}
		List<RolePermission> permissionList = baseDao.queryForObject(sqlBuffer.toString(), new MapListProcessor(){
			 public Object processResultSet(ResultSet rs) throws SQLException{
				 List<RolePermission> list = new ArrayList<RolePermission>();
				 while(rs.next()){
					 RolePermission rp = new RolePermission();
					 rp.setId(rs.getString("id"));
					 rp.setPermissionCode(rs.getString("permission_code"));
					 rp.setPermissionId(rs.getString("permission_id"));
					 rp.setPermissionType(rs.getString("permission_type"));
					 rp.setRoleCode(rs.getString("role_code"));
					 rp.setRoleId(rs.getString("role_id"));
					 rp.setSysId(rs.getString("sys_id"));
					 rp.setTenantId(rs.getString("tenant_id"));
					 list.add(rp);
				 }
				 return list;
			 }
		});
		return permissionList;
	}
}
