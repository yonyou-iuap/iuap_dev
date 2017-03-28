package com.yonyou.iuap.persistence;

import com.yonyou.iuap.entity.uapjdbc.UapIpuQuotation;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.processor.BeanListProcessor;
import com.yonyou.iuap.persistence.jdbc.framework.processor.BeanProcessor;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengxs on 2016/1/5.
 */
//@TransactionConfiguration(defaultRollback = false)

@ContextConfiguration(
		locations = { 
			"classpath:applicationContext.xml",
			"classpath:applicationContext-jdbc.xml"
		}
	)
@TransactionConfiguration(defaultRollback = true)
public class BaseDAOTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private BaseDAO baseDAO;

    @Autowired
    private JdbcTemplate jt;

    @Test
    public void testUpdate() throws DAOException {
    	UapIpuQuotation entity = baseDAO.queryByPK(UapIpuQuotation.class, "5c30747c-c7e2-404b-b0b8-345d9bec8edb");
    	entity.setDescription("basedaotest");
    	baseDAO.update(entity,new String[]{"description"});
        System.out.println(entity.getDescription());
        Assert.assertEquals("basedaotest",entity.getDescription() );
    }



    @Test
    public void testUpdate2() throws DAOException {
        UapIpuQuotation quotation = baseDAO.queryByPK(UapIpuQuotation.class, "1b2d669f-a80a-4334-af17-b8333aa08d5f");
        quotation.setDescription("savelist1");
        UapIpuQuotation quotation2 = baseDAO.queryByPK(UapIpuQuotation.class, "fa5dec1d-9e8a-4da4-9cc2-4751ae3f46f8");
        quotation2.setDescription("savelist2");
        List<UapIpuQuotation> list = new ArrayList<UapIpuQuotation>();
        list.add(quotation);
        list.add(quotation2);
        baseDAO.update(list, new String[]{"description"});
        
        UapIpuQuotation result= baseDAO.queryByPK(UapIpuQuotation.class,quotation.getIpuquotaionid());
        Assert.assertEquals("savelist1", result.getDescription());
        
        
    }


    @Test
    public void testSave() throws DAOException {
        UapIpuQuotation quotation = new UapIpuQuotation();
        quotation.setDescription("basedao save");
        baseDAO.insert(quotation);
        UapIpuQuotation result= baseDAO.queryByPK(UapIpuQuotation.class,quotation.getIpuquotaionid());
        Assert.assertEquals("basedao save", result.getDescription());

    }

    @Test
    public void testSaveList() throws DAOException, InvocationTargetException, IllegalAccessException {
        UapIpuQuotation quotation1 = new UapIpuQuotation();
        quotation1.setDescription("savelist1");
        UapIpuQuotation quotation2 = new UapIpuQuotation();
        quotation2.setDescription("savelist2");

        List<UapIpuQuotation> list = new ArrayList<UapIpuQuotation>();
        list.add(quotation1);
        list.add(quotation2);
        baseDAO.insert(list);
        UapIpuQuotation result= baseDAO.queryByPK(UapIpuQuotation.class,quotation1.getIpuquotaionid());
        Assert.assertEquals("savelist1", result.getDescription());
    }


//    @Test
//    public void testDelete() throws DAOException {
//        final UapIpuQuotation ipuQuotation = new UapIpuQuotation();
//        ipuQuotation.setIpuquotaionid("4b5dffb5-50ce-439c-b3a3-96fc8c1bd8cc");
//        final UapIpuQuotation ipuQuotation2 = new UapIpuQuotation();
//        ipuQuotation2.setIpuquotaionid("f7c3e4eb-4010-4c9d-b8e0-a32982839f64");
//        baseDAO.remove(new ArrayList<Object>() {{
//            add(ipuQuotation);
//            add(ipuQuotation2);
//        }});
//    }

    @Test
    public void testQueryAll() throws DAOException {
        List<UapIpuQuotation> quotation = baseDAO.queryAll(UapIpuQuotation.class);
        System.out.println(quotation.size());
        Assert.assertNotNull(quotation);
    }

    @Test
    public void testQueryByClause2() throws DAOException {
        SQLParameter parameter = new SQLParameter();
        parameter.addParam("test");
        List<UapIpuQuotation> ipuQuotations = baseDAO.queryByClause(UapIpuQuotation.class, "select * from ipuquotation" +
                " where description=?", parameter);
        System.out.println(ipuQuotations.size());
        Assert.assertNotNull(ipuQuotations);
    }


    @Test
    public void testQueryForList2() throws DAOException {
        SQLParameter parameter = new SQLParameter();
        parameter.addParam("%test%");
        List<UapIpuQuotation> ipuQuotations = baseDAO.queryForList("select * from ipuquotation" +
                " where description like ?", parameter, new BeanListProcessor(UapIpuQuotation.class));
        System.out.println(ipuQuotations.size());
        Assert.assertNotNull(ipuQuotations);
    }


}
