package com.yonyou.iuap.crm.user.repository.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.crm.user.entity.ExtIeopRoleVO;
import com.yonyou.iuap.crm.user.entity.ExtIeopUserRoleVO;
import com.yonyou.iuap.crm.user.entity.ExtIeopUserVO;
import com.yonyou.iuap.crm.user.repository.itf.IExtIeopUserDao;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.processor.BeanListProcessor;
import com.yonyou.iuap.persistence.jdbc.framework.processor.BeanProcessor;
import com.yonyou.uap.ieop.security.entity.UserRole;

//用于标注数据访问组件，即DAO组件
@Repository
public class ExtIeopUserDaoImpl implements IExtIeopUserDao {

	@Autowired
	@Qualifier("baseDAO") private BaseDAO baseDao;

	public ExtIeopUserVO getBdUser(String id) throws DAOException {
		String sql = "select * from ieop_user where id=? ";
		SQLParameter sqlParameter = new SQLParameter();
		sqlParameter.addParam(id);
		ExtIeopUserVO vo = baseDao.queryForObject(sql, sqlParameter, new BeanProcessor(ExtIeopUserVO.class));
		return vo;
	}

	@Override
	public List<ExtIeopUserVO> getBdUsers() throws DAOException {
		String sql = "select * from ieop_user where dr=0 order by ts desc";
		return baseDao.queryByClause(ExtIeopUserVO.class, sql);
	}
	public List<ExtIeopUserVO> getBdUsers(String wherestr) throws DAOException {
		String sql = "select * from ieop_user where dr=0 "+wherestr+" order by ts desc";
		return baseDao.queryByClause(ExtIeopUserVO.class, sql);
	}
	public Page<ExtIeopUserVO> getBdUsernum(String condition, SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException{
		if(sqlParameter.getCountParams()==0)
			sqlParameter = null;
		StringBuffer sqlBuffer = new StringBuffer("SELECT  ieop_user.id,ieop_user.login_name,ieop_user.name, ieop_user.password,ieop_user.salt,ieop_user.roles,ieop_user.states,ieop_user.register_date,ieop_user.def1,ieop_user.def2,ieop_user.def3,ieop_user.def4,ieop_user.def5,ieop_user. def6,ieop_user. def7,ieop_user. def8,ieop_user. def9,ieop_user. def10,ieop_user.def11,ieop_user.def12,ieop_user.def13,ieop_user. def14,ieop_user.def15,ieop_user. tenant_userid,ieop_user.passrank,ieop_user. pk_usertype,ieop_user. pk_corp,ieop_user. pk_dept,ieop_user. creator,ieop_user. creationtime,ieop_user. modifier,ieop_user. modifiedtime,ieop_user. pk_psndoc,ieop_user. psnseal,ieop_user. locked,ieop_user. psntel,ieop_user. email,ieop_user. dr,ieop_user. ts,ieop_user. pk_propt from ieop_user left join bd_corp on ieop_user. pk_corp=bd_corp.pk_corp where '"+condition+"'ieop_user.dr=0 ");
		sqlBuffer.append(condition);
		sqlBuffer.append(" order by ieop_user.ts desc");
		//new PageRequest(0,10, new Sort(Sort.Direction.ASC, "ipuquotaionid", "ts");
		return baseDao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, ExtIeopUserVO.class);
	}

	
	@Override
	public List<ExtIeopUserVO> getBdUsernum(String condition) throws DAOException {
		String sql = "select * from ieop_user where "+condition+" and dr=0 order by ts desc";
		return baseDao.queryByClause(ExtIeopUserVO.class, sql);
	}
	@Override
	public Page<ExtIeopUserVO> getBdUsersBypage(String condition, SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException{
		if(sqlParameter.getCountParams()==0)
			sqlParameter = null;
		StringBuffer sqlBuffer = new StringBuffer("select  ieop_user.id,ieop_user.login_name,ieop_user.name, ieop_user.password,ieop_user.salt,ieop_user.roles,ieop_user.states,ieop_user.register_date,ieop_user.def1,ieop_user.def2,ieop_user.def3,ieop_user.def4,ieop_user.def5,ieop_user. def6,ieop_user. def7,ieop_user. def8,ieop_user. def9,ieop_user. def10,ieop_user.def11,ieop_user.def12,ieop_user.def13,ieop_user. def14,ieop_user.def15,ieop_user. tenant_userid,ieop_user.passrank,ieop_user. pk_usertype,ieop_user. pk_corp,ieop_user. pk_dept,ieop_user. creator,ieop_user. creationtime,ieop_user. modifier,ieop_user. modifiedtime,ieop_user. pk_psndoc,ieop_user. psnseal,ieop_user. locked,ieop_user. psntel,ieop_user. email,ieop_user. dr,ieop_user. ts,ieop_user. pk_propt from ieop_user left join bd_corp on ieop_user. pk_corp=bd_corp.pk_corp  where ieop_user.dr=0 ");
		sqlBuffer.append(condition);
		sqlBuffer.append("");
//		new PageRequest(0,10, new Sort(Sort.Direction.ASC, "ipuquotaionid", "ts");
		return baseDao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, ExtIeopUserVO.class);
	}
	
	/**
	* 销售经理参照查询
	* @author 
	* @date 2017年1月19日
	* @param condition
	* @param sqlParameter
	* @param pageRequest
	* @return
	* @throws DAOException
	* (non-Javadoc)
	* @see com.yonyou.iuap.crm.user.repository.itf.IExtIeopUserDao#getSaleManagerBypage(java.lang.String, com.yonyou.iuap.persistence.jdbc.framework.SQLParameter, org.springframework.data.domain.PageRequest)
	*/
	@Override
	public Page<ExtIeopUserVO> getSaleManagerBypage(String condition, SQLParameter sqlParameter, PageRequest pageRequest) throws DAOException{
		if(sqlParameter.getCountParams()==0)
			sqlParameter = null;
		StringBuffer sqlBuffer = new StringBuffer("select iuser.* from ieop_user iuser where exists ( select 1 from ieop_user_position iup left join ieop_role_position irp on iup.position_code =irp.position_code left join ieop_role ir on ir.role_code = irp.role_code where iuser.id = user_id and ir.role_name like '%销售经理%' ) and iuser.dr = '0' ");
		sqlBuffer.append(condition);
		return baseDao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, ExtIeopUserVO.class);
	}
	@Override
	public Page<ExtIeopUserVO> getBdUsersBypagenum(String condition, SQLParameter sqlParameter, PageRequest pageRequest,String login_name, String name, String corp) throws DAOException{
		if(sqlParameter.getCountParams()==0)
			sqlParameter = null;
		String sql=null;
		if(login_name!=null||name!=null||corp!=null){
			sql="select * from ieop_user where (login_name='"+login_name+"' or name='"+name+"' or pk_corp='"+corp+"') and dr=0";
		}else{
			sql="select * from ieop_user where  dr=0";

		}
		StringBuffer sqlBuffer = new StringBuffer(sql);
		SQLParameter sqlParameternum = new SQLParameter();
		sqlParameternum.addParam(login_name);
		sqlParameternum.addParam(name);
		sqlParameternum.addParam(corp);//		sqlBuffer.append(condition);
//		sqlBuffer.append("");
		//new PageRequest(0,10, new Sort(Sort.Direction.ASC, "ipuquotaionid", "ts");
		return baseDao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, ExtIeopUserVO.class);
	}

	@Override
	@Transactional
	public void updateBdUser(ExtIeopUserVO entity) throws DAOException {
		Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(date);
//        entity.setTs(time);
		int result = baseDao.update(entity);
	}
	@Override
	public void updateBdUsernum(List<ExtIeopUserVO> entity) throws DAOException {
		Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(date);
//        entity.setTs(time);
		int result = baseDao.update(entity);
	}


	@Override
	@Transactional
	public void updateBdUserByCondition(String sql) throws DAOException {
		Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(date);
		int result = baseDao.update(sql+",ts='"+time+"'");
	}
	
	@Override
	@Transactional
	public String saveBdUser(ExtIeopUserVO entity) throws DAOException {
		Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(date);
//        entity.setTs(time);
        baseDao.insertWithPK(entity);
		return entity.getId();
	}
	
	@Override
	@Transactional
	public String saveBdUserWithPK(ExtIeopUserVO entity) throws DAOException {
//		Date date = new Date(System.currentTimeMillis());
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time = df.format(date);
//        entity.setTs(time);
		return baseDao.insertWithPK(entity);
	}

	@Override
	@Transactional
	public void deleteBdUser(ExtIeopUserVO entity) throws DAOException {
		baseDao.remove(entity);
	}
	
	@Override
	@Transactional
	public void deleteBdUserById(String id) throws DAOException {
		ExtIeopUserVO entity = new ExtIeopUserVO();
		entity.setId(id);
		baseDao.remove(entity);
	}
	
	/**
	 * 逻辑删除，非物理删除
	 */
	@Override
	@Transactional
	public void deleteBdUserByIdTS(String id) throws DAOException {
		String sql = "update ieop_user set dr=1 where id='" +id+ "'";
		int result = baseDao.update(sql);
	}

	@Override
	public List<ExtIeopUserVO> getBdUserByCodeAndName(String usercode, String userame,String corp)
			throws DAOException {
		String sql = "select * from ieop_user where login_name=? and dr=0";
		SQLParameter sqlParameter = new SQLParameter();
		sqlParameter.addParam(usercode);

		List<ExtIeopUserVO> vo = baseDao.queryForObject(sql, sqlParameter, new BeanListProcessor(ExtIeopUserVO.class));
		return vo;
	}
	
	@Override
	public void stopBdUser(String id) throws DAOException {
		String sql = "update ieop_user set locked=0 where id='" +id+ "'";
		int result = baseDao.update(sql);
	}
	@Override
	public void stopBdUserlist(List<ExtIeopUserVO> entity) throws DAOException {
		for(int i=0;i<entity.size();i++){
			String id=entity.get(i).getId();
		String sql = "update ieop_user set locked=0 where id='" +id+ "'";
		int result = baseDao.update(sql);
		}
	}
	@Override
	public void startBdUser(String id) throws DAOException {
		String sql = "update ieop_user set locked=1 where id='" +id+ "'";
		int result = baseDao.update(sql);
	}
	@Override
	public void startBdUserlist(List<ExtIeopUserVO> entity) throws DAOException {
		for(int i=0;i<entity.size();i++){
			String id=entity.get(i).getId();
		String sql = "update ieop_user set locked=1 where id='" +id+ "'";
		int result = baseDao.update(sql);
		}
	}
	@Override
	public void stopBdUsersd(String id) throws DAOException {
		String sql = "update ieop_user set psnseal=0 where id='" +id+ "'";
		int result = baseDao.update(sql);
	}

	@Override
	public void stopBdUsersdlist(List<ExtIeopUserVO> entity) throws DAOException {
		for(int i=0;i<entity.size();i++){
			String id=entity.get(i).getId();
		String sql = "update ieop_user set psnseal=0 where id='" +id+ "'";
		int result = baseDao.update(sql);
		}
	}
	@Override
	public void startBdUsersd(String id) throws DAOException {
		String sql = "update ieop_user set psnseal=1 where id='" +id+ "'";
		int result = baseDao.update(sql);
	}
	@Override
	public void startBdUsersdlist(List<ExtIeopUserVO> entity) throws DAOException {
		for(int i=0;i<entity.size();i++){
			String id=entity.get(i).getId();
		String sql = "update ieop_user set psnseal=1 where id='" +id+ "'";
		int result = baseDao.update(sql);
		}
	}
	@Override
	public void removelist(List<ExtIeopUserVO> entity) throws DAOException {
		for(int i=0;i<entity.size();i++){
			String id=entity.get(i).getId();
		String sql = "update ieop_user set dr=1 where id='" +id+ "'";
		int result = baseDao.update(sql);
		}
	}
	@Override
	public void startUpdatepassword(List<ExtIeopUserVO> entity) throws DAOException {
		for(int i=0;i<entity.size();i++){
			String id=entity.get(i).getId();
			String salt=entity.get(i).getSalt();
			String password=entity.get(i).getPassword();

		String sql = "update ieop_user set password='"+password+"' where id='" +id+ "'";
		String sqlnum = "update ieop_user set salt='"+salt+"' where id='" +id+ "'";

		
		int result = baseDao.update(sql);
		int resultbu = baseDao.update(sqlnum);

		}
	}
	@Override
	public List<ExtIeopUserRoleVO> queryByUserID(String id) throws DAOException {
		String sql = "select ieop_role_user.role_code as role_code,ieop_role_user.role_code as roleCode,ieop_role_user.user_code as user_code ,ieop_role.role_name as role_name  from ieop_role_user  left join ieop_role ON ieop_role.id=ieop_role_user.role_id where ieop_role_user.user_id='" +id+ "'";
		return baseDao.queryByClause(ExtIeopUserRoleVO.class, sql);
	}

	@Override
	public Page<ExtIeopUserVO> getBdUserByCodeAndNamenum(String usercode,
			String username, String corp) throws DAOException {
		// TODO 自动生成的方法存根
		return null;
	}
	@Override
	public Page<UserRole> queryByUserIDnum(String condition, SQLParameter sqlParameter, PageRequest pageRequest,String id) throws DAOException{
		if(sqlParameter.getCountParams()==0)
			sqlParameter = null;
		StringBuffer sqlBuffer = new StringBuffer("select * from ieop_role_user where user_id='" +id+ "'");
//		SQLParameter sqlParameternum = new SQLParameter();
//		sqlParameternum.addParam(login_name);
//		sqlParameternum.addParam(name);
//		sqlParameternum.addParam(corp);//		sqlBuffer.append(condition);
//		sqlBuffer.append("");
		//new PageRequest(0,10, new Sort(Sort.Direction.ASC, "ipuquotaionid", "ts");
		return baseDao.queryPage(sqlBuffer.toString(), sqlParameter, pageRequest, UserRole.class);
	}

	@Override
	public void updatepassword(String id ,String password) throws DAOException {
		String sql = "update ieop_user set password='"+password+"' where id='" +id+ "'";
		baseDao.update(sql);
	}

	@Override
	public void updateattr(String id,String userattr)throws DAOException {
		// TODO 自动生成的方法存根
		String sql = "update ieop_user set userattr='"+userattr+"' where id='" +id+ "'";
		baseDao.update(sql);
	}
	public List<ExtIeopUserVO> findByName(String name) throws DAOException {
		String sql = "select * from ieop_user where dr=0 and name='"+name+"' order by ts ";
		return baseDao.queryByClause(ExtIeopUserVO.class, sql);
	}
	public List<ExtIeopUserVO> findByCode(String wherestr) throws DAOException {
		String sql = "select * from ieop_user where dr=0 "+wherestr+" order by ts desc";
		return baseDao.queryByClause(ExtIeopUserVO.class, sql);
	}
	
	@Override
	public List<ExtIeopRoleVO> getBdUserrole(String condition) throws DAOException {
		String sql ="select * from ieop_role where "+condition+"";
		return baseDao.queryByClause(ExtIeopRoleVO.class, sql);
	}

}
