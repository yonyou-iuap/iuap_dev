package com.yonyou.iuap.crm.user.service.itf;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.uap.ieop.security.entity.UserRole;
import com.yonyou.iuap.crm.user.entity.ExtIeopRoleVO;
import com.yonyou.iuap.crm.user.entity.ExtIeopUserRoleVO;
import com.yonyou.iuap.crm.user.entity.ExtIeopUserVO;

public interface IExtIeopUserService {

	public abstract void setClock(Clock clock);

	public abstract Page<ExtIeopUserVO> getBdUsersBypage(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws BusinessException;

	public abstract Page<ExtIeopUserVO> getBdUsersBypagenum(
			Map<String, Object> searchParams, PageRequest pageRequest,
			String login_name, String name, String corp)
			throws BusinessException;

	public abstract String saveEntity(ExtIeopUserVO entity)
			throws BusinessException;

	public abstract String updateEntity(ExtIeopUserVO entity)
			throws BusinessException;

	public abstract String updateEntitynum(List<ExtIeopUserVO> entity)
			throws BusinessException;

	public abstract void deleteBdUser(ExtIeopUserVO entity)
			throws BusinessException;

	public abstract void deleteBdUserById(String id) throws BusinessException;

	public abstract ExtIeopUserVO getBdUser(String id) throws BusinessException;

	public abstract String stopBdUserlist(List<ExtIeopUserVO> entity)
			throws BusinessException;

	public abstract String stopBdUser(String id) throws BusinessException;

	public abstract String startBdUserlist(List<ExtIeopUserVO> entity)
			throws BusinessException;

	public abstract String updateattr(String id, String userattr)
			throws BusinessException;

	/**
	 * 使用默认密码批量重置用户密码
	 * @param entity
	 * @return
	 * @throws BusinessException
	 */
	public abstract String startUpdatepassword(List<ExtIeopUserVO> entity)
			throws BusinessException;

	public abstract String startBdUser(String id) throws BusinessException;

	public abstract String stopBdUsersd(String id) throws BusinessException;

	public abstract String stopBdUsersdlist(List<ExtIeopUserVO> entity)
			throws BusinessException;

	public abstract String startBdUsersdlist(List<ExtIeopUserVO> entity)
			throws BusinessException;

	public abstract String removelist(List<ExtIeopUserVO> entity)
			throws BusinessException;

	public abstract String startBdUsersd(String id) throws BusinessException;

	public abstract List<ExtIeopUserVO> getBdUserByCodeAndName(String code,
			String name, String corp) throws BusinessException;

	public abstract Page<ExtIeopUserVO> getBdUserByCodeAndNamenum(String code,
			String name, String corp) throws BusinessException;

	public abstract Page<UserRole> queryByUserID(
			Map<String, Object> searchParams, PageRequest pageRequest, String id)
			throws BusinessException;

	public abstract List<ExtIeopUserRoleVO> queryByUserID(String id)
			throws DAOException;

	/**
	 * 获取未分配当前角色的所有用户
	 * @param roleid
	 * @return
	 * @throws DAOException
	 */
	public abstract List<ExtIeopUserVO> queryUnassignUsers(String roleid)
			throws DAOException;

	public abstract List<ExtIeopUserVO> queryUnassignUsername(String username)
			throws DAOException;

	/**
	 * 获取未分配当前角色的所有用户
	 * @param roleid
	 * @return
	 * @throws DAOException
	 */
	public abstract Page<ExtIeopUserVO> queryUnassignUsers2(SQLParameter arg1,
			PageRequest pageRequest) throws DAOException;

	public abstract void updatepassword(String id, String password)
			throws DAOException;

	public abstract List<ExtIeopRoleVO> queryUnassignRoles(String roleid)
			throws DAOException;
	
	public Page<ExtIeopUserVO> getBdUsersRefBypage(Map<String, Object> searchParams, PageRequest pageRequest) throws BusinessException;

	
	/**
	* 销售经理参照
	* @author 
	* @date 2017年1月19日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws BusinessException
	*/
	public Page<ExtIeopUserVO> getSaleManagerRefBypage(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws BusinessException;

}