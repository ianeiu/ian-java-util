package com.ianeiu.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static byte[] getMD5(byte[] src) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(src);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("系统不支持MD5", e);
		}
		return messageDigest.digest();
	}

	public static byte[] getMD5(String str) {
		return getMD5(str, "UTF-8");
	}

	public static byte[] getMD5(String str, String encode) {
		try {
			return getMD5(str.getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("系统不支持编码【" + encode + "】", e);
		}
	}

	public static String getMD5Str(String str) {
		byte[] byteArray = getMD5(str);
		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}
	

}