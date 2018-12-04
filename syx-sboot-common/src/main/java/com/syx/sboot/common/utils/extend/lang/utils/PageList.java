// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PageList.java

package com.syx.sboot.common.utils.extend.lang.utils;

import java.util.ArrayList;
import java.util.Collection;

// Referenced classes of package com.yjf.common.lang.util:
//			Paginator

public class PageList extends ArrayList
{

	private static final long serialVersionUID = 0x2d35353138353831L;
	private Paginator paginator;

	public PageList()
	{
		paginator = new Paginator();
	}

	public PageList(Collection c)
	{
		this(c, null);
	}

	public PageList(Collection c, Paginator paginator)
	{
		super(c);
		this.paginator = paginator != null ? paginator : new Paginator();
	}

	public Paginator getPaginator()
	{
		return paginator;
	}

	public void setPaginator(Paginator paginator)
	{
		if (paginator != null)
			this.paginator = paginator;
	}

	public int getPageSize()
	{
		return paginator.getItemsPerPage();
	}

	public int getTotalItem()
	{
		return paginator.getItems();
	}

	public int getTotalPage()
	{
		return paginator.getPages();
	}

	public void setPageSize(int i)
	{
		paginator.setItemsPerPage(i);
	}

	public void setTotalItem(int i)
	{
		paginator.setItems(i);
	}

	public void setTotalPage(int i)
	{
		setTotalItem(i * getPageSize());
	}
}
