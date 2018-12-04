// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PageQuery.java

package com.syx.sboot.common.utils.extend.lang.utils;

import java.io.Serializable;

public class PageQuery
	implements Serializable
{

	private static final long serialVersionUID = 0x1d574d60027ab770L;
	protected int pageNum;
	protected int pageSize;

	public PageQuery()
	{
		pageNum = 0;
		pageSize = 0;
	}

	public PageQuery(int pageNum, int pageSize)
	{
		this.pageNum = 0;
		this.pageSize = 0;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	public int getPageNum()
	{
		return pageNum;
	}

	public void setPageNum(int pageNum)
	{
		this.pageNum = pageNum;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}
}
