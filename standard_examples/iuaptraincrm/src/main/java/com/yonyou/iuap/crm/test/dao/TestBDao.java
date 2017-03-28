package com.yonyou.iuap.crm.test.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.crm.test.entity.TestB;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;
import com.yonyou.iuap.persistence.jdbc.framework.util.SQLHelper;

@Repository
public class TestBDao {

    @Autowired
    private MetadataDAO dao;

    public TestB findById(String id) throws DAOException {

        String sql = "select * from iuaptraincrm_TestB  where dr='0' and id=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(id);
        List<TestB> list = dao.queryByClause(TestB.class, sql, sqlparam);
        return list == null || list.isEmpty() ? null : list.get(0);
    }


    public void delete(TestB entity) {

        if (null != entity) {
            dao.remove(entity);
        }
    }

    public Page<TestB> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams)
            throws DAOException {

        String sql = " select * from iuaptraincrm_TestB";
        SQLParameter sqlparam = new SQLParameter();

        if (!searchParams.isEmpty()) {
            sql = sql + " where ";
            for (String key : searchParams.keySet()) {
                sql = sql + FastBeanHelper.getColumn(TestB.class, key) + " like ? AND ";
                sqlparam.addParam("%" + searchParams.get(key) + "%");
            }
            sql = sql.substring(0, sql.length() - 4);
        }
        return dao.queryPage(sql, sqlparam, pageRequest, TestB.class);
    }

}
