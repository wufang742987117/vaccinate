package com.thinkgem.jeesite.common.utils;



import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

/**
 * DES加密和解密工具,可以对字符串进行加密和解密操作 。
 */
public class DesUtil {

	private static Cipher encryptCipher = null;// 加密工具

	private static Cipher decryptCipher = null;// 解密工具
	
	private static Logger logger = Logger.getLogger(DesUtil.class);

	/**
	 * 加密字符串(自定义密钥)
	 */
	public static String encode(String strIn, String ownKey) {
		encryptCipher=initEncrypt(ownKey);
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}	

	/**
	 * 解密字符串(自定义密钥)
	 */
	public static String decode(String strIn, String ownKey) {
		decryptCipher=initDecrypt(ownKey);
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}
	
	/**
	 * 初始化加密工具
	 */
	public static Cipher initEncrypt(String ownKey) {
		Cipher encryptCipher = null;
		try {
			Key key = getKey(ownKey.getBytes());
			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return encryptCipher;
	}
	
	/**
	 * 初始化解密工具
	 */
	public static Cipher initDecrypt(String ownKey) {
		Cipher decryptCipher = null;
		try {
			Key key = getKey(ownKey.getBytes());
			decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return decryptCipher;
	}
	
	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 */
	public static String byteArr2HexStr(byte[] arrB) {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 * 
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 */
	public static byte[] hexStr2ByteArr(String strIn) {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 加密字节数组
	 * 
	 * @param arrB
	 *            需加密的字节数组
	 * @return 加密后的字节数组
	 */
	public static byte[] encrypt(byte[] arrB) {
		try {
			return encryptCipher.doFinal(arrB);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 解密字节数组
	 * 
	 * @param arrB
	 *            需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] arrB) {		
		try {
			return decryptCipher.doFinal(arrB);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws java.lang.Exception
	 */
	private static Key getKey(byte[] arrBTmp) {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// 生成密钥
		Key key = new SecretKeySpec(arrB, "DES");

		return key;
	}

	/**
	 * main方法
	 */
	public static void main(String[] args) {
		try {
			String test = "attach=OFFJN12017102115095612|1_1|3406030301|D3201211994901353X|1#1703_201409037_47_9500_1#1703_201608026_47_9500_1#";
			System.out.println("加密前的字符：" + test);
			String encode=DesUtil.encode(test,"123456789123456789123456789123456789");
//			String encode="feec98ea40ff4e562aa95e6a35cee51b0b43b9d791c8ccc1bd1fbdff3c058e1a2d5b5f686ba2ab2daa7be8b7ff2a668b51ac69a59641a7a879a2a655eb2fe69e4f48f0db27634b0bdcc165ab6c008700e923243a0e1373ce68442c9b1c01fdd6e2cfeed597bd34faff7ba199d547fa914f5f4c9efcc9dfa8";
			System.out.println("加密后的字符：" + encode);
			System.out.println("解密后的字符：" + DesUtil.decode(encode,"123456789123456789123456789123456789"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}