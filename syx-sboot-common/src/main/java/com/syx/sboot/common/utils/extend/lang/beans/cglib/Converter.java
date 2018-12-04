package com.syx.sboot.common.utils.extend.lang.beans.cglib;

public abstract interface Converter extends net.sf.cglib.core.Converter
{
  public abstract Object convert(Object paramObject1, Class paramClass, Object paramObject2);
}