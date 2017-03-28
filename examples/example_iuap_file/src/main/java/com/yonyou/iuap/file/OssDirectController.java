package com.yonyou.iuap.file;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yonyou.iuap.file.utils.OssDirectHelper;

/**
 * <p>Title: OssDirectController</p>
 * <p>Description: </p>
 * @author zhukai
 */
@Controller
@RequestMapping(value="/osscontroller")
public class OssDirectController {
	 public static Logger logger = LoggerFactory.getLogger(OssDirectController.class);

	@RequestMapping(value = "getsign", method = RequestMethod.GET)
	public void  getSign(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> respMap = new LinkedHashMap<String, String>(); 
		try {
			 respMap = OssDirectHelper.getSignMap(request);
		} catch (UnsupportedEncodingException e) {
			logger.error("getResponseMap 编码错误",e);
		}
        JSONObject ja1 = JSONObject.fromObject(respMap);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setCharacterEncoding("UTF-8");

        try {
			response.getWriter().print(ja1);
			response.flushBuffer();
		} catch (IOException e) {
			logger.error("response 写入错误",e);
		}

	}
	
}
