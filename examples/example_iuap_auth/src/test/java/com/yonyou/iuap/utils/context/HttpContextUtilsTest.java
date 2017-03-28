package com.yonyou.iuap.utils.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.MDC;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.yonyou.iuap.context.ContextHolder;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.log.constants.LogConstants;
import com.yonyou.iuap.utils.HttpContextUtil;

@ContextConfiguration(
	locations = { 
		"classpath:applicationContext.xml"
	}
)
public class HttpContextUtilsTest extends AbstractJUnit4SpringContextTests {
	
	@Before
	public void setUp() throws Exception {
		ContextHolder.setContext(this.applicationContext);
		InvocationInfoProxy.setToken("bWEsLTEsNGxjYitKM2Q5NFgyaG50SHlNQXRReVJDRkNNaXp4ZzM5Yk9sMHFtUEd4UDJEZDE3T3VtejR4Wi9BaTZHbFVhemtxNGsxckx4VElnOEtCcEJGTmFZb1E9PQ");
		InvocationInfoProxy.setUserid("test001");
		InvocationInfoProxy.setTenantid("JHOFdSdP");
		InvocationInfoProxy.setSysid("hr_cloud");
		InvocationInfoProxy.setLogints("1465179310151");
		InvocationInfoProxy.setCallid("test_thread_call_id");
		
		MDC.put(LogConstants.CURRENT_TENANTID, "JHOFdSdP");
		MDC.put(LogConstants.CURRENT_USERNAME, "test001");
		MDC.put(LogConstants.THREAD_CALLID, "test_thread_call_id");
	}
	
	@Test
	public void testGetWithContext(){
		
	}
	
	@Test
	public void testPostWithContext() throws Exception{
		String url = "http://localhost:8080/example_iuap_auth/springmvc/demo/upload";
		//String url = "http://localhost:8080/iuap-saas-filesystem-service/file/upload";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("filepath", "test");
		params.put("groupname", "testset");
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("testcodeheader", "testcodeheadervalue");
		
		ContentBody contentBody = new FileBody(new java.io.File("G:\\authfile.txt")); 
		FormBodyPart formBodyPart = FormBodyPartBuilder.create("test.txt", contentBody).build();
		List<FormBodyPart> formParts = new ArrayList<FormBodyPart>();
		formParts.add(formBodyPart);
		HttpContextUtil.getInstance().multipartPostWithContext(url, params, formParts);
	}
	
	@Test
	public void testPostWithContextAndCustomProvider(){
	}
}
