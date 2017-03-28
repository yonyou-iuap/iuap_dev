package test.metadata.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.category.UnStable;
import org.springside.modules.test.spring.SpringContextTestCase;

import com.yonyou.iuap.context.ContextHolder;
import com.yonyou.iuap.example.entity.meta.SysDictTypeMeta;
import com.yonyou.iuap.example.repository.meta.SysDictTypeDao;
import com.yonyou.iuap.persistence.bs.dao.DAOException;

@Category(UnStable.class)
@DirtiesContext
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
    "classpath:applicationContext-cache.xml", "classpath:applicationContext-persistence.xml",
    "classpath:applicationContext-metadata.xml" })
public class TestDictTypeMeta extends SpringContextTestCase {

  public static ApplicationContext context;

  @Autowired
  private SysDictTypeDao sdtDAO;

  @Before
  public void setUp() throws Exception {

    context = new ClassPathXmlApplicationContext(new String[] { "classpath:applicationContext.xml",
        "classpath:applicationContext-cache.xml", "classpath:applicationContext-persistence.xml",
        "classpath:applicationContext-metadata.xml" });
    ContextHolder.setContext(context);

  }

  @Test
  public void testInsert() throws DAOException {

    SysDictTypeMeta dt = new SysDictTypeMeta();
    dt.setDictid(UUID.randomUUID().toString());
    dt.setDicttypename("T字典名称");
    dt.setDicttypecode("T字典编码");

    sdtDAO.insert(dt);
  }

  @Test
  public void testdeleteByPrimaryKey() throws DAOException {

    SysDictTypeMeta dt = sdtDAO.queryByCode("T字典编码");

    sdtDAO.deleteByPrimaryKey(dt.getDictid());
  }

  @Test
  public void testUpdate() throws DAOException {

    SysDictTypeMeta dt = sdtDAO.queryByCode("T字典编码");
    dt.setModifier("修改者");

    sdtDAO.updateByPrimaryKey(dt);
  }

  @Test
  public void testSelectAllByPage() throws DAOException {

    PageRequest page = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "dict_id"));
    Page<SysDictTypeMeta> result = sdtDAO.selectAllByPage(page, null);
    System.out.print(result);
  }

  @Test
  public void testBatchInsert() throws DAOException {

    List<SysDictTypeMeta> sdtmLst = new ArrayList<>();
    for (int i = 0; i < 10; i++) {

      SysDictTypeMeta s = new SysDictTypeMeta();
      s.setDictid(UUID.randomUUID().toString());
      s.setDicttypename(i + "T字典名称");
      s.setDicttypecode(i + "T字典编码");
      sdtmLst.add(s);
    }
    sdtDAO.batchInsert(sdtmLst);
  }

  @Test
  public void testBatchUpdate() throws DAOException {

    List<SysDictTypeMeta> sdtmLst = new ArrayList<>();
    for (int i = 0; i < 10; i++) {

      SysDictTypeMeta s = sdtDAO.queryByCode(i + "T字典编码");
      s.setModifier(i + "修改者");
      sdtmLst.add(s);
    }
    sdtDAO.batchUpdate(sdtmLst);
  }

  @Test
  public void testSelectByPrimaryKey() throws DAOException {

    SysDictTypeMeta sdtm = sdtDAO.selectByPrimaryKey("9024f11a-95cf-4e4e-8aeb-4c51cd5cbf7a");
    System.out.println(sdtm.getDicttypecode());
  }

  @Test
  public void testBatchDelete() throws DAOException {

    List<SysDictTypeMeta> sdtmLst = new ArrayList<>();
    for (int i = 0; i < 10; i++) {

      SysDictTypeMeta s = sdtDAO.queryByCode(i + "T字典编码");
      sdtmLst.add(s);
    }
    sdtDAO.batchDeleteByPrimaryKey(sdtmLst);
  }
}
