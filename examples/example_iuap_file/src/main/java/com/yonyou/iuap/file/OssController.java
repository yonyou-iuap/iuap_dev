package com.yonyou.iuap.file;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.iuap.file.client.AliOSSClient;
import com.yonyou.iuap.file.utils.BucketInfoFromFile;

@Controller
@RequestMapping(value="/ossget")
public class OssController {
	public static BucketInfoFromFile bucketMapper=new BucketInfoFromFile();
	public static Logger logger = LoggerFactory.getLogger(OssController.class);
	@RequestMapping(value = "url", method = RequestMethod.GET)
	public void  getUrl(HttpServletRequest request,
			HttpServletResponse response) {
		String bucketName=request.getParameter("bucketname");
		String fileName=request.getParameter("filename");
		String fielUrl;
		//传入bucket名和文件名从oss获得文件url
		AliOSSClient client= AliOSSClient.getInstance();
		//获取oss上文件的临时url 三个参数分别为bucket名、文件名、过期时间（ms）
		
		try {
			fielUrl=client.getUrl(bucketName, fileName,3600);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().println(fielUrl);
			response.setStatus(HttpServletResponse.SC_OK);
			response.flushBuffer();
		} catch (IOException e) {
			logger.error("oss组件错误",e);
		} catch (Exception e) {
			logger.error("oss组件错误",e);
		}
		
		//return "forward:/index.html";
	}
	
	
	@RequestMapping(value = "filelist", method = RequestMethod.GET)
	public @ResponseBody Map<String, String>  getFileList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> filemap=new HashMap<String, String>();
		String bucketName=request.getParameter("bucketname");
		//传入bucket名和文件名从oss获得文件url
		AliOSSClient client= AliOSSClient.getInstance();
		//获取oss上文件的临时url 三个参数分别为bucket名、文件名、过期时间（ms）
		List<String> filelist = null;
		try {
			filelist = client.getFileList(bucketName);
			for (String filekey : filelist) {
				filemap.put(filekey, client.getUrl(bucketName,filekey, 3600));
			}
		} catch (Exception e) {
			logger.error("oss组件错误",e);
		}
		return filemap;
	}

}