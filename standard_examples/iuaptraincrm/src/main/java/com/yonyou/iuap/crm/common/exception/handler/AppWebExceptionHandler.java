package com.yonyou.iuap.crm.common.exception.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.yonyou.iuap.crm.common.exception.view.AppExceptionView;

public class AppWebExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception ex) {
		ModelAndView mv = new ModelAndView();
		View view = new AppExceptionView(ex);
		mv.setView(view);
		return mv;
	}

}