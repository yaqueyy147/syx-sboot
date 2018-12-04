package com.syx.sboot.common.access;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @ClassName: BeanUtil.java
 *
 * @Description: BeanUtil
 *
 * @author GERRARD
 *
 * @date 2015年1月19日下午1:38:19
 * 
 */
public final class PropertiesUtil {

	public static final char UNDERLINE = '_';

	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 利用反射实现对象之间相同属性复制
	 * 
	 * @param source
	 *            要复制的
	 * @param to
	 *            复制给
	 */
	public static void copyProperties(Object source, Object target) throws Exception {

		copyPropertiesExclude(source, target, null);
	}

	/**
	 * 复制对象属性
	 * 
	 * @param from
	 * @param to
	 * @param excludsArray
	 *            排除属性列表
	 * @throws Exception
	 */
	public static void copyPropertiesExclude(Object from, Object to, String[] excludsArray) throws Exception {

		List<String> excludesList = null;

		if (excludsArray != null && excludsArray.length > 0) {

			excludesList = Arrays.asList(excludsArray); // 构造列表对象
		}

		Method[] fromMethods = ReflectUtil.getDeclaredMethods(from.getClass());
		Method[] toMethods = ReflectUtil.getDeclaredMethods(to.getClass());
		Method fromMethod = null, toMethod = null;
		String fromMethodName = null, toMethodName = null;

		for (int i = 0; i < fromMethods.length; i++) {

			fromMethod = fromMethods[i];
			fromMethodName = fromMethod.getName();

			if (!fromMethodName.startsWith("get"))
				continue;
			// 排除列表检测
			if (excludesList != null && excludesList.contains(fromMethodName.substring(3).toLowerCase())) {

				continue;
			}
			toMethodName = "set" + fromMethodName.substring(3);
			toMethod = findMethodByName(toMethods, toMethodName);

			if (toMethod == null)
				continue;
			Object value = fromMethod.invoke(from, new Object[0]);

			if (value == null)
				continue;
			// 集合类判空处理
			if (value instanceof Collection) {

				Collection<?> newValue = (Collection<?>) value;

				if (newValue.size() <= 0)
					continue;
			}

			toMethod.invoke(to, new Object[] { value });
		}
	}

	/**
	 * 对象属性值复制，仅复制指定名称的属性值
	 * 
	 * @param from
	 * @param to
	 * @param includsArray
	 * @throws Exception
	 */
	public static void copyPropertiesInclude(Object from, Object to, String[] includsArray) throws Exception {

		List<String> includesList = null;

		if (includsArray != null && includsArray.length > 0) {

			includesList = Arrays.asList(includsArray);

		} else {

			return;
		}
		Method[] fromMethods = from.getClass().getDeclaredMethods();
		Method[] toMethods = to.getClass().getDeclaredMethods();
		Method fromMethod = null, toMethod = null;
		String fromMethodName = null, toMethodName = null;

		for (int i = 0; i < fromMethods.length; i++) {

			fromMethod = fromMethods[i];
			fromMethodName = fromMethod.getName();

			if (!fromMethodName.contains("get"))
				continue;

			// 排除列表检测
			String str = fromMethodName.substring(3);

			if (!includesList.contains(str.substring(0, 1).toLowerCase() + str.substring(1))) {
				continue;
			}

			toMethodName = "set" + fromMethodName.substring(3);
			toMethod = findMethodByName(toMethods, toMethodName);

			if (toMethod == null)
				continue;

			Object value = fromMethod.invoke(from, new Object[0]);

			if (value == null)
				continue;

			// 集合类判空处理
			if (value instanceof Collection) {

				Collection<?> newValue = (Collection<?>) value;

				if (newValue.size() <= 0)
					continue;
			}

			toMethod.invoke(to, new Object[] { value });
		}
	}

	/**
	 * 从方法数组中获取指定名称的方法
	 * 
	 * @param methods
	 * @param name
	 * @return
	 */
	public static Method findMethodByName(Method[] methods, String name) {

		for (int j = 0; j < methods.length; j++) {

			if (methods[j].getName().equals(name)) {

				return methods[j];
			}

		}
		return null;
	}

	/*
	 * 循环递归获取所有属性
	 */
	public static void getAllFileds(List<Field> fieldLst, Class<?> clazz) {
		if (clazz == Object.class)
			return;
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			if (fieldLst == null)
				fieldLst = new ArrayList<Field>();
			Collections.addAll(fieldLst, fields);
		}
		getAllFileds(fieldLst, clazz.getSuperclass());
	}
}
