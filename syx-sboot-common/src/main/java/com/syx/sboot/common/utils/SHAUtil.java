//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syx.sboot.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtil {
	public static String DEFAULT_CHARSET = "UTF-8";
	
	protected static char hexDigits[] = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			'a', 'b', 'c', 'd', 'e', 'f'
		};

	public SHAUtil() {
	}

	/**
	 * SHA加密
	 * 
	 * @param bytes
	 *            an array of byte.
	 * @return a {@link String} object.
	 */
	public static String encodeSHA(final byte[] bytes) {
		return DigestUtils.sha512Hex(bytes);
	}

	/**
	 * SHA加密
	 *
	 * @param str
	 *            a {@link String} object.
	 * @param charset
	 *            a {@link String} object.
	 * @return a {@link String} object.
	 */
	public static String encodeSHA(final String str, final String charset) {
		if (str == null) {
			return null;
		}
		try {
			byte[] bytes = str.getBytes(charset);
			return encodeSHA(bytes);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * SHA加密,默认utf-8
	 *
	 * @param str
	 *            a {@link String} object.
	 * @return a {@link String} object.
	 */
	public static String encodeSHA(final String str) {
		return encodeSHA(str, DEFAULT_CHARSET);
	}

	private static String encodeSHA(File file) {
		FileInputStream fis = null;
		FileChannel filechannel = null;
		MappedByteBuffer byteBuffer;

		MessageDigest messagedigest;
		try {
			messagedigest = MessageDigest.getInstance("SHA-512");
			fis = null;
			fis = new FileInputStream(file);
			filechannel = fis.getChannel();
			byteBuffer = filechannel.map(FileChannel.MapMode.READ_ONLY, 0L, file.length());
			messagedigest.update(byteBuffer);
			return SHAUtil.bufferToHex(messagedigest.digest());
		} catch (NoSuchAlgorithmException e) {
			;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			;
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				;
			}
			try {
				if (filechannel != null)
					filechannel.close();
			} catch (IOException e) {
				;
			}

		}

		return "";
	}
	
	private static String bufferToHex(byte bytes[], int m, int n)
	{
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++)
			appendHexPair(bytes[l], stringbuffer);

		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer)
	{
		char c0 = SHAUtil.hexDigits[(bt & 0xf0) >> 4];
		char c1 = SHAUtil.hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
	
	private static String bufferToHex(byte bytes[])
	{
		return bufferToHex(bytes, 0, bytes.length);
	}

	public static void main(String[] args) {
		File file = new File("D:\\11.rar");
		System.out.println(SHAUtil.encodeSHA(file));
	}
}
