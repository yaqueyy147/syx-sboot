package com.syx.sboot.common.utils.extend.lang;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ParameterHolderSupport
  implements ParameterHolder
{
  private final Map<Object, Object> params = new LinkedHashMap();

  public void setParameter(Object name, Object value) {
    this.params.put(name, value);
  }

  public Object getParameter(Object name) {
    return this.params.get(name);
  }

  public Object removeParameter(Object name) {
    return this.params.remove(name);
  }

  public Set<Object> getParameterNames() {
    return this.params.keySet();
  }

  public Collection<Object> getParameterValues() {
    return this.params.values();
  }

  public boolean hasParameter(Object name) {
    return this.params.containsKey(name);
  }

  public void copy(ParameterHolder parameterHolder) {
    if (parameterHolder == null) {
      return;
    }
    if ((parameterHolder instanceof ParameterHolderSupport)) {
      ParameterHolderSupport phs = (ParameterHolderSupport)parameterHolder;
      phs.params.putAll(this.params);
      return;
    }
    for (Map.Entry entry : this.params.entrySet())
      parameterHolder.setParameter(entry.getKey(), entry.getValue());
  }
}