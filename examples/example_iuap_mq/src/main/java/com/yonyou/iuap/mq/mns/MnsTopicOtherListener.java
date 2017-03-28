package com.yonyou.iuap.mq.mns;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MnsTopicOtherListener extends AbstracTopicListener{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @RequestMapping(value = "/othernotifications", method = RequestMethod.POST)
    public void receiveMnsTopic(HttpServletRequest request,HttpServletResponse response) {
    	
    	String message = fetchMessage(request,response);
    	if(StringUtils.isNotBlank(message)){
    		try {
				onMessage(message);
			} catch (Exception e) {
				logger.error("procss business logic with message error!",e);
				response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			}
    	}
    }

	private void onMessage(String message)  {
		logger.info("MnsTopicOtherListener-"+message);
	}
    
}
