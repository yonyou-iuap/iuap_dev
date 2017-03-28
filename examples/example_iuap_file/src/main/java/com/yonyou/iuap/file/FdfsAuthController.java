package com.yonyou.iuap.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>
 * Title: FdfsAuthController
 * </p>
 * <p>
 * Description:通过该Controller接收nginx_fdfs鉴权模块的鉴权子请求,在fdfsfilelist列表内的文件将获得通过权限
 * </p>
 * 
 * @author zhukai
 */
@Controller
@RequestMapping(value = "/fdfsauth")
public class FdfsAuthController {

	public static Logger logger = LoggerFactory
			.getLogger(FdfsAuthController.class);
	public static Set<String> authedFiles = new HashSet<String>();

	static {
		FileInputStream in = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		File file = null;
		String filename;

		try {
			file = new File(Thread.currentThread().getContextClassLoader()
					.getResource("fdfsfilelist").getPath());
			in = new FileInputStream(file);
			isr = new InputStreamReader(in, "UTF-8");
			br = new BufferedReader(isr);

			while ((filename = br.readLine()) != null) {
				authedFiles.add(filename);
			}
		} catch (IOException e) {
			logger.info("已认证文件列表生成失败");
			logger.error("",e);
		} finally {
			try {
				br.close();
				isr.close();
				in.close();
			} catch (IOException e) {
				logger.info("已认证文件列表生成流关闭出错");
				logger.error("",e);
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public void fdfsauth(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			// 变量声明
			Cookie[] cookies;
			cookies = request.getCookies();
			Map<String, String[]> maps = request.getParameterMap();
			String accessfilename;
			String requestinfo;
			
			//拼接文件名
			requestinfo="[用户请求文件名:";	
			if(maps.containsKey("rawuri")){
				requestinfo+=maps.get("rawuri")[0]+"]";
			}else{
				requestinfo+="]";
			}
			//拼接cookies
			requestinfo+=" [用户请求cookies:";
			if(cookies != null){
				for (Cookie c : cookies) {
					requestinfo+=c.getName() + " = " + c.getValue()+" ";
				}
			}
			requestinfo+="]";
			//拼接http参数
			requestinfo+="[用户请求http参数:";
			
			for (Map.Entry<String, String[]> e : maps.entrySet()) {
				requestinfo+="key=" + e.getKey() + "  ";
				for (String s : e.getValue()) {
					requestinfo+="value=" + s+" ";
				}
			}
			
			requestinfo+="]";
			
			if (!maps.isEmpty()&&maps.containsKey("rawuri")) {
				//logger.info("文件名为：" + maps.get("rawuri")[0]);
				accessfilename = maps.get("rawuri")[0];
				accessfilename = accessfilename.substring(1);
			} else {
				accessfilename = "no file";
			}
			// logger.info("模拟鉴权通过文件名" + accessfilename);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().println("fdfsauth_ready");

			if (authedFiles.contains(accessfilename)) {
				response.setStatus(HttpServletResponse.SC_OK);
				requestinfo+="鉴权通过";
				//logger.info("鉴权通过");
			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				requestinfo+="鉴权不通过";
				//logger.info("鉴权不通过");
			}
			response.flushBuffer();	
			logger.info(requestinfo);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.flushBuffer();
			logger.error("鉴权过程出错",e);
		}
		
	}
}
