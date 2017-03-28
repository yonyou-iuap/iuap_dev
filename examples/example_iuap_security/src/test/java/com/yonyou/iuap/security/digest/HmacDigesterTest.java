package com.yonyou.iuap.security.digest;

import org.junit.Test;

import com.yonyou.iuap.security.rest.digest.core.HMACDigestUtils;

public class HmacDigesterTest {

	@Test
	public void testGenSeed() throws Exception{
		String seed = HMACDigestUtils.initSeed();
		System.out.println(seed);
	}
	
}
