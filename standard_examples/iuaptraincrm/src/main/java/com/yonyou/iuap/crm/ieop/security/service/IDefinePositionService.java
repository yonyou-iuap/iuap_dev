package com.yonyou.iuap.crm.ieop.security.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.ieop.security.entity.DefinePositionVO;
import com.yonyou.iuap.crm.ieop.security.entity.DefineRolePostitionVO;
import com.yonyou.iuap.crm.ieop.security.entity.DefineRoleSaveVO;
import com.yonyou.iuap.crm.ieop.security.entity.DefineUserPositionVO;
import com.yonyou.uap.ieop.security.entity.RolePermission;

/**
 * 岗位维护
* <p>description：</p>
* @author 
* @created 2016年12月23日 上午9:53:04
* @version 1.0
 */
public interface IDefinePositionService {
	/**
	 * 岗位分页查询
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws AppBusinessException
	 */
	public Page<DefinePositionVO> getPostitionBypages(
			Map<String, Object> searchParams, PageRequest pageRequest) throws AppBusinessException;
	
	/**
	 * 保存
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param vo
	* @return
	* @throws AppBusinessException
	 */
	public String save(DefinePositionVO vo) throws AppBusinessException;
	
	/**
	 * 修改
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param vo
	* @return
	* @throws AppBusinessException
	 */
	public String modify(DefinePositionVO vo) throws AppBusinessException;
	
	/**
	 * 根据岗位ID查询角色
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param positionId
	* @return
	* @throws AppBusinessException
	 */
	public List<DefineRoleSaveVO> getRoleByPositionId(String positionId)  throws AppBusinessException;
	
	/**
	 * 根据岗位Id，查询没有分配的角色
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param positionId
	* @return
	* @throws AppBusinessException
	 */
	public List<DefineRoleSaveVO> queryRoleForAdd(String positionId)  throws AppBusinessException;
	
	/**
	 * 添加角色
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param vo
	* @return
	* @throws AppBusinessException
	 */
	public String addRole(DefinePositionVO vo) throws AppBusinessException;
	
	/**
	 * 取消角色
	* TODO description
	* @author 
	* @date 2016年12月23日
	* @param vo
	* @return
	* @throws AppBusinessException
	 */
	public String deleteRole(DefinePositionVO vo) throws AppBusinessException;
	
	/**
	 * 启用停用岗位
	* TODO description
	* @author 
	* @date 2016年12月25日
	* @param vo
	* @throws AppBusinessException
	 */
	public void openPosition(DefinePositionVO[] vo) throws AppBusinessException;
	
	/**
	 * 根据用户查询岗位信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws AppBusinessException
	 */
	public List<DefinePositionVO> getPositionWithUserId(String userId) throws AppBusinessException;
	
	/**
	 * 根据用户查询未分配的岗位信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws AppBusinessException
	 */
	public List<DefinePositionVO> queryPositionForUserAdd(String userId) throws AppBusinessException;
	
	/**
	 * 批量为用户添加岗位信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param userPositionVOArr
	* @return
	* @throws AppBusinessException
	 */
	public String addUserPosition(DefineUserPositionVO[] userPositionVOArr) throws AppBusinessException;
	
	/**
	 * 批量为用户删除岗位信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param userPositionVOArr
	* @return
	* @throws AppBusinessException
	 */
	public String deleteUserPosition(DefineUserPositionVO[] userPositionVOArr) throws AppBusinessException;
	
	
	/**
	 * 根据角色ID查询关联的岗位信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param roleId
	* @return
	* @throws AppBusinessException
	 */
	public List<DefinePositionVO> getPositionWithRoleId(String roleId) throws AppBusinessException;
	
	/**
	 * 根据角色ID查询未关联的岗位信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param roleId
	* @return
	* @throws AppBusinessException
	 */
	public List<DefinePositionVO> queryPositionForRoleAdd(String roleId) throws AppBusinessException;
	
	/**
	 * 批量添加角色岗位关系信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param rolePositionVOArr
	* @return
	* @throws AppBusinessException
	 */
	public String addRolePosition(DefineRolePostitionVO[] rolePositionVOArr) throws AppBusinessException;
	
	/**
	 * 批量删除角色岗位关系信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param rolePositionVOArr
	* @return
	* @throws AppBusinessException
	 */
	public String deletePositionForRole(DefineRolePostitionVO[] rolePositionVOArr) throws AppBusinessException;
	
	/**
	 * 根据登录用户去授权信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws AppBusinessException
	 */
	public Set<String> getStringPermission(String userId)  throws AppBusinessException;
	
	/**
	 * 根据登录用户去授权角色信息
	* TODO description
	* @author 
	* @date 2016年12月26日
	* @param userId
	* @return
	* @throws AppBusinessException
	 */
	public Set<String> findAllRoleByUserId(String userId)  throws AppBusinessException;
	
	/**
	 * 根据登录用户去授权角色信息
	 * TODO description
	 * @author 
	 * @date 2016年12月26日
	 * @param userId
	 * @return
	 * @throws AppBusinessException
	 */
	public List<RolePermission> findAllFuncByUserId(String userId)  throws AppBusinessException;
}
