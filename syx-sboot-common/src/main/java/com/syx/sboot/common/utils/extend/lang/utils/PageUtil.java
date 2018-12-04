// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PageUtil.java

package com.syx.sboot.common.utils.extend.lang.utils;


public class PageUtil
{

	private int listbegin;
	private int listend;
	private int currentPage;
	private int liststep;
	private int totalPage;

	public PageUtil()
	{
		liststep = 8;
	}

	public static String buildPager(int currentPage, int totalPages, int totalItems, String url)
	{
		String xml = (new StringBuilder()).append("<div>�� ").append(totalItems).append(" ����¼, ��ǰ�� ").append(currentPage).append(" ҳ , ��").append(totalPages).append(" ҳ  ").toString();
		PageUtil pageUtil = new PageUtil();
		pageUtil.setCurrentPage(currentPage);
		pageUtil.setTotalPage(totalPages);
		StringBuffer sb = new StringBuffer(xml);
		int i = pageUtil.getListbegin();
		int end = pageUtil.getListend();
		if (pageUtil.getListend() > pageUtil.getTotalPage())
		{
			i = (pageUtil.getTotalPage() - pageUtil.getListstep()) + 1;
			end = pageUtil.getTotalPage();
			if (pageUtil.getTotalPage() < pageUtil.getListstep())
				i = 1;
		}
		if (i == 1 && end != pageUtil.getTotalPage())
		{
			end = pageUtil.getListstep();
			if (pageUtil.getListstep() > pageUtil.getTotalPage())
				end = pageUtil.getTotalPage();
		}
		if (pageUtil.getListbegin() != 1)
		{
			String temp = (new StringBuilder()).append(url).append(url.indexOf("?") != -1 ? "&pi=1" : "?pi=1").toString();
			sb.append((new StringBuilder()).append("<a href='").append(temp).append("'>��ҳ</a>&nbsp;&nbsp;").toString());
		}
		if (pageUtil.getCurrentPage() != 1)
		{
			String temp = (new StringBuilder()).append(url).append(url.indexOf("?") != -1 ? (new StringBuilder()).append("&pi=").append(pageUtil.getCurrentPage() - 1).toString() : (new StringBuilder()).append("?pi=").append(pageUtil.getCurrentPage() - 1).toString()).toString();
			sb.append((new StringBuilder()).append("<a href='").append(temp).append("'>��һҳ</a>&nbsp;&nbsp;").toString());
		}
		for (; i <= end; i++)
		{
			String temp = (new StringBuilder()).append(url).append(url.indexOf("?") != -1 ? (new StringBuilder()).append("&pi=").append(i).toString() : (new StringBuilder()).append("?pi=").append(i).toString()).toString();
			if (pageUtil.getCurrentPage() == i)
				sb.append((new StringBuilder()).append("<b><a href='").append(temp).append("'>").append(i).append("</a></b>&nbsp;&nbsp;").toString());
			else
				sb.append((new StringBuilder()).append("<a href='").append(temp).append("'/>").append(i).append("</a>&nbsp;&nbsp;").toString());
		}

		if (pageUtil.getCurrentPage() < pageUtil.getTotalPage())
		{
			String temp = (new StringBuilder()).append(url).append(url.indexOf("?") != -1 ? (new StringBuilder()).append("&pi=").append(pageUtil.getCurrentPage() + 1).toString() : (new StringBuilder()).append("?pi=").append(pageUtil.getCurrentPage() + 1).toString()).toString();
			sb.append((new StringBuilder()).append("<a href='").append(temp).append("'>��һҳ&nbsp;&nbsp;</a>").toString());
		}
		if (end != pageUtil.getTotalPage())
		{
			String temp = (new StringBuilder()).append(url).append(url.indexOf("?") != -1 ? (new StringBuilder()).append("&pi=").append(pageUtil.getTotalPage()).toString() : (new StringBuilder()).append("?pi=").append(pageUtil.getTotalPage()).toString()).toString();
			sb.append((new StringBuilder()).append("<a href='").append(temp).append("'>βҳ</a>").toString());
		}
		sb.append("</div>");
		return sb.toString();
	}

	public int getListbegin()
	{
		int a = (int)Math.ceil((double)liststep / 2D);
		if (currentPage <= a)
			listbegin = 1;
		else
			listbegin = currentPage - (a - 1);
		return listbegin;
	}

	public int getListend()
	{
		int a = (int)Math.ceil((double)liststep / 2D);
		listend = currentPage + a;
		return listend;
	}

	public int getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}

	public int getListstep()
	{
		return liststep;
	}

	public void setListstep(int liststep)
	{
		this.liststep = liststep;
	}

	public int getTotalPage()
	{
		return totalPage;
	}

	public void setTotalPage(int totalPage)
	{
		this.totalPage = totalPage;
	}

	public void setListbegin(int listbegin)
	{
		this.listbegin = listbegin;
	}

	public void setListend(int listend)
	{
		this.listend = listend;
	}

	public String toString()
	{
		return String.format("PageUtil [listbegin=%s, listend=%s, currentPage=%s, liststep=%s, totalPage=%s]", new Object[] {
			Integer.valueOf(listbegin), Integer.valueOf(listend), Integer.valueOf(currentPage), Integer.valueOf(liststep), Integer.valueOf(totalPage)
		});
	}
}
