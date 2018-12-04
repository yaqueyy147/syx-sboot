// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PrintLogTool.java

package com.syx.sboot.common.utils.extend.lang.utils;

import org.slf4j.Logger;

/**
 * @deprecated Class PrintLogTool is deprecated
 */

public class PrintLogTool
{

	public PrintLogTool()
	{
	}

	public static void info(String logStr, Logger logger)
	{
		if (logger.isInfoEnabled())
			logger.info(logStr);
	}

	public static void debug(String logStr, Logger logger)
	{
		if (logger.isDebugEnabled())
			logger.debug(logStr);
	}
}
