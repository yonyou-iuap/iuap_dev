package com.yonyou.iuap.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.iuap.security.rest.common.AuthConstants;
import com.yonyou.iuap.security.rest.common.Credential;
import com.yonyou.iuap.security.rest.common.SignProp;
import com.yonyou.iuap.security.rest.exception.UAPSecurityException;
import com.yonyou.iuap.security.rest.factory.ServerVerifyFactory;
import com.yonyou.iuap.security.rest.utils.ClientCredentialGenerator;
import com.yonyou.iuap.security.rest.utils.PostParamsHelper;
import com.yonyou.iuap.security.rest.utils.SignPropGenerator;
import com.yonyou.iuap.utils.PropertyUtil;

/**
 * 签名验证过滤器器
 *
 */
public class ServerSignVerifyFilter implements Filter {

	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(getClass());
	// 允许的调用和真正请求之间的间隔
	private static final long DEFAULT_EXPIRED = 300000;

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;

		StringBuffer url = request.getRequestURL();
		// 1、得到完整的URL
		if (!StringUtils.isEmpty(request.getQueryString())) {
			url.append("?");
			url.append(request.getQueryString());
		}

		// 2、得到请求参数中的appId、ts，以及header中的签名sign
		String appid = request.getParameter("appId");
		String ts = request.getParameter("ts");
		String sign = request.getHeader("sign");

		if (StringUtils.isNumeric(ts)) {
			long sendTs = Long.parseLong(ts);
			// 3、判断请求是否超时
			if (System.currentTimeMillis() - sendTs > DEFAULT_EXPIRED) {
				logger.error("请求超时！不允许，应返回错误信息!");
			} else {
				// 4、根据完整的URL得到SignProp
				SignProp prop = SignPropGenerator.genSignProp(url.toString());
				// 5、如果是post请求，加上表单参数和content-length
				if (request.getMethod().endsWith("POST")) {
					prop.setPostParamsStr(PostParamsHelper
							.genParamsStrByReqeust(request));
					prop.setContentLength(request.getContentLength());
				}
				logger.info("Server SignProp: " + prop);
				// 6、在服务端，根据appid得到相应请求端的证书，进而验证header中的sign值与当前计算出的sign值是否相同
				// 相同则继续请求，不同则拒绝服务返回
				DemoServerVirifyFactory factory = new DemoServerVirifyFactory();
				Boolean result = false;
				try {
					result = factory.getVerifier(appid).verify(sign, prop);
				} catch (UAPSecurityException e) {
					logger.error("verifiy error ",e);
				}
				logger.info("验证结果:" + result);
				if (result) {
					arg2.doFilter(arg0, arg1);
				} else {
					logger.error("参数可能被恶意修改，服务被拒绝!");
				}
			}
		}
	}

	/**
	 * 
	 * 服务端必须实现ServerVerifyFactory，用来获取某个请求端的签名凭证
	 *
	 */
	class DemoServerVirifyFactory extends ServerVerifyFactory {
		@Override
		protected Credential genCredential(String appId) {
			try {
				// 应该根据appid查询并构造
				return ClientCredentialGenerator
						.loadCredential(PropertyUtil
								.getPropertyByKey(AuthConstants.CLIENT_CREDENTIAL_PATH));
			} catch (UAPSecurityException e) {
				logger.error("genCredential error ",e);
			}
			return null;
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	@Override
	public void destroy() {

	}

}
