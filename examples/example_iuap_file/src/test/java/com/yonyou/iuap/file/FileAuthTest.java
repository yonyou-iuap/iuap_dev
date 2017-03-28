package com.yonyou.iuap.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Title: FileAuthTest
 * </p>
 * <p>
 * Description: fdfs鉴权组件测试类
 * </p>
 * 
 * @author zhukai
 */
public class FileAuthTest {
	private static Logger Logger = LoggerFactory.getLogger(FileAuthTest.class);

	//生成文件字节数组
	public byte[] generateFileByte(String filename) throws IOException{
		File file=null;
		FileInputStream in=null;
		byte[] bytes = null;
		
		String filepath = Thread.currentThread().getContextClassLoader().getResource(filename).getPath();
		file=new File(filepath);
		try{
			if(file!=null){
				in=new FileInputStream(file);
				long length = file.length();
				if (length > Integer.MAX_VALUE) {
					// 文件太大，无法读取
					throw new IOException("File is to large " + file.getName());
				}
				bytes = new byte[(int) length];
				int offset = 0;
				int numRead = 0;
				while (offset < bytes.length&& (numRead = in.read(bytes, offset, bytes.length - offset)) >= 0) {
					offset += numRead;
				}
				if (offset < bytes.length) {
					bytes=null;
					throw new IOException("Could not completely read file "+ file.getName());
				}
			}			
		}finally{
			in.close();
		}
		return bytes;
	}
	
	// 上传fdfs文件并,并将上传文件名键入列表（classpath:fdfsfilelist）
	public void fdfsFileUpload() {
		List<String> filenames = new ArrayList<String>();
		String filename;
		String filealias;
		File file=null;
		OutputStream out = null;
		OutputStreamWriter osw =null;
		BufferedWriter  bw = null;
		
		try {
			file = new File(Thread.currentThread().getContextClassLoader()
					.getResource("fdfsfilelist").getPath());
			if (file != null) {
				out = new FileOutputStream(file);
				osw = new OutputStreamWriter(out, "UTF-8");
				bw = new BufferedWriter(osw);
			}
			Integer i;
			// 上传文件
			for (i = 0; i < 100; i++) {
				filename = FileManager.uploadFile("PRIVATE", "Koala.jpg",
						generateFileByte("Koala.jpg"));
				filenames.add(filename);
				// 生成列表
				bw.write(filename+"\n");
				Logger.info("写入文件列表：" +filename);
			}
			//刷新输出流
			bw.flush();
						
			 
		} catch (FileNotFoundException e) {
			Logger.error("无法写入文件" + file.getAbsolutePath());
			e.printStackTrace();
		} catch (IOException e) {
			Logger.error("写入文件出错" + file.getAbsolutePath());
			e.printStackTrace();
		}finally{
			try {
				bw.close();
				osw.close();
				out.close();
			} catch (IOException e) {
				Logger.error("无法关闭输出流");
				e.printStackTrace();
			}
		}
	}
	
	//删除fdfsfilelist文件列表内的文件
	public void deleteTestFiles() throws IOException {
		List<String> filenames = new ArrayList<String>();
		String filename;
		FileInputStream in = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		File file = null;
		try {
			file = new File(Thread.currentThread().getContextClassLoader()
					.getResource("fdfsfilelist").getPath());
			in = new FileInputStream(file);
			isr = new InputStreamReader(in, "UTF-8");
			br = new BufferedReader(isr);

			while ((filename = br.readLine()) != null) {
				filenames.add(filename);
			}

			// 删除测试文件
			boolean delflag;
			for (String delfilename : filenames) {
				delflag = FileManager.deleteFile("PRIVATE", delfilename);
				if (!delflag) {
					Logger.error(delfilename + "删除失败");
				}else{
					Logger.info( "已删除"+ delfilename);
				}
			}
		} finally {
			br.close();
			isr.close();
			in.close();
		}

	}

	@Test
	public void testFdfsAuth() throws IOException{
		fdfsFileUpload();
		//deleteTestFiles();
	}
	
	@Test
	public void testFdfsAuthOneFile() throws IOException{
		String backname = FileManager.uploadFile("PRIVATE", "Koala.jpg",generateFileByte("Koala.jpg"));
		//deleteTestFiles();
	}
	
	
	public void httpRequest(TimeCount reqCount) throws IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://172.20.7.24/g1/M00/00/16/rBQHGFgXBg6AGXkfAAvqH_kipG8227.jpg");
		//HttpGet httpget = new HttpGet("http://172.20.7.24/NginxStatus");
		long starttime =System.currentTimeMillis();
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
			System.out.println(response.getStatusLine());
			long endttime =System.currentTimeMillis();
			long count = endttime-starttime;
			reqCount.addReqCount(count);
			if(response.getStatusLine().getStatusCode()==200){
			reqCount.addAuthOkNum(1);;
			}
			System.out.println(count);
		} finally {
		    response.close();
		}
	}
	
	public class TimeCount{
		int authOkNum = 0;
		long reqCount = 0;
		public long getReqCount() {
			return reqCount;
		}
		public void setReqCount(long reqCount) {
			this.reqCount = reqCount;
		}
		
		public void addReqCount(long increment) {
			this.reqCount+= increment;
		}
		public int getAuthOkNum() {
			return authOkNum;
		}
		public void setAuthOkNum(int authOkNum) {
			this.authOkNum = authOkNum;
		}
		
		public void addAuthOkNum(int increment) {
			this.authOkNum+= increment;
		}
		
	}
	
	public class TestHttpWork implements Runnable {
		TimeCount reqCount;
	    public TestHttpWork(TimeCount reqCount){
	    	this.reqCount=reqCount;
	    }
		/**
		 * Description:
		 * 发送fdfs权限验证的http请求
		 */
		@Override
		public void run() {
			try {
				httpRequest(this.reqCount);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	@Test
	public void testRequest() throws IOException, InterruptedException{
		TimeCount reqCount = new TimeCount();
		//创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        TestHttpWork[] works = new TestHttpWork[1000];
        for(TestHttpWork w : works){
        	w=new TestHttpWork(reqCount);
        	pool.execute(w);
        }
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println(reqCount.getReqCount());
        System.out.println(reqCount.getAuthOkNum());
        
	}

}
