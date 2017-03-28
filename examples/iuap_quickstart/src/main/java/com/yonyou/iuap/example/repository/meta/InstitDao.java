package com.yonyou.iuap.example.repository.meta;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.example.entity.meta.Instit;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;

/**
 * <p>Title: InstitDao</p>
 * <p>Description: </p>
 * @author zhukai
 */
@Repository
public class InstitDao {
	
	@Qualifier("mdBaseDAO")
	@Autowired
	private MetadataDAO metadataDAO;
	
    public List<Instit> findAll() {
        return metadataDAO.queryAll(Instit.class);
    }

    public List<Instit> findByFid(String id) {
        String sql = "select * from instit where parent_id=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(id);
        List<Instit> list = metadataDAO.queryByClause(Instit.class, sql, sqlparam);
        return list;
    }

    public List<Instit> findByCode(String code) {
        String sql = "select * from instit where instit_code=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(code);
        List<Instit> list = metadataDAO.queryByClause(Instit.class, sql, sqlparam);
        return list;
    }

    public Page<Instit> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        StringBuilder sql = new StringBuilder("select * from instit");
        SQLParameter sqlparam = new SQLParameter();
        if (null != searchParams && !searchParams.isEmpty()) {
            sql.append(" where ");
            for (Entry<String, Object>  key : searchParams.entrySet()) {
            	sql.append(FastBeanHelper.getColumn(Instit.class,key.getKey()));
            	sql.append(" like ? AND ");
                sqlparam.addParam("%" + key.getValue() + "%");
            }
            sql.delete(sql.length() - 4, sql.length());
        }
        return metadataDAO.queryPage(sql.toString(), sqlparam, pageRequest, Instit.class);
    }

    public void batchInsert(List<Instit> addList) throws DAOException {
        metadataDAO.insert(addList);
    }

    public void batchUpdate(List<Instit> updateList) {
        metadataDAO.update(updateList);
    }

    public void batchDelete(List<Instit> list) {
        metadataDAO.remove(list);
    }


}
