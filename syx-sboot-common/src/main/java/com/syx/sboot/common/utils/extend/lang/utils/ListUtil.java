// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ListUtil.java

package com.syx.sboot.common.utils.extend.lang.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package com.yjf.common.lang.util:
//			StringUtil

public class ListUtil
{

	public ListUtil()
	{
	}

	public static List toList(String str)
	{
		if (StringUtil.isBlank(str))
		{
			return null;
		} else
		{
			str = StringUtil.replace(str, "��", ",");
			String array[] = StringUtil.split(str, ",");
			List list = Arrays.asList(array);
			return list;
		}
	}

	public static List toList(String str, String eparatorChar)
	{
		if (StringUtil.isBlank(str))
		{
			return null;
		} else
		{
			String array[] = StringUtil.split(str, eparatorChar);
			List list = Arrays.asList(array);
			return list;
		}
	}

	public static String toStr(List list)
	{
		if (list == null || list.isEmpty())
			return "";
		StringBuilder sb = new StringBuilder();
		String _str;
		for (Iterator i$ = list.iterator(); i$.hasNext(); sb.append(_str).append(","))
			_str = (String)i$.next();

		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String toStr(String list[])
	{
		if (list == null || list.length == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		String arr$[] = list;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			String _str = arr$[i$];
			sb.append(_str).append(",");
		}

		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static boolean isEmpty(List list)
	{
		return null == list || list.isEmpty();
	}

	public static boolean isNotEmpty(List list)
	{
		return null != list && !list.isEmpty();
	}
}
