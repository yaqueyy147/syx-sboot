// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Paginator.java

package com.syx.sboot.common.utils.extend.lang.utils;

import java.io.Serializable;

public class Paginator
	implements Serializable, Cloneable
{
	private static final long serialVersionUID = -8508896172759181668L;
	  public static final int DEFAULT_ITEMS_PER_PAGE = 5;
	  public static final int DEFAULT_SLIDER_SIZE = 7;
	  public static final int UNKNOWN_ITEMS = 2147483647;
	  private int page;
	  private int items;
	  private int itemsPerPage;

	  public Paginator()
	  {
	    this(0);
	  }

	  public Paginator(int itemsPerPage)
	  {
	    this(itemsPerPage, 2147483647);
	  }

	  public Paginator(int itemsPerPage, int items)
	  {
	    this.items = (items >= 0 ? items : 0);
	    this.itemsPerPage = (itemsPerPage > 0 ? itemsPerPage : 5);
	    this.page = calcPage(0);
	  }

	  public int getPages()
	  {
	    return (int)Math.ceil(this.items / this.itemsPerPage);
	  }

	  public int getPage()
	  {
	    return this.page;
	  }

	  public int setPage(int page)
	  {
	    return this.page = calcPage(page);
	  }

	  public int getItems()
	  {
	    return this.items;
	  }

	  public int setItems(int items)
	  {
	    this.items = (items >= 0 ? items : 0);
	    setPage(this.page);
	    return this.items;
	  }

	  public int getItemsPerPage()
	  {
	    return this.itemsPerPage;
	  }

	  public int setItemsPerPage(int itemsPerPage)
	  {
	    int tmp = this.itemsPerPage;

	    this.itemsPerPage = (itemsPerPage > 0 ? itemsPerPage : 5);

	    if (this.page > 0) {
	      setPage((int)((this.page - 1) * tmp / this.itemsPerPage) + 1);
	    }

	    return this.itemsPerPage;
	  }

	  public int getOffset()
	  {
	    return this.page > 0 ? this.itemsPerPage * (this.page - 1) : 0;
	  }

	  public int getLength()
	  {
	    if (this.page > 0) {
	      return Math.min(this.itemsPerPage * this.page, this.items) - this.itemsPerPage * (this.page - 1);
	    }
	    return 0;
	  }

	  public int getBeginIndex()
	  {
	    if (this.page > 0) {
	      return this.itemsPerPage * (this.page - 1);
	    }
	    return 0;
	  }

	  public int getEndIndex()
	  {
	    if (this.page > 0) {
	      return Math.min(this.itemsPerPage * this.page, this.items);
	    }
	    return 0;
	  }

	  public int setItem(int itemOffset)
	  {
	    return setPage(itemOffset / this.itemsPerPage + 1);
	  }

	  public int getFirstPage()
	  {
	    return calcPage(1);
	  }

	  public int getLastPage()
	  {
	    return calcPage(getPages());
	  }

	  public int getPreviousPage()
	  {
	    return calcPage(this.page - 1);
	  }

	  public int getPreviousPage(int n)
	  {
	    return calcPage(this.page - n);
	  }

	  public int getNextPage()
	  {
	    return calcPage(this.page + 1);
	  }

	  public int getNextPage(int n)
	  {
	    return calcPage(this.page + n);
	  }

	  public boolean isDisabledPage(int page)
	  {
	    return (page < 1) || (page > getPages()) || (page == this.page);
	  }

	  public int[] getSlider()
	  {
	    return getSlider(7);
	  }

	  public int[] getSlider(int width)
	  {
	    int pages = getPages();

	    if ((pages < 1) || (width < 1)) {
	      return new int[0];
	    }
	    if (width > pages) {
	      width = pages;
	    }

	    int[] slider = new int[width];
	    int first = this.page - (width - 1) / 2;

	    if (first < 1) {
	      first = 1;
	    }

	    if (first + width - 1 > pages) {
	      first = pages - width + 1;
	    }

	    for (int i = 0; i < width; i++) {
	      slider[i] = (first + i);
	    }

	    return slider;
	  }

	  protected int calcPage(int page)
	  {
	    int pages = getPages();

	    if (pages > 0) {
	      return page > pages ? pages : page < 1 ? 1 : page;
	    }

	    return 0;
	  }

	  public Object clone()
	  {
	    try
	    {
	      return super.clone(); } catch (CloneNotSupportedException e) {
	    }
	    return null;
	  }

	  public String toString()
	  {
	    StringBuffer sb = new StringBuffer("Paginator: page ");

	    if (getPages() < 1) {
	      sb.append(getPage());
	    } else {
	      int[] slider = getSlider();

	      for (int i = 0; i < slider.length; i++) {
	        if (isDisabledPage(slider[i]))
	          sb.append('[').append(slider[i]).append(']');
	        else {
	          sb.append(slider[i]);
	        }

	        if (i < slider.length - 1) {
	          sb.append('\t');
	        }
	      }
	    }

	    sb.append(" of ").append(getPages()).append(",\n");
	    sb.append("    Showing items ").append(getBeginIndex()).append(" to ").append(getEndIndex()).append(" (total ").append(getItems()).append(" items), ");

	    sb.append("offset=").append(getOffset()).append(", length=").append(getLength());

	    return sb.toString();
	  }
}
