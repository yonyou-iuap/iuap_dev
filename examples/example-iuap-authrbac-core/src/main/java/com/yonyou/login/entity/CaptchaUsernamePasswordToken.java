package com.yonyou.login.entity;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 *	扩展shiro提供的UsernamePasswordToken类，用于接收页面验证码的输入
 *
 * @author taomk 2016年3月18日 下午6:02:30
 *
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken{

	private static final long serialVersionUID = 1L;
	
	private String captcha;
	
	public CaptchaUsernamePasswordToken(){
		super();
	}

	public CaptchaUsernamePasswordToken(String username, char[] password, boolean rememberMe, String host, String captcha) {
		super(username,password,rememberMe,host);
		this.captcha = captcha;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
}
