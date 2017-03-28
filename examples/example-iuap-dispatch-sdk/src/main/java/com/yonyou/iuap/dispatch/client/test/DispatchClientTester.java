package com.yonyou.iuap.dispatch.client.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.iuap.dispatch.client.CronTaskConfig;
import com.yonyou.iuap.dispatch.client.DispatchRemoteManager;
import com.yonyou.iuap.dispatch.client.SimpleTaskConfig;
import com.yonyou.iuap.dispatch.client.TimeConfig;

public class DispatchClientTester {
	private static Logger logger = LoggerFactory.getLogger(DispatchClientTester.class);
	
	public static void main(String[] args) {
		DispatchClientTester tester = new DispatchClientTester();
//		tester.addSimpleTaskOne();
//		tester.addSimpleTaskTwo();
//		tester.addSimpleTaskThree();
//		tester.addSimpleTaskFour();
		tester.addCronTask();
//		tester.sendByCronOne();
	}
	
	/**
	 * 开始时间少于当前的非周期任务
	 */
	public void addSimpleTaskOne(){
		Date startDate = new Date(System.currentTimeMillis()-10*1000);
		addSimpleTask(startDate, null, "current time before no schedule task");
	}
	
	/**
	 * 开始时间少于当前的周期任务
	 */
	public void addSimpleTaskTwo(){
		TimeConfig timeConfig = TimeConfig.getInstance().withIntervalInSeconds(2).withRepeatCount(1);
		Date startDate = new Date(System.currentTimeMillis()-10*1000);
		addSimpleTask(startDate, timeConfig, "current time before schedule task");
	}
	
	/**
	 * 开始时间大于当前的非周期任务
	 */
	public void addSimpleTaskThree(){
		Date startDate = new Date(System.currentTimeMillis()+10*1000);
		addSimpleTask(startDate, null, "current time after no schedule task");
	}
	
	/**
	 * 开始时间大于当前的周期任务
	 */
	public void addSimpleTaskFour(){
		TimeConfig timeConfig = TimeConfig.getInstance().withIntervalInSeconds(2).withRepeatCount(1);
		Date startDate = new Date(System.currentTimeMillis()+10*1000);
		addSimpleTask(startDate, timeConfig,"current time after schedule task");
	}
	
	private void addSimpleTask(Date startDate, TimeConfig timeConfig, String note){
		SimpleTaskConfig taskConfig = 
					new SimpleTaskConfig(UUID.randomUUID().toString()/*任务名*/, 
										 "simpleTaskGroup"/*任务组名*/, 
										 timeConfig);
		taskConfig.setStartDate(startDate);
		Map<String,String> params = new HashMap<String,String>();
		params.put("note", note);
		boolean success = DispatchRemoteManager.add(taskConfig, TestTaskImpl.class, params, true);
		if(success){
			logger.error("任务添加成功--"+taskConfig.getJobCode());
		}else{
			logger.error("任务添加失败--"+taskConfig.getJobCode());
		}
	}
	
	
	public void addCronTask(){
		CronTaskConfig cronTaskConfig = new CronTaskConfig("cronTask", 
														   "cronTaskGroup", 
														   "* */1 * * * ?");
		Map<String,String> params = new HashMap<String,String>();
		params.put("serverName", System.getProperty("os.name"));//任务可传入附加数据
		boolean success = DispatchRemoteManager.add(cronTaskConfig, 
													TestTaskImpl.class, 
													params, 
													true);
		if(success){
			logger.error("任务添加成功");
		}else{
			logger.error("任务添加失败");
		}
	}

}
