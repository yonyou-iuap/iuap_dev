package com.yonyou.login.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 *
 *	当验证码校验不通过时，抛出此异常
 * @author taomk 2016年3月18日 下午6:29:07
 *
 */
public class IncorrectCaptchaException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public IncorrectCaptchaException(){
		super();
	}
	
	public IncorrectCaptchaException(String message , Throwable cause){
		super(message, cause);
	}
	
	public IncorrectCaptchaException(String message){
		super(message);
	}
	
	public IncorrectCaptchaException(Throwable cause){
		super(cause);
	}
}
