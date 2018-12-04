// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CookieHelper.java

package com.syx.sboot.common.utils.extend.lang.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieHelper
{

	public CookieHelper()
	{
	}

	public static void addCookies(HttpServletResponse response, Cookie cookie, int time)
	{
		cookie.setMaxAge(time);
		response.addCookie(cookie);
	}

	public static void removeCookie(HttpServletResponse response, Cookie cookie, String key)
	{
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	public static String getCookieByName(HttpServletRequest request, String name)
	{
		Map cookieMap = readCookieMap(request);
		if (cookieMap.containsKey(name))
		{
			Cookie cookie = (Cookie)cookieMap.get(name);
			return null != cookie ? cookie.getValue() : null;
		} else
		{
			return null;
		}
	}

	private static Map readCookieMap(HttpServletRequest request)
	{
		Map cookieMap = new HashMap();
		Cookie cookies[] = request.getCookies();
		if (null != cookies)
		{
			Cookie arr$[] = cookies;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Cookie cookie = arr$[i$];
				cookieMap.put(cookie.getName(), cookie);
			}

		}
		return cookieMap;
	}
}
