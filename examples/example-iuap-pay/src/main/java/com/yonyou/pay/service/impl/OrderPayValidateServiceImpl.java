package com.yonyou.pay.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.uap.ieop.pay.service.IOrderPayValidateService;

/**
 *
 * @author taomk 2016年7月13日 下午1:29:10
 *
 */
@Service
public class OrderPayValidateServiceImpl implements IOrderPayValidateService {
	
	private static Logger logger = LoggerFactory.getLogger(OrderPayValidateServiceImpl.class);

	@Override
	public boolean validate(HttpServletRequest arg0) {
		logger.error("执行业务检查逻辑");
		return true;
	}

}
