package com.yonyou.iuap.crm.test.dao;

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
import com.yonyou.iuap.crm.test.entity.Test;
import com.yonyou.iuap.crm.test.entity.TestB;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;
import com.yonyou.iuap.persistence.jdbc.framework.util.SQLHelper;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;


@Repository
public class TestDao {

    @Autowired
    private MetadataDAO dao;

	@Transactional
    public Test save(Test entity) {
    	if(entity.getId() ==null ){
    		 entity.setStatus(VOStatus.NEW);
    		 entity.setId(UUID.randomUUID().toString());
             entity.setDr(0);// 未删除标识         
    	}else{
    		entity.setStatus(VOStatus.UPDATED);
    	}
    	
		if(entity.getId_testb()!=null && entity.getId_testb().size()>0){
    		for(TestB child : entity.getId_testb() ){
    			if(child.getId() == null ){
    				child.setStatus(VOStatus.NEW);
    				child.setDr(entity.getDr());
    			}else{
    				child.setStatus(VOStatus.UPDATED);
    			}
    		}
    		dao.save(entity, entity.getId_testb().toArray(new TestB[entity.getId_testb().size()]));
    	}else{
    		dao.save(entity);
    	}
    	return entity ;
    }


    public int delete(Test entity) throws Exception {

        if (null == entity) {
            return 0;
        }
        dao.remove(entity);
        return 1;
    }

    public Page<Test> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) throws DAOException {

        String sql = " select * from iuaptraincrm_Test"; // user_name = ?
        SQLParameter sqlparam = new SQLParameter();

     	if (null != searchParams && !searchParams.isEmpty()) {
            sql = sql + " where ";
            for (String key : searchParams.keySet()) {
                sql = sql + FastBeanHelper.getColumn(Test.class, key) + " like ? AND ";
                sqlparam.addParam("%" + searchParams.get(key) + "%");
            }
            sql = sql.substring(0, sql.length() - 4);
        }
        return dao.queryPage(sql, sqlparam, pageRequest, Test.class);
    }

    public void batchDelete(List<Test> list) throws DAOException {

        dao.remove(list);
    }

    public void batchDelChild(List<Test> list) throws DAOException {
        SQLParameter sqlparam = new SQLParameter();
		String deleteSQL = SQLHelper.createDeleteIn("iuaptraincrm_TestB", "fk_id_testb", list.size());
        for (Test item : list) {
            sqlparam.addParam(item.getId());
        }
        dao.update(deleteSQL, sqlparam);
    }     

	public List<com.yonyou.iuap.crm.currtype.entity.Currtype> findAllCurrtype() {
        return dao.queryAll(com.yonyou.iuap.crm.currtype.entity.Currtype.class);
    }
    
}
