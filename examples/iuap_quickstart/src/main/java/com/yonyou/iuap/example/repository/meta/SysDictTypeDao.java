package com.yonyou.iuap.example.repository.meta;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.example.entity.meta.SysDictTypeMeta;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;

@Repository
public class SysDictTypeDao {

  @Autowired
  private MetadataDAO metadataDAO;

  public int deleteByPrimaryKey( String id ) {

    SysDictTypeMeta stm = selectByPrimaryKey(id);
    if (null != stm) {
      metadataDAO.remove(stm);
    }
    return 1;
  }

  public int insert( SysDictTypeMeta record ) {

    metadataDAO.insert(record);
    return 1;
  }

  public SysDictTypeMeta selectByPrimaryKey( String id ) {

    return metadataDAO.queryByPK(SysDictTypeMeta.class, id);
  }

  public int updateByPrimaryKey( SysDictTypeMeta record ) {

    metadataDAO.update(record);
    return 1;
  }

  public Page<SysDictTypeMeta> selectAllByPage( PageRequest pageRequest ,
      Map<String, Object> searchParams ) {

    String sql = " select * from sys_dict_type_meta"; // user_name = ?
    SQLParameter sqlparam = new SQLParameter();
    if (null != searchParams && !searchParams.isEmpty()) {
      sql = sql + " where ";
      for (String key : searchParams.keySet()) {
        sql = sql + FastBeanHelper.getColumn(SysDictTypeMeta.class, key) + " like ? AND ";
        sqlparam.addParam("%" + searchParams.get(key) + "%");
      }
      sql = sql.substring(0, sql.length() - 4);
    }
    return metadataDAO.queryPage(sql, sqlparam, pageRequest, SysDictTypeMeta.class);
  }

  public void batchInsert( List<SysDictTypeMeta> addList ) {

    metadataDAO.insert(addList);
  }

  public void batchUpdate( List<SysDictTypeMeta> updateList ) {

    metadataDAO.update(updateList);
  }

  public void batchDeleteByPrimaryKey( List<SysDictTypeMeta> list ) {

    metadataDAO.remove(list);
  }

  public SysDictTypeMeta queryByCode( String dictTypeCode ) {

    String sql = "select * from sys_dict_type_meta where dict_type_code=?";
    SQLParameter sqlparam = new SQLParameter();
    sqlparam.addParam(dictTypeCode);
    List<SysDictTypeMeta> list = metadataDAO.queryByClause(SysDictTypeMeta.class, sql, sqlparam);
    return list == null || list.isEmpty() ? null : list.get(0);
  }
}
