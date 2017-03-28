package persistence.mybatis;

import java.util.Date;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import com.yonyou.iuap.entity.quotation.IpuQuotation;
import com.yonyou.iuap.entity.quotation.IpuQuotationDetail;
import com.yonyou.iuap.repository.quotation.IpuQuotationDetailMapper;
import com.yonyou.iuap.repository.quotation.IpuQuotationMapper;

public class MybatisMapperTest {
	
	public   ApplicationContext context;
	
	private IpuQuotationMapper mapper;
	
	private IpuQuotationDetailMapper detailMapper;
	
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[]{"classpath:applicationContext.xml","classpath:applicationContext-persistence.xml"});
		mapper = context.getBean(IpuQuotationMapper.class);
		
		detailMapper = context.getBean(IpuQuotationDetailMapper.class);
	
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 普通测试
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMapper() throws Exception {
		IpuQuotation q = mapper.selectByPrimaryKey("1b2d669f-a80a-4334-af17-b8333aa08d5f");
		Assert.notNull(q);
	}
	
	
	@Test
	public void testQueryWithChildren() throws Exception {
		IpuQuotation q = mapper.selectChildrenByPrimaryKey("1b2d669f-a80a-4334-af17-b8333aa08d5f");
		Assert.notNull(q);
	}
	
	@Test
	public void testInsert() throws Exception {
		
		IpuQuotation entity = new IpuQuotation();
		entity.setIpuquotaionid(UUID.randomUUID().toString());
		entity.setDescription("test");
		entity.setBuyoffertime(new Date(System.currentTimeMillis()));
		
		mapper.insert(entity);
		IpuQuotation q = mapper.selectChildrenByPrimaryKey(entity.getIpuquotaionid());
		Assert.notNull(q);
	}
	
	@Test
	public void testDetailQuery() throws Exception {
		String detailId = "2ecea343-1219-45de-9747-6850e504e10c";
		IpuQuotationDetail dt = detailMapper.selectByPrimaryKey(detailId);
		System.out.println(dt.getProductname());
		Assert.notNull(dt);
	}
	
}
