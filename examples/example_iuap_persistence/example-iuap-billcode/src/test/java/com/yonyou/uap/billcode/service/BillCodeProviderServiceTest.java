package com.yonyou.uap.billcode.service;


import java.sql.Timestamp;

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
import com.yonyou.iuap.lock.exception.LockException;
import com.yonyou.iuap.lock.zkpool.ZkPool;
import com.yonyou.uap.billcode.BillCodeContext;
import com.yonyou.uap.billcode.BillCodeException;
import com.yonyou.uap.billcode.elemproc.result.BillCodeElemInfo;
import com.yonyou.uap.billcode.entity.PubBcrElem;
import com.yonyou.uap.billcode.entity.PubBcrRulebase;
import com.yonyou.uap.billcode.model.BillCodeRuleVO;
import com.yonyou.uap.billcode.model.IBillCodeElemVO;
import com.yonyou.uap.billcode.repository.PubBcrElemDao;
import com.yonyou.uap.billcode.repository.PubBcrPrecodeDao;
import com.yonyou.uap.billcode.repository.PubBcrReturnDao;
import com.yonyou.uap.billcode.repository.PubBcrRuleBaseDao;
import com.yonyou.uap.billcode.repository.PubBcrSnDao;

@ContextConfiguration(locations = { "classpath:billcode-applicationContext-forTest.xml" })
public class BillCodeProviderServiceTest extends AbstractJUnit4SpringContextTests {
	private static Logger logger = LoggerFactory.getLogger(BillCodeProviderServiceTest.class);

	private BillCodeRuleVO codeRuleVO = new BillCodeRuleVO();

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
	public void setRuleVO() throws Exception {

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

	@After
	public void tearDown() throws Exception {
		logger.error("Test End!");
	}

	// TODO ==========获取编码规则上下文（属性）==========
	/**
	 * 返回编码规则上下文（前编码、可编辑）
	 * 
	 */
	@Test
	public void testGetBillCodeContext_1() {
		try {

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

			// 执行测试对象
			BillCodeContext context = billCodeProviderService.getBillCodeContext(codeRuleVO);

			Assert.assertTrue(codeRuleVO.getBaseVO().getBolIsEditable() == context.isEditable());// 生成的编码是否可以编辑

			boolean infactPrecodeValue = codeRuleVO.getBaseVO().getStrCodemode().equals(PubBcrRulebase.STYLE_PRE) ? true
					: false;
			Assert.assertTrue(infactPrecodeValue == context.isPrecode());// 是否前编码

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 返回编码规则上下文（后编码、可编辑）
	 */
	@Test
	public void testGetBillCodeContext_2() {
		try {

			// 初始化codeRuleVO
			PubBcrRulebase base = new PubBcrRulebase();
			base.setCodemode(PubBcrRulebase.STYLE_AFT);// 后编码
			base.setIseditable("Y");// 可编辑
			base.setIsautofill("Y");
			base.setIsgetpk("N");
			base.setIseditable("N");
			base.setIslenvar("Y");
			base.setRulecode("20160505-0001");
			base.setRulename("for test");
			base.setCreatedate(new Timestamp(System.currentTimeMillis()));
			base.setPkbillobj("101");

			codeRuleVO.setBaseVO(base);

			// 执行测试对象
			BillCodeContext context = billCodeProviderService.getBillCodeContext(codeRuleVO);

			Assert.assertTrue(codeRuleVO.getBaseVO().getBolIsEditable() == context.isEditable());// 生成的编码是否可以编辑

			boolean infactPrecodeValue = codeRuleVO.getBaseVO().getStrCodemode().equals(PubBcrRulebase.STYLE_PRE) ? true
					: false;
			Assert.assertTrue(infactPrecodeValue == context.isPrecode());// 是否前编码

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 返回编码规则上下文（前编码、不可编辑）
	 */
	@Test
	public void testGetBillCodeContext_3() {
		try {

			// 初始化codeRuleVO
			PubBcrRulebase base = new PubBcrRulebase();
			base.setCodemode(PubBcrRulebase.STYLE_PRE);// 前编码
			base.setIseditable("N");// 不可编辑
			base.setIsautofill("Y");
			base.setIsgetpk("N");
			base.setIseditable("N");
			base.setIslenvar("Y");
			base.setRulecode("20160505-0001");
			base.setRulename("for test");
			base.setCreatedate(new Timestamp(System.currentTimeMillis()));
			base.setPkbillobj("101");

			codeRuleVO.setBaseVO(base);

			// 执行测试对象
			BillCodeContext context = billCodeProviderService.getBillCodeContext(codeRuleVO);

			Assert.assertTrue(codeRuleVO.getBaseVO().getBolIsEditable() == context.isEditable());// 生成的编码是否可以编辑

			boolean infactPrecodeValue = codeRuleVO.getBaseVO().getStrCodemode().equals(PubBcrRulebase.STYLE_PRE) ? true
					: false;
			Assert.assertTrue(infactPrecodeValue == context.isPrecode());// 是否前编码

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 返回编码规则上下文（后编码、不可编辑）
	 */
	@Test
	public void testGetBillCodeContext_4() {
		try {

			// 初始化codeRuleVO
			PubBcrRulebase base = new PubBcrRulebase();
			base.setCodemode(PubBcrRulebase.STYLE_PRE);// 后编码
			base.setIseditable("N");// 不可编辑
			base.setIsautofill("Y");
			base.setIsgetpk("N");
			base.setIseditable("N");
			base.setIslenvar("Y");
			base.setRulecode("20160505-0001");
			base.setRulename("for test");
			base.setCreatedate(new Timestamp(System.currentTimeMillis()));
			base.setPkbillobj("101");

			codeRuleVO.setBaseVO(base);

			// 执行测试对象
			BillCodeContext context = billCodeProviderService.getBillCodeContext(codeRuleVO);

			Assert.assertTrue(codeRuleVO.getBaseVO().getBolIsEditable() == context.isEditable());// 生成的编码是否可以编辑

			boolean infactPrecodeValue = codeRuleVO.getBaseVO().getStrCodemode().equals(PubBcrRulebase.STYLE_PRE) ? true
					: false;
			Assert.assertTrue(infactPrecodeValue == context.isPrecode());// 是否前编码

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	// TODO ==========生成一个前编码==========

	/**
	 * 生成一个前编码（rulevo为null）
	 */
	@Test
	public void testGetPreBillCode_1() {
		try {

			String preBillCode = billCodeProviderService.getPreBillCode(null, null, null);
			Assert.assertNull(preBillCode);
		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 生成一个前编码（rulevo是随机码模式）
	 * @throws LockException 
	 */
	@Test
	public void testGetPreBillCode_2() throws LockException {
		try {

			// 初始化codeRuleVO
			PubBcrRulebase base = new PubBcrRulebase();
			base.setCodemode(PubBcrRulebase.STYLE_PRE);
			base.setIseditable("N");// 是否可编辑
			base.setIsautofill("Y");// 自动补位
			base.setIsgetpk("Y");// 随机码模式
			base.setIslenvar("Y");// 随机码位自动补零
			base.setRulecode("20160505-0001");
			base.setRulename("for test");
			base.setCreatedate(new Timestamp(System.currentTimeMillis()));
			base.setPkbillobj("101");

			codeRuleVO.setBaseVO(base);

			String preBillCode = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			logger.error("preBillCode : " + preBillCode);
			Assert.assertNotNull(preBillCode);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 生成一个前编码（rulevo是后编码模式）
	 * @throws LockException 
	 */
	@Test
	public void testGetPreBillCode_3() throws LockException {

		try {

			// 初始化codeRuleVO
			PubBcrRulebase base = new PubBcrRulebase();
			base.setCodemode(PubBcrRulebase.STYLE_AFT);// 后编码模式
			base.setIseditable("N");// 规则是否可编辑
			base.setIsautofill("Y");// 自动补位，保证编码连续
			base.setIsgetpk("N");// 随机码模式
			base.setIslenvar("Y");// 随机码位自动补零
			base.setRulecode("20160505-0001");
			base.setRulename("for test");
			base.setCreatedate(new Timestamp(System.currentTimeMillis()));
			base.setPkbillobj("101");

			codeRuleVO.setBaseVO(base);
			codeRuleVO.setElems(initElems());

			// expectedExecption.expectMessage(substring);

			billCodeProviderService.getPreBillCode(codeRuleVO, null, null);

		} catch (BillCodeException e) {
			String infactExceptionMsg = "当前的编码规则 : " + codeRuleVO + " 是后编码模式，不能生成前编码！";
			Assert.assertEquals(e.getMessage(), infactExceptionMsg);

		}
	}

	/**
	 * 生成一个前编码（数据库中不存在传入的rulevo）
	 * @throws LockException 
	 */
	@Test
	public void testGetPreBillCode_4() throws LockException {

		try {

			// 初始化codeRuleVO
			PubBcrRulebase base = new PubBcrRulebase();
			base.setCodemode(PubBcrRulebase.STYLE_PRE);
			base.setIseditable("N");
			base.setIsautofill("Y");
			base.setIsgetpk("N");
			base.setIslenvar("Y");
			base.setRulecode("20160526-0001");
			base.setRulename("for test");
//			base.setRenterid("YY-20160526001");
//			base.setSysid("sysid-20160526001");
			base.setCreatedate(new Timestamp(System.currentTimeMillis()));
			base.setPkbillobj("101");

			codeRuleVO.setBaseVO(base);
			codeRuleVO.setElems(initElems());

			// 需将先将编码规则VO保存到数据库中去
			billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);

			// 执行测试对象
			String preBillCode = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			logger.error("preBillCode : " + preBillCode);
			Assert.assertNotNull(preBillCode);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 生成一个前编码（编码不连续）
	 * @throws LockException 
	 */
	@Test
	public void testGetPreBillCode_5() throws LockException {

		try {

			// 初始化codeRuleVO
			PubBcrRulebase base = new PubBcrRulebase();
			base.setCodemode(PubBcrRulebase.STYLE_PRE);
			base.setIseditable("N");
			base.setIsautofill("N");// 非连续
			base.setIsgetpk("N");
			base.setIslenvar("Y");
			base.setRulecode("20160505-0001");
			base.setRulename("测试用001");
			base.setCreatedate(new Timestamp(System.currentTimeMillis()));
			base.setPkbillobj("101");

			codeRuleVO.setBaseVO(base);
			codeRuleVO.setElems(initElems());

			// 需将先将编码规则VO保存到数据库中去
			billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);

			// 执行测试对象
			String preBillCode = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			logger.error("preBillCode1 : " + preBillCode);
			Assert.assertNotNull(preBillCode);

			preBillCode = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			logger.error("preBillCode2 : " + preBillCode);
			Assert.assertNotNull(preBillCode);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 生成一个前编码（设置BillCodeElemInfo）
	 * @throws LockException 
	 */
	@Test
	public void testGetPreBillCode_6() throws LockException {

		try {

			// 初始化codeRuleVO
			PubBcrRulebase base = new PubBcrRulebase();
			base.setCodemode(PubBcrRulebase.STYLE_PRE);
			base.setIseditable("N");
			base.setIsautofill("N");// 非连续
			base.setIsgetpk("N");
			base.setIslenvar("Y");
			base.setRulecode("20160505-0001");
			base.setRulename("测试用001");
			base.setCreatedate(new Timestamp(System.currentTimeMillis()));
			base.setPkbillobj("101");

			codeRuleVO.setBaseVO(base);
			codeRuleVO.setElems(initElems());

			// 需将先将编码规则VO保存到数据库中去
			billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);

			// 附加编码元素信息
			BillCodeElemInfo externalElemInfo = new BillCodeElemInfo();
			externalElemInfo.setElemSNRefer("201605");
			externalElemInfo.setElemSNReferDesc("流水依据为：年月(yyyymm)");
			externalElemInfo.setElemLength(4);
			externalElemInfo.setElemValue("TAO");

			// 执行测试对象
			String preBillCode = billCodeProviderService.getPreBillCode(codeRuleVO, externalElemInfo, null);
			logger.error("preBillCode1 : " + preBillCode);
			Assert.assertNotNull(preBillCode);

			preBillCode = billCodeProviderService.getPreBillCode(codeRuleVO, externalElemInfo, null);
			logger.error("preBillCode1 : " + preBillCode);
			Assert.assertNotNull(preBillCode);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	// TODO ==========提交指定的前编码==========
	/**
	 * 提交指定的前编码（指定的VO为null）
	 */
	@Test
	public void testCommitPreBillCode_1() {

		try {
			// 测试对象
			billCodeProviderService.commitPreBillCode(null, null, null);
		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 提交指定的前编码（指定的前编码为null）
	 */
	@Test
	public void testCommitPreBillCode_2() {

		try {

			// 初始化codeRuleVO
			PubBcrRulebase base = new PubBcrRulebase();
			base.setCodemode(PubBcrRulebase.STYLE_PRE);
			base.setIseditable("N");
			base.setIsautofill("Y");
			base.setIsgetpk("N");
			base.setIslenvar("Y");
			base.setRulecode("20160505-0001");
			base.setRulename("for test");
			base.setCreatedate(new Timestamp(System.currentTimeMillis()));
			base.setPkbillobj("101");

			codeRuleVO.setBaseVO(base);
			codeRuleVO.setElems(initElems());

			// 测试对象
			billCodeProviderService.commitPreBillCode(codeRuleVO, null, null);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 提交指定的前编码（编码不连续、非随机码模式）
	 */
	@Test
	public void testCommitPreBillCode_3() {

		try {

			// 初始化codeRuleVO
			PubBcrRulebase base = new PubBcrRulebase();
			base.setCodemode(PubBcrRulebase.STYLE_PRE);
			base.setIseditable("N");
			base.setIsautofill("N");// 编码不连续
			base.setIsgetpk("N");// 非随机码模式
			base.setIslenvar("Y");
			base.setRulecode("20160505-0001");
			base.setRulename("for test");
			base.setCreatedate(new Timestamp(System.currentTimeMillis()));
			base.setPkbillobj("101");

			codeRuleVO.setBaseVO(base);
			codeRuleVO.setElems(initElems());

			// 测试对象
			billCodeProviderService.commitPreBillCode(codeRuleVO, null, null);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 提交指定的前编码（编码不连续、随机码模式）
	 */
	@Test
	public void testCommitPreBillCode_4() {

		try {

			// 初始化codeRuleVO
			PubBcrRulebase base = new PubBcrRulebase();
			base.setCodemode(PubBcrRulebase.STYLE_PRE);
			base.setIseditable("N");
			base.setIsautofill("N");// 编码不连续
			base.setIsgetpk("Y");// 随机码模式
			base.setIslenvar("Y");
			base.setRulecode("20160505-0001");
			base.setRulename("for test");
			base.setCreatedate(new Timestamp(System.currentTimeMillis()));
			base.setPkbillobj("101");

			codeRuleVO.setBaseVO(base);
			codeRuleVO.setElems(initElems());

			billCodeProviderService.commitPreBillCode(codeRuleVO, null, null);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 提交指定的前编码（随机码模式）
	 * @throws LockException 
	 */
	@Test
	public void testCommitPreBillCode_5() throws LockException {

		try {

			// 初始化codeRuleVO
			PubBcrRulebase base = new PubBcrRulebase();
			base.setCodemode(PubBcrRulebase.STYLE_PRE);
			base.setIseditable("N");
			base.setIsautofill("N");
			base.setIsgetpk("Y");// 随机码模式
			base.setIslenvar("Y");
			base.setRulecode("20160505-0001");
			base.setRulename("for test");
			base.setCreatedate(new Timestamp(System.currentTimeMillis()));
			base.setPkbillobj("101");

			codeRuleVO.setBaseVO(base);
			codeRuleVO.setElems(initElems());

			// 首先生成一个前编码
			String preBillCode = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);

			// 提交指定的前编码
			billCodeProviderService.commitPreBillCode(codeRuleVO, null, preBillCode);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 提交指定的前编码
	 * @throws LockException 
	 */
	@Test
	public void testCommitPreBillCode_6() throws LockException {

		try {

			// 初始化codeRuleVO
			PubBcrRulebase base = new PubBcrRulebase();
			base.setCodemode(PubBcrRulebase.STYLE_PRE);
			base.setIseditable("N");
			base.setIsautofill("N");
			base.setIsgetpk("N");// 随机码模式
			base.setIslenvar("Y");
			base.setRulecode("20160505-0001");
			base.setRulename("for test");
			base.setCreatedate(new Timestamp(System.currentTimeMillis()));
			base.setPkbillobj("101");

			codeRuleVO.setBaseVO(base);
			codeRuleVO.setElems(initElems());

			// 首先保存编码规则
			billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);

			// 然后生成一个前编码
			String preBillCode = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			logger.error("preBillCode : " + preBillCode);

			// 业务方法：提交指定的前编码
			billCodeProviderService.commitPreBillCode(codeRuleVO, null, preBillCode);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	// TODO ==========回滚指定的前编码==========
	/**
	 * 回滚一个前编码（指定的VO为null）
	 */
	@Test
	public void testRollbackPreBillCode_1() {

		try {
			billCodeProviderService.rollbackPreBillCode(null, null, null);
		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 回滚一个前编码（指定的前编码为null）
	 */
	@Test
	public void testRollbackPreBillCode_2() {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);
		base.setIseditable("N");
		base.setIsautofill("Y");
		base.setIsgetpk("N");
		base.setIslenvar("Y");
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElems());

		try {

			billCodeProviderService.rollbackPreBillCode(codeRuleVO, null, null);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 回滚一个前编码（非自动补号）
	 */
	@Test
	public void testRollbackPreBillCode_3() {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);
		base.setIseditable("N");
		base.setIsautofill("N");// 非自动补号
		base.setIsgetpk("N");
		base.setIslenvar("Y");
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElems());

		try {

			billCodeProviderService.rollbackPreBillCode(codeRuleVO, null, null);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 回滚一个前编码（随机码模式）
	 */
	@Test
	public void testRollbackPreBillCode_4() {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);
		base.setIseditable("N");
		base.setIsautofill("N");
		base.setIsgetpk("Y");// 随机码模式
		base.setIslenvar("Y");
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElems());

		try {

			billCodeProviderService.rollbackPreBillCode(codeRuleVO, null, null);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 回滚一个前编码（传入的编码与规则不匹配）
	 */
	@Test
	public void testRollbackPreBillCode_5() {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);
		base.setIseditable("N");
		base.setIsautofill("Y");
		base.setIsgetpk("N");
		base.setIslenvar("Y");
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElems());

		try {

			billCodeProviderService.rollbackPreBillCode(codeRuleVO, null, "ABC");

		} catch (BillCodeException e) {
			Assert.assertEquals("传入的编码与规则不匹配。", e.getMessage());
		}
	}

	/**
	 * 回滚一个前编码
	 * @throws LockException 
	 */
	@Test
	public void testRollbackPreBillCode_6() throws LockException {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);
		base.setIseditable("N");
		base.setIsautofill("Y");
		base.setIsgetpk("N");
		base.setIslenvar("Y");
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElems());

		try {

			// 1，报错编码规则
			billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);

			// 2，生成一个前编码
			String preBillCode = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);

			// 3，回滚前编码
			billCodeProviderService.rollbackPreBillCode(codeRuleVO, null, preBillCode);

		} catch (BillCodeException e) {

		}
	}

	/**
	 * 回滚一个前编码（混滚后再提交）
	 * @throws LockException 
	 */
	@Test
	public void testRollbackPreBillCode_7() throws LockException {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);
		base.setIseditable("N");
		base.setIsautofill("Y");
		base.setIsgetpk("N");
		base.setIslenvar("Y");
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElems());

		try {

			// 1，报错编码规则
			billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);

			// 2，生成一个前编码
			String preBillCode = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);

			// 3，回滚前编码
			billCodeProviderService.rollbackPreBillCode(codeRuleVO, null, preBillCode);

			// 4，提交前编码
			billCodeProviderService.commitPreBillCode(codeRuleVO, null, preBillCode);

		} catch (BillCodeException e) {

		}
	}

	// TODO ==========批量生成后编码==========
	/**
	 * 批量生成编码（后编码，codeRuleVO为null）
	 */
	@Test
	public void testGetBatchBillCodes_1() {

		try {
			billCodeProviderService.getBatchBillCodes(null, null, null, null, 5);
		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 批量生成编码（后编码，随机码模式）
	 */
	@Test
	public void testGetBatchBillCodes_2() {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);
		base.setIseditable("N");
		base.setIsautofill("Y");
		base.setIsgetpk("Y");// 随机码模式
		base.setIslenvar("Y");
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElems());

		try {
			String[] batchBillCodes = billCodeProviderService.getBatchBillCodes(codeRuleVO, null, null, null, 5);
			for (String batchBillCode : batchBillCodes) {
				logger.error(batchBillCode);
			}
		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 批量生成编码（后编码，num=0）
	 */
	@Test
	public void testGetBatchBillCodes_3() {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);
		base.setIseditable("N");
		base.setIsautofill("Y");
		base.setIsgetpk("Y");// 随机码模式
		base.setIslenvar("Y");
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElems());

		try {
			String[] batchBillCodes = billCodeProviderService.getBatchBillCodes(codeRuleVO, null, null, null, 0);
			for (String batchBillCode : batchBillCodes) {
				logger.error(batchBillCode);
			}
		} catch (BillCodeException e) {
			Assert.assertEquals("生成的编码数量需要>=1", e.getMessage());
		}
	}

	/**
	 * 批量生成编码（后编码）
	 */
	@Test
	public void testGetBatchBillCodes_4() {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);// 前编码方式，仍然可以生成后编码
		base.setIseditable("N");
		base.setIsautofill("Y");
		base.setIsgetpk("N");
		base.setIslenvar("Y");
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElems());

		try {

			// 需将先将编码规则VO保存到数据库中去
			billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);

			// 测试对象：批量生成5个后编码
			String[] batchBillCodes = billCodeProviderService.getBatchBillCodes(codeRuleVO, null, null, null, 5);
			for (String billCodes : batchBillCodes) {
				logger.error(billCodes);
			}
		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	// TODO ==========生成一个后编码==========
	/**
	 * 生成1个编码（后编码）
	 */
	@Test
	public void testGetBillCode() {
		try {

			// 初始化codeRuleVO
			PubBcrRulebase base = new PubBcrRulebase();
			base.setCodemode(PubBcrRulebase.STYLE_PRE);// 前编码方式
			base.setIseditable("Y");
			base.setIsautofill("Y");
			base.setIsgetpk("N");
			base.setIslenvar("Y");
			base.setRulecode("1004");
			base.setRulename("for test");
			base.setCreatedate(new Timestamp(System.currentTimeMillis()));
			base.setPkbillobj("101");

			codeRuleVO.setBaseVO(base);
			codeRuleVO.setElems(initElems());

			// 需将先将编码规则VO保存到数据库中去
			billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);

			// 执行测试对象
			String billCode = billCodeProviderService.getBillCode(codeRuleVO, null, null, null);
			logger.error("billCode : " + billCode);
			Assert.assertNotNull(billCode);

			// 再执行一次
			billCode = billCodeProviderService.getBillCode(codeRuleVO, null, null, null);
			logger.error("billCode : " + billCode);
			Assert.assertNotNull(billCode);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}

	}

	// TODO ==========将指定编码回退==========
	/**
	 * 将指定编码回退（后编码模式下，需要补码）
	 */
	@Test
	public void testReturnBillCode_1() {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_AFT);
		base.setIseditable("N");// 是否可编辑
		base.setIsautofill("Y");// 是否自动填充以保证编码连续
		base.setIsgetpk("N");// 是否是随机码模式
		base.setIslenvar("Y");// 流水号是否自动补零
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("001");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElemsWithoutRandomCodeElement());

		// 保存编码规则
		billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);

		try {
			// 批量生成5个后编码
			String[] batchBillCodes = billCodeProviderService.getBatchBillCodes(codeRuleVO, null, null, null, 5);
			for (String billCodes : batchBillCodes) {
				logger.error(billCodes);
			}

			// 测试对象：将第三个后编码回退
			billCodeProviderService.returnBillCode(codeRuleVO, null, null, batchBillCodes[2]);

			// 继续生成一个编码
			String billCode = billCodeProviderService.getBillCode(codeRuleVO, null, null, null);
			logger.error(billCode);

			// 补码
			Assert.assertEquals(batchBillCodes[2], billCode);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}

	}

	/**
	 * 将指定编码回退（前编码模式下，需要补码）
	 * @throws LockException 
	 */
	@Test
	public void testReturnBillCode_2() throws LockException {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);
		base.setIseditable("N");// 是否可编辑
		base.setIsautofill("Y");// 是否自动填充以保证编码连续
		base.setIsgetpk("N");// 是否是随机码模式
		base.setIslenvar("Y");// 流水号是否自动补零
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElemsWithoutRandomCodeElement());

		// 保存编码规则
		billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);

		try {
			// 批量生成5个预取编码
			String[] batchPreBillCodes = new String[5];
			batchPreBillCodes[0] = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			batchPreBillCodes[1] = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			batchPreBillCodes[2] = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			batchPreBillCodes[3] = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			batchPreBillCodes[4] = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			for (String billCodes : batchPreBillCodes) {
				logger.error(billCodes);
			}

			// 测试对象：将第三个编码回退
			billCodeProviderService.returnBillCode(codeRuleVO, null, null, batchPreBillCodes[2]);

			// 继续生成一个编码
			String billCode = billCodeProviderService.getBillCode(codeRuleVO, null, null, null);
			logger.error(billCode);

			// 补码
			Assert.assertEquals(batchPreBillCodes[2], billCode);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}

	}

	/**
	 * 将指定编码回退（后编码模式下，不需要补码）
	 */
	@Test
	public void testReturnBillCode_3() {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_AFT);
		base.setIseditable("N");// 是否可编辑
		base.setIsautofill("N");// 是否自动填充以保证编码连续
		base.setIsgetpk("N");// 是否是随机码模式
		base.setIslenvar("Y");// 流水号是否自动补零
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElemsWithoutRandomCodeElement());

		// 保存编码规则
		billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);

		try {
			// 批量生成5个后编码
			String[] batchBillCodes = billCodeProviderService.getBatchBillCodes(codeRuleVO, null, null, null, 5);
			for (String billCodes : batchBillCodes) {
				logger.error(billCodes);
			}

			// 测试对象：将第三个后编码回退
			billCodeProviderService.returnBillCode(codeRuleVO, null, null, batchBillCodes[2]);

			// 继续生成一个编码
			String billCode = billCodeProviderService.getBillCode(codeRuleVO, null, null, null);
			logger.error(billCode);

			// 不补码
			Assert.assertNotEquals(batchBillCodes[2], billCode);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}

	}

	/**
	 * 将指定编码回退（前编码模式下，不需要补码）
	 * @throws LockException 
	 */
	@Test
	public void testReturnBillCode_4() throws LockException {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);
		base.setIseditable("N");// 是否可编辑
		base.setIsautofill("N");// 是否自动填充以保证编码连续
		base.setIsgetpk("N");// 是否是随机码模式
		base.setIslenvar("Y");// 流水号是否自动补零
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElemsWithoutRandomCodeElement());

		// 保存编码规则
		billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);

		try {
			// 批量生成5个预取编码
			String[] batchPreBillCodes = new String[5];
			batchPreBillCodes[0] = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			batchPreBillCodes[1] = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			batchPreBillCodes[2] = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			batchPreBillCodes[3] = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			batchPreBillCodes[4] = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			for (String billCodes : batchPreBillCodes) {
				logger.error(billCodes);
			}

			// 测试对象：将第三个编码回退
			billCodeProviderService.returnBillCode(codeRuleVO, null, null, batchPreBillCodes[2]);

			// 继续生成一个编码
			String billCode = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			logger.error(billCode);

			// 不补码
			Assert.assertNotEquals(batchPreBillCodes[2], billCode);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}

	}

	// TODO ==========将指定的回退编码删除==========

	/**
	 * 将指定的回退编码删除（编码规则VO为null）
	 */
	@Test
	public void testDeleteRetrunedBillCode_1() {

		try {
			billCodeProviderService.DeleteRetrunedBillCode(null, null, null);
		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 将指定的回退编码删除（编码规则VO为null）
	 */
	@Test
	public void testDeleteRetrunedBillCode_2() {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);
		base.setIseditable("N");// 是否可编辑
		base.setIsautofill("N");// 是否自动填充以保证编码连续
		base.setIsgetpk("N");// 是否是随机码模式
		base.setIslenvar("Y");// 流水号是否自动补零
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElemsWithoutRandomCodeElement());

		try {
			billCodeProviderService.DeleteRetrunedBillCode(codeRuleVO, null, null);
		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 将指定的回退编码删除
	 */
	@Test
	public void testDeleteRetrunedBillCode_3() {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);
		base.setIseditable("N");// 是否可编辑
		base.setIsautofill("Y");// 是否自动填充以保证编码连续
		base.setIsgetpk("N");// 是否是随机码模式
		base.setIslenvar("Y");// 流水号是否自动补零
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElemsWithoutRandomCodeElement());

		try {

			// 保存编码规则
			billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);

			// 批量生成单据号
			String[] batchBillCodes = billCodeProviderService.getBatchBillCodes(codeRuleVO, null, null, null, 5);
			for (String billCodes : batchBillCodes) {
				logger.error(billCodes);
			}

			// 将生成第三个的单据号回退
			billCodeProviderService.returnBillCode(codeRuleVO, null, null, batchBillCodes[2]);

			// 测试对象：将回退的单据号删除
			billCodeProviderService.DeleteRetrunedBillCode(codeRuleVO, null, batchBillCodes[2]);

			// 再次生成一个编码
			String billCode = billCodeProviderService.getBillCode(codeRuleVO, null, null, null);

			// 因为已经将回退的编码删除，所以新生成的编码应该与删除的回退编码不一样
			Assert.assertNotEquals(billCode, batchBillCodes[2]);

		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	// TODO ==========将回退的前编码删除==========
	/**
	 * 将回退的前编码删除（codeRuleVO为null）
	 */
	@Test
	public void testAbandenBillCode_1() {

		try {
			billCodeProviderService.AbandenBillCode(null, null, null, null);
		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 将回退的前编码删除（billCode为null）
	 */
	@Test
	public void testAbandenBillCode_2() {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);
		base.setIseditable("N");// 是否可编辑
		base.setIsautofill("Y");// 是否自动填充以保证编码连续
		base.setIsgetpk("N");// 是否是随机码模式
		base.setIslenvar("Y");// 流水号是否自动补零
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElemsWithoutRandomCodeElement());

		try {
			
			billCodeProviderService.AbandenBillCode(codeRuleVO, null, null, null);
			
		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}
	
	/**
	 * 将回退的前编码删除（billCode不存在于数据库中）
	 */
	@Test
	public void testAbandenBillCode_3() {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);
		base.setIseditable("N");// 是否可编辑
		base.setIsautofill("Y");// 是否自动填充以保证编码连续
		base.setIsgetpk("N");// 是否是随机码模式
		base.setIslenvar("Y");// 流水号是否自动补零
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElemsWithoutRandomCodeElement());

		try {
			
			billCodeProviderService.AbandenBillCode(codeRuleVO, null, null, "ABC");
			
		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}
	
	/**
	 * 将回退的前编码删除
	 * @throws LockException 
	 */
	@Test
	public void testAbandenBillCode_4() throws LockException {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);
		base.setIseditable("N");// 是否可编辑
		base.setIsautofill("Y");// 是否自动填充以保证编码连续
		base.setIsgetpk("N");// 是否是随机码模式
		base.setIslenvar("Y");// 流水号是否自动补零
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElemsWithoutRandomCodeElement());

		try {
			
			// 将编码规则保存到数据库中去
			billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);
			
			// 生成五条前编码
			String[] preBillCodes = new String[5];
			preBillCodes[0] = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			preBillCodes[1] = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			preBillCodes[2] = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			preBillCodes[3] = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			preBillCodes[4] = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			
			for (String billCodes : preBillCodes) {
				logger.error(billCodes);
			}
			
			// 将第三条编码回退
			billCodeProviderService.rollbackPreBillCode(codeRuleVO, null, preBillCodes[2]);
			
			// 测试对象：将回退的前编码删除
			billCodeProviderService.AbandenBillCode(codeRuleVO, null, null, preBillCodes[2]);
			
			// 再次生成一个前编码
			String preBillCode = billCodeProviderService.getPreBillCode(codeRuleVO, null, null);
			logger.error(preBillCode);
			
			// 因为已经将回退表中的编码删除，所以再次生成的编码将不会补位
			Assert.assertNotEquals(preBillCodes[2], preBillCode);
			
		} catch (BillCodeException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 将回退的后编码删除
	 */
	@Test
	public void testAbandenBillCode_5() {

		// 初始化codeRuleVO
		PubBcrRulebase base = new PubBcrRulebase();
		base.setCodemode(PubBcrRulebase.STYLE_PRE);
		base.setIseditable("N");// 是否可编辑
		base.setIsautofill("Y");// 是否自动填充以保证编码连续
		base.setIsgetpk("N");// 是否是随机码模式
		base.setIslenvar("Y");// 流水号是否自动补零
		base.setRulecode("20160505-0001");
		base.setRulename("for test");
		base.setCreatedate(new Timestamp(System.currentTimeMillis()));
		base.setPkbillobj("101");

		codeRuleVO.setBaseVO(base);
		codeRuleVO.setElems(initElemsWithoutRandomCodeElement());

		try {
			
			// 将编码规则保存到数据库中去
			billCodeRuleMgrService.saveBillCodeRule(codeRuleVO);
			
			// 生成五条前编码
			String[] preBillCodes = billCodeProviderService.getBatchBillCodes(codeRuleVO, null, null, null, 5);
			
			for (String billCodes : preBillCodes) {
				logger.error(billCodes);
			}
			
			// 将第三条编码回退
			billCodeProviderService.returnBillCode(codeRuleVO, null, null, preBillCodes[2]);
			
			// 测试对象：将回退的前编码删除
			billCodeProviderService.AbandenBillCode(codeRuleVO, null, null, preBillCodes[2]);
			
			// 再次生成一个后编码
			String billCode = billCodeProviderService.getBillCode(codeRuleVO, null, null, null);
			logger.error(billCode);
			
			// 因为已经将回退表中的编码删除，所以再次生成的编码将不会补位
			Assert.assertNotEquals(preBillCodes[2], billCode);
			
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

	/**
	 * 不带有随机码元素
	 * 
	 * @return
	 */
	private IBillCodeElemVO[] initElemsWithoutRandomCodeElement() {

		// 第一个元素是值为"TAO-"的常量
		PubBcrElem elem1 = new PubBcrElem();
		elem1.setElemlenth((short) 4);
		elem1.setElemvalue("TTT-");
		elem1.setElemtype((short) IBillCodeElemVO.TYPE_CONST);
		elem1.setEorder((short) 0);
		elem1.setIsrefer((short) IBillCodeElemVO.REF_NOT);
		elem1.setCreatedate(new Timestamp(System.currentTimeMillis()));

		// 第二个元素是 yyMMdd 形式的系统时间元素
		PubBcrElem elem2 = new PubBcrElem();
		elem2.setDateDisplayFormat(IBillCodeElemVO.DATEFORMAT_YYMMDD);
		elem2.setElemlenth((short) 6);
		elem2.setElemtype((short) IBillCodeElemVO.TYPE_SYSTIME);
		elem2.setEorder((short) 1);
		elem2.setIsrefer((short) IBillCodeElemVO.REF_DAY);
		elem2.setCreatedate(new Timestamp(System.currentTimeMillis()));

		// 第三个元素是6位长度的流水号
		PubBcrElem elem3 = new PubBcrElem();
		elem3.setElemlenth((short) 6);
		elem3.setElemtype((short) IBillCodeElemVO.TYPE_SN);
		elem3.setEorder((short) 2);
		elem3.setIsrefer((short) IBillCodeElemVO.REF_NOT);
		elem3.setCreatedate(new Timestamp(System.currentTimeMillis()));

		return new IBillCodeElemVO[] { elem1, elem2, elem3, };

	}

}
