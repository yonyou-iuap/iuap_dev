package com.yonyou.iuap.crm.common.exception.view;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;


public class AppExceptionView implements View {
	private static Logger logger = LoggerFactory.getLogger(AppExceptionView.class);

	private static final String X_ERROR = "X-Error";
	Exception ex;

	public AppExceptionView(Exception ex) {
		super();
		this.ex = ex;
	}

	@Override
	public String getContentType() {
		return "application/Json";
	}

	@Override
	public void render(Map<String, ?> arg0, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.error(ex.getMessage(),ex);
		response.setContentType("UTF-8");
		response.addHeader(X_ERROR, Boolean.TRUE.toString());
		String exMessage = ex.getMessage() == null ? "未知异常" : ex.getMessage();
		exMessage = exMessage.replaceAll("\n", "<br>");
		
		JSONObject json = new JSONObject();
		//待咨询
		response.setStatus(500);
		/*if(ex instanceof AppBusinessException){
			AppBusinessException e = (AppBusinessException) ex;
			response.setStatus(Integer.parseInt(e.getErrorCodeString()));
		}*/
		
		if(ex instanceof ConstraintViolationException){
			Set<ConstraintViolation<?>> hint = ((ConstraintViolationException) ex).getConstraintViolations();
			StringBuffer sb = new StringBuffer();
			for(ConstraintViolation er : hint){
				String msg = er.getMessage();
				if(!StringUtils.isEmpty(msg)){
					sb.append(msg);
					sb.append("\n");
				}				
			}
			String ermsg = sb.toString();
			if(!StringUtils.isEmpty(ermsg)){
				exMessage = ermsg;
			}
		}
		
		json.put("msg", StringEscapeUtils.escapeHtml(exMessage));
		String str = json.toString();
		response.getWriter().write(str);
	};  
	
}