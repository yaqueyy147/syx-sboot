package com.syx.sboot.common.entity;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SysDataSource t = new SysDataSource();
		Class<?> entityClass = t.getClass();
		DbTableField tfiled = t.getClass().getAnnotation(DbTableField.class);
		System.out.println(tfiled.value());
		if(t.getClass().isAnnotationPresent(DbTableField.class)){
			System.out.println("aaaaaaaaaaa");
		}else{
			System.out.println("bbbbbbbbbbb");
		}
	}

}
