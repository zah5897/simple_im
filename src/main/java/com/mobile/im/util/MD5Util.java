package com.mobile.im.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	// 静态方法，便于作为工具类
	public static String getMd5(String plainText) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(plainText.getBytes());
		byte b[] = md.digest();

		int i;

		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		// 32位加密
		return buf.toString();
		// 16位的加密
		// return buf.toString().substring(8, 24);
	}

	public static String getMd5_16(String plainText) throws NoSuchAlgorithmException {
		return getMd5(plainText).substring(8, 24);
	}
	
	public static void main(String[] args) {
		try {
			System.out.println("MD5_32:"+getMd5("15"));
			System.out.println("MD5_16:"+getMd5_16("26"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
