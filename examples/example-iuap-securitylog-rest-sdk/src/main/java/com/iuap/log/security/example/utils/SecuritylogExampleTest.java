package com.iuap.log.security.example.utils;

import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iuap.log.security.entities.SecurityLog;
import com.iuap.log.security.utils.SecurityLogUtil;
import com.iuap.log.security.utils.SpringContextUtil;

public class SecuritylogExampleTest {
	
	public static void main(String[] args){
		start();
		SecurityLog log = new SecurityLog();
		log.setContentDes("测试额外企鹅企鹅王企鹅额企鹅请问请问请问请问我去恶趣味问请问请问请问测试额外企鹅企鹅王企鹅额企鹅请问请问请问请问测试额外企鹅企鹅王企鹅额企鹅请问请问请问请问我去恶趣味问请问请问请问测试额外企鹅企鹅王企鹅额企鹅请问请问请问请问我去恶趣味问请问请问请问我去恶趣味问请问请问请问");
		log.setNotice("测试 额外俄武器恶趣味请问请问额委屈额企鹅企鹅全文请请问去 ");
		log.setTimestamp(new Date());
		log.setIp("172.16.50.238");
		log.setProduct("UAP产品中心");
		log.setResult("成功");
		log.setSystem("windows系统");
		log.setLevel("-1");
		log.setUserAuthType("登录人认证");
		log.setUserIdentify("CA认证");
		log.setUserCode("usercode");
		log.setLessee("iuap.liuxing");
		SecurityLogUtil.saveLog(log);
	}
	
	private static void start(){
		SpringContextUtil springContextUtil = new SpringContextUtil();
		springContextUtil.setApplicationContext(new ClassPathXmlApplicationContext("classpath:securitylogrestsdk-applicationContext.xml","classpath:securitylogrestsdk-applicationContext-mq-provider.xml"));
	}
}
