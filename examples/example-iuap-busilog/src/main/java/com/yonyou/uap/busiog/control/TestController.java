package com.yonyou.uap.busiog.control;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/test")

public class TestController {

	@RequestMapping(value = "/testTasks", method = RequestMethod.GET)
	public String testTasks(){
		return "test";
	}
	

}
