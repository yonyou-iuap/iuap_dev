package com.yonyou.iuap.persistence.trans;

import com.yonyou.iuap.persistence.bs.mw.sqltrans.TranslateToSqlServer;
import org.junit.Test;

/**
 * Created by zengxs on 2016/1/20.
 */
public class TestSQLServerTranser {

    private TranslateToSqlServer mySQLTranser = new TranslateToSqlServer();

    @Test
    public void transLimitNest() throws Exception {
        String sql = "select * from  (select * from table limit 10) where 1=1";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void transLimit() throws Exception {
        String sql = "select * from  (select * from table limit 10) where 1=1";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    private String getSQL(String sql) throws Exception {
        mySQLTranser.setSql(sql);
        return mySQLTranser.getSql();
    }


}
