package com.yonyou.uap.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.uap.entity.content.EmailContent;
import com.yonyou.uap.entity.content.MessageContent;
import com.yonyou.uap.entity.receiver.EmailReceiver;
import com.yonyou.uap.entity.receiver.MessageReceiver;
import com.yonyou.uap.entity.response.MessageResponse;
import com.yonyou.uap.exception.MessageSendException;
import com.yonyou.uap.service.impl.mail.EMailSend;

/**
 *
 * @author taomk 2016年4月6日 下午3:25:36
 *
 */
public class MessageSendTest {
	
	private static Logger logger = LoggerFactory.getLogger(MessageSendTest.class);
	
	/**
	 * Test method for {@link com.yonyou.uap.service.MessageSend#send()}.
	 * @throws MessageSendException 
	 */
	public void testSend() throws MessageSendException {
		
		long startTimeMillis = System.currentTimeMillis();
		
		MessageReceiver emailReceivers = new EmailReceiver("taomaokun@icloud.com,taomaokun@foxmail.com");
		MessageContent emailContent = new EmailContent("我是标题1", "测试内容1");

		List<MessageResponse> responseList = new MessageSend(emailReceivers, emailContent).send();
		
		for (Iterator<MessageResponse> iterator = responseList.iterator(); iterator.hasNext();) {
			MessageResponse messageResponse = iterator.next();
			logger.error(messageResponse.getResponseContent());
		}
		long endTimeMillis = System.currentTimeMillis();
		logger.error("Cost time(/ms): " + (endTimeMillis-startTimeMillis));
		
	}

	public static void testSend2() throws MessageSendException {
		String receivers = "songhx1@yonyou.com,songhx1@yonyou.com";
		String msgTitle = "消息标题-测试";
		String msgContent = "<h3>消息内容</h3>";
		String copyReceivers = "wujiank@yonyou.com";
		String blindCopyReceivers = "songhx1@yonyou.com";
		String[] attachFiles = {"C:\\MsgPersistenceController.java","D:\\attachFilesTes\\20111230_165626_11794.jpg"};
		EmailContent emailContent = new EmailContent(msgTitle, msgContent, copyReceivers, blindCopyReceivers, attachFiles);
		EMailSend emailSend= EMailSend.initialization();
		List<MessageResponse> msgResponseList = emailSend.send(null, new MessageReceiver(receivers), emailContent);
		log(msgResponseList);

	}
	
    public static void testSend3() throws MessageSendException {
    	String receivers = "songhx1@yonyou.com,songhx1@yonyou.com";
		String msgTitle = "消息标题-测试";
		String msgContent = "<h3>消息内容</h3>";
		String copyReceivers = "wujiank@yonyou.com";
		String blindCopyReceivers = "songhx1@yonyou.com";
		List<String> attachUrlList = new ArrayList<String>();
		attachUrlList.add("http://localhost:8080/iuap-message-center/static/css/bootstrap.css");
		attachUrlList.add("http://localhost:8080/iuap-message-center/static/css/message.css");

		EmailContent emailContent = new EmailContent(msgTitle, msgContent, copyReceivers, blindCopyReceivers, attachUrlList);
		EMailSend emailSend= EMailSend.initialization();
		List<MessageResponse> msgResponseList = emailSend.send(null, new MessageReceiver(receivers), emailContent);
		log(msgResponseList);
	}
    
    public static void testSend4() throws MessageSendException {
    	String receivers = "songhx1@yonyou.com,songhx1@yonyou.com";
		String msgTitle = "消息标题-测试";
		String msgContent = "<h3>消息内容</h3>";
		String copyReceivers = "songhx1@yonyou.com";
		String blindCopyReceivers = "songhx1@yonyou.com";
		Map<String,byte[]> attachBytesMap = new HashMap<String,byte[]>();

		File file = new File("C:\\备忘记录.xlsx");
		File file1 = new File("C:\\MsgPersistenceController.java");

		try {
			FileInputStream inStream = new FileInputStream(file);
			byte[] bytes = new byte[inStream.available()];
			inStream.read(bytes);
			attachBytesMap.put(file.getName(), bytes);
			FileInputStream inStream1 = new FileInputStream(file1);
			byte[] bytes1 = new byte[inStream1.available()];
			inStream1.read(bytes1);

			attachBytesMap.put(file1.getName(), bytes1);

		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}

		EmailContent emailContent = new EmailContent(msgTitle, msgContent, copyReceivers, blindCopyReceivers, attachBytesMap);
		EMailSend emailSend= EMailSend.initialization();
		List<MessageResponse> msgResponseList = emailSend.send(null, new MessageReceiver(receivers), emailContent);
		log(msgResponseList);
}
    
    private static void log(List<MessageResponse> msgResponseList){
    	if (msgResponseList!=null) {
			for (MessageResponse messageResponse : msgResponseList) {
				logger.error("recevier:"+messageResponse.getReceiver()+"  content:"+messageResponse.getResponseContent());
			}
		}
    }
}
