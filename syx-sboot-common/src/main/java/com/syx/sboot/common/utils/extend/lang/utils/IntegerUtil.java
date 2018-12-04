// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IntegerUtil.java

package com.syx.sboot.common.utils.extend.lang.utils;


// Referenced classes of package com.yjf.common.lang.util:
//			StringUtil

public class IntegerUtil
{

	public IntegerUtil()
	{
	}

	public static int strToInt(String str)
	{
		return StringUtil.isNotBlank(str) ? Integer.valueOf(str).intValue() : 0;
	}
}
