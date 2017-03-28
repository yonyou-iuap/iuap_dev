package com.yonyou.iuap.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.category.UnStable;
import org.springside.modules.test.spring.SpringContextTestCase;

import com.yonyou.iuap.context.ContextHolder;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.metadata.spi.Attribute;
import com.yonyou.metadata.spi.AttributeList;
import com.yonyou.metadata.spi.Component;
import com.yonyou.metadata.spi.Entity;
import com.yonyou.metadata.spi.EnumItem;
import com.yonyou.metadata.spi.Enumerate;
import com.yonyou.metadata.spi.EnumitemList;
import com.yonyou.metadata.spi.ITypeEnum;
import com.yonyou.metadata.spi.Relation;
import com.yonyou.metadata.spi.service.ExportMetaDataSqlService;
import com.yonyou.metadata.spi.service.MetadataDynamicUpdateService;
import com.yonyou.metadata.spi.service.MetadataPublishService;
import com.yonyou.metadata.spi.service.MetadataService;
import com.yonyou.metadata.spi.service.ServiceFinder;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
@Category(UnStable.class)
@DirtiesContext
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
    "classpath:applicationContext-cache.xml", "classpath:applicationContext-persistence.xml" })
public class MetadataCacheTest extends SpringContextTestCase {

  public static ApplicationContext context;

  public MetadataCacheTest() {}

  @Before
  public void setUp() throws Exception {

    InvocationInfoProxy.setTenantid("tenant01");

    context = new ClassPathXmlApplicationContext(new String[] { "classpath:applicationContext.xml",
        "classpath:applicationContext-cache.xml", "classpath:applicationContext-persistence.xml" });
    ContextHolder.setContext(context);

    ServiceFinder.findMetadataService();
  }

  @Test
  public void testCachedPublishMetadata() throws Exception {

    Map<String, Object> data = new HashMap<String, Object>();
    final String key = "C:\\Users\\jiaohf\\Desktop\\tmp\\myyilai.bmf";
    Object value = null;
    data.put(key, value);
    data.clear();
    data.put("METADATA_FILES", new ArrayList<String>() {

      {
        add(key);
      }
    });
    String key2 = "ALLOW_LOWEVERSION_PUB";
    Boolean value2 = false;

    data.put(key2, value2);

    MetadataPublishService mps = ServiceFinder.findMetadataService(MetadataPublishService.class);
    for (int i = 0; i < 1000; i++) {
      mps.publishMetaData(data);
    }
  }

  @Test
  public void testgetComponentByTypeFullClassName() throws Exception {

    InvocationInfoProxy.setTenantid("publishtest");
    MetadataService service = ServiceFinder.findMetadataService();
    Component component = service.getComponentByTypeName("Org", null);
    System.out.println(component);
  }

  @Test
  public void testgetRelations() throws Exception {

    InvocationInfoProxy.setTenantid("publishtest");
    MetadataService service = ServiceFinder.findMetadataService();
    Relation relZHUZI =
        service.getRelation("AAAAARR", "Entity1AAAAXXXXAARR", "Entity0AAAAXXAARR", null);
    Assert.assertNotNull(relZHUZI);
  }

  @Test
  public void testbatchGetEntities() throws Exception {

    InvocationInfoProxy.setTenantid("publishtest");
    MetadataService service = ServiceFinder.findMetadataService();
    List<String> ids = new ArrayList<String>();
    ids.add(null);
    ids.add("g");
    ids.add(null);
    ids.add("06903956-3acc-4972-9327-bcf6eeb0a9ca");// 实体
    ids.add("c077b73c-959c-4761-a17c-ded81be3b62b");// 实体
    ids.add("BS000010000100001031");// 基础类型
    ids.add("");// 空
    ids.add("edc3f8ca-cc96-4c32-a359-2024b0d4d7e3");// 枚举
    Entity[] attrs = service.getEntities(ids.toArray(new String[8]), null);
    Assert.assertNotNull(attrs);
  }

  @Test
  public void testgetImplementsAttributes() throws Exception {

    InvocationInfoProxy.setTenantid("publishtest");
    Map<String, Map<Attribute, Attribute>> attrs = null;
    Map<String, Map<Attribute, Attribute>> attrs1 = null;
    MetadataService service = ServiceFinder.findMetadataService();
    attrs = service.getImplementsAttributes("com.iuap.org", "Org", null);
    Assert.assertNotNull(attrs);
  }

  @Test
  public void testgetExtendAttributeCount() throws Exception {

    InvocationInfoProxy.setTenantid("publishtest");
    MetadataService service = ServiceFinder.findMetadataService();
    int count = service.getExtendAttributeCount("Entity0", null);
    Assert.assertSame(2, count);
  }

  @Test
  public void testgetEntityByID() throws Exception {

    InvocationInfoProxy.setTenantid("publishtest");
    MetadataService service = ServiceFinder.findMetadataService();
    for (int i = 0; i < 1000; i++) {
      Entity en = service.getEntityByID("50762419-d502-4dd7-9c5b-24de1cecd3fb", null);
      AttributeList al = en.getAllAttributeList();
      List<Attribute> als = al.getAttributes();
      Assert.assertNotNull(als);
    }
    System.out.println("DDD");
  }

  @Test
  public void testExportMetaData() throws Exception {

    InvocationInfoProxy.setTenantid("publishtest");
    // ExportMetaDataSqlService emdss = new ExportMetaDataSqlServiceImpl();
    ExportMetaDataSqlService emdss =
        ServiceFinder.findMetadataService(ExportMetaDataSqlService.class);

    Map<String, Object> userdata = new HashMap<String, Object>();
    userdata.put("ALLOW_LOWEVERSION_PUB", true);
    userdata.put("DEST_DIR", "C:\\Users\\jiaohf\\Desktop\\123.txt");
    userdata.put("METADATA_FILES", new ArrayList<String>() {

      {
        add("C:\\Users\\jiaohf\\Desktop\\tmp\\new16.bmf");
      }
    });
    emdss.exportMetaDataSqls(userdata);

  }

  @Test
  public void testisImplementBizInterface() throws Exception {

    InvocationInfoProxy.setTenantid("publishtest");
    MetadataService service = ServiceFinder.findMetadataService();
    String entityID = "a6e03ce7-3e53-4fa1-bdd5-464be33da6cf";
    String itfName = "businInterface0";
    boolean result = service.isImplementBizInterface(entityID, itfName, null);
    Assert.assertTrue(result);
    boolean result1 = service.isImplementBizInterface(entityID, itfName, null);
    Assert.assertTrue(result1);
    entityID = "fakeid";
    itfName = null;
    boolean resultF = service.isImplementBizInterface(entityID, itfName, null);
    Assert.assertFalse(resultF);
  }

  @Test
  public void testgetEntityAndFK() throws Exception {

    InvocationInfoProxy.setTenantid("publishtest");
    MetadataService service = ServiceFinder.findMetadataService();
    // 关联关系
    Map<Attribute, Entity> en = service.getEntityAndFK("uap", "Entity0", null);
    Assert.assertNotNull(en);
    // 关联关系
    Map<Attribute, Entity> en1 = service.getEntityAndFK("uap", "Entity0", null);
    Assert.assertNotNull(en1);
    // REF关系
    Map<Attribute, Entity> enref = service.getEntityAndFK("uap", "Entity2", null);
    Assert.assertNotNull(enref);
    // REF关系
    Map<Attribute, Entity> enref1 = service.getEntityAndFK("uap", "Entity2", null);
    Assert.assertNotNull(enref1);
  }

  @Test
  public void testgetBizInterfaceNames() throws Exception {

    InvocationInfoProxy.setTenantid("publishtest");
    MetadataService service = ServiceFinder.findMetadataService();
    List<String> bizItfNames =
        service.getBizInterfaceNames("a6e03ce7-3e53-4fa1-bdd5-464be33da6cf", null);
    List<String> bizItfNames1 =
        service.getBizInterfaceNames("a6e03ce7-3e53-4fa1-bdd5-464be33da6cf", null);
    Assert.assertSame(1, bizItfNames.size());
    Assert.assertSame(1, bizItfNames1.size());
  }

  @Test
  public void testgetEnumerate() throws Exception {

    InvocationInfoProxy.setTenantid("publishtest");
    MetadataService service = ServiceFinder.findMetadataService();
    Enumerate attrs = service.getEnumerate("ieop", "Enumerate0", null);
    Enumerate attrs1 = service.getEnumerate("com.iuap.org", "Gender", null);
    EnumitemList el = attrs.getEnumitemlist();
    List<EnumItem> els = el.getEnumItems();
    Assert.assertNotNull(els);
  }

  /**
   * 测试通过组件ID获取名称空间
   * 
   * @throws Exception
   */
  @Test
  public void testgetNameSpaceByCompID() throws Exception {

    InvocationInfoProxy.setTenantid("publishtest");
    MetadataService service = ServiceFinder.findMetadataService();
    String en = service.getNameSpaceByCompID("05ab4927-03de-4fd0-b118-6f65df6378d0", null);
    Assert.assertNotNull(en);

  }

  @Test
  public void testupdateEntity() throws Exception {

    InvocationInfoProxy.setTenantid("publishtest");
    MetadataDynamicUpdateService imd =
        ServiceFinder.findMetadataService(MetadataDynamicUpdateService.class);

    Entity tmp = new Entity();
    tmp.setId("0077ea52-eb9d-4509-9c82-5c2bf8e3406c");
    String name = "zhu" + new Random().nextInt(10000);
    tmp.setDisplayName(name);
    imd.updateEntity(tmp, null);

    MetadataService service = ServiceFinder.findMetadataService();
    Entity en = service.getEntityByID("0077ea52-eb9d-4509-9c82-5c2bf8e3406c", null);
    Assert.assertEquals(name, en.getDisplayName());

  }

  @Test
  public void testupdateAtt() throws Exception {

    InvocationInfoProxy.setTenantid("publishtest");
    MetadataDynamicUpdateService imd =
        ServiceFinder.findMetadataService(MetadataDynamicUpdateService.class);

    MetadataService service = ServiceFinder.findMetadataService();
    String en = service.getNameSpaceByCompID("05ab4927-03de-4fd0-b118-6f65df6378d0", null);
    Assert.assertNotNull(en);

  }

  @Test
  public void testADD() throws Exception {

    InvocationInfoProxy.setTenantid("publishtest");
    Entity ent = new Entity();
    ent.setTableName("t_teacher" + new Random().nextInt(10000));
    ent.setName("teacher_dyna" + new Random().nextInt(10000));

    AttributeList als = new AttributeList();
    Attribute id = new Attribute();
    id.setDisplayName("id");
    id.setFieldName("dt_id");
    id.setName("dt_id");
    id.setDataType(ITypeEnum.TYPE_MDID.id);
    id.setDataTypeStyle(ITypeEnum.STYLE_SINGLE.type);
    als.getAttributes().add(id);

    Attribute name = new Attribute();
    name.setDisplayName("name");
    name.setFieldName("dname");
    name.setName("dname");
    name.setDataType(ITypeEnum.TYPE_String.id);
    name.setDataTypeStyle(ITypeEnum.STYLE_SINGLE.type);
    als.getAttributes().add(name);

    Attribute age = new Attribute();
    age.setDisplayName("dage");
    age.setFieldName("dage");
    age.setName("dage");
    age.setDataType(ITypeEnum.TYPE_Integer.id);
    age.setDataTypeStyle(ITypeEnum.STYLE_SINGLE.type);
    als.getAttributes().add(age);

    ent.setComponentID("acee2298-3f43-4b15-ba61-04a71dd3b6a3");
    ent.setAttributeList(als);
    MetadataPublishService cmps = ServiceFinder.findMetadataService(MetadataPublishService.class);
    cmps.addDynamicSubEntity(ent, "d7b6a4e4-6d68-4c93-b9b5-55ce5dbcbf8c", null);
  }
}