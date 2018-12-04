/**
 * Copyright 2014-2015 <a href="http://www.xmhlideal.com">exam</a> All rights reserved.
 */
package com.syx.sboot.webback.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

public class UserUtils {

	public static final String COOKIE_NAME = "userinfo";

	/**
	 * 根据ID获取登录用户
	 * 
	 * @param request
	 * @return 取不到返回null
	 */
	public static JSONObject getCookie(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = CookieUtil.cookieValueToJsonObject(request, COOKIE_NAME);
		}catch (Exception e){
			e.printStackTrace();
		}
		return jsonObject;
	}

	public static String getUserid(HttpServletRequest request) {
		try {
			return getCookie(request).get("id") + "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getUsername(HttpServletRequest request) {
		try {
			return getCookie(request).get("name") + "";
		} catch (Exception e) {
			;
		}
		return "";
	}
}