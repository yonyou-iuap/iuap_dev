package com.yonyou.iuap.persistence.trans;

import com.yonyou.iuap.persistence.bs.mw.sqltrans.TranslateToOracle;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zengxs on 2016/1/20.
 */
public class TestOracleTranser {

    private TranslateToOracle mySQLTranser = new TranslateToOracle();


    @Test
    public void transLimitNest() throws Exception {
        String sql = "select * from  (select * from table limit 10) where 1=1";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void transLimit() throws Exception {
        String sql = "select * from table limit 10";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void testTransISNULL() throws Exception {
        String sql = "select isnull(null,1)";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void testTransLen() throws Exception {
        String sql = "select len('testlength')";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);

    }

    @Test
    public void testlLRtrimCase() throws Exception {
        String sql = "select ltrim_case(' testlength'),rtrim_case('testrtrim ')";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void testPI() throws Exception {
        String sql = "select PI()";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void testOracleResove() throws Exception {
        String sql = "SELECT /*+ rowid(dda) */ count(*) from bsd_ticket where rowid between 'AABNYBAASAAD4iCAAA' and 'AABNYBAAkAAENJaAAM';";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void testMod() throws Exception {
        String sql = "select 1 % 3 from table where a like '1 % 3'";
        String destSQL = getSQL(sql).trim();
    }

    @Test
    public void testAnd() throws Exception {
        String sql = "select * from table where a in (a&&B&&c&&d)";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void testGetDate() throws Exception {
        String sql = "select getDate()";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void testLike() throws Exception {
        String sql = "select * from table where a like '%a_b%'";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void testTop() throws Exception {
        String sql = "select top 10 * from table where a like '%a_b%'";
        String destSQL = getSQL(sql).trim();
    }

    @Test
    public void testNestSelect() throws Exception {
        String sql = "select * from (select top 10 * from table where a like '%a_b%')";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void testOrderBy() throws Exception {
        String sql = "select * from (select top 10 * from table where a like '%a_b%' order by a desc)";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void testFuncSquare() throws Exception {
        String sql = "select square(2)";
        String destSQL = getSQL(sql).trim();
    }

    @Test
    public void testFuncPatindex() throws Exception {
        String sql = "select patindex('%teststr%','test')";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void testFuncDateAddYearNow() throws Exception {
        String sql = "select dateadd(yyyy,2,sysdate)";
        String destSQL = getSQL(sql).trim();
    }

    @Test
    public void testFuncDateAddYear() throws Exception {
        String sql = "select dateadd(yyyy,2,'2012-03-28')";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void testFuncDateAddMonthNow() throws Exception {
        String sql = "select dateadd(mm,2,sysdate)";
        String destSQL = getSQL(sql).trim();
    }

    @Test
    public void testFuncDateAddMonth() throws Exception {
        String sql = "select dateadd(mm,2,'2012-03-28')";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void testFuncDateAddDayNow() throws Exception {
        String sql = "select dateadd(dd,2,sysdate)";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void testFuncDateAddDay() throws Exception {
        String sql = "select dateadd(dd,2,'2012-03-28')";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void testFuncDateDiffYear() throws Exception {
        String sql = "select datediff(yyyy,sysdate,'2012-02-12')";
        String destSQL = getSQL(sql).trim();
    }

    @Test
    public void testFuncDateDiffMonth() throws Exception {
        String sql = "select datediff(mm,sysdate,'2012-02-12')";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }

    @Test
    public void testFuncDateDiffDay() throws Exception {
        String sql = "select datediff(dd,sysdate,'2012-02-12')";
        String destSQL = getSQL(sql).trim();
        System.out.println(destSQL);
    }


    private String getSQL(String sql) throws Exception {
        mySQLTranser.setSql(sql);
        return mySQLTranser.getSql();
    }


}
