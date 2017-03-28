package dubbox;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.MDC;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import uap.web.dubbox.DubboxProviderItfTest;

import com.yonyou.iuap.context.InvocationInfoProxy;

public class TestDubboxConsumer {
	
	private static ApplicationContext context;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext-dubbo-consumer.xml");
		initMDC();
	}
	
	private void initMDC() {
		String username = "test";
		
		// MDC中记录用户信息
		MDC.put("current_user_name", username);
		String current_thread_id = Thread.currentThread().getId()+"_"+System.currentTimeMillis();
		MDC.put("current_thread_id", current_thread_id);
	}
	
	private void clearMDC() {
		// MDC中记录用户信息
		MDC.remove("current_user_name");
		MDC.remove("current_thread_id");
	}

	@After
	public void tearDown() throws Exception {
		clearMDC();
	}

	@Test
	public void testConsumer() throws InterruptedException {
		//设置测试的上下文信息
		InvocationInfoProxy.setTenantid("tenant02");
		InvocationInfoProxy.setLocale("ec_GB");
		InvocationInfoProxy.setCallid("test_call_id");
		InvocationInfoProxy.setUserid("admin");
		InvocationInfoProxy.setToken("testtoken");
		InvocationInfoProxy.setLogints("testlogints");
		InvocationInfoProxy.setTimeZone("CCT+08");
		InvocationInfoProxy.getParamters().put("test_exentd", "extend");
		
		//从spring容器中获取bean，直接调用
		DubboxProviderItfTest dt = (DubboxProviderItfTest)context.getBean("dubboxConsumer");
		
		String result = dt.testDubboxProvider();
		System.out.println(result);
		Assert.notNull(result);
	}
	
}
