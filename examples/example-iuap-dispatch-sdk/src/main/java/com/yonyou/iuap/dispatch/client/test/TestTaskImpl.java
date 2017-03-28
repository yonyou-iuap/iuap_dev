package com.yonyou.iuap.dispatch.client.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.iuap.dispatch.client.ITask;

public class TestTaskImpl implements ITask{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Logger logger = LoggerFactory.getLogger(TestTaskImpl.class);
	private static final long serialVersionUID = 1L;

	public String execute(Map<String,String> data) {
		//业务操作
		try {
			Thread.sleep(10000);
			logger.debug(sdf.format(new Date(System.currentTimeMillis()))+" job is runned, jobClass="+TestTaskImpl.class.getName()+", data="+data);
		} catch (InterruptedException e) {
			logger.error(sdf.format(new Date(System.currentTimeMillis()))+" job run error, jobClass="+TestTaskImpl.class.getName()+", data="+data, e);
		}
		return null;
	}
}
