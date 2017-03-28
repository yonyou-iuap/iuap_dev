package com.yonyou.iuap.security.digest;

import java.net.MalformedURLException;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yonyou.iuap.security.rest.common.AuthConstants;
import com.yonyou.iuap.security.rest.common.Credential;
import com.yonyou.iuap.security.rest.common.SignProp;
import com.yonyou.iuap.security.rest.exception.UAPSecurityException;
import com.yonyou.iuap.security.rest.factory.ServerVerifyFactory;
import com.yonyou.iuap.security.rest.utils.ClientCredentialGenerator;
import com.yonyou.iuap.security.rest.utils.SignPropGenerator;
import com.yonyou.iuap.utils.PropertyUtil;

public class ServerVerifyTest {
	
	// 允许的调用和真正请求之间的间隔
	private static final long DEFAULT_EXPIRED = 3000000;
	
	@Before
	public void setUp() throws Exception {
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * 测试验证签名的方法，应该放在服务提供方的过滤器中
	 * 
	 * @throws MalformedURLException
	 * @throws UAPSecurityException
	 */
	@Test
	public void testVerify() throws MalformedURLException, UAPSecurityException {
		String url = "http://172.20.6.48:8080/ecmgr/rest/testrest?username=admin&appId=3c8242c9467d059f9b718a890c9932ca&ts=1456969820236&sign=6eec45e7367d50f406be6fea1fca49847320d1ff";
		//String url2 = "http://172.20.6.48:8080/ecmgr/rest/testrest?username=admin&appId=3c8242c9467d059f9b718a890c9932ca&ts=1453098360150&sign=3ca89c25557a69b3af32f474ecced528c33430fe";
		String sign = "6eec45e7367d50f406be6fea1fca49847320d1ff";
		String appid = "3c8242c9467d059f9b718a890c9932ca";
		String ts = "1456969820236";
        if (StringUtils.isNumeric(ts)) {
            long sendTs = Long.parseLong(ts);
            if (System.currentTimeMillis() - sendTs > DEFAULT_EXPIRED) {
                System.err.println("请求超时！不允许，应返回错误信息!");
            }else {
            	SignProp prop = SignPropGenerator.genSignProp(url);
            	DemoServerVirifyFactory factory = new DemoServerVirifyFactory();
            	Boolean result = factory.getVerifier(appid).verify(sign, prop);
            	System.out.println(result);
            }
        }
        
	}
	
	class DemoServerVirifyFactory extends ServerVerifyFactory{
		@Override
		protected Credential genCredential(String appId) {
			try {
				// 应该根据appid查询并构造
				// return ClientCredentialGenerator.loadCredential(PropertyUtil.getPropertyByKey(AuthConstants.CLIENT_CREDENTIAL_PATH));
				// 服务标识+"."+默认路径
				return ClientCredentialGenerator.loadCredential(PropertyUtil.getPropertyByKey("bpm" + AuthConstants.CLIENT_CREDENTIAL_PATH));
			} catch (UAPSecurityException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
