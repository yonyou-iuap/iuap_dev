package com.yonyou.iuap.auth.example.web;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yonyou.iuap.auth.session.SessionManager;
import com.yonyou.iuap.auth.shiro.AuthConstants;
import com.yonyou.iuap.auth.token.ITokenProcessor;
import com.yonyou.iuap.auth.token.TokenParameter;

/**
 * 默认登录逻辑
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
	
    private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final int HASH_INTERATIONS = 1024;
	
    @Autowired
    private SessionManager sessionManager;

	//为网页版本的登录Controller指定webTokenProcessor 相应的移动的指定为maTokenProcessor
	@Autowired()
	protected ITokenProcessor webTokenProcessor;
	
	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}
	
	@RequestMapping(method = RequestMethod.POST,value="formLogin")
	public String formLogin(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String userName = request.getParameter("username");
        String passWord = request.getParameter("password");
		
		if (passWord != null && userName != null) {
			// 模拟用户
			if("admin".equals(userName) && passWord.equals("admin")
				|| ("test001".equals(userName) && passWord.equals("test001"))){
                
                TokenParameter tp = new TokenParameter();
                tp.setUserid(userName);
                //设置登录时间
                tp.setLogints(String.valueOf(System.currentTimeMillis()));
                //租户信息
                tp.getExt().put(AuthConstants.PARAM_TENANTID , "hr_cloud");
                Cookie[] cookies = webTokenProcessor.getCookieFromTokenParameter(tp);
                for(Cookie cookie : cookies){
            	    response.addCookie(cookie);
                }
            } else {
            	logger.error("用户名密码错误!");
                model.addAttribute("accounterror", "用户名密码错误!");
                return "login";
            }
            return "redirect";
		} else {
            model.addAttribute("accounterror", "你输入的用户不存在!");
            return "login";
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return "login";
	}

}
