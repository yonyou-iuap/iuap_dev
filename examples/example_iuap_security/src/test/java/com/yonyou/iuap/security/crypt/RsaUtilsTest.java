package com.yonyou.iuap.security.crypt;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

import org.apache.commons.codec.binary.Hex;

import com.yonyou.iuap.security.utils.RSAUtils;

public class RsaUtilsTest {

	public static void main(String[] args) throws Exception {
		String password = "123456";
		KeyPair gk = RSAUtils.generateKeyPair();
		System.out.println(gk.toString());
		
		RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
		String hexM = Hex.encodeHexString(publicKey.getModulus().toByteArray());
		String hexE = Hex.encodeHexString(publicKey.getPublicExponent().toByteArray());
		
		System.out.println("hexM:" + hexM);
		System.out.println("hexE:" + hexE);
		
		
		byte[] encryptBytes = RSAUtils.encrypt(publicKey, password.getBytes());
		String encryptStr = new String(Hex.encodeHex(encryptBytes));
		System.out.println("加密结果1：" + encryptStr);
		
		RSAPublicKey publicKey2 = RSAUtils.getRSAPublidKey(hexM, hexE);
		byte[] encryptBytes2 = RSAUtils.encrypt(publicKey2, password.getBytes());
		String encryptStr2 = new String(Hex.encodeHex(encryptBytes2));
		System.out.println("加密结果2：" + encryptStr2);
	}

}
