package uap.web.dubbox;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDubboxProvider {
	
	private static ApplicationContext context;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext-dubbo-provider.xml");
	}
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProvider() throws InterruptedException {
		
		while(true){
			System.out.println(context.getApplicationName());
			System.out.println("spring container is alived!");
			Thread.sleep(10000L);
		}
	}

}
