package com.syx.sboot.common.utils.extend.lang;

import java.util.Collection;
import java.util.Set;

public abstract interface ParameterHolder {
	public abstract void setParameter(Object paramObject1, Object paramObject2);

	public abstract Object getParameter(Object paramObject);

	public abstract Object removeParameter(Object paramObject);

	public abstract Set<Object> getParameterNames();

	public abstract Collection<Object> getParameterValues();

	public abstract boolean hasParameter(Object paramObject);

	public abstract void copy(ParameterHolder paramParameterHolder);
}