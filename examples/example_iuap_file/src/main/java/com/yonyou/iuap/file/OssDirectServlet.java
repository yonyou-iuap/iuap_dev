package com.yonyou.iuap.file;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(asyncSupported = true,name = "Oss",urlPatterns = { "/oss" })
public class OssDirectServlet extends CallbackServer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger logger = LoggerFactory.getLogger(OssDirectServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ossCallbackBody = GetPostBody(request.getInputStream(), Integer.parseInt(request.getHeader("content-length")));//获取回调
		boolean ret = VerifyOSSCallbackRequest(request, ossCallbackBody);//验证回调
		
		if (ret)
		{
			//验证通过进行操作
			Map<String, Object> map = getUrlParams(ossCallbackBody);
			String filename=(String) map.get("filename");//获取存储的文件名
			String bucket=(String) map.get("bucket");//获取存储的bucket
			logger.info("filename is :" + filename);
			logger.info("bucketname is :" + bucket);
			
			response(request, response, "{\"Status\":\"OK\"}", HttpServletResponse.SC_OK);

		}
		else
		{
			response(request, response, "{\"Status\":\"verdify not ok\"}", HttpServletResponse.SC_BAD_REQUEST);
		}
	}

}
