package com.yonyou.iuap.example.repository.meta;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.example.entity.meta.Instit;
import com.yonyou.iuap.example.entity.meta.Telbook;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;
import com.yonyou.iuap.persistence.jdbc.framework.util.SQLHelper;

@Repository
public class TelBookDao {

  private Logger logger = LoggerFactory.getLogger(TelBookDao.class);

  @Autowired
  private MetadataDAO metadataDAO;

	/**
	 * 根据某一非主键字段查询实体
	 */
  public List<Telbook> findByCode( String peocode ) {

    String sql = "select * from telbook where peocode=?";
    SQLParameter sqlparam = new SQLParameter();
    sqlparam.addParam(peocode);
    List<Telbook> list = metadataDAO.queryByClause(Telbook.class, sql, sqlparam);
    return list;
  }

	/**
	 *分页相关
	 */
  public Page<Telbook> selectAllByPage( PageRequest pageRequest , Map<String, Object> searchParams )
      throws DAOException {

	StringBuilder sql = new StringBuilder("select * from telbook ");
    SQLParameter sqlparam = new SQLParameter();

    if (null != searchParams && !searchParams.isEmpty()) {
      sql.append(" where ");
      for (Entry<String, Object> key : searchParams.entrySet()) {
        if (key.getKey().equalsIgnoreCase("searchParam")) {
        	sql.append(" (peoname like ? OR peocode like ? OR tel like ? OR email like ? ) AND ");
        	for (int i = 0; i < 4; i++) {
            sqlparam.addParam("%" + key.getValue() + "%");
          }
        }
        else if (key.getKey().equalsIgnoreCase("institid")) {
          sql.append(FastBeanHelper.getColumn(Telbook.class, key.getKey()));
          sql.append(" in (");
          String value = (String) key.getValue();
          String[] ids = value.split(",");
          for (String s : ids) {
        	  sql.append("? ,");
            sqlparam.addParam(s);
          }
          sql.deleteCharAt(sql.length() - 1);
          sql.append(") AND ");
        }
      }
      sql.delete(sql.length() - 4, sql.length());
    }
    return metadataDAO.queryPage(sql.toString(), sqlparam, pageRequest, Telbook.class);
  }

	/**
	 *批量操作
	 */
  public void batchInsert( List<Telbook> addList ) throws DAOException {

    metadataDAO.insert(addList);
  }

  public void batchUpdate( List<Telbook> updateList ) throws DAOException {

    metadataDAO.update(updateList);
  }

  public void batchDelete( List<Telbook> list ) throws DAOException {

    metadataDAO.remove(list);
  }

  /**
   * 根据 参照主键id 查询所属机构
   * 
   */
  public List<Instit> selectInstitByIds( Set<String> ids ) {

    String sql = SQLHelper.createSQLIn("instit", "institid", ids.size());
    SQLParameter sqlparam = new SQLParameter();
    for (String id : ids) {
      sqlparam.addParam(id);
    }
    return metadataDAO.queryByClause(Instit.class, sql, sqlparam);
  }

}
