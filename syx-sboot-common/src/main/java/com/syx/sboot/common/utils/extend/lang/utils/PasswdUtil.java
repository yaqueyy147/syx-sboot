// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PasswdUtil.java

package com.syx.sboot.common.utils.extend.lang.utils;

import java.util.Random;

public class PasswdUtil
{

	private static final char pwdChars[] = {
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
		'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
		'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', 
		'4', '5', '6', '7', '8', '9', '~', '!', '@', '#', 
		'$', '%', '^', '&', '*', '_', '+', 'A', 'B', 'C', 
		'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
		'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 
		'X', 'Y', 'Z'
	};
	private static final char dChars[] = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
	};
	private static final char verifyCodeChars[] = "2345679ACDEFHJKLMNPRSTUVWXYZ".toCharArray();
	private static final Random rand = new Random();

	public PasswdUtil()
	{
	}

	public static String genVerifyCode(int length)
	{
		if (length <= 0)
			throw new IllegalArgumentException("���Ȳ���С�ڻ������");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++)
			sb.append(verifyCodeChars[rand.nextInt(verifyCodeChars.length)]);

		return sb.toString();
	}

	public static String genPasswd(int length)
	{
		if (length <= 0)
			throw new IllegalArgumentException("���Ȳ���С�ڻ������");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++)
			sb.append(pwdChars[rand.nextInt(pwdChars.length)]);

		return sb.toString();
	}

	public static String genPasswdWithDigit(int length)
	{
		if (length <= 0)
			throw new IllegalArgumentException("���Ȳ���С�ڻ������");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++)
			sb.append(dChars[rand.nextInt(dChars.length)]);

		return sb.toString();
	}

}
