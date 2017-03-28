package uap.web.dubbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.iuap.context.InvocationInfoProxy;

public class DubboxProviderImplTest implements DubboxProviderItfTest {

	private static Logger logger = LoggerFactory.getLogger(DubboxProviderImplTest.class);
	
	@Override
	public String testDubboxProvider() {
		logger.info("get dubbox info!");
		
		logger.info("当前上下文信息中的租户ID为 {}!",InvocationInfoProxy.getTenantid());
		logger.info("当前上下文信息中的语种为 {}!",InvocationInfoProxy.getLocale());
		logger.info("当前上下文信息中的token为 {}!",InvocationInfoProxy.getToken());
		logger.info("当前上下文信息中的logints为 {}!",InvocationInfoProxy.getLogints());
		logger.info("当前上下文信息中的timezoone为 {}!",InvocationInfoProxy.getTimeZone());
		return "you get the dubbox service result!";
	}
}
