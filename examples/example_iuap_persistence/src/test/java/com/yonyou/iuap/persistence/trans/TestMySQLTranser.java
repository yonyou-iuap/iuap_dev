package com.yonyou.iuap.persistence.trans;

import com.yonyou.iuap.persistence.bs.mw.sqltrans.TranslateToMySQL;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zengxs on 2016/1/20.
 */
public class TestMySQLTranser {

    private TranslateToMySQL mySQLTranser = new TranslateToMySQL();

    @Test
    public void testTransISNULL() throws Exception {
        String sql = "select isnull(null,1)";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals("select ifnull( null, 1 )", destSQL);
    }

    @Test
    public void testTransLen() throws Exception {
        String sql = "select len('testlength')";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals("select length( 'testlength' )", destSQL);

    }

    @Test
    public void testlLRtrimCase() throws Exception {
        String sql = "select ltrim_case(' testlength'),rtrim_case('testrtrim ')";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals("select ltrim ( ' testlength' ), rtrim ( 'testrtrim ' )", destSQL);
    }

    @Test
    public void testPI() throws Exception {
        String sql = "select PI()";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals("select 3.1415926535897931", destSQL);
    }

    @Test
    public void testOracleResove() throws Exception {
        String sql = "SELECT /*+ rowid(dda) */ count(*) from bsd_ticket where rowid between 'AABNYBAASAAD4iCAAA' and 'AABNYBAAkAAENJaAAM';";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(
                "SELECT /*+ rowid ( dda )  */  count( * ) from bsd_ticket where rowid between 'AABNYBAASAAD4iCAAA' " +
                        "and 'AABNYBAAkAAENJaAAM'",
                destSQL);
    }

    @Test
    public void testMod() throws Exception {
        String sql = "select 1 % 3 from table where a like '1 % 3'";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL, "select mod(1,3) from table where a like '1 % 3'");
    }

    @Test
    public void testAnd() throws Exception {
        String sql = "select * from table where a in (a&&B&&c&&d)";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL, "select * from table where a in ( a, B, c, d )");
    }

    @Test
    public void testGetDate() throws Exception {
        String sql = "select getDate()";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL, "select now()");
    }

    @Test
    public void testLike() throws Exception {
        String sql = "select * from table where a like '%a_b%'";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL, "select * from table where a like '%a_b%'");
    }

    @Test
    public void testTop() throws Exception {
        String sql = "select top 10 * from table where a like '%a_b%'";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL, "select * from table where a like '%a_b%' limit 10");
    }

    @Test
    public void testNestSelect() throws Exception {
        String sql = "select * from (select top 10 * from table where a like '%a_b%') t";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL, "select * from ( select * from table where a like '%a_b%' limit 10 ) t");
    }

    @Test
    public void testOrderBy() throws Exception {
        String sql = "select * from (select top 10 * from table where a like '%a_b%' order by a desc) t";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL,
                "select * from ( select * from table where a like '%a_b%' order by a desc limit 10 ) t");
    }

    @Test
    public void testFuncSquare() throws Exception {
        String sql = "select square(2)";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL, "select power ( 2, 2 )");
    }

    @Test
    public void testFuncPatindex() throws Exception {
        String sql = "select patindex('%es%','testest')";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL, "select instr ( 'testest', 'es' )");
    }

    @Test
    public void testFuncDateAddYearNow() throws Exception {
        String sql = "select dateadd(yyyy,2,sysdate)";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL, "select DATE_ADD( date_format(now(),'%Y-%m-%d') , INTERVAL 2 YEAR)");
    }

    @Test
    public void testFuncDateAddYear() throws Exception {
        String sql = "select dateadd(yyyy,2,'2012-03-28')";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL, "select DATE_ADD( date_format('2012-03-28','%Y-%m-%d') , INTERVAL 2 YEAR)");
    }

    @Test
    public void testFuncDateAddMonthNow() throws Exception {
        String sql = "select dateadd(mm,2,sysdate)";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL, "select DATE_ADD( date_format(now(),'%Y-%m-%d') , INTERVAL 2 MONTH)");
    }

    @Test
    public void testFuncDateAddMonth() throws Exception {
        String sql = "select dateadd(mm,2,'2012-03-28')";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL, "select DATE_ADD( date_format('2012-03-28','%Y-%m-%d') , INTERVAL 2 MONTH)");
    }

    @Test
    public void testFuncDateAddDayNow() throws Exception {
        String sql = "select dateadd(dd,2,sysdate)";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL, "select DATE_ADD( date_format(now(),'%Y-%m-%d') , INTERVAL 2 DAY)");
    }

    @Test
    public void testFuncDateAddDay() throws Exception {
        String sql = "select dateadd(dd,2,'2012-03-28')";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL, "select DATE_ADD( date_format('2012-03-28','%Y-%m-%d') , INTERVAL 2 DAY)");
    }

    @Test
    public void testFuncDateDiffYear() throws Exception {
        String sql = "select datediff(yyyy,sysdate,'2012-02-12')";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL, "select PERIOD_DIFF(date_format('2012-02-12','%Y'),date_format(now(),'%Y'))");
    }

    @Test
    public void testFuncDateDiffMonth() throws Exception {
        String sql = "select datediff(mm,sysdate,'2012-02-12')";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL, "select PERIOD_DIFF(date_format('2012-02-12','%Y%m'),date_format(now(),'%Y%m'))");
    }

    @Test
    public void testFuncDateDiffDay() throws Exception {
        String sql = "select datediff(dd,sysdate,'2012-02-12')";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL,
                "select PERIOD_DIFF(date_format('2012-02-12','%Y%m%d'),date_format(now(),'%Y%m%d'))");
    }


    @Test
    public void testSubStr() throws Exception {
        String sql = "select substr('test',1,2)";
        String destSQL = getSQL(sql).trim();
        Assert.assertEquals(destSQL, "select substr( 'test', 1, 2 )");
    }

    private String getSQL(String sql) throws Exception {
        System.out.println("origin: " + sql);
        mySQLTranser.setSql(sql);
        String destSQL = mySQLTranser.getSql();
        System.out.println("destSQL: " + destSQL);
        return destSQL;
    }


}
