package com.yonyou.iuap.example.repository.meta;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.example.entity.meta.Dept;
import com.yonyou.iuap.example.entity.meta.SysUserJob;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;

@Repository
public class SysUserJobDao {

    @Autowired
    private MetadataDAO metadataDAO;


    public void delete(SysUserJob entity) {
        if (null != entity) {
            metadataDAO.remove(entity);
        }
    }

    public Page<SysUserJob> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams)
            throws DAOException {
        String sql = " select * from sys_user_job";
        SQLParameter sqlparam = new SQLParameter();
        StringBuilder sb = new StringBuilder();
   	    sb.append( sql );
   	    if (null != searchParams && !searchParams.isEmpty()) {
   	    	sb.append(  " where " );
   	    	for(Entry<String,Object> entry : searchParams.entrySet() ){
   	    		sb.append( FastBeanHelper.getColumn(SysUserJob.class, entry.getKey()) );
   	    		if (entry.getKey().equals("userid")) {
   	    		    sb.append(" = ? AND ");
   	    		    sqlparam.addParam( entry.getValue());
   	    		}else{
   	    			sb.append(" like ? AND ");
   	    			sqlparam.addParam("%" + entry.getValue() + "%");
   	    		}
   	    	}
   	        sql = sb.toString().substring(0, sb.toString().length() - 4);
   	    }
   	    return metadataDAO.queryPage(sql, sqlparam, pageRequest, SysUserJob.class);

    }


    public List<Dept> findAllDept() {
    	return metadataDAO.queryAll(Dept.class) ;
    }
    

    
}
