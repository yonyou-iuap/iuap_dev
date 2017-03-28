package com.yonyou.iuap.taskexample.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * 任务测试例子访问的controller
 * 
 * @author lihn
 *
 */
@Controller
@RequestMapping("/test")
public class TaskExampleController {
	private static Logger logger = LoggerFactory.getLogger(TaskExampleController.class);

	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	@ResponseBody
	/***
	 * 测试任务调用的例子
	 * 
	 */
	public void test(HttpServletRequest request) {
		//往日志里面插入一条记录
		logger.debug("----------------任务执行测试例子运行--------------------"+new Date());
	}

}
