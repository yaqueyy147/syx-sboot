package com.syx.sboot;

import com.syx.sboot.common.json.JsonMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AdminBaseController{
	/**
	 * 客户端返回JSON字符串
	 * @param response
	 * @param object
	 * @return
	 */
	protected String renderString(HttpServletResponse response, Object object) {
		return renderString(response, JsonMapper.toJsonString(object), "application/json");
	}

	protected String renderString(HttpServletResponse response, Object object, String jsonCallBack) {
		return renderString(response, jsonCallBack + "(" + JsonMapper.toJsonString(object) + ")" , "application/json");
	}

	/**
	 * 客户端返回字符串
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
			response.setContentType(type);
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}
}
