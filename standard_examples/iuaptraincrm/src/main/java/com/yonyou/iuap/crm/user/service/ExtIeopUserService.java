package com.yonyou.iuap.crm.user.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Clock;
import org.springside.modules.utils.Encodes;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.uap.ieop.security.entity.UserRole;
import com.yonyou.iuap.crm.common.utils.AppTools;
import com.yonyou.iuap.crm.user.entity.ExtIeopRoleVO;
import com.yonyou.iuap.crm.user.entity.ExtIeopUserRoleVO;
import com.yonyou.iuap.crm.user.entity.ExtIeopUserVO;
import com.yonyou.iuap.crm.user.repository.itf.IExtIeopUserDao;
import com.yonyou.iuap.crm.user.service.itf.IExtIeopUserService;
//用于标注业务层组件
@Service
public class ExtIeopUserService implements IExtIeopUserService {

	private static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	@Autowired
	private IExtIeopUserDao dao;
	private Clock clock = Clock.DEFAULT;
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#setClock(org.springside.modules.utils.Clock)
	 */
	@Override
	public void setClock(Clock clock) {
        this.clock = clock;
    }
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#getBdUsersBypage(java.util.Map, org.springframework.data.domain.PageRequest)
	 */
	@Override
	public Page<ExtIeopUserVO> getBdUsersBypage(Map<String, Object> searchParams, PageRequest pageRequest) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		for(Map.Entry<String, Object> entry : searchParams.entrySet()){
			String[] keySplit = entry.getKey().split("_");
			if(keySplit.length==2){
				String columnName = keySplit[1];
				if(columnName.equals("loginName")){
					columnName = "ieop_user.login_name";
				}else if(columnName.equals("name")){
					columnName = "ieop_user.name";
				}else if(columnName.equals("pkCorp")){
					columnName = "bd_corp.unitname";
				}
				else if(columnName.equals("pkdept")){
					columnName = "ieop_user.pk_dept";
				}
				else{
					
				}
				String comparator = keySplit[0];
				Object value = entry.getValue();
				if(value != null && StringUtils.isNotBlank(value.toString())){
					condition.append(" and ").append(columnName).append(" ").append(comparator).append("?");
					value = "like".equalsIgnoreCase(comparator)? "%"+value + "%" : value;
					sqlParameter.addParam(value);
				}
			}
		}
		
		return dao.getBdUsersBypage(condition.toString(), sqlParameter, pageRequest);
	}
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#getBdUsersBypagenum(java.util.Map, org.springframework.data.domain.PageRequest, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Page<ExtIeopUserVO> getBdUsersBypagenum(Map<String, Object> searchParams, PageRequest pageRequest,String login_name,String name,String corp) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		for(Map.Entry<String, Object> entry : searchParams.entrySet()){
			String[] keySplit = entry.getKey().split("_");
			if(keySplit.length==2){
				String columnName = keySplit[1];
				String comparator = keySplit[0];
				Object value = entry.getValue();
				if(value != null && StringUtils.isNotBlank(value.toString())){
					condition.append(" and ").append(columnName).append(" ").append(comparator).append("?");
					value = "like".equalsIgnoreCase(comparator)?"%"+value + "%" : value;
					sqlParameter.addParam(value);
				}
			}
		}
		
		return dao.getBdUsersBypagenum(condition.toString(), sqlParameter, pageRequest,login_name,name,corp);
	}
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#saveEntity(com.yonyou.iuap.crm.user.entity.IeopUserVO)
	 */
	@Override
	@Transactional
	public String saveEntity(ExtIeopUserVO entity) throws BusinessException{
			String pk = AppTools.generatePK();
			entity.setId(pk);
			entity.setDr(0);
//			entity.setLocked(1);
//			entity.setPsnseal(1);
			String password = "defaultpwd";
			byte[] salt = Digests.generateSalt(SALT_SIZE);
			entity.setSalt(Encodes.encodeHex(salt));
			byte[] hashPassword = Digests.sha1(password.getBytes(), salt, HASH_INTERATIONS);
			entity.setPassword(Encodes.encodeHex(hashPassword));
			entity.setCreator(InvocationInfoProxy.getUserid());
			entity.setCreationtime(clock.getCurrentDate());
			return dao.saveBdUser(entity);
	}
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#updateEntity(com.yonyou.iuap.crm.user.entity.IeopUserVO)
	 */
	@Override
	@Transactional
	public String updateEntity(ExtIeopUserVO entity) throws BusinessException{
		
			dao.updateBdUser(entity);
//			psnService.updateuser(entity.getId(),entity.getPkPsndoc());
			return "success";
//		}
	}
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#updateEntitynum(java.util.List)
	 */
	@Override
	public String updateEntitynum( List<ExtIeopUserVO> entity) throws BusinessException{
		dao.updateBdUsernum(entity);
			return "success";
	}

	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#deleteBdUser(com.yonyou.iuap.crm.user.entity.IeopUserVO)
	 */
	@Override
	@Transactional
	public void deleteBdUser(ExtIeopUserVO entity) throws BusinessException{
		dao.deleteBdUserByIdTS(entity.getId());
	}
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#deleteBdUserById(java.lang.String)
	 */
	@Override
	@Transactional
	public void deleteBdUserById(String id) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		dao.deleteBdUserByIdTS(id);
	}
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#getBdUser(java.lang.String)
	 */
	@Override
	public ExtIeopUserVO getBdUser(String id) throws BusinessException{
		return dao.getBdUser(id);
	}
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#stopBdUserlist(java.util.List)
	 */
	@Override
	@Transactional
	public String stopBdUserlist( List<ExtIeopUserVO> entity) throws BusinessException{
		dao.stopBdUserlist(entity);
			return "success";
	}
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#stopBdUser(java.lang.String)
	 */
	@Override
	@Transactional
	public String stopBdUser(String id) throws BusinessException{
		dao.stopBdUser(id);
		return "success";
	}	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#startBdUserlist(java.util.List)
	 */
	@Override
	@Transactional
	public String startBdUserlist( List<ExtIeopUserVO> entity) throws BusinessException{
		dao.startBdUserlist(entity);
			return "success";
	}
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#updateattr(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public String updateattr( String id,String userattr) throws BusinessException{
		dao.updateattr(id,userattr);
			return "success";
	}
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#startUpdatepassword(java.util.List)
	 */
	@Override
	@Transactional
	public String startUpdatepassword( List<ExtIeopUserVO> entity) throws BusinessException{
		String password = "defaultpassword";
			for(ExtIeopUserVO num: entity ){
				byte[] salt = Digests.generateSalt(SALT_SIZE);
				num.setSalt(Encodes.encodeHex(salt));
				byte[] hashPassword = Digests.sha1(password.getBytes(), salt, HASH_INTERATIONS);
				num.setPassword(Encodes.encodeHex(hashPassword));
			}
		dao.startUpdatepassword(entity);

			return password;
	}
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#startBdUser(java.lang.String)
	 */
	@Override
	@Transactional
	public String startBdUser(String id) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		dao.startBdUser(id);
		return "success";
	}

	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#stopBdUsersd(java.lang.String)
	 */
	@Override
	@Transactional
	public String stopBdUsersd(String id) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		dao.stopBdUsersd(id);
		return "success";
	}	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#stopBdUsersdlist(java.util.List)
	 */
	@Override
	@Transactional
	public String stopBdUsersdlist( List<ExtIeopUserVO> entity) throws BusinessException{
		dao.stopBdUsersdlist(entity);
			return "success";
	}
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#startBdUsersdlist(java.util.List)
	 */
	@Override
	@Transactional
	public String startBdUsersdlist( List<ExtIeopUserVO> entity) throws BusinessException{
		dao.startBdUsersdlist(entity);
			return "success";
	}
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#removelist(java.util.List)
	 */
	@Override
	@Transactional
	public String removelist( List<ExtIeopUserVO> entity) throws BusinessException{
		dao.removelist(entity);
			return "success";
	}
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#startBdUsersd(java.lang.String)
	 */
	@Override
	@Transactional
	public String startBdUsersd(String id) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		dao.startBdUsersd(id);
		return "success";
	}
	
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#getBdUserByCodeAndName(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<ExtIeopUserVO> getBdUserByCodeAndName(String code,String name,String corp) throws BusinessException{
		return   dao.getBdUserByCodeAndName(code,name,corp);
	}
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#getBdUserByCodeAndNamenum(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Page<ExtIeopUserVO> getBdUserByCodeAndNamenum(String code,String name,String corp) throws BusinessException{
		return   dao.getBdUserByCodeAndNamenum(code,name,corp);
	}
	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#queryByUserID(java.util.Map, org.springframework.data.domain.PageRequest, java.lang.String)
	 */
	@Override
	public Page<UserRole> queryByUserID(Map<String, Object> searchParams, PageRequest pageRequest,String id) throws BusinessException{
		/**
		 * 添加业务逻辑
		 */
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		for(Map.Entry<String, Object> entry : searchParams.entrySet()){
			String[] keySplit = entry.getKey().split("_");
			if(keySplit.length==2){
				String columnName = keySplit[1];
				String comparator = keySplit[0];
				Object value = entry.getValue();
				if(value != null && StringUtils.isNotBlank(value.toString())){
					condition.append(" and ").append(columnName).append(" ").append(comparator).append("?");
					value = "like".equalsIgnoreCase(comparator)?value + "%" : value;
					sqlParameter.addParam(value);
				}
			}
		}
		
		return dao.queryByUserIDnum(condition.toString(), sqlParameter, pageRequest,id);
	}
	 /* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#queryByUserID(java.lang.String)
	 */
	@Override
	public List<ExtIeopUserRoleVO> queryByUserID(String id)  throws DAOException {
	 // TODO 自动生成的方法存根
	 return dao.queryByUserID(id);
	 }
	 
	 /* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#queryUnassignUsers(java.lang.String)
	 */
	 @Override
	public List<ExtIeopUserVO> queryUnassignUsers(String roleid)  throws DAOException {
		 String whereStr = " id not in (select user_id from ieop_role_user where role_id = '"+roleid+"') ";
		 return dao.getBdUsernum(whereStr);
	 }
	 /* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#queryUnassignUsername(java.lang.String)
	 */
	@Override
	public List<ExtIeopUserVO> queryUnassignUsername(String username)  throws DAOException {
		 String whereStr = " login_name = '"+username+"' ";
		 return dao.getBdUsernum(whereStr);
	 }
	 /* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#queryUnassignUsers2(com.yonyou.iuap.persistence.jdbc.framework.SQLParameter, org.springframework.data.domain.PageRequest)
	 */
	 @Override
	public Page<ExtIeopUserVO> queryUnassignUsers2(SQLParameter arg1,PageRequest pageRequest)  throws DAOException {
//		 String whereStr = " and pk_usertype not in('03','04') and id not in (select user_id from ieop_role_user where role_id =?) ";
		 String whereStr = " and id not in (select user_id from ieop_role_user where role_id =?) ";
//		 return dao.getBdUsernum(whereStr);
		 return dao.getBdUsersBypage(whereStr, arg1, pageRequest);
	 }

	/* （非 Javadoc）
	 * @see com.yonyou.iuap.crm.user.service.IIeopUserService#updatepassword(java.lang.String, java.lang.String)
	 */
	@Override
	public void updatepassword(String id, String password) throws DAOException {
		dao.updatepassword(id, password);
	}
	
	@Override
	public List<ExtIeopRoleVO> queryUnassignRoles(String userid)  throws DAOException {
		 String whereStr = "role_code not in (select role_code from ieop_role_user where user_id = '"+userid+"') ";
		 return dao.getBdUserrole(whereStr);
	 }

	@Override
	public Page<ExtIeopUserVO> getBdUsersRefBypage(
			Map<String, Object> searchParams, PageRequest pageRequest)
			throws BusinessException {
		/**
		 * 添加业务逻辑
		 */
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		if(null != searchParams.get("pkdept") && !searchParams.get("pkdept").toString().equals("")){
			condition.append(" and ieop_user.pk_dept = ?");
			sqlParameter.addParam(searchParams.get("pkdept").toString());
		}
		//添加用户参照索引
		if(null!=searchParams.get("condition")&&!"".equals(searchParams.get("condition"))){
			condition.append(" ").append("and (ieop_user.login_name like").append(" ").append("?").append(" ").append("||").append(" ieop_user.name like").append(" ").append(" ? )");
			sqlParameter.addParam("%"+searchParams.get("condition")+ "%");
			sqlParameter.addParam("%"+searchParams.get("condition")+ "%");
		}
		for(Map.Entry<String, Object> entry : searchParams.entrySet()){
			String[] keySplit = entry.getKey().split("=");
			if(keySplit.length==2){
				String columnName = keySplit[1];
				if(columnName.equals("loginName")){
					columnName = "ieop_user.login_name";
				}else if(columnName.equals("name")){
					columnName = "ieop_user.name";
				}else if(columnName.equals("pkCorp")){
					columnName = "bd_corp.unitname";
				}
				else if(columnName.equals("pkdept")){
					columnName = "ieop_user.pk_dept";
				}
				Object value = entry.getValue();
					if(value != null && StringUtils.isNotBlank(value.toString())){
						condition.append(" and ").append(columnName).append(" = ?");
						sqlParameter.addParam(value);
					}
			}
		}
		
		return dao.getBdUsersBypage(condition.toString(), sqlParameter, pageRequest);
	}
	
	/**
	* 销售经理参照
	* @author 
	* @date 2017年1月19日
	* @param searchParams
	* @param pageRequest
	* @return
	* @throws BusinessException
	*/
		
	@Override
	public Page<ExtIeopUserVO> getSaleManagerRefBypage( Map<String, Object> searchParams, PageRequest pageRequest) throws BusinessException {
		/**
		 * 添加业务逻辑
		 */
		StringBuffer condition = new StringBuffer();
		SQLParameter sqlParameter = new SQLParameter();
		if(null!=searchParams.get("condition")&&!"".equals(searchParams.get("condition"))){
			condition.append(" ").append("and (ieop_user.LoginName like").append(" ").append("?").append(" ").append("||").append(" ieop_user.name like").append(" ").append(" ? )");
			sqlParameter.addParam("%"+searchParams.get("condition")+ "%");
			sqlParameter.addParam("%"+searchParams.get("condition")+ "%");
		}
			return dao.getSaleManagerBypage(condition.toString(), sqlParameter, pageRequest);
	}
}
