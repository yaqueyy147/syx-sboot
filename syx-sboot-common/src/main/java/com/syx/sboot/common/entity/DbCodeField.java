/**
 * Copyright 2014-2015 <a href="http://www.hlideal.com">dingding</a> All rights reserved.
 */
package com.syx.sboot.common.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DbCodeField {
	String value() default "system";

}