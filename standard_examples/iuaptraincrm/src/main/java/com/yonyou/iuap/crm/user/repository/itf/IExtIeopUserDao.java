package com.yonyou.iuap.crm.user.repository.itf;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.uap.ieop.security.entity.UserRole;
import com.yonyou.iuap.crm.user.entity.ExtIeopRoleVO;
import com.yonyou.iuap.crm.user.entity.ExtIeopUserRoleVO;
import com.yonyou.iuap.crm.user.entity.ExtIeopUserVO;

public interface IExtIeopUserDao {

	public ExtIeopUserVO getBdUser(String id) throws DAOException;
	public List<ExtIeopUserVO> getBdUsernum(String where) throws DAOException;

	public List<ExtIeopUserVO> getBdUsers() throws DAOException;
	
	public List<ExtIeopUserVO> getBdUserByCodeAndName(String usercode, String username,String corp) throws DAOException;
	public Page<ExtIeopUserVO> getBdUserByCodeAndNamenum(String usercode, String username,String corp) throws DAOException;

	public void updateBdUser(ExtIeopUserVO entity) throws DAOException;
	public void updateBdUsernum(List<ExtIeopUserVO> entity) throws DAOException;

	public void updateBdUserByCondition(String condition) throws DAOException;
	
	public String saveBdUser(ExtIeopUserVO entity) throws DAOException;
	
	public void deleteBdUser(ExtIeopUserVO entity) throws DAOException;
	
	public Page<ExtIeopUserVO> getBdUsersBypage(String condition, SQLParameter arg1,
			PageRequest pageRequest) throws DAOException;

	public void deleteBdUserById(String id) throws DAOException;

	public void deleteBdUserByIdTS(String id) throws DAOException;

	public String saveBdUserWithPK(ExtIeopUserVO entity) throws DAOException;
	/*停用*/
	public void stopBdUser(String id)  throws DAOException;
	public List<ExtIeopUserRoleVO> queryByUserID(String id)  throws DAOException;

	/*启用*/
	public void startBdUser(String id) throws DAOException;
	Page<ExtIeopUserVO> getBdUsernum(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException;
	void stopBdUsersd(String id) throws DAOException;
	void startBdUsersd(String id) throws DAOException;
	void stopBdUserlist(List<ExtIeopUserVO> entity) throws DAOException;
	void startBdUserlist(List<ExtIeopUserVO> entity) throws DAOException;
	void stopBdUsersdlist(List<ExtIeopUserVO> entity) throws DAOException;
	void startBdUsersdlist(List<ExtIeopUserVO> entity) throws DAOException;
	void removelist(List<ExtIeopUserVO> entity) throws DAOException;

	public Page<ExtIeopUserVO> getBdUsersBypagenum(String condition, SQLParameter arg1,PageRequest pageRequest,String login_name, String name, String corp) throws DAOException;
	public Page<UserRole> queryByUserIDnum(String condition, SQLParameter arg1,PageRequest pageRequest, String id) throws DAOException;
	public void startUpdatepassword(List<ExtIeopUserVO> entity) throws DAOException;
	public void updatepassword(String id, String password)throws DAOException;
	public void updateattr(String id,String userattr)throws DAOException;
	public List<ExtIeopUserVO> findByName(String name)throws DAOException;
	public List<ExtIeopUserVO> findByCode(String code)throws DAOException;

	public List<ExtIeopRoleVO> getBdUserrole(String where) throws DAOException;
	
	/**
	* 销售经理参照
	* @author 
	* @date 2017年1月19日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	*/
	public  Page<ExtIeopUserVO> getSaleManagerBypage(String condition,
			SQLParameter sqlParameter, PageRequest pageRequest)
			throws DAOException;
}
