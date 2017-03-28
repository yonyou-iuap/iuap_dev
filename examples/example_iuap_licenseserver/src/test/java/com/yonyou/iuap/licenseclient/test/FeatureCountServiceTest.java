package com.yonyou.iuap.licenseclient.test;

import org.junit.Test;

import com.yonyou.iuap.licenseclient.service.FeatureCountService;

/**
 * "特征数"场景
 * 
 * @author rayoo
 */
public class FeatureCountServiceTest {

	/**
	 * 根据productCode获取此产品可分配的license数量.
	 */
	@Test
	public void testFeatureCount() {
		String productCode = "abc"; // 产品编码
		int permitCount = FeatureCountService.getPermitCount(productCode);
		System.out.println(permitCount);
	}

}
