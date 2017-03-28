package com.yonyou.iuap.file;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import com.aliyun.oss.OSSClient;
import com.yonyou.iuap.file.client.LocalClient;



public class FileMgrTest {
	private static ApplicationContext context;
	public static Logger logger = LoggerFactory.getLogger(FileMgrTest.class);
	private OSSClient client;
	//这是fdfsbucket名变量，方便调用
	public static final String Private = "PRIVATE";
	public static final String Read = "READ";
	public static final String Full = "FULL";
	//这是bucket名变量，方便调用
	public static final String PrivateBucket = "your PrivateBucket";
	public static final String ReadBucket = "your ReadBucket";
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

	}
	
	/**
	 * 文件系统上传下载删除测试
	 * 测试前请设置application.properties的storeType=Local
	 * @throws Exception
	 */
	@Test
	public void testFSUpload() throws Exception {
			String filename;
			//下载并将文件转换为二进制数组
			//借用文件流生成文件的二进制数组
			LocalClient client =LocalClient.getInstance();		
			byte[] content =client.download("/etc/filetest/test.txt");
			
			//上传	
			filename=FileManager.uploadFile(null,"test.txt", content);
			//删除
			boolean flag=FileManager.deleteFile(null,filename);
			
			logger.info("filename is :"+ filename);
			logger.info("删除状态 :"+ flag);
			Assert.isTrue(flag);

	}
	
	
	/**
	 * fastdfs上传下载删除测试
	 * 测试前请设置application.properties的storeType=FastDfs
	 * @throws Exception
	 */
	@Test
	public void testFdfsUpload() throws Exception {
		String filename;
		//借用文件流生成文件的二进制数组
		LocalClient client =LocalClient.getInstance();		
		//参数为测试文件路径
		byte[] content =client.download("/etc/filetest/test.txt");
		//==================准备工作完毕=============================
		
		//==================private权限测试=============================
		//fastdfs上传
		filename=FileManager.uploadFile(Private, "test.txt",content);
		//fastdfs下载
		byte[] downloadContent=FileManager.downLoadFile(Private,filename);
		//获取文件url
		String url = FileManager.getUrl(Private, filename, 0);
		//获取图片url(将上传文件路径改为图片)
		//String imgurl = FileManager.getImgUrl(Private, filename, 0);
		//删除
		boolean flag=FileManager.deleteFile(Private,filename);	
		
		logger.info("filename is :"+ filename);
		logger.info("uri is :"+ url);
		logger.info("删除状态 :"+ flag);
		
		
		//==================read权限测试=============================
		//fastdfs上传
		filename=FileManager.uploadFile(Read, "testRead.txt",content);
		//fastdfs下载
		byte[] downloadContentRead=FileManager.downLoadFile(Read,filename);
		//获取文件url
		String urlRead = FileManager.getUrl(Read, filename, 0);
		//获取图片url(将上传文件路径改为图片)
		//String imgurl = FileManager.getImgUrl(Private, filename, 0);
		//删除
		boolean flagRead=FileManager.deleteFile(Read,filename);	
		
		
		logger.info("filename is :"+ filename);
		logger.info("uri is :"+ url);
		logger.info("删除状态 :"+ flag);
		
		Assert.isTrue(flag&&flagRead);
	}
	
	
	/**
	 * 阿里云上传下载删除测试
	 * 测试前请设置application.properties的storeType=AliOss
	 * 参数"zhukai"为阿里云oss的bucket名，请更换为您的bucket名
	 * @throws Exception
	 */
	@Test
	public void testAliUpload() throws Exception {
		String filename;
		//借用文件流生成文件的二进制数组
		LocalClient client =LocalClient.getInstance();
		//参数为测试文件路径;		
		byte[] content =client.download("/etc/filetest/test.txt");
		//==================准备工作完毕=============================
		
		//==================private权限bucket测试=============================
		//阿里云上传
		filename=FileManager.uploadFile(PrivateBucket,"test.txt",content);
		//阿里云下载
		byte[] downloadContent=FileManager.downLoadFile(PrivateBucket,filename);
		//获取文件url
		String url = FileManager.getUrl(PrivateBucket, filename, 60);
		//获取图片url(将上传文件路径改为图片)
		//String imgurl = FileManager.getImgUrl(PrivateBucket, filename, 60);
		//删除
		boolean flag=FileManager.deleteFile(PrivateBucket,filename);
		
		logger.info("filename is :"+ filename);
		logger.info("uri is :"+ url);
		logger.info("删除状态 :"+ flag);
		
		
		//==================read权限bucket测试=============================
		//阿里云上传
		filename=FileManager.uploadFile(ReadBucket,"testRead.txt",content);
		//阿里云下载
		byte[] downloadContentRead=FileManager.downLoadFile(ReadBucket,filename);
		//获取文件url
		String urlRead = FileManager.getUrl(ReadBucket, filename, 60);
		//获取图片url(将上传文件路径改为图片)
		//String imgurlRead = FileManager.getImgUrl(ReadBucket, filename, 60);
		//删除
		boolean flagRead=FileManager.deleteFile(ReadBucket,filename);
		
		
		logger.info("filename is :"+ filename);
		logger.info("uri is :"+ url);
		logger.info("删除状态 :"+ flag);
		
		Assert.isTrue(flag&&flagRead);

	}	
	
}