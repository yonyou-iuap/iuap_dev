package com.yonyou.login.util;
import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * RSA算法加密/解密工具类。
 */
public abstract class RSAUtils {

    /** 算法名称 */
    private static final String ALGORITHOM = "RSA";
    /** 密钥大小 */
    private static final int KEY_SIZE = 1024;
    /** 默认的安全服务提供者 */
    private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();

    private static KeyPairGenerator keyPairGen = null;
    private static KeyFactory keyFactory = null;
    /** 缓存的密钥对。 */
    private static KeyPair oneKeyPair = null;
    
    static {
        try {
            keyPairGen = KeyPairGenerator.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
            keyFactory = KeyFactory.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        } catch (NoSuchAlgorithmException ex) {
        }
    }

    private RSAUtils() {
    }

    /**
     * 生成并返回RSA密钥对，web应用启动的时候调用
     */
	public static synchronized KeyPair generateKeyPair() {
        try {
            keyPairGen.initialize(KEY_SIZE, new SecureRandom(DateFormatUtils.format(System.currentTimeMillis(),"yyyyMMdd").getBytes()));
            oneKeyPair = keyPairGen.generateKeyPair();
            return oneKeyPair;
        } catch (InvalidParameterException ex) {
        } catch (NullPointerException ex) {
        }
        return null;
    }

    /**
     * 返回RSA密钥对,需要保证在启动的时候生成并写入redis缓存，oneKeyPair为本地jvm中的缓存
     */
    public static KeyPair getKeyPair() {
    	return oneKeyPair;
    }
    
    public static void setKeyPair(KeyPair redisKeyPair) {
    	oneKeyPair = redisKeyPair;
    }
    
    /**
     * 根据给定的系数和专用指数构造一个RSA专用的公钥对象。
     * 
     * @param modulus 系数。
     * @param publicExponent 专用指数。
     * @return RSA专用公钥对象。
     */
    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(modulus),
                new BigInteger(publicExponent));
        try {
            return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException ex) {
        } catch (NullPointerException ex) {
        }
        return null;
    }

    /**
     * 根据给定的系数和专用指数构造一个RSA专用的私钥对象。
     * 
     * @param modulus 系数。
     * @param privateExponent 专用指数。
     * @return RSA专用私钥对象。
     */
    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) {
        RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus),
                new BigInteger(privateExponent));
        try {
            return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
        } catch (InvalidKeySpecException ex) {
        } catch (NullPointerException ex) {
        }
        return null;
    }
    
    /**
     * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的私钥对象。
     * 
     * @param modulus 系数。
     * @param privateExponent 专用指数。
     * @return RSA专用私钥对象。
     */
    public static RSAPrivateKey getRSAPrivateKey(String hexModulus, String hexPrivateExponent) {
        if(StringUtils.isBlank(hexModulus) || StringUtils.isBlank(hexPrivateExponent)) {
            return null;
        }
        byte[] modulus = null;
        byte[] privateExponent = null;
        try {
            modulus = Hex.decodeHex(hexModulus.toCharArray());
            privateExponent = Hex.decodeHex(hexPrivateExponent.toCharArray());
        } catch(DecoderException ex) {
        }
        if(modulus != null && privateExponent != null) {
            return generateRSAPrivateKey(modulus, privateExponent);
        }
        return null;
    }
    
    /**
     * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的公钥对象。
     * 
     * @param modulus 系数。
     * @param publicExponent 专用指数。
     * @return RSA专用公钥对象。
     */
    public static RSAPublicKey getRSAPublidKey(String hexModulus, String hexPublicExponent) {
        if(StringUtils.isBlank(hexModulus) || StringUtils.isBlank(hexPublicExponent)) {
            return null;
        }
        byte[] modulus = null;
        byte[] publicExponent = null;
        try {
            modulus = Hex.decodeHex(hexModulus.toCharArray());
            publicExponent = Hex.decodeHex(hexPublicExponent.toCharArray());
        } catch(DecoderException ex) {
        }
        if(modulus != null && publicExponent != null) {
            return generateRSAPublicKey(modulus, publicExponent);
        }
        return null;
    }

    /**
     * 使用指定的公钥加密数据。
     * 
     * @param publicKey 给定的公钥。
     * @param data 要加密的数据。
     * @return 加密后的数据。
     */
    public static byte[] encrypt(PublicKey publicKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        ci.init(Cipher.ENCRYPT_MODE, publicKey);
        return ci.doFinal(data);
    }

    /**
     * 使用指定的私钥解密数据。
     * 
     * @param privateKey 给定的私钥。
     * @param data 要解密的数据。
     * @return 原数据。
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        ci.init(Cipher.DECRYPT_MODE, privateKey);
        return ci.doFinal(data);
    }

    /**
     * 使用给定的公钥加密给定的字符串。
     * <p />
     * 若 {@code publicKey} 为 {@code null}，或者 {@code plaintext} 为 {@code null} 则返回 {@code
     * null}。
     * 
     * @param publicKey 给定的公钥。
     * @param plaintext 字符串。
     * @return 给定字符串的密文。
     */
    public static String encryptString(PublicKey publicKey, String plaintext) {
        if (publicKey == null || plaintext == null) {
            return null;
        }
        byte[] data = plaintext.getBytes();
        try {
            byte[] en_data = encrypt(publicKey, data);
            return new String(Hex.encodeHex(en_data));
        } catch (Exception ex) {
        }
        return null;
    }
    
    /**
     * 使用默认的公钥加密给定的字符串。
     * <p />
     * 若{@code plaintext} 为 {@code null} 则返回 {@code null}。
     * 
     * @param plaintext 字符串。
     * @return 给定字符串的密文。
     */
    public static String encryptString(String plaintext) {
        if(plaintext == null) {
            return null;
        }
        byte[] data = plaintext.getBytes();
        KeyPair keyPair = getKeyPair();
        try {
            byte[] en_data = encrypt((RSAPublicKey)keyPair.getPublic(), data);
            return new String(Hex.encodeHex(en_data));
        } catch(NullPointerException ex) {
        } catch(Exception ex) {
        }
        return null;
    }

    /**
     * 使用给定的私钥解密给定的字符串。
     * <p />
     * 若私钥为 {@code null}，或者 {@code encrypttext} 为 {@code null}或空字符串则返回 {@code null}。
     * 私钥不匹配时，返回 {@code null}。
     * 
     * @param privateKey 给定的私钥。
     * @param encrypttext 密文。
     * @return 原文字符串。
     */
    public static String decryptString(PrivateKey privateKey, String encrypttext) {
        if (privateKey == null || StringUtils.isBlank(encrypttext)) {
            return null;
        }
        try {
            byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
            byte[] data = decrypt(privateKey, en_data);
            return new String(data);
        } catch (Exception ex) {
        }
        return null;
    }
    
    /**
     * 使用默认的私钥解密给定的字符串。
     * <p />
     * 若{@code encrypttext} 为 {@code null}或空字符串则返回 {@code null}。
     * 私钥不匹配时，返回 {@code null}。
     * 
     * @param encrypttext 密文。
     * @return 原文字符串。
     */
    public static String decryptString(String encrypttext) {
        if(StringUtils.isBlank(encrypttext)) {
            return null;
        }
        KeyPair keyPair = getKeyPair();
        try {
            byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
            byte[] data = decrypt((RSAPrivateKey)keyPair.getPrivate(), en_data);
            return new String(data);
        } catch(NullPointerException ex) {
        } catch (Exception ex) {
        }
        return null;
    }
    
    /**
     * 使用默认的私钥解密由JS加密（使用此类提供的公钥加密）的字符串。
     * 
     * @param encrypttext 密文。
     * @return {@code encrypttext} 的原文字符串。
     */
    public static String decryptStringByJs(String encrypttext) {
        String text = decryptString(encrypttext);
        if(text == null) {
            return null;
        }
        return StringUtils.reverse(text);
    }
    
    /** 返回已初始化的默认的公钥。*/
    public static RSAPublicKey getDefaultPublicKey() {
        KeyPair keyPair = getKeyPair();
        if(keyPair != null) {
            return (RSAPublicKey)keyPair.getPublic();
        }
        return null;
    }
    
    /** 返回已初始化的默认的私钥。*/
    public static RSAPrivateKey getDefaultPrivateKey() {
        KeyPair keyPair = getKeyPair();
        if(keyPair != null) {
            return (RSAPrivateKey)keyPair.getPrivate();
        }
        return null;
    }
}