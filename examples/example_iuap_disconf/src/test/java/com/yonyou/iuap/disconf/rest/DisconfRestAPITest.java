package com.yonyou.iuap.disconf.rest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.iuap.utils.HttpContextUtil;

/**
 * restAPI 接口示例
 * 
 * @author Administrator
 */
public class DisconfRestAPITest {
	
	protected static final Logger LOG = LoggerFactory.getLogger(DisconfRestAPITest.class);
	
	private static final String BASE_URL = "http://127.0.0.1:8080/iuap-disconfweb";
	
	private static HashMap authorityHeaderMap = new HashMap();
	
	@Before
	public void setUp() throws Exception {
		authorityHeaderMap.put("Authority", "token=d2ViLDYwMDAsak9iWHlGNG1wY3oxNkpRUk5zNWhZdTZtYmJjZ0VDWS9VamoxVCtWOGxMUzQ4U2RiT0MyK2NyOVRUeEV3Y1N4bDRaR1BzK29Dbml6T0xmdUhvWEpTa0E9PQ;u_usercode=test;u_logints=1478672551178;tenantid=DISCONF_WEB");
	}

	
	/**
	 * 创建应用
	 */
	@Test
	public void createAppTest(){
		String appName = "apptest_api";
		String appDesc = "测试rest接口创建APP";
		String emails = "test@yonyou.com";
		Map<String,String> paramers = new HashMap<String,String>();
		paramers.put("app", appName);
		paramers.put("desc", appDesc);
		paramers.put("emails", emails);
		String createAppUrl = BASE_URL+"/api/app";
		try {
			String result = HttpContextUtil.getInstance().doPostForStringWithContext(createAppUrl, paramers, null, authorityHeaderMap);
			LOG.info("创建APP REST服务测试结果:" + result);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	
	@Test
	public void deleteAppTest(){
		String appId = "16";
		String createAppUrl = BASE_URL+"/api/app/"+appId;
		try {
			String result = HttpContextUtil.getInstance().doDeleteForStringWithContext(createAppUrl, null, authorityHeaderMap);
			LOG.info("删除APP REST服务测试结果:" + result);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 查询config列表
	 */
	@Test
	public void ListConfig(){
		//http://localhost:8080/iuap-disconfweb/api/web/config/list.do?appId=2&envId=1&version=1_0_0_0&pgSize=5&pgNo=1
		try {
			String listConfigUrl = BASE_URL + "/api/web/config/list.do?appId=4&envId=1&version=1_0_0_0&pgSize=5&pgNo=1";
			String result = HttpContextUtil.getInstance().doGetForStringWithContext(listConfigUrl, null, authorityHeaderMap);
			LOG.info("查询config列表测试结果:" + result);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 创建配置文件
	 */
	@Test
	public void createConfigFileTest(){
		Map<String,String> paramers = new HashMap<String,String>();
		paramers.put("appId", "4");
		paramers.put("version", "1_0_0_0");
		paramers.put("envId", "1");
		paramers.put("fileContent", "testkey=testvalue");
		paramers.put("fileName", "test.properties");
		
		String createConfigUrl = BASE_URL+"/api/web/config/filetext";
		try {
			String result = HttpContextUtil.getInstance().doPostForStringWithContext(createConfigUrl, null, paramers, authorityHeaderMap);
			LOG.info("创建Config REST服务测试结果:" + result);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 删除配置文件/配置项
	 */
	@Test
	public void deleteConfig(){
		int configId = 175;
		String deleteConfigUrl = BASE_URL+"/api/web/config/"+configId;
		try {
			String result = HttpContextUtil.getInstance().doDeleteForStringWithContext(deleteConfigUrl, null, authorityHeaderMap);
			LOG.info("删除Config REST服务测试结果:" + result);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 获取配置项/配置文件的值
	 */
	@Test
	public void getConfig(){
		int configId = 175;
		String deleteConfigUrl = BASE_URL+"/api/web/config/"+configId;
		try {
			String result = HttpContextUtil.getInstance().doGetForStringWithContext(deleteConfigUrl, null, authorityHeaderMap);
			LOG.info("获取Config REST服务测试结果:" + result);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 更新配置文件
	 */
	@Test
	public void updateConfigFileTest(){
		String configId = "175";
		
		Map<String,String> paramers = new HashMap<String,String>();
		paramers.put("fileContent", "testkey=testvaluenew");
		String putConfigUrl = BASE_URL+"/api/web/config/filetext/" + configId;
		try {
			String result = HttpContextUtil.getInstance().doPutForStringWithContext(putConfigUrl, null, paramers, authorityHeaderMap);
			LOG.info("更新Config REST服务测试结果:" + result);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
		
}