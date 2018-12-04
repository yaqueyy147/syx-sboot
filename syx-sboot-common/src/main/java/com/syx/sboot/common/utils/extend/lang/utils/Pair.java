// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Pair.java

package com.syx.sboot.common.utils.extend.lang.utils;


public class Pair
{

	private final Object f;
	private final Object s;

	public static Pair build(Object f, Object s)
	{
		return new Pair(f, s);
	}

	public Pair(Object k, Object v)
	{
		f = k;
		s = v;
	}

	public Object getF()
	{
		return f;
	}

	public Object getS()
	{
		return s;
	}

	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = 31 * result + (f != null ? f.hashCode() : 0);
		result = 31 * result + (s != null ? s.hashCode() : 0);
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair)obj;
		if (f == null)
		{
			if (other.f != null)
				return false;
		} else
		if (!f.equals(other.f))
			return false;
		if (s == null)
		{
			if (other.s != null)
				return false;
		} else
		if (!s.equals(other.s))
			return false;
		return true;
	}

	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Pair <").append(f).append(",").append(s).append(">");
		return builder.toString();
	}
}
