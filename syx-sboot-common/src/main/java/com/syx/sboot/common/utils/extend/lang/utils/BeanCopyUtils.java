// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BeanCopyUtils.java

package com.syx.sboot.common.utils.extend.lang.utils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;

// Referenced classes of package com.yjf.common.lang.util:
//			Converter

/**
 * @deprecated Class BeanCopyUtils is deprecated
 */

public class BeanCopyUtils {
	private static final Map<Key, BeanCopier> copierMap = Maps.newConcurrentMap();

	public static void copy(Object from, Object to) {
		copy(from, to, (Converter) null);
	}

	public static void copy(Object from, Object to, Converter converter) {
		Assert.notNull(from, "源对象不能为空");
		Assert.notNull(to, "目标对象不能为空");
		boolean useConverter = converter != null;
		Key key = getKey(from, to, useConverter);
		if (!copierMap.containsKey(key)) {
			synchronized (BeanCopyUtils.class) {
				if (!copierMap.containsKey(key)) {
					BeanCopier copy = BeanCopier.create(from.getClass(), to.getClass(), useConverter);

					copierMap.put(key, copy);
				}
			}
		}
		copy(from, to, converter, key);
	}

	static Set<String> convertPropertiesToSetter(String[] ignoreProperties) {
		Set set = Sets.newHashSet();
		for (String p : ignoreProperties)
			if (!StringUtils.isBlank(p)) {
				StringBuilder sb = new StringBuilder();
				sb.append("set");
				sb.append(Character.toUpperCase(p.charAt(0)));
				sb.append(p.substring(1, p.length()));
				set.add(sb.toString());
			}
		return set;
	}

	private static void copy(Object from, Object to, Converter converter, Key key) {
		BeanCopier copy = (BeanCopier) copierMap.get(key);
		copy.copy(from, to, converter);
	}

	private static Key getKey(Object from, Object to, boolean useConverter) {
		Class fromClass = from.getClass();
		Class toClass = to.getClass();
		return new Key(fromClass, toClass, useConverter);
	}

	private static class Key {
		private Class<?> fromClass;
		private Class<?> toClass;
		private boolean useConverter;

		public Key(Class<?> fromClass, Class<?> toClass, boolean useConverter) {
			this.fromClass = fromClass;
			this.toClass = toClass;
			this.useConverter = useConverter;
		}

		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = 31 * result + (this.fromClass == null ? 0 : this.fromClass.hashCode());
			result = 31 * result + (this.toClass == null ? 0 : this.toClass.hashCode());
			result = 31 * result + (this.useConverter ? 1231 : 1237);
			return result;
		}

		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			if (this.fromClass == null) {
				if (other.fromClass != null)
					return false;
			} else if (!this.fromClass.equals(other.fromClass))
				return false;
			if (this.toClass == null) {
				if (other.toClass != null)
					return false;
			} else if (!this.toClass.equals(other.toClass))
				return false;
			if (this.useConverter != other.useConverter)
				return false;
			return true;
		}
	}
}
