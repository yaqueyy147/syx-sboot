package com.syx.sboot.common.access.sql;

import com.syx.sboot.common.entity.IdField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractSqlBuilder {
	
	public static String camelConvertColumnName(String befor) {
		if(befor==null) return null;
		
		befor=befor.toLowerCase();
		
		if (befor.indexOf("_")>0) {
			String[] words=befor.split("_");
			StringBuilder finalWord=new StringBuilder(words[0]);
			for (int i = 1; i < words.length; i++) {
				String itemWord=words[i];
				String first,rest;
				first=itemWord.substring(0, 1).toUpperCase();
				rest=itemWord.substring(1,itemWord.length());
				finalWord.append(first).append(rest);
			}
			return finalWord.toString();
		}
		else return befor;
	}
	
	/**
	 * convert string like userInfo to user_info, password to password , UserEntity to user_entity
	 * @param befor a string like userInfo
	 * @return a string like user_info, null if catch exception
	 */
	public static String camelConvertFieldName(String befor) {
		if(befor==null) return null;

		char[] characters = befor.toCharArray();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < characters.length; i++) {
			char c = characters[i];
			if (c >= 65 && c <= 90) {
				char tempc = (char) ((int) c + 32);
				if(i==0) builder.append(tempc);
				else builder.append("_").append(tempc);
			}
			else builder.append(c);
		}
		return builder.toString();
	}
	
	/**
	 * convert user_info to UserInfo, password to Password
	 * @param befor
	 * @return
	 */
	public static String camelConvertClassName(String befor) {
		String after=camelConvertColumnName(befor);
		return firstCharToUpperCase(after);
	}
	
	public static String firstCharToUpperCase(String befor){
		char first=Character.toUpperCase(befor.charAt(0));
		return first+befor.substring(1);
	}
	
	public static <T> String getIdFieldName(T entity){
		List<Field> fields= getListField(entity);
		for (Field field : fields) {
			if(field.isAnnotationPresent(IdField.class)){
				return camelConvertFieldName(field.getName());
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T>  Class<T> getClassFromSql(String sql,String modulePackage){
		try {
			String entityName=null;
			if (sql.indexOf("select")>-1) {
				int pos=sql.indexOf("from")+4;
				String temp=sql.substring(pos).trim();
				if((pos=temp.indexOf(" "))>0){
					entityName=temp.substring(0, temp.indexOf(" ")).trim();
				}else{
					entityName=temp;
				}
			}
			String tempEntityName=camelConvertColumnName(entityName);
			tempEntityName=tempEntityName.substring(0,1).toUpperCase()+tempEntityName.substring(1);
			Class<T> entityClass = (Class<T>) Class.forName(modulePackage + "." + tempEntityName);
			return entityClass;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> List<Field> getListFieldByClass(Class<T> clazz){
		List<Field> fieldList = new ArrayList<Field>() ;
		Class<?> tempClass = clazz.getClass();
		while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
		      fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
		      tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
		}
		return fieldList;
	}
	
	public static <T> List<Field> getListField(T entity){
		List<Field> fieldList = new ArrayList<Field>() ;
		Class<?> tempClass = entity.getClass();
		while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
		      fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
		      tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
		}
		return fieldList;
	}
}
