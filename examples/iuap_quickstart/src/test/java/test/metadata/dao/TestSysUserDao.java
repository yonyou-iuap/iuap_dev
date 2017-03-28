package test.metadata.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.category.UnStable;
import org.springside.modules.test.spring.SpringContextTestCase;

import com.yonyou.iuap.context.ContextHolder;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.entity.meta.SysUser;
import com.yonyou.iuap.example.repository.meta.SysUserDao;
import com.yonyou.iuap.persistence.bs.dao.DAOException;

@Category(UnStable.class)
@DirtiesContext
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
    "classpath:applicationContext-cache.xml", "classpath:applicationContext-persistence.xml",
    "classpath:applicationContext-metadata.xml" })
public class TestSysUserDao extends SpringContextTestCase {

  private static final String TENANTID_LOCAL_PG = "ec";

  public static String CURRENT_TENANTID = TENANTID_LOCAL_PG;

  public static ApplicationContext context;

  @Autowired
  private SysUserDao sysUserDAO;

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
//    String id = "1cdc7067-a293-4b6d-8db0-ba59235601cc";
//    SysUser sysusr = sysUserDAO.findById(id);
//    System.out.println(sysusr);
//  }

//  @Test
//  public void testgetTsById() throws DAOException {
//
//    String id = "1cdc7067-a293-4b6d-8db0-ba59235601cc";
//    Date da = sysUserDAO.findTsById(id);
//    System.out.println(da);
//  }

//  @Test
//  public void testfindByLoginName() throws DAOException {
//
//    String loginName = "00045";
//    SysUser da = sysUserDAO.findByLoginName(loginName);
//    System.out.println(da);
//
//  }

//  @Test
//  public void testfindUserByUserCode() throws DAOException {
//
//    String userCode = "00044";
//    SysUser da = sysUserDAO.findByUserCode(userCode);
//    System.out.println(da);
//
//  }

//  @Test
//  public void testfindUserByUserEmail() throws DAOException {
//
//    String userEmail = "lfjia@xiangyu.cn";
//    SysUser da = sysUserDAO.findByUserEmail(userEmail);
//    System.out.println(da);
//  }

//  @Test
//  public void testfindUserByUserMobile() throws DAOException {
//
//    String userMobile = "110";
//    SysUser da = sysUserDAO.findByUserMobile(userMobile);
//    System.out.println(da);
//  }
 

  public SysUserDao getSysUserDAO() {

    return sysUserDAO;
  }

  public void setSysUserDAO( SysUserDao sysUserDAO ) {

    this.sysUserDAO = sysUserDAO;
  }

  
}
