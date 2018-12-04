package com.syx.sboot.common.utils;

import com.syx.sboot.common.utils.extend.DateUtil;
import com.syx.sboot.common.utils.extend.NumberUtil;
import com.syx.sboot.common.utils.extend.lang.money.Money;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;


public class WebUtil {
	private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);
	
	public static Map<String, String> getRequestMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();
		Enumeration<String> en = request.getParameterNames();
		while (en.hasMoreElements()) {
			String parameterName = en.nextElement();
			map.put(parameterName, request.getParameter(parameterName));
		}
		return map;
	}
	
	public static String getPoPropertyByRequest(HttpServletRequest request, String paramname){
		try{
			String parameterValue = request.getParameter(paramname);
			if (!StringUtils.isEmpty(parameterValue))
				return parameterValue;
		}catch(Exception e){
			;
		}
		return "";
	}
	public static void setPoPropertyByRequest(Object po, HttpServletRequest request) {
		if (po == null)
			return;
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(po.getClass());
		for (int i = 0; i < pds.length; i++) {
			try {
				
				PropertyDescriptor pd = pds[i];
				String propertyName = pd.getName();
				Method method = PropertyUtils.getWriteMethod(pd);
				if (method == null)
					continue;
				Object parameterValue = request.getParameter(propertyName);
				if (parameterValue == null)
					continue;
				setObjectProperty(po, pd, propertyName, parameterValue);
			} catch (IllegalAccessException e) {
				logger.error("setPoPropertyByRequest error", e);
			} catch (InvocationTargetException e) {
				logger.error("setPoPropertyByRequest error", e);
			} catch (NoSuchMethodException e) {
				logger.error("setPoPropertyByRequest error", e);
			}
		}
		
	}
	
	private static void setObjectProperty(Object po, PropertyDescriptor pd, String propertyName,
											Object parameterValue) throws IllegalAccessException,
																	InvocationTargetException,
																	NoSuchMethodException {
		if (po == null)
			return;
		if (propertyName != null) {
			if (pd.getPropertyType().equals(String.class)) {
				PropertyUtils.setProperty(po, propertyName, parameterValue);
			} else if (pd.getPropertyType() == Integer.class || pd.getPropertyType() == int.class) {
				if ("".equals(parameterValue)) {
					if (pd.getPropertyType() == int.class)
						parameterValue = Integer.valueOf(0);
					else
						parameterValue = null;
				}
				BeanUtils.setProperty(po, pd.getName(), parameterValue);
			} else if (pd.getPropertyType() == Long.class || pd.getPropertyType() == long.class) {
				if ("".equals(parameterValue)) {
					if (pd.getPropertyType() == long.class)
						parameterValue = Long.valueOf(0);
					else
						parameterValue = null;
				} else {
					parameterValue = NumberUtil.parseLong((String) parameterValue, 0);
				}
				BeanUtils.setProperty(po, pd.getName(), parameterValue);
			} else if (pd.getPropertyType() == Double.class || pd.getPropertyType() == double.class) {
				if ("".equals(parameterValue)) {
					if (pd.getPropertyType() == double.class)
						parameterValue = Double.valueOf(0);
					else
						parameterValue = null;
				}
				BeanUtils.setProperty(po, pd.getName(), parameterValue);
			} else if (pd.getPropertyType() == Date.class) {
				if (StringUtils.isEmpty((String) parameterValue)) {
					parameterValue = null;
				} else {
					try {
						if (((String) parameterValue).length() == 10) {
							parameterValue = DateUtil.strToDtSimpleFormat((String) parameterValue);
						} else {
							try {
								parameterValue = DateUtil.getFormat(DateUtil.simple).parse(
									(String) parameterValue);
							} catch (Exception e) {
								logger.info(e.getMessage(), e);
								parameterValue = null;
							}
						}
						
						BeanUtils.setProperty(po, pd.getName(), parameterValue);
					} catch (Exception e) {
						logger.error("parse date parameterValue=[" + parameterValue + "] error", e);
					}
				}
				
			} else if (pd.getPropertyType() == BigDecimal.class) {
				if (StringUtils.isEmpty((String) parameterValue)) {
					parameterValue = null;
				} else
					parameterValue = new BigDecimal((String) parameterValue);
				ConvertUtils.register(null, BigDecimal.class);
				BeanUtils.setProperty(po, pd.getName(), parameterValue);
				
			} else if (pd.getPropertyType() == Byte.class || pd.getPropertyType() == byte.class) {
				if ("".equals(parameterValue)) {
					if (pd.getPropertyType() == byte.class)
						parameterValue = Byte.valueOf("0");
					else
						parameterValue = null;
				}
				BeanUtils.setProperty(po, pd.getName(), parameterValue);
			} else if (pd.getPropertyType() == Money.class) {
				if (StringUtils.isEmpty((String) parameterValue)) {
					parameterValue = null;
				} else {
					parameterValue = new Money((String) parameterValue);
					BeanUtils.setProperty(po, pd.getName(), parameterValue);
				}
				
			} else if (pd.getPropertyType() == short.class) {
				if (StringUtils.isEmpty((String) parameterValue)) {
					parameterValue = Short.valueOf("0");
				} else {
					parameterValue = Short.valueOf((String) parameterValue);
				}
				BeanUtils.setProperty(po, pd.getName(), parameterValue);
			}
		}
	}
	
	public static String getDomainUrl(HttpServletRequest request) {
		String httpStr = request.getRequestURL().toString();
		//httpStr="http://yz.cqsxjr.com/";
		if (StringUtils.isNotBlank(httpStr)) {
			if (httpStr.startsWith("http://")) {
				httpStr = httpStr.replace("http://", "");
			} else if (httpStr.startsWith("https://")) {
				httpStr = httpStr.replace("https://", "");
			}
			String[] str = httpStr.split("/");
			String[] str2 = str[0].split(":");
			return str2[0];
		}
		return "";
	}
	
	public static String getDomainHttpUrl(HttpServletRequest request) {
		String httpStr = request.getRequestURL().toString();
		//httpStr="http://yz.cqsxjr.com/";
		if (StringUtils.isNotBlank(httpStr)) {
			String[] str;
			String str2;
			if (httpStr.startsWith("http://")) {
				httpStr = httpStr.replace("http://", "");
				str = httpStr.split("/");
				str2 = "http://" + str[0];
				return str2;
			} else if (httpStr.startsWith("https://")) {
				httpStr = httpStr.replace("https://", "");
				str = httpStr.split("/");
				str2 = "https://" + str[0];
				return str2;
			} else {
				return "";
			}
		}
		return "";
	}

	public static String listToString(List<String> list){
		if(list==null){
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean first = true;
		//第一个前面不拼接","
		for(String string :list) {
			if(first) {
				first=false;
			}else{
				result.append(",");
			}
			result.append(string);
		}
		return result.toString();
	}
}
