package com.yonyou.iuap.context;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Filter的mapping顺序决定处理顺序，配置时候注意
 */
public class ContextFilter implements Filter {
	static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ContextFilter.class); // auto append.

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOGGER.info("设置上下文...1");
		//InvocationInfoProxy.setTenantid("tenant01");
		
		chain.doFilter(request, response);
		
		LOGGER.info("清理上下文...1");
		//InvocationInfoProxy.reset();
	}

	@Override
	public void destroy() {
	}

}
