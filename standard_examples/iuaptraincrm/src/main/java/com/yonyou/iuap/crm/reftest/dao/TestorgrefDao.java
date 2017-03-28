package com.yonyou.iuap.crm.reftest.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.crm.reftest.entity.Testorgref;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;

/**
 * <p>Title: CardTableMetaDao</p>
 * <p>Description: </p>
 */
@Repository
public class TestorgrefDao {
	
	@Qualifier("mdBaseDAO")
	@Autowired
	private MetadataDAO dao;
	
	//根据某一非主键字段查询实体
	public List<Testorgref> findByName(String name){
		String sql = "select * from iuaptraincrm_testorgref where name=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(name);
        List<Testorgref> list = dao.queryByClause(Testorgref.class, sql, sqlparam);
        return list;
	}
	//根据某一非主键字段查询实体
	public List<Testorgref> findByCity(String city){
		String sql = "select * from iuaptraincrm_testorgref where city=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(city);
        List<Testorgref> list = dao.queryByClause(Testorgref.class, sql, sqlparam);
        return list;
	}
    
    public Page<Testorgref> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        String sql = " select * from iuaptraincrm_testorgref"; 
        SQLParameter sqlparam = new SQLParameter();
        if (!searchParams.isEmpty()) {
            sql = sql + " where ";
            for (String key : searchParams.keySet()) {
                if (key.equalsIgnoreCase("searchParam")) {
                    sql =sql + "(name like ? OR city like ?) AND";
                    for (int i = 0; i < 2; i++) {
                        sqlparam.addParam("%" + searchParams.get(key) + "%");
                    }
                }
            }
            sql = sql.substring(0, sql.length() - 4);
        }
        return dao.queryPage(sql, sqlparam, pageRequest, Testorgref.class);
    }
    
    
    public void batchInsert(List<Testorgref> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<Testorgref> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<Testorgref> list) {
        dao.remove(list);
    }
    
	public List<com.yonyou.iuap.crm.corp.entity.BdCorpVO> findAllBdCorpVO() {
        return dao.queryAll(com.yonyou.iuap.crm.corp.entity.BdCorpVO.class);
    }
    
	public List<com.yonyou.iuap.crm.user.entity.ExtIeopUserVO> findAllExtIeopUserVO() {
        return dao.queryAll(com.yonyou.iuap.crm.user.entity.ExtIeopUserVO.class);
    }
    
	public List<com.yonyou.iuap.crm.psn.entity.BdPsndocVO> findAllBdPsndocVO() {
        return dao.queryAll(com.yonyou.iuap.crm.psn.entity.BdPsndocVO.class);
    }
    

}
