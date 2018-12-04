//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

public class Md5Util {
	public Md5Util() {
	}

	public static String Md5Of32(String str) {
		return Md5Of32(str, "UTF-8");
	}

	public static String Md5Of32(String str, String charsetName) {
		return MD5(str, charsetName, false);
	}

	public static String Md5Of16(String str) {
		return Md5Of16(str, "UTF-8");
	}

	public static String Md5Of16(String str, String charsetName) {
		return MD5(str, charsetName, true);
	}

	private static String MD5(String str, String charsetName, boolean is16) {
		try {
			char[] e = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
			byte[] btInput = str.getBytes(charsetName);
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char[] strarr = new char[j * 2];
			int k = 0;

			for (int i = 0; i < j; ++i) {
				byte byte0 = md[i];
				strarr[k++] = e[byte0 >>> 4 & 15];
				strarr[k++] = e[byte0 & 15];
			}

			return is16 ? (new String(strarr)).substring(8, 24) : new String(strarr);
		} catch (Exception var12) {
			return null;
		}
	}

	public static String MD5(String text) {
		StringBuffer md5 = new StringBuffer();

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte[] digest = md.digest();

			for (int i = 0; i < digest.length; ++i) {
				md5.append(Character.forDigit((digest[i] & 240) >> 4, 16));
				md5.append(Character.forDigit(digest[i] & 15, 16));
			}
		} catch (Exception var5) {
			;
		}

		return md5.toString();
	}

	public static String Md5Of32(File file) {
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[4096];

			int hasRead;
			while ((hasRead = fin.read(buffer)) != -1) {
				md.update(buffer, 0, hasRead);
			}

			return Md5Util.bufferToHex(md.digest());
		} catch (Exception e) {
			;
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					;
				}
			}
		}
		return "";
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++)
			appendHexPair(bytes[l], stringbuffer);

		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = SHAUtil.hexDigits[(bt & 0xf0) >> 4];
		char c1 = SHAUtil.hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	public static void main(String[] args) {
		File file = new File("D:\\11.rar");
		System.out.println(Md5Util.Md5Of32(file));
	}
}
