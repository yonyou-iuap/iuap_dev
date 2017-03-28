package com.yonyou.iuap.crm.ieop.security.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.ieop.security.entity.DefinePositionVO;
import com.yonyou.iuap.crm.ieop.security.entity.DefineRolePostitionVO;
import com.yonyou.iuap.crm.ieop.security.entity.DefineRoleSaveVO;
import com.yonyou.iuap.crm.ieop.security.entity.DefineUserPositionVO;
import com.yonyou.uap.ieop.security.entity.RolePermission;

/**
 * 岗位数据库操作接口
* <p>description：</p>
* @author 
* @created 2016年12月23日 上午9:28:20
* @version 1.0
 */
public interface IDefinePositionDao {
	/**
	 * 岗位分页查询
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	 */
	public Page<DefinePositionVO> getPositionBypages(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException;
	
	/**
	 * 保存
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param vo
	* @return
	* @throws DAOException
	 */
	public String save(DefinePositionVO vo) throws DAOException;
	
	/**
	 * 修改
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param vo
	* @throws DAOException
	 */
	public void modify(DefinePositionVO vo) throws DAOException;
	
	/**
	 * 批量修改
	* TODO description
	* @author 
	* @date 2016年12月25日
	* @param vo
	* @throws DAOException
	 */
	public void modifyBatch(List<DefinePositionVO> vo) throws DAOException;
	
	/**
	 * 根据条件查询岗位信息
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param condition
	* @return
	* @throws DAOException
	 */
	public List<DefinePositionVO> queryWithCondition(String condition) throws DAOException;
	
	/**
	 * 根据岗位ID查询角色
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param positionId
	* @return
	* @throws DAOException
	 */
	public List<DefineRoleSaveVO> getRoleByPositionId(String positionId) throws DAOException;
	
	/**
	 * 根据岗位ID，查询没有分配的角色
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param sql
	* @return
	* @throws DAOException
	 */
	public List<DefineRoleSaveVO> queryRoleByPositionId(String positionId) throws DAOException;
	
	/**
	 * 批量增加角色
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param rolePositionList
	* @throws DAOException
	 */
	public void saveRoleBatch(List<DefineRolePostitionVO> rolePositionList) throws DAOException;
	
	/**
	 * 批量删除角色
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param rolePositionList
	* @throws DAOException
	 */
	public void deleteRoleBatch(List<DefineRolePostitionVO> rolePositionList) throws DAOException;
	
	/**
	 * 根据岗位ID，查询角色岗位关系信息
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param positionId
	* @return
	* @throws DAOException
	 */
	public List<DefineRolePostitionVO> quereRoleWithPositionId(String positionId) throws DAOException;
	
	
	/**
	 * 根据用户查询岗位信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws AppBusinessException
	 */
	public List<DefinePositionVO> getPositionWithUserId(String userId) throws DAOException;
	
	/**
	 * 根据用户查询未分配的岗位信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws AppBusinessException
	 */
	public List<DefinePositionVO> queryPositionForUserAdd(String userId) throws DAOException;
	
	/**
	 * 批量为用户添加岗位信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param userPositionVOArr
	* @return
	* @throws AppBusinessException
	 */
	public String addUserPosition(List<DefineUserPositionVO> volist) throws DAOException;
	
	/**
	 * 批量为用户删除岗位信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param userPositionVOArr
	* @return
	* @throws AppBusinessException
	 */
	public String deleteUserPosition(List<DefineUserPositionVO> volist) throws DAOException;
	
	/**
	 * 根据用户Id查询岗位信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws DAOException
	 */
	public List<DefineUserPositionVO> quereyPositionWithUserId(String userId) throws DAOException;
	

	/**
	 * 根据角色ID查询关联的岗位信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param roleId
	* @return
	* @throws AppBusinessException
	 */
	public List<DefinePositionVO> getPositionWithRoleId(String roleId) throws DAOException;
	
	/**
	 * 根据角色ID查询未关联的岗位信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param roleId
	* @return
	* @throws AppBusinessException
	 */
	public List<DefinePositionVO> queryPositionForRoleAdd(String roleId) throws DAOException;
	
	/**
	 * 批量添加角色岗位关系信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param volist
	* @return
	* @throws AppBusinessException
	 */
	public String addRolePosition(List<DefineRolePostitionVO> volist) throws DAOException;
	
	/**
	 * 批量删除角色岗位关系信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param volist
	* @return
	* @throws AppBusinessException
	 */
	public String deleteRolePosition(List<DefineRolePostitionVO> volist) throws DAOException;
	
	/**
	 * 根据角色ID查询角色与岗位的关系信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param roleId
	* @return
	* @throws DAOException
	 */
	public List<DefineRolePostitionVO> quereyPositionWithRoleId(String roleId) throws DAOException;
	
	/**
	 * 根据登录用户获取授权信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws DAOException
	 */
	public Set<String> getStringPermission(String userId)  throws DAOException;
	
	/**
	 * 根据登录用户获取角色授权信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws DAOException
	 */
	public Set<String> findAllRoleByUserId(String userId)  throws DAOException;
	
	/**
	 * 根据登录用户获取功能授权信息
	 * TODO description
	 * @author 
	 * @date 2016年12月26日
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public List<RolePermission> findAllFuncByUserId(String userId)  throws DAOException;
}
