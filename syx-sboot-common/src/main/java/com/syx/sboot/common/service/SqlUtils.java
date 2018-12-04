package com.syx.sboot.common.service;


import com.syx.sboot.common.entity.Page;
import com.syx.sboot.common.entity.Page4Map;

/**
 * Created by suyx on 2017/7/21 0021.
 */
public class SqlUtils {

    public static String getPageCountSql(String sql){
        String str = "select count(*) ";
        if(sql.toLowerCase().indexOf("group by") > -1){
            str += "from ("+sql + " ) t" ;
        }else{
            str += sql.substring(sql.indexOf("from"));
        }


        return str;
    }

    public static String getPageQuerySql(String sql, Page page){
        return sql + " limit " + page.getFirstResult() + "," + page.getMaxResults();
    }

    public static String getPageQuerySql(String sql, Page4Map page){
        return sql + " limit " + page.getFirstResult() + "," + page.getMaxResults();
    }

}
