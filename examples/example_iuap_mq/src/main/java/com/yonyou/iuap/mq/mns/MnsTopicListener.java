package com.yonyou.iuap.mq.mns;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * MNS要求配置的服务路径只能有一层，如果为/appname/notifications,出现两个/则不合法
 * 
 * @author liujmc
 */
@Controller
public class MnsTopicListener extends AbstracTopicListener{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @RequestMapping(value = "/notifications", method = RequestMethod.POST)
    public void receiveMnsTopic(HttpServletRequest request,HttpServletResponse response) {
    	
    	//获取消息
    	String message = fetchMessage(request,response);
    	if(StringUtils.isNotBlank(message)){
    		onMessage(message);
    	}
    }

    // 业务逻辑，拿到消息体后的处理
	private void onMessage(String message) {
		logger.info(message);
	}
    
}
