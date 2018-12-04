package com.syx.sboot.common.datasource;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Created by suyx on 2018/11/22 0022.
 */
public class SqlUtilDbConfigStatic {

    public static String driver = "com.mysql.jdbc.Driver";
    public static String url = "jdbc:mysql://127.0.0.1:3307/syx_base?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true";
    public static String username = "root";
    public static String password = "123456";
    public static int poolMaxActive = 500;
    public static int poolMaxIdle = 50;
    public static int poolCheckoutTime = 20000;
    public static int poolWaitTime = 20000;
    public static boolean poolPingEnabled = true;
    public static String poolPingQuery = "SELECT 1";
    public static int poolPingTime = 3600000;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
