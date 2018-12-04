// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PageResult.java

package com.syx.sboot.common.utils.extend.lang.utils;

import java.util.*;

// Referenced classes of package com.yjf.common.lang.util:
//			Paginator

public class PageResult implements Iterable {

	private Paginator paginator;
	private List list;

	public PageResult() {
		this(null, null);
	}

	public PageResult(Collection c) {
		this(c, null);
	}

	public PageResult(Collection c, Paginator paginator) {
		if (c == null)
			c = new ArrayList();
		if (paginator == null)
			paginator = new Paginator(0, c.size());
		list = new ArrayList(c);
		this.paginator = paginator;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		if (paginator != null)
			this.paginator = paginator;
	}

	public int getPageSize() {
		return paginator.getItemsPerPage();
	}

	public int getTotalItem() {
		return paginator.getItems();
	}

	public int getTotalPage() {
		return paginator.getPages();
	}

	public void setPageSize(int i) {
		paginator.setItemsPerPage(i);
	}

	public void setTotalItem(int i) {
		paginator.setItems(i);
	}

	public void setTotalPage(int i) {
		setTotalItem(i * getPageSize());
	}

	public Iterator iterator() {
		return list.iterator();
	}

	public boolean add(Object t) {
		list.add(t);
		resetPaginator();
		return true;
	}

	private void resetPaginator() {
	}

	public boolean addAll(Collection c) {
		list.addAll(c);
		resetPaginator();
		return true;
	}

	public Object get(int index) {
		return list.get(index);
	}

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public void add(int index, Object element) {
		list.add(index, element);
		resetPaginator();
	}

	public boolean contains(Object o) {
		return list.contains(o);
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public Object[] toArray(Object a[]) {
		return list.toArray(a);
	}

	public boolean remove(Object o) {
		boolean b = list.remove(o);
		if (b)
			resetPaginator();
		return b;
	}

	public void clear() {
		list.clear();
		resetPaginator();
	}

	public Object set(int index, Object element) {
		return list.set(index, element);
	}

	public Object remove(int index) {
		Object t = list.remove(index);
		resetPaginator();
		return t;
	}

	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	public ListIterator listIterator() {
		return list.listIterator();
	}

	public ListIterator listIterator(int index) {
		return list.listIterator(index);
	}

	public List subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("PageResult{");
		sb.append("list=").append(list);
		sb.append(", paginator=").append(paginator);
		sb.append('}');
		return sb.toString();
	}
}
