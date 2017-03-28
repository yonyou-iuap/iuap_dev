/**
 用户操作dao层
 */
package com.yonyou.iuap.example.repository.meta;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.entity.meta.Org;
import com.yonyou.iuap.example.entity.meta.SysUser;
import com.yonyou.iuap.example.entity.meta.SysUserJob;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;
import com.yonyou.iuap.persistence.jdbc.framework.util.SQLHelper;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;
import com.yonyou.iuap.persistence.vo.pub.util.StringUtil;

@Repository
public class SysUserDao {

    @Autowired
    private MetadataDAO metadataDAO;
    
    @Transactional
    public SysUser save(SysUser entity) {
    	if(entity.getId() ==null ){
    		entity.setStatus(VOStatus.NEW);
    		 entity.setId(UUID.randomUUID().toString());
             entity.setCreatorid(InvocationInfoProxy.getUserid());
             entity.setCreator(InvocationInfoProxy.getUsername());
             entity.setCreatetime(new Date());
             entity.setDr(0);// 未删除标识
    	}else{
    		entity.setStatus(VOStatus.UPDATED);
    	}
    	if(entity.getIdSysUserJob()!=null && entity.getIdSysUserJob().size()>0){
    		for(SysUserJob job : entity.getIdSysUserJob() ){
    			if(job.getId() == null ){
    				job.setStatus(VOStatus.NEW);
    				job.setUserid(entity.getId());
    				job.setUsername(entity.getUsername() );
    				job.setCreatorid(entity.getCreatorid() );
    				job.setCreator(entity.getCreator());
    				job.setCreatetime( new Date() );
    				job.setDr(entity.getDr());
    			}else{
    				job.setStatus(VOStatus.UPDATED);
    				job.setModifier(entity.getUsername());
    				job.setModifytime(new Date());
    				job.setModifierid(entity.getId());
    			}
    		}
    		metadataDAO.save(entity, entity.getIdSysUserJob().toArray(new SysUserJob[entity.getIdSysUserJob().size()]));
    	}else{
    		metadataDAO.save(entity);
    	}
    	return entity ;
    }
    

    public Page<SysUser> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) throws DAOException {
    	 String sql = " select sys_user.* from sys_user "; // left join org on sys_user.org_id=org.org_id 
    	 StringBuilder sb = new StringBuilder();
    	 sb.append( sql );
    	 SQLParameter sqlparam = new SQLParameter();
    	 if (null != searchParams && !searchParams.isEmpty()) {  
    		 if( !StringUtil.isEmpty(String.valueOf(searchParams.get("orgname")) )   ){
    			 sb.append(" left join org on sys_user.org_id=org.org_id where ") ;
    		 }else{
    			 sb.append(" where ") ; 
    		 }
    		 for(Map.Entry<String, Object> entry : searchParams.entrySet() ){
    			 if( entry.getKey().equals("orgname") ){ //orgname为参照表里面的字段
    				 sb.append( FastBeanHelper.getTableName(Org.class) );
    				 sb.append(".");
    				 sb.append( FastBeanHelper.getColumn(Org.class, entry.getKey() ) );
    				 sb.append(" like ? AND " );
    				 sqlparam.addParam("%" + entry.getValue() + "%");
    			 }else{
    				 sb.append( FastBeanHelper.getTableName(SysUser.class) );
    				 sb.append(".");
    				 sb.append( FastBeanHelper.getColumn(SysUser.class, entry.getKey() ) );
    				 sb.append(" like ? AND " );
    				 sqlparam.addParam("%" + entry.getValue() + "%");
    			 }
    		 }
    		 sql = sb.toString().substring( 0, sb.toString().length() - 4) ;
    	 }
    	 return metadataDAO.queryPage(sql, sqlparam, pageRequest, SysUser.class);
    }


    public void batchDelete(List<SysUser> list) throws DAOException {
        metadataDAO.remove(list);
    }
    
    public void batchDelChild(List<SysUser> list) throws DAOException {
        SQLParameter sqlparam = new SQLParameter();
        String deleteSQL = SQLHelper.createDeleteIn("sys_user_job", "fk_id_sys_user_job", list.size());
        for (SysUser user : list) {
            sqlparam.addParam(user.getId());
        }
        metadataDAO.update(deleteSQL, sqlparam);
    }
    

    public List<SysUser> findByUserCode(String code) throws DAOException {

        String sql = "SELECT * FROM sys_user where user_code=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(code);
        return  metadataDAO.queryByClause(SysUser.class, sql, sqlparam);
//        return sys == null || sys.isEmpty() ? null : sys.get(0);
    }
    
    public List<SysUser> findByUserCode(String code, String id) {
        String sql = " SELECT * FROM sys_user where user_code=? AND id !=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(code);
        sqlparam.addParam(id);
        return  metadataDAO.queryByClause(SysUser.class, sql, sqlparam);
    }
    
    public List<Org> findAllOrg() {
        return metadataDAO.queryAll(Org.class);
    }

}
