package com.yonyou.iuap.security.esapi;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.crypto.CipherText;
import org.owasp.esapi.crypto.PlainText;
import org.owasp.esapi.errors.EncryptionException;

/**
 * 
 * ESAPI加密工具类测试
 * 
 * 需要在ESAPI.properties中预先设置Encryptor.MasterKey和Encryptor.MasterSalt
 * 可以通过执行/src/test/resources/setMasterKey.bat生成后，替换ESAPI.properties中原有配置
 * #Encryptor.MasterKey= #
 * #Encryptor.MasterSalt= #
 * 
 * 具体的加密算法、hash算法、随机数算法、数字签名算法均在ESAPI.properties中进行配置
 * 
 * 编程示例：
 * 1、测试加密算法：testEncryptor()、testEncryptorWithSecretKey()
 * 2、测试hash算法：testHash()
 * 3、测试签名算法：testSign()
 * 4、测试有效性加密算法
 *
 */
public class EncryptorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 使用ESAPI.properties中的MasterKey进行加密
	 * 
	 * @throws EncryptException
	 * @throws IOException
	 */
	@Test
	public void testEncryptor() throws EncryptException, IOException {
		String plainText = "我是被加密的信息!";
		System.out.println("明文：" + plainText);

		String info = IUAPESAPI.encryptor().encrypt(plainText);
		System.err.println("密文：" + info);

		String decryptorInfo = IUAPESAPI.encryptor().decrypt(info);
		//String decryptorInfo = IEOPESAPI.encryptor().decrypt(IEOPESAPI.encoder().decodeFromBase64(info));
		System.err.println("解密后得到明文：" + decryptorInfo);
	}
	
	/**
	 * 使用自定义的SecretKey进行加密
	 * 
	 * @throws EncryptException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	@Test
	public void testEncryptorWithSecretKey() throws EncryptException, IOException, NoSuchAlgorithmException {
		// 生成SecretKey
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");   
        keyGenerator.init(128);
        SecretKey secretKey= keyGenerator.generateKey();
		System.out.println("Base64编码的秘钥："
				+ IUAPESAPI.encoder().encodeForBase64(secretKey.getEncoded()));
		
		String plainText = "我是被加密的信息!";
		System.err.println("明文：" + plainText);

		String info = IUAPESAPI.encryptor().encrypt(secretKey, plainText);
		System.err.println("密文：" + info);

		String decryptorInfo = IUAPESAPI.encryptor().decrypt(secretKey, info);
		System.err.println("解密后得到明文：" + decryptorInfo);
	}
	
	/**
	 * 测试hash
	 * @throws EncryptException
	 */
	@Test
	public void testHash() throws EncryptException {
		String text = "abc";
		String salt = "123";
		String hash = IUAPESAPI.encryptor().hash(text, salt);
		System.err.println(text + " hash后的结果：" + hash);
	}
	
	/**
	 * 测试签名
	 * @throws EncryptException
	 */
	@Test
	public void testSign() throws EncryptException {
		String text = "abc";
		String sign = IUAPESAPI.encryptor().sign(text);
		System.err.println(text + "签名后：" + sign);
		
		boolean isValid = IUAPESAPI.encryptor().verifySignature(sign, text);
		System.err.println("************正确文本验签***********");
		System.err.println("签名验证结果：" + isValid);
		
		isValid = IUAPESAPI.encryptor().verifySignature(sign, "abcd");
		System.err.println("************错误文本验签***********");
		System.err.println("签名验证结果：" + isValid);
	}
	
	/**
	 * 测试对数据的有效性加密
	 * @throws EncryptException
	 */
	@Test
	public void testSeal() throws EncryptException {
		String text = "abc";
		System.err.println("封装前：" + text);
		
		long expiration = IUAPESAPI.encryptor().getRelativeTime(3000);
		String sealedText = IUAPESAPI.encryptor().seal(text, expiration);
		System.err.println("封装后：" + sealedText);
		
		String unSealedText = IUAPESAPI.encryptor().unseal(sealedText);
		System.err.println("解封后：" + unSealedText);
	}

}
