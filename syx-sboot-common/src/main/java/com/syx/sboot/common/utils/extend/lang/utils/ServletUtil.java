// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServletUtil.java

package com.syx.sboot.common.utils.extend.lang.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletUtil {
	private static final Logger logger = LoggerFactory.getLogger("COMMON");
	public static final String TEXT = "text/html";
	public static final String JSON = "application/x-json";

	public static void asynPrintResult(HttpServletResponse response, String pageResultType, Object value) {
		if (null == value) {
			return;
		}

		response.setContentType(pageResultType);
		response.setCharacterEncoding("UTF-8");

		PrintWriter writer = null;
		try {
			if (logger.isInfoEnabled()) {
				logger.info("异步输出结果 [value = " + value + "]");
			}
			writer = response.getWriter();
			writer.print(value.toString());
			writer.flush();

			if (null != writer)
				writer.close();
		} catch (IOException e) {
			logger.error("异步输出结果异常 [pageResultType = " + pageResultType + ", value = " + value + "]", e);

			if (null != writer)
				writer.close();
		} finally {
			if (null != writer)
				writer.close();
		}
	}
}
