package com.yonyou.uap.billcode.service;

import java.sql.Timestamp;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.yonyou.iuap.generic.adapter.InvocationInfoProxyAdapter;
import com.yonyou.iuap.lock.zkpool.ZkPool;
import com.yonyou.uap.billcode.BillCodeException;
import com.yonyou.uap.billcode.entity.PubBcrElem;
import com.yonyou.uap.billcode.entity.PubBcrRulebase;
import com.yonyou.uap.billcode.model.BillCodeRuleVO;
import com.yonyou.uap.billcode.model.IBillCodeElemVO;
import com.yonyou.uap.billcode.repository.PubBcrElemDao;
import com.yonyou.uap.billcode.repository.PubBcrPrecodeDao;
import com.yonyou.uap.billcode.repository.PubBcrReturnDao;
import com.yonyou.uap.billcode.repository.PubBcrRuleBaseDao;
import com.yonyou.uap.billcode.repository.PubBcrSnDao;

/**
 *
 * 测试zookeeper分布式锁
 * 
 * @author taomk 2016年6月3日 上午10:13:22
 *
 */
@ContextConfiguration(locations = { "classpath:billcode-applicationContext-forTest.xml" })
public class ZKLockTest extends AbstractJUnit4SpringContextTests {
	private static Logger logger = LoggerFactory.getLogger(BillCodeRuleMgrServiceTest.class);

	
	@Autowired
	private BillCodeProviderService billCodeProviderService;
	
	@Autowired
	private BillCodeRuleMgrService billCodeRuleMgrService;

	@Autowired
	private PubBcrPrecodeDao preDao;

	@Autowired
	private PubBcrReturnDao rtnDao;

	@Autowired
	private PubBcrSnDao snDao;

	@Autowired
	private PubBcrElemDao elemDao;

	@Autowired
	private PubBcrRuleBaseDao baseDao;
	
	@Before
	public void setUp() throws Exception {

		logger.error("Test Start!");

		InvocationInfoProxyAdapter.setTenantid("tenantid-test-taomk");
		InvocationInfoProxyAdapter.setSysid("sysid-test-taomk-001");
		
		// 清空数据库
		preDao.deleteAll();
		rtnDao.deleteAll();
		snDao.deleteAll();
		elemDao.deleteAll();
		baseDao.deleteAll();
		
		GenericObjectPoolConfig config = (GenericObjectPoolConfig) this.applicationContext.getBean("zkPoolConfig");
		ZkPool.initPool(config);
	}

	@Test
	public void testZKLock() throws InterruptedException {

		BillCodeRuleVO codeRuleVO = new BillCodeRuleVO();
		
		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);
		base.setIseditable("N");
		base.setIsautofill("Y");
		base.setIsgetpk("N");// 随机码模式
		base.setIslenvar("Y");
		base.setRulecode("20160603-0001");
		base.setRulename("For test");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElems());

		try {
			
			boolean isSaved = billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);
			
			if(isSaved){
				
				String[] batchBillCodes = billCodeProviderService.getBatchBillCodes(codeRuleVO, null, null, null, 10);
				for (String batchBillCode : batchBillCodes) {
					logger.error(batchBillCode);
				}
			}else{
				Assert.fail();
			}
			
		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}
	
	/**
	 * 初始化编码元素
	 * 
	 */
	private IBillCodeElemVO[] initElems() {

		// 第一个元素是 yyMMdd 形式的系统时间元素
		PubBcrElem elem1 = new PubBcrElem();
		elem1.setDateDisplayFormat(IBillCodeElemVO.DATEFORMAT_YYMMDD);
		elem1.setElemlenth((short) 1);
		elem1.setElemtype((short) IBillCodeElemVO.TYPE_SYSTIME);
		elem1.setEorder((short) 0);
		elem1.setIsrefer((short) IBillCodeElemVO.REF_DAY);
		elem1.setCreatedate(new Timestamp(System.currentTimeMillis()));

		// 第二个元素是6位长度的流水号
		PubBcrElem elem2 = new PubBcrElem();
		elem2.setElemlenth((short) 6);
		elem2.setElemtype((short) IBillCodeElemVO.TYPE_SN);
		elem2.setEorder((short) 2);
		elem2.setIsrefer((short) IBillCodeElemVO.REF_NOT);
		elem2.setCreatedate(new Timestamp(System.currentTimeMillis()));

		// 第三个元素是值为"TTT-"的常量
		PubBcrElem elem3 = new PubBcrElem();
		elem3.setElemlenth((short) 4);
		elem3.setElemvalue("TTT-");
		elem3.setElemtype((short) IBillCodeElemVO.TYPE_CONST);
		elem3.setEorder((short) 0);
		elem3.setIsrefer((short) IBillCodeElemVO.REF_NOT);
		elem3.setCreatedate(new Timestamp(System.currentTimeMillis()));

		return new IBillCodeElemVO[] { elem1, elem2, elem3};

	}
}
