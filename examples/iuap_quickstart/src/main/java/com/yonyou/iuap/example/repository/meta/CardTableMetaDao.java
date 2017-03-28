package com.yonyou.iuap.example.repository.meta;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.example.entity.meta.CardTableMeta;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;

/**
 * <p>Title: CardTableMetaDao</p>
 * <p>Description: </p>
 * @author zhukai
 */
@Repository
public class CardTableMetaDao {
	
	@Qualifier("mdBaseDAO")
	@Autowired
	private MetadataDAO metadataDAO;
	
	/**
	 * 根据某一非主键字段查询实体
	 */
    public List<CardTableMeta> findByCode(String code) {
        String sql = "select * from cardTableMeta where code=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(code);
        List<CardTableMeta> list = metadataDAO.queryByClause(CardTableMeta.class, sql, sqlparam);
        return list;
    }
    
	/**
	 *分页相关
	 */
    public Page<CardTableMeta> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        StringBuilder sql = new StringBuilder("select * from cardTableMeta");
        SQLParameter sqlparam = new SQLParameter();
        if (null != searchParams && !searchParams.isEmpty()) {
            sql.append(" where ");
            for (Entry<String, Object> key : searchParams.entrySet()) {
                if (key.getKey().equalsIgnoreCase("searchParam")) {
                    sql.append("(code like ? OR name like ?) AND ");
                    for (int i = 0; i < 2; i++) {
                        sqlparam.addParam("%" + key.getValue() + "%");
                    }
                }
            }
            sql.delete(sql.length() - 4, sql.length());
        }
        return metadataDAO.queryPage(sql.toString(), sqlparam, pageRequest, CardTableMeta.class);
    }
    
	/**
	 *批量操作
	 */
    public void batchInsert(List<CardTableMeta> addList) throws DAOException {
        metadataDAO.insert(addList);
    }

    public void batchUpdate(List<CardTableMeta> updateList) {
        metadataDAO.update(updateList);
    }

    public void batchDelete(List<CardTableMeta> list) {
        metadataDAO.remove(list);
    }
    


}
