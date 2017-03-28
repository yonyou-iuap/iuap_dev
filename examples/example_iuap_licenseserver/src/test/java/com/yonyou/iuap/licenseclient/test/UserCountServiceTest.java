package com.yonyou.iuap.licenseclient.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.yonyou.iuap.licenseclient.service.UserCountService;

/**
 * "用户数"场景
 * 
 * @author rayoo
 */
public class UserCountServiceTest {

	/**
	 * 根据用户id, 计算机名称, 产品编码 进行注册license操作.返回值为下次和服务器进行校验的时间(毫秒为单位), -1代表验证失败.
	 */
	@Test
	public void testRegLicense() {
		String identifier = "id-1"; // 唯一标识
		String hostName = "host-1"; // 主机名
		String productCode = "abc"; // 产品编码
		long ret = UserCountService.regLicense(identifier, hostName, productCode);
		System.out.println(ret + "  " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(ret)));
	}

	/**
	 * 根据用户id/客户端唯一标识 和 产品编码 进行 注销license操作, 返回值为是否操作成功.
	 */
	@Test
	public void testCancelLicense() {
		String identifier = "60-02-B4-95-52-F5"; // 唯一标识
		String productCode = "abc"; // 产品编码
		boolean ret = UserCountService.cancelLicense(identifier, productCode);
		System.out.println(ret);
	}

	/**
	 * 根据产品编码获取剩余license数量.
	 */
	@Test
	public void testGetRemainLicenseCount() {
		String productCode = "abc"; // 产品编码
		int ret = UserCountService.getRemainLicenseCount(productCode);
		System.out.println(ret);
	}

	/**
	 * 根据产品编码获取是否有license可用, 返回值为boolean.
	 */
	@Test
	public void testIsAvaliableLicense() {
		String productCode = "abc"; // 产品编码
		boolean ret = UserCountService.isAvaliableLicense(productCode);
		System.out.println(ret);
	}

	/**
	 * 根据productCode获取此产品license总数量.
	 */
	@Test
	public void testGetTotalCount() {
		String productCode = "abc"; // 产品编码
		int totalCount = UserCountService.getTotalCount(productCode);
		System.out.println(totalCount);
	}
}
