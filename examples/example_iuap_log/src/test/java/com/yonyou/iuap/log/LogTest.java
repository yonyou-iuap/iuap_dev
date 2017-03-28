package com.yonyou.iuap.log;

import org.apache.log4j.MDC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.iuap.log.constants.LogConstants;

public class LogTest {
	
	private static final Logger logger = LoggerFactory.getLogger(LogTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 简单示例
	 */
	@Test
	public void testSimpleLog() {
		
		logger.info("this is a simple log demo!");
	}
	
	/**
	 * 参数化示例
	 */
	@Test
	public void testParamsLog() {
		logger.info("this is a params log demo! level is：{}", "info");
	}
	
	/**
	 * 多参数示例
	 */
	@Test
	public void testMultiParamsLog() {
		logger.info("this is a {} log demo! level is：{}", "multi params", "info");
	}
	
	
	/**
	 * 异常信息示例
	 */
	@Test
	public void testExceptionLog() {
		try {
			String errNum = "123456a";
			Integer.parseInt(errNum);
		} catch (Exception e) {
			logger.error("this is a {} demo! ", "exception log", e);
		}
		
	}
	
	/**
	 * MDC定制日志信息
	 */
	@Test
	public void testMDCLog() {
		MDC.put(LogConstants.CURRENT_USERNAME, "admin");
		MDC.put(LogConstants.THREAD_CALLID, Thread.currentThread().getName());
		
		logger.info("this is a mdc log demo!");
	}

}
