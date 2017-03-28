package com.yonyou.login.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.google.code.kaptcha.Constants;
import com.yonyou.login.entity.CaptchaUsernamePasswordToken;
import com.yonyou.login.exception.IncorrectCaptchaException;

/**
 * 扩展shiro提供的基于表单认证的FormAuthenticationFilter，增加验证码校验的功能
 *
 * @author taomk 2016年3月18日 下午6:06:56
 *
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter{

	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	public String getCaptchaParam() {
		return captchaParam;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}
	
	// 从request中取得Captcha
	protected String getCaptcha(ServletRequest request){
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}
	
	//创建Token
	protected CaptchaUsernamePasswordToken createToken(ServletRequest request , ServletResponse response) {
		
		String userName = getUsername(request);
		String password = getPassword(request);
		String captcha = getCaptcha(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		
		return new CaptchaUsernamePasswordToken(userName, password.toCharArray(), rememberMe, host, captcha);
	}
	
	//验证码校验
	public void doCaptchaValidate(HttpServletRequest request , CaptchaUsernamePasswordToken token) {
		
		// 获取session中的图形码字符串
		String captcha = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		
		if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())) {
			throw new IncorrectCaptchaException("验证码错误！");
		}
	}
	
	//认证
	protected boolean executeLogin(ServletRequest request , ServletResponse response) throws Exception {
		CaptchaUsernamePasswordToken token = createToken(request, response);
		
		try {
			
			// 验证框架Captchat提供的API，用于校验验证码是否输入正确
			doCaptchaValidate((HttpServletRequest) request, token);
			
			// shiro框架的正常登录验证流程
			Subject subject = getSubject(request , response);
			subject.login(token);
			
			// 登录成功之后的处理
			return onLoginSuccess(token, subject, request, response);
		} catch (AuthenticationException e) {
			// 登录失败之后的处理
			return onLoginFailure(token, e, request, response);
		}
	}
	
}