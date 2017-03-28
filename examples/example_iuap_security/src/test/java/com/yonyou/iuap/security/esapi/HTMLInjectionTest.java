package com.yonyou.iuap.security.esapi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.owasp.esapi.codecs.Codec;

import com.yonyou.iuap.security.esapi.IUAPEncoder.TextCodec;

/**
 * HTML注入漏洞防御示例，包括HTML、CSS、JavaScript、URL。由于不同的脚本使用不同的解释器，不同的脚本需要采用不同的编码
 * 方式，ESAPI针对不同的脚本提供了不同的编码函数，请大家依据不同的场景使用。
 * 
 * 编程示例： 
 * 1. HTML编码：testHTMLEncode()； 
 * 2. HTML Attribute编码：testHTMLAttributeEncode()； 
 * 3. CSS编码：testCSSEncode()； 
 * 4. JavaScript编码：testJavaScriptEncode(); 
 * 5. URL编码：testURLEncode()；
 * 
 */
public class HTMLInjectionTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHTMLEncode() {
		String inputString = "<script>abcd1234567890</script>";
		System.out.println("");
		System.err.println("testHTMLEncode-->编码前：" + inputString + "，编码后："
				+ IUAPESAPI.encoder().htmlEncode(inputString));

		inputString = ",.-_ ";
		System.err.println("testHTMLEncode-->编码前：" + inputString + "，编码后："
				+ IUAPESAPI.encoder().htmlEncode(inputString));

		inputString = "one&two";
		System.err.println("testHTMLEncode-->编码前：" + inputString + "，编码后："
				+ IUAPESAPI.encoder().htmlEncode(inputString));
	}

	@Test
	public void testHTMLAttributeEncode() {
		String inputString = "<script>abcd1234567890</script>";
		System.out.println("");
		System.err.println("testHTMLAttributeEncode-->编码前：" + inputString
				+ "，编码后："
				+ IUAPESAPI.encoder().htmlAttributeEncode(inputString));

		inputString = ",.-_ ";
		System.err.println("testHTMLAttributeEncode-->编码前：" + inputString
				+ "，编码后："
				+ IUAPESAPI.encoder().htmlAttributeEncode(inputString));

		inputString = " !@$%()=+{}[]";
		System.err.println("testHTMLAttributeEncode-->编码前：" + inputString
				+ "，编码后："
				+ IUAPESAPI.encoder().htmlAttributeEncode(inputString));
	}

	@Test
	public void testCSSEncode() {
		String inputString = "<script>abcd1234567890</script>";
		System.out.println("");
		System.err.println("testCSSEncode-->编码前：" + inputString + "，编码后："
				+ IUAPESAPI.encoder().cssEncode(inputString));

		inputString = ",.-_ ";
		System.err.println("testCSSEncode-->编码前：" + inputString + "，编码后："
				+ IUAPESAPI.encoder().cssEncode(inputString));

		inputString = "!@$%()=+{}[]";
		System.err.println("testCSSEncode-->编码前：" + inputString + "，编码后："
				+ IUAPESAPI.encoder().cssEncode(inputString));
	}

	@Test
	public void testJavaScriptEncode() {
		String inputString = "<script>abcd1234567890</script>";
		System.out.println("");
		System.err.println("testJavaScriptEncode-->编码前：" + inputString
				+ "，编码后：" + IUAPESAPI.encoder().javaScriptEncode(inputString));

		inputString = ",.-_ ";
		System.err.println("testJavaScriptEncode-->编码前：" + inputString
				+ "，编码后：" + IUAPESAPI.encoder().javaScriptEncode(inputString));

		inputString = "!@$%()=+{}[]";
		System.err.println("testJavaScriptEncode-->编码前：" + inputString
				+ "，编码后：" + IUAPESAPI.encoder().javaScriptEncode(inputString));
	}

	@Test
	public void testURLEncode() throws Exception {
		String inputString = "<script>abcd1234567890</script>";
		System.out.println("");
		System.err.println("testURLEncode-->编码前：" + inputString + "，编码后："
				+ IUAPESAPI.encoder().urlEncode(inputString));

		inputString = ",.-_ &";
		System.err.println("testURLEncode-->编码前：" + inputString + "，编码后："
				+ IUAPESAPI.encoder().urlEncode(inputString));

		inputString = "!@$%()=+{}[]";
		System.err.println("testURLEncode-->编码前：" + inputString + "，编码后："
				+ IUAPESAPI.encoder().urlEncode(inputString));
	}

	@Test
	public void testXMLEncode() {
		String inputString = "<script>abcd1234567890</script>";
		System.out.println("");
		System.err.println("testXMLEncode-->编码前：" + inputString + "，编码后："
				+ IUAPESAPI.encoder().xmlEncode(inputString));

		inputString = ",.-_ &";
		System.err.println("testXMLEncode-->编码前：" + inputString + "，编码后："
				+ IUAPESAPI.encoder().xmlEncode(inputString));

		inputString = "!@$%()=+{}[]";
		System.err.println("testXMLEncode-->编码前：" + inputString + "，编码后："
				+ IUAPESAPI.encoder().xmlEncode(inputString));
	}

	@Test
	public void testXMLAttributeEncode() {
		String inputString = "<script>abcd1234567890</script>";
		System.out.println("");
		System.err
				.println("testXMLAttributeEncode-->编码前：" + inputString
						+ "，编码后："
						+ IUAPESAPI.encoder().xmlAttributeEncode(inputString));

		inputString = ",.-_ &";
		System.err
				.println("testXMLAttributeEncode-->编码前：" + inputString
						+ "，编码后："
						+ IUAPESAPI.encoder().xmlAttributeEncode(inputString));

		inputString = "!@$%()=+{}[]";
		System.err
				.println("testXMLAttributeEncode-->编码前：" + inputString
						+ "，编码后："
						+ IUAPESAPI.encoder().xmlAttributeEncode(inputString));
	}

	/**
	 * A parameterized string that uses escaping to make untrusted data safe
	 * before combining it with a command or query intended for use in an
	 * interpreter. 使用占位符（例如：?）的参数化字符串，在将它们组合成解释器命令或者查询之前可能会隐藏掉不安全地信息，因此需要对其进行处理
	 * 
	 * @throws Exception
	 */
	@Test
	public void testWebPreparedStringEncode() {
		// 缺省占位符为"?"，若模板字符串中有正常的"?"，需要用其他字符作为占位符，
		// 调用IEOPESAPI.encoder().webPreparedString(String strTemplate, String[]
		// paras, Codec[] codecs,
		// char placeholder)
		// 本示例采用缺省占位符。
		String templateString = "<a href=\"http:\\\\example.com;id=?\" onmouseover=\"alert('?')\">test:?</a>";
		// templateString中第一个?是URL组成部分，第二个？是JavaScript，第三个？是HTML。分别使用不同的Codec进行编码。

		String[] paras = new String[3];
		Codec[] codecs = new Codec[3];
		paras[0] = "ID#001";
		paras[1] = "This is a test.";
		paras[2] = "Push Me!";
		codecs[0] = TextCodec.PERCENT.codec();
		codecs[1] = TextCodec.JS.codec();
		codecs[2] = TextCodec.HTML.codec();

		// 另一种写法
		// String[] paras = new String[3];
		// TextCodec[] codecs = new TextCodec[3];
		// paras[0] = "ID#001";
		// paras[1] = "This is a test.";
		// paras[2] = "Push Me!";
		// codecs[0] = TextCodec.PERCENT;
		// codecs[1] = TextCodec.JS;
		// codecs[2] = TextCodec.HTML;

		System.out.println("");
		System.err.println("testWebPreparedStringEncode-->\n编码前："
				+ templateString
				+ "，\n编码后："
				+ IUAPESAPI.encoder().webPreparedString(templateString, paras,
						codecs));
	}

}
