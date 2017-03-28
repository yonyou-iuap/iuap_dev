package com.yonyou.iuap.crm.test2.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.crm.test2.entity.Entity0;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;

/**
 * <p>Title: CardTableMetaDao</p>
 * <p>Description: </p>
 */
@Repository
public class Entity0Dao {
	
	@Qualifier("mdBaseDAO")
	@Autowired
	private MetadataDAO dao;
	
    
    public Page<Entity0> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        String sql = " select * from iuaptraincrm_Entity0"; 
        SQLParameter sqlparam = new SQLParameter();
        if (!searchParams.isEmpty()) {
            sql = sql + " where ";
            for (String key : searchParams.keySet()) {
                if (key.equalsIgnoreCase("searchParam")) {
                    sql =sql + "() AND";
                    for (int i = 0; i < 2; i++) {
                        sqlparam.addParam("%" + searchParams.get(key) + "%");
                    }
                }
            }
            sql = sql.substring(0, sql.length() - 4);
        }
        return dao.queryPage(sql, sqlparam, pageRequest, Entity0.class);
    }
    
    
    public void batchInsert(List<Entity0> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<Entity0> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<Entity0> list) {
        dao.remove(list);
    }
    

}
