package com.yonyou.login.controller;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yonyou.login.entity.CaptchaUsernamePasswordToken;
import com.yonyou.login.exception.IncorrectCaptchaException;
import com.yonyou.login.filter.CaptchaFormAuthenticationFilter;
import com.yonyou.login.service.AccountService;
import com.yonyou.login.util.RSAUtils;

/**
 * 登录相关Controller
 * @author taomk
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	public static final int HASH_INTERATIONS = 1024;
	public static final String COOKIES_PATH = "/example-iuap-authrbac-core";

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CaptchaFormAuthenticationFilter captchaFilter;

	@RequestMapping(value = "jump", method = RequestMethod.GET)
	public String login(Model model) {
		initPubKeyParams(model);
		return "login";
	}
	
	@RequestMapping(value = "403", method = RequestMethod.GET)
	public String forbidden(Model model) {
		initPubKeyParams(model);
		return "error/403";
	}
	
	@RequestMapping(value = "/logout/{id}", method = RequestMethod.GET)
	public String loginOut(@PathVariable("id") String userID, Model model) {
		
		//TODO 可以在此处根据userName进行登出业务处理
		
		SecurityUtils.getSubject().logout();
		return reLogin(model);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/formLogin")
	public String formlogin(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

		String userName = request.getParameter("username");
		String encryptedPassWord = request.getParameter("password");
		String captcha = request.getParameter("captcha");
		String rememberMe = request.getParameter("rememberMe");
		// 构造登录token
		CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken(userName, encryptedPassWord.toCharArray(),
				new Boolean(rememberMe), "", captcha);

		// 从Shiro组件中获取当前登录主体Subject
		Subject subject = SecurityUtils.getSubject();
		
		try {
			// 验证码校验
			captchaFilter.doCaptchaValidate(request, token);
			
		} catch (IncorrectCaptchaException e) {//当验证码不正确的时候会出现此异常
			initPubKeyParams(model);
			model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME , e);
			return "login";
		}
		
		try {
			// 登录
			subject.login(token);
			
		} catch (UnknownAccountException uae) {//username不存在
			model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,uae);
			return reLogin(model);
		}catch (IncorrectCredentialsException ice) {//password错误
			model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,ice);
			return reLogin(model);
		}catch (LockedAccountException lae) {//账户被锁住
			model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,lae);
			return reLogin(model);
		}catch (AuthenticationException ae) {//其他异常
			model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,ae);
			return reLogin(model);
		}
		
		if(subject.isAuthenticated()){//登录成功
			
			// TODO 添加其他的业务逻辑，例如将用户信息存入session...
			model.addAttribute(accountService.findUserByLoginName(userName));
			return "loginSuccess";
			
		}else{//登录失败
			
			model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,"未通过验证");
			return reLogin(model);
		}
	}

	private String reLogin(Model model) {
		initPubKeyParams(model);
		return "login";
	}

	private void initPubKeyParams(Model model) {
		RSAUtils.generateKeyPair();
		RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
		String publicKeyExponent = publicKey.getPublicExponent().toString(16);// 16进制
		String publicKeyModulus = publicKey.getModulus().toString(16);// 16进制
		model.addAttribute("exponent", publicKeyExponent);
		model.addAttribute("modulus", publicKeyModulus);
	}

}
