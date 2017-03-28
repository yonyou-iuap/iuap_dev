package com.yonyou.uap.busiog.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yonyou.uap.busiog.service.ExampleService;
import com.yonyou.uap.ieop.busilog.context.ContextKeyConstant;
import com.yonyou.uap.ieop.busilog.context.ThreadLocalBusiLogContext;

@Controller
@RequestMapping("/busilog")
public class ExampleControl {
	@Autowired
	private ExampleService exampleService;
	@RequestMapping(value = "/testExampleSrvice", method = RequestMethod.GET)
	public ModelAndView testExampleSrvice(){
		 ThreadLocalBusiLogContext.put(ContextKeyConstant.BUSINESS_OPERATION_USER, "test002");
		 ThreadLocalBusiLogContext.put(ContextKeyConstant.BUSINESS_SYS_ID, "应用1");
		ThreadLocalBusiLogContext.put(ContextKeyConstant.BUSINESS_TENANT_ID, "test002");
		ThreadLocalBusiLogContext.put(ContextKeyConstant.BUSINESS_OPERATION_IP, "127.0.0.1");
		exampleService.save("save方法");
		exampleService.delete("delete删除方法");
		return new ModelAndView("redirect:/index.jsp");
	}

}
