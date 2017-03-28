package com.yonyou.iuap.licenseclient.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.yonyou.iuap.licenseclient.service.CPULicenseService;

/**
 * "CPU个数"场景接口
 * 
 * @author rayoo
 */
public class CPULicenseServiceTest {

	/**
	 * 根据产品编码注册license, 返回值为下次和服务器进行校验的时间(毫秒为单位), -1代表验证失败.
	 */
	@Test
	public void testRegLicense() {
		String productCode = "abc"; // 产品编码
		long ret = CPULicenseService.regLicense(productCode);
		System.out.println(ret + "  " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(ret)));
	}

}
