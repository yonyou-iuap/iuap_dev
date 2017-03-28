package test.metadata.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.category.UnStable;
import org.springside.modules.test.spring.SpringContextTestCase;

import com.yonyou.iuap.context.ContextHolder;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.entity.meta.Dept;
import com.yonyou.iuap.example.entity.meta.SysUserJob;
import com.yonyou.iuap.example.repository.meta.SysUserJobDao;
import com.yonyou.iuap.persistence.bs.dao.DAOException;

@Category(UnStable.class)
@DirtiesContext
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
    "classpath:applicationContext-cache.xml", "classpath:applicationContext-persistence.xml",
    "classpath:applicationContext-metadata.xml" })
public class TestSysUserJobDao extends SpringContextTestCase {

  private static final String TENANTID_LOCAL_PG = "ec";

  public static String CURRENT_TENANTID = TENANTID_LOCAL_PG;

  public static ApplicationContext context;

  @Autowired
  private SysUserJobDao sysUserJobDAO;

  @Before
  public void setUp() throws Exception {

    InvocationInfoProxy.setTenantid(CURRENT_TENANTID);

    context = new ClassPathXmlApplicationContext(new String[] { "classpath:applicationContext.xml",
        "classpath:applicationContext-cache.xml", "classpath:applicationContext-persistence.xml",
        "classpath:applicationContext-metadata.xml" });
    ContextHolder.setContext(context);

  }

//  @Test
//  public void testfindById() throws DAOException {
//
//    String id = "601256d2-9583-4944-96a4-942c8d6a6ac6";
//    SysUserJob sysusr = sysUserJobDAO.findById(id);
//    System.out.println(sysusr);
//
//  }

  
  @Test
  public void testCopy(){
	  Dept d = new Dept() ;
	  d.setDeptcode("dddd");
	  Dept d2 = new Dept();
	  BeanUtils.copyProperties(d, d2);
	  System.out.print(d2.getDeptcode());
  }
  
}
