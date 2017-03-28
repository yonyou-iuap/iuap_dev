package com.yonyou.uap.billcode.service;


import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.After;
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
import com.yonyou.uap.billcode.entity.PubBcrElem;
import com.yonyou.uap.billcode.entity.PubBcrRulebase;
import com.yonyou.uap.billcode.model.BillCodeRuleVO;
import com.yonyou.uap.billcode.model.IBillCodeElemVO;
import com.yonyou.uap.billcode.repository.PubBcrElemDao;
import com.yonyou.uap.billcode.repository.PubBcrRuleBaseDao;

@ContextConfiguration(locations = { "classpath:billcode-applicationContext-forTest.xml" })
public class BillCodeRuleMgrServiceTest extends AbstractJUnit4SpringContextTests {
	private static Logger logger = LoggerFactory.getLogger(BillCodeRuleMgrServiceTest.class);

	private BillCodeRuleVO codeRuleVO = new BillCodeRuleVO();

	@Autowired
	private BillCodeRuleMgrService billCodeRuleMgrService;

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
		elemDao.deleteAll();
		baseDao.deleteAll();
		
		GenericObjectPoolConfig config = (GenericObjectPoolConfig) this.applicationContext.getBean("zkPoolConfig");
		ZkPool.initPool(config);

	}

	@After
	public void tearDown() throws Exception {
		logger.error("Test End!");
	}

	@Test
	public void testGetBillCodeRuleByRuleCode() {
		
		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);// 前编码
		base.setIseditable("Y");// 可编辑
		base.setIsautofill("Y");
		base.setIsgetpk("N");
		base.setIseditable("N");
		base.setIslenvar("Y");
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElems());
		
		billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);
		
		List<BillCodeRuleVO>  billCodeRuleVOList= billCodeRuleMgrService.getBillCodeRuleByRuleCode(base.getRulecode());
		
		for (int i = 0; i < billCodeRuleVOList.size(); i++) {
			BillCodeRuleVO billCodeRuleVO = billCodeRuleVOList.get(i);
			Assert.assertNotNull(billCodeRuleVO);
		}
	}

	@Test
	public void testGetBillCodeRuleByRenterID() {
		
		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);// 前编码
		base.setIseditable("Y");// 可编辑
		base.setIsautofill("Y");
		base.setIsgetpk("N");
		base.setIseditable("N");
		base.setIslenvar("Y");
		base.setRulecode("20160530-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElems());
		
		billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);
		
		List<BillCodeRuleVO>  billCodeRuleVOList= billCodeRuleMgrService.getBillCodeRuleByRenterID();
		
		for (int i = 0; i < billCodeRuleVOList.size(); i++) {
			BillCodeRuleVO billCodeRuleVO = billCodeRuleVOList.get(i);
			Assert.assertNotNull(billCodeRuleVO);
		}
		
		logger.error("billCodeRuleVOList.size() : " + billCodeRuleVOList.size());
	
	}

	@Test
	public void testDelBillCodeRule() {
		
		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);// 前编码
		base.setIseditable("Y");// 可编辑
		base.setIsautofill("Y");
		base.setIsgetpk("N");
		base.setIseditable("N");
		base.setIslenvar("Y");
		base.setRulecode("20160530-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElems());
		
		billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);
		
		billCodeRuleMgrService.delBillCodeRule(base.getRulecode());
	}

	@Test
	public void testSaveBillCodeRule() {
		
		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);// 前编码
		base.setIseditable("Y");// 可编辑
		base.setIsautofill("Y");
		base.setIsgetpk("N");
		base.setIseditable("N");
		base.setIslenvar("Y");
		base.setRulecode("20160530-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElems());
		
		billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);
		
	}
	
	/**
	 * 初始化编码元素
	 * 
	 */
	private IBillCodeElemVO[] initElems() {

		// 第一个元素是 yyMMdd 形式的系统时间元素
		PubBcrElem elem1 = new PubBcrElem();
		elem1.setDateDisplayFormat(IBillCodeElemVO.DATEFORMAT_YYMMDD);
		elem1.setElemlenth((short) 6);
		elem1.setElemtype((short) IBillCodeElemVO.TYPE_SYSTIME);
		elem1.setEorder((short) 0);
		elem1.setIsrefer((short) IBillCodeElemVO.REF_DAY);
		elem1.setCreatedate(new Timestamp(System.currentTimeMillis()));

		// 第二个元素是6位长度的流水号
		PubBcrElem elem2 = new PubBcrElem();
		elem2.setElemlenth((short) 6);
		elem2.setElemtype((short) IBillCodeElemVO.TYPE_SN);
		elem2.setEorder((short) 1);
		elem2.setIsrefer((short) IBillCodeElemVO.REF_NOT);
		elem2.setCreatedate(new Timestamp(System.currentTimeMillis()));

		// 第三个元素是值为"TAO-"的常量
		PubBcrElem elem3 = new PubBcrElem();
		elem3.setElemlenth((short) 4);
		elem3.setElemvalue("TTT-");
		elem3.setElemtype((short) IBillCodeElemVO.TYPE_CONST);
		elem3.setEorder((short) 2);
		elem3.setIsrefer((short) IBillCodeElemVO.REF_NOT);
		elem3.setCreatedate(new Timestamp(System.currentTimeMillis()));

		// 第四个元素是长度为3的随机码
		PubBcrElem elem4 = new PubBcrElem();
		elem4.setElemlenth((short) 6);
		elem4.setElemtype((short) IBillCodeElemVO.TYPE_RANDOM);
		elem4.setEorder((short) 3);
		elem4.setIsrefer((short) IBillCodeElemVO.REF_NOT);
		elem4.setCreatedate(new Timestamp(System.currentTimeMillis()));

		return new IBillCodeElemVO[] { elem1, elem2, elem3, elem4 };

	}

}
