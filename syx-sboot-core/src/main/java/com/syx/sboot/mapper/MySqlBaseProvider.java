package com.syx.sboot.mapper;

import com.syx.sboot.common.entity.*;
import com.syx.sboot.utils.CommonUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by Administrator on 2018/11/28 0028.
 */
public class MySqlBaseProvider<T extends DataEntity<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySqlBaseProvider.class);

    public String insert(T entity){
        String sql = "insert into ";
        try {

            DbTableField tfiled = entity.getClass().getAnnotation(DbTableField.class);
            String tablename = "";
            SQL sql1 = new SQL();
            if(!CommonUtils.isBlank(tfiled)){
                tablename = tfiled.value();
                sql += " " + tablename + " ";
                sql1.INSERT_INTO(tablename);
                Field[] fields = FieldUtils.getAllFields(entity.getClass());//entity.getClass().getDeclaredFields();
                for(Field field : fields){
                    field.setAccessible(true);
                    DbField cfield = field.getAnnotation(DbField.class);
                    if(!CommonUtils.isBlank(cfield)){
                        String varname = field.getName();
                        String clumnname = cfield.value();
                        if(!StringUtils.isEmpty(clumnname)){
                            clumnname = cfield.value();
                        }
                        if(StringUtils.isEmpty(clumnname)){
                            clumnname = varname;
                        }
                        Object value = field.get(entity);
                        value = CommonUtils.isBlank(value)?"":value;
                        if(field.getType().equals(Date.class)){
                            sql1.VALUES(clumnname,"'" + CommonUtils.ObjToDateStr(value,"yyyy-MM-dd HH:mm:ss") + "'");
                        }else{
                            sql1.VALUES(clumnname,"'" + value + "'");
                        }

                    }
                }
                LOGGER.info("生成的sql-->" + sql1.toString());
                sql = sql1.toString();
            }else {
                throw new Exception("entity没有注解表名");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sql;
    }

    public String getOneById(T entity){
        String sql = "select * from ";
        try {

            if(entity.getClass().isAnnotationPresent(DbTableField.class)){
                DbTableField tfiled = entity.getClass().getAnnotation(DbTableField.class);
                String tablename = "";
                if(!CommonUtils.isBlank(tfiled)){
                    tablename = tfiled.value();
                    sql += " " + tablename + " where 1=1";

                    String id = entity.getId() + "";
                    if(!CommonUtils.isBlank(id)){
                        sql += " and id='" + id + "'";
                        LOGGER.info("生成的sql-->" + sql);
                        return sql;
                    }else{
                        throw new Exception("没有传入id值，无法进行删除！");
                    }
                }
            } else {
                throw new Exception("entity没有注解表名");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getOneByCondition(T entity, String condition){
        String sql = "select * from ";
        try {
            if(entity.getClass().isAnnotationPresent(DbTableField.class)) {
                DbTableField tfiled = entity.getClass().getAnnotation(DbTableField.class);
                String tablename = "";
                if (!CommonUtils.isBlank(tfiled)) {
                    tablename = tfiled.value();
                    sql += " " + tablename + " where 1=1" + condition;
                }
            }
            LOGGER.info("生成的sql-->" + sql);
            return sql;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getAllByParams(T entity){
        String sql = "select * from ";
        try {
            DbTableField tfiled = entity.getClass().getAnnotation(DbTableField.class);
            String tablename = "";
            if(!CommonUtils.isBlank(tfiled)){
                tablename = tfiled.value();
                sql += " " + tablename + " where 1=1";
                Field[] fields = entity.getClass().getDeclaredFields();
                for(Field field : fields){
                    field.setAccessible(true);
                    DbField cfield = field.getAnnotation(DbField.class);
                    if(!CommonUtils.isBlank(cfield)){
                        String varname = field.getName();
                        String clumnname = cfield.value();
                        if(!StringUtils.isEmpty(clumnname)){
                            clumnname = cfield.value();
                        }
                        if(StringUtils.isEmpty(clumnname)){
                            clumnname = varname;
                        }
                        Object value = field.get(entity) + "";
                        if(!CommonUtils.isBlank(value)){
                            LOGGER.info("列名-->" + clumnname + "******列值-->" + value);
                            sql += " and " + clumnname + " " + value;
                        }
                    }
                }
                LOGGER.info("生成的sql-->" + sql);
            }else {
                throw new Exception("entity没有注解表名");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return sql;
    }

    public String getListByCondition(T entity, String condition){
        String sql = "select * from ";
        try {
            DbTableField tfiled = entity.getClass().getAnnotation(DbTableField.class);
            String tablename = "";
            if(!CommonUtils.isBlank(tfiled)){
                tablename = tfiled.value();
                sql += " " + tablename + " where 1=1 " + condition;
                LOGGER.info("生成的sql-->" + sql);
            }else {
                throw new Exception("entity没有注解表名");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return sql;
    }

    public String getCountByCondition(T entity, String condition){
        String sql = "select count(*) total from ";
        try {
            DbTableField tfiled = entity.getClass().getAnnotation(DbTableField.class);
            String tablename = "";
            if(!CommonUtils.isBlank(tfiled)){
                tablename = tfiled.value();
                sql += " " + tablename + " where 1=1 " + condition;
                LOGGER.info("生成的sql-->" + sql);
            }else {
                throw new Exception("entity没有注解表名");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return sql;
    }

    public String getPageByParams(T entity, Page<T> page, String condition){
        String sql = "select * from ";
        try {
            DbTableField tfiled = entity.getClass().getAnnotation(DbTableField.class);
            String tablename = "";
            if(!CommonUtils.isBlank(tfiled)){
                tablename = tfiled.value();
                sql += " " + tablename + " where 1=1 " + condition;
                sql += "limit " + page.getPagefirstrow() + "," + page.getPageSize();
                LOGGER.info("生成的sql-->" + sql);
            }else {
                throw new Exception("entity没有注解表名");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return sql;
    }

    public String updateById(T entity){
        String sql = "update ";
        try {
            DbTableField tfiled = entity.getClass().getAnnotation(DbTableField.class);
            String tablename = "";
            if(!CommonUtils.isBlank(tfiled)) {
                tablename = tfiled.value();
                sql += " " + tablename + " set ";
                FieldUtils.getAllFields(entity.getClass());
                Field[] fields = entity.getClass().getDeclaredFields();//FieldUtils.getAllFields(entity.getClass());
                String setStr = "";
                String idvalue = "";

                for(Field field : fields){
                    field.setAccessible(true);
                    if(field.isAnnotationPresent(IdField.class)){
                        idvalue = field.get(entity) + "";
                        if(CommonUtils.isBlank(idvalue)){
                            throw new Exception("没有传入id值，无法执行修改!");
                        }
                        continue;
                    }
                    if(field.isAnnotationPresent(DbField.class)){
                        DbField cfield = field.getAnnotation(DbField.class);
                        String varname = field.getName();
                        String clumnname = cfield.value();
                        if(!StringUtils.isEmpty(clumnname)){
                            clumnname = cfield.value();
                        }
                        if(StringUtils.isEmpty(clumnname)){
                            clumnname = varname;
                        }
                        Object value = field.get(entity);

                        if(!CommonUtils.isBlank(value)){
                            LOGGER.info("列名-->" + clumnname + "******列值-->" + value);
                            setStr += "," + clumnname + "='" + value + "'";
                        }
                    }

                }
                if(!CommonUtils.isBlank(setStr) && setStr.length() > 1){
                    setStr = setStr.substring(1);
                }
                sql += " " + setStr + " where id='" + idvalue + "'";
                LOGGER.info("生成的sql-->" + sql);
            }else {
                throw new Exception("entity没有注解表名");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sql;
    }

    public String fakeDeleteById(T entity){
        String sql = " update ";
        try {
            DbTableField tfiled = entity.getClass().getAnnotation(DbTableField.class);
            String tablename = "";
            if (!CommonUtils.isBlank(tfiled)) {
                tablename = tfiled.value();
                sql += " " + tablename + " set deleteflag='0' where 1=1";
                String id = entity.getId() + "";
                if(!CommonUtils.isBlank(id)){
                    sql += " and id='" + id + "'";
                    LOGGER.info("生成的sql-->" + sql);
                    return sql;
                }else{
                    throw new Exception("没有传入id值，无法进行删除！");
                }
//                Field[] fields = FieldUtils.getAllFields(entity.getClass());
//                for(Field ff : fields){
//                    if(ff.isAnnotationPresent(IdField.class)){
//                        String id = ff.get(entity) + "";
//                        if(!CommonUtils.isBlank(id)){
//                            sql += " and id='" + id + "'";
//                            return sql;
//                        }else{
//                            throw new Exception("没有传入id值，无法进行删除！");
//                        }
//                    }
//                }
            }else {
                throw new Exception("entity没有注解表名");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String deleteById(T entity){
        String sql = " delete from ";
        try {
            DbTableField tfiled = entity.getClass().getAnnotation(DbTableField.class);
            String tablename = "";
            if (!CommonUtils.isBlank(tfiled)) {
                tablename = tfiled.value();
                sql += " " + tablename + " where 1=1";
                String id = entity.getId() + "";
                if(!CommonUtils.isBlank(id)){
                    sql += " and id='" + id + "'";
                    LOGGER.info("生成的sql-->" + sql);
                    return sql;
                }else{
                    throw new Exception("没有传入id值，无法进行删除！");
                }
//                Field[] fields = FieldUtils.getAllFields(entity.getClass());
//                for(Field ff : fields){
//                    if(ff.isAnnotationPresent(IdField.class)){
//                        String id = ff.get(entity) + "";
//                        if(!CommonUtils.isBlank(id)){
//                            sql += " and id='" + id + "'";
//                            return sql;
//                        }else{
//                            throw new Exception("没有传入id值，无法进行删除！");
//                        }
//                    }
//                }
            }else {
                throw new Exception("entity没有注解表名");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
