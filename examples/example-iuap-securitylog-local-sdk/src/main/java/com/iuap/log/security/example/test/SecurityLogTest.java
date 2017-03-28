package com.iuap.log.security.example.test;

import java.util.Date;

import com.iuap.log.security.entities.SecurityLog;
import com.iuap.log.security.utils.SecurityLogUtil;

public class SecurityLogTest {

	public static void main(String[] args){
		SecurityLog log = new SecurityLog();
		log.setContentDes("content");
		log.setNotice("测试下");
		log.setTimestamp(new Date());
		SecurityLogUtil.saveLog(log);
	}
}
