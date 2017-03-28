package com.yonyou.iuap.security.esapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Base64编码/解码测试
 * 
 * 编程示例：
 * 1、测试Base64编码
 * 2、测试Base64解码
 *
 */
public class Base64Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 测试Base64编码
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void encodeForBase64Test() throws UnsupportedEncodingException {
		String plainText = "Base64编码/解码测试";
		System.err.println("编码前：" + plainText);
		byte[] bytes = plainText.getBytes("UTF-8");
		String base64Text = IUAPESAPI.encoder().encodeForBase64(bytes);
		System.err.println("编码后：" + base64Text);

	}
	
	/**
	 * 测试Base64解码
	 * @throws IOException
	 */
	@Test
	public void decodeForBase64Test() throws IOException {
		String base64Text = "QmFzZTY057yW56CBL+ino+eggea1i+ivlQ==";
		System.err.println("解码前：" + base64Text);
		byte[] bytes = IUAPESAPI.encoder().decodeFromBase64(base64Text);
		String plainText = new String(bytes, "UTF-8");
		System.err.println("解码后：" + plainText);
	}
}
