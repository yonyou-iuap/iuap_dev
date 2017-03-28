package com.yonyou.uap.busiog.service;

import com.yonyou.uap.ieop.busilog.config.annotation.BusiLogConfig;

public class ExampleService {
	
	@BusiLogConfig("ExampleService_save")
	public void save(String param0) {
		System.out.println(param0);
	}
	@BusiLogConfig("ExampleService_delete")
	public void delete(String param0){
		System.out.println(param0);
		
	}

}
