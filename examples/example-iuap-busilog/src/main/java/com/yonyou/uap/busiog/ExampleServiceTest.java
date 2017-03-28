package com.yonyou.uap.busiog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yonyou.uap.busiog.service.ExampleService;
import com.yonyou.uap.ieop.busilog.context.ContextKeyConstant;
import com.yonyou.uap.ieop.busilog.context.ThreadLocalBusiLogContext;
/**
 * 由于JUNIT不支持多线程测试，因此目前使用main方法跑测试
 * @author lihn
 *
 */

public class ExampleServiceTest {
	@Autowired
	private static ExampleService exampleService;
	
	
	private static ApplicationContext ac;

	
		

	public static void testSave(){
		ac = new ClassPathXmlApplicationContext(
				"busilog-applicationContext.xml");
		 exampleService=(ExampleService) ac
					.getBean("exampleService");
		 ThreadLocalBusiLogContext.put(ContextKeyConstant.BUSINESS_OPERATION_USER, "test002");
		 ThreadLocalBusiLogContext.put(ContextKeyConstant.BUSINESS_SYS_ID, "应用1");
		ThreadLocalBusiLogContext.put(ContextKeyConstant.BUSINESS_TENANT_ID, "test002");
		ThreadLocalBusiLogContext.put(ContextKeyConstant.BUSINESS_OPERATION_IP, "127.0.0.1");
		exampleService.save("save方法");
		exampleService.delete("delete删除方法");
	}
	
	public static void main(String args[]){
		testSave();
	}
	
	
}
