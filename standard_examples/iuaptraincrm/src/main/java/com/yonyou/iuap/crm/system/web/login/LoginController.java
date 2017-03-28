package com.yonyou.iuap.crm.system.web.login;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.yonyou.iuap.auth.session.SessionManager;
import com.yonyou.iuap.auth.shiro.AuthConstants;
import com.yonyou.iuap.auth.token.ITokenProcessor;
import com.yonyou.iuap.auth.token.TokenParameter;
import com.yonyou.iuap.cache.CacheManager;
import com.yonyou.iuap.crm.common.exception.AppBusinessException;
import com.yonyou.iuap.crm.user.entity.ExtIeopUserVO;
import com.yonyou.iuap.crm.user.service.itf.IExtIeopUserService;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.security.utils.RSAUtils;

/**
 * 默认登录逻辑示例，项目使用时候根据自己的需求，添加rsa加密、配置token生成参数等
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
	
    private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final int HASH_INTERATIONS = 1024;
	
    @Autowired
    private SessionManager sessionManager;
    
    @Autowired
    private CacheManager cacheManager;//缓存管理器

	@Autowired
	protected IExtIeopUserService userService;
	//为网页版本的登录Controller指定webTokenProcessor 相应的移动的指定为maTokenProcessor
	@Autowired()
	protected ITokenProcessor webTokenProcessor;
	
	@RequestMapping(method = RequestMethod.GET,value="login")
	public String login(Model model) {
		model.addAttribute("flag", "failed");
		return "redirect";
//		return "forward:/login.html";
//		return "redirect:login.html";
//		return "login1";
	}
	
	@RequestMapping(method = RequestMethod.GET,value="getKey")
	@ResponseBody
	public Map<String,String> getKey(Model model) {
		RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
		Map<String,String>  keymap = new HashMap<String,String>();
		String publicKeyExponent = "";
		String publicKeyModulus = "";
		if(publicKey != null){			
			publicKeyExponent = publicKey.getPublicExponent().toString(16);//16进制
			publicKeyModulus = publicKey.getModulus().toString(16);//16进制
		}
 	    keymap.put("exponent", publicKeyExponent);
 	    keymap.put("modulus", publicKeyModulus);
		return keymap;
	}
	
	@RequestMapping(method = RequestMethod.POST,value="formLogin")
	@ResponseBody
	public Map<String,String> formLogin(HttpServletRequest request, HttpServletResponse response, Model model) throws AppBusinessException {
		String userName = request.getParameter("username");
        String old_passWord = request.getParameter("password").replace("_encrypted", "");
        String passWord = RSAUtils.decryptStringByJs(old_passWord);
		/**
		 * 注意，此示例中简单验证了用户的登录过程，正规上线环境，需要考虑
		 * 前端用ras算法加密，此处用iuap-security组件中的RsaUtils解密，前端需要做输入校验
		 */
        Map<String,String>  errormap = new HashMap<String,String>();
        List<ExtIeopUserVO> user_list = null;
		try {
			user_list = userService.getBdUserByCodeAndName(userName, "", "");
		} catch (BusinessException e) {
			throw new AppBusinessException("登陆获取用户出错",e);
		}
        if (!user_list.isEmpty() && user_list.size() > 0) {
			ExtIeopUserVO user = user_list.get(0);
			byte[] hashPassword = Digests.sha1(passWord.getBytes(), Encodes.decodeHex(user.getSalt()), HASH_INTERATIONS);
			String checkPwd = Encodes.encodeHex(hashPassword);
			if(checkPwd.equals(user.getPassword())){
                
                TokenParameter tp = new TokenParameter();
                tp.setUserid(userName);
                // 设置登录时间
                tp.setLogints(String.valueOf(System.currentTimeMillis()));
                // 租户信息,saas应用登录的时候获取用户信息，设置租户id
//				tp.getExt().put(AuthConstants.PARAM_TENANTID, "");
//				tp.getExt().put(AuthConstants.PARAM_SYSID, "iuaptraincrm");
                
                try {
					tp.getExt().put("userCNName" , URLEncoder.encode(user.getName(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					logger.error("encode error!", e);
				}
                tp.getExt().put("pk_user" , user.getId());
                tp.getExt().put("pk_corp" , user.getPkCorp());
                tp.getExt().put("pk_dept" , user.getPkDept());
                
                Cookie[] cookies = webTokenProcessor.getCookieFromTokenParameter(tp);
                for(Cookie cookie : cookies){
            	   response.addCookie(cookie);
                }
//                String token = CookieUtil.findCookieValue(cookies, "token");
//				sessionManager.putSessionCacheAttribute(token,
//						user.getLoginName(), user.getName());
//				sessionManager.putSessionCacheAttribute(token, "pk_user",
//						user.getId());
//				//设置当前组织或者公司
//				sessionManager.putSessionCacheAttribute(token, "pk_corp",
//						user.getPkCorp());
//				//设置当前部门
//				sessionManager.putSessionCacheAttribute(token, "pk_dept",
//						user.getPkDept());
                model.addAttribute("flag", "success");
                errormap.put("flag", "success");
            } else {
            	logger.error("用户名密码错误!");
            	model.addAttribute("flag", "failed");
                model.addAttribute("accounterror", "用户名密码错误!");
                errormap.put("flag", "failed");
                errormap.put("accounterror", "用户名密码错误!");
            }
		} else {
			model.addAttribute("flag", "failed");
            model.addAttribute("accounterror", "你输入的用户不存在!");
            errormap.put("flag", "failed");
            errormap.put("accounterror", "你输入的用户不存在!");
		}
		return errormap;
	}
	
}
