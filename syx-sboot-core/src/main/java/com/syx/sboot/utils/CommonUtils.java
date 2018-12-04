package com.syx.sboot.utils;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by suyx on 2017/8/7 0007.
 */
public class CommonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

    /**
     * 将实体类对象转换成map
     * @param object
     * @return
     */
    public static Map<String,Object> bean2Map(Object object){
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(object);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    params.put(name, propertyUtilsBean.getNestedProperty(object, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    /**
     * 将实体类对象转换成map
     * @param object
     * @return
     */
    public static Map<String,String> bean2Stringmap(Object object){
        Map<String, String> params = new HashMap<String, String>();
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(object);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    params.put(name, propertyUtilsBean.getNestedProperty(object, name) + "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    /**
     * 把obj转化成Integer
     * @param obj
     * @return
     */
    public static Integer parseInt(Object obj) {
        if(obj == null || "null".equalsIgnoreCase(obj + "") || "".equals(null)) {
            return 0;
        }
        return Integer.parseInt(obj + "");
    }

    /**
     * 左边补0(或者其他的char) 如 leftConcat(3, char '0', Integer 3)  -> 003
     * 若length小于originalStr, 返回元字符串右边的length位 如 leftConcat("1234", '0', 3) -> 234
     * @param originalStr 原字符串
     * @param toAdd 用来补的char
     * @param length 补后的长度
     * @return 补后的字符串
     */
    public static String leftConcat(String originalStr, char toAdd, Integer length) {
        Integer originalLen = originalStr.length();
        if(originalLen > length) {
            return originalStr.substring(originalLen - length, originalLen);
        }
        return getDuplicateChar(toAdd, length - originalLen) + originalStr;
    }

    /**
     * 返回length个c字符串的重复叠加组合
     * @param c			要重复的字符串
     * @param length	重复的次数
     * @return	重复后的字符串
     */
    public static String getDuplicateChar(Object c, int length) {
        int i = 0;
        String resString = "";
        for(i = 0; i < length; i++) {
            resString += c;
        }
        return resString;
    }

    /**
     * 若参数为空, 则返回 0.0.
     * @param str 字符串,数字或空
     * @return Double
     */
    public static Double parseDouble(Object str) {
        if(isBlank(str)) {
            return 0.0;
        } else {
            return Double.parseDouble(str.toString());
        }

    }

    /**
     * 判读字符串是否为空
     * @author TianChen
     * @createdTime 2013-8-29 10:32am
     * @param obj str
     * @return true:为空; false:不为空
     */
    public static boolean isBlank(Object obj) {
        try {
            if(obj == null || obj.toString().trim().length() == 0 || "null".equalsIgnoreCase(obj.toString()) || "null".equals(obj.toString().trim().toLowerCase())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 把日期(String或Date) 转化成指定格式的字符串
     * @param obj String或Date
     * @param format 像yyyy-MM-dd HH:mm:ss这样的格式字符串
     * @return String  指定格式的时间字符串
     */
    public static String ObjToDateStr(Object obj, String format) throws Exception{
        Date date = ObjToDate(obj);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * Object)(字符串或Date)转换成日期
     * @param obj "yyyy-MM-dd HH:mm:ss" 格式的日期字符串
     * @return date
     * @throws Exception
     */
    public static Date ObjToDate(Object obj) throws Exception {
        String className = obj.getClass().getName();
        Date date = null;
        if("java.lang.String".equals(className)) {
            return StrToDate(obj + "");
        } else if("java.util.Date".equals(className)){
            return (Date)obj;
        } else {
            throw new Exception("请输入正确的起始日期");
        }
    }

    /**
     * 字符串转换成日期
     * @param str "yyyy-MM-dd HH:mm:ss" 格式的日期字符串
     * @return date
     * @throws Exception
     */
    public static Date StrToDate(String str) throws Exception {
        SimpleDateFormat format = null;
        if(isBlank(str)) {
            throw new Exception("String日期不能为空");
        }
        str = str.trim();
        if(str.length() > 19) {		//长度过长时要截取其中的yyyy-MM-dd HH:mm:ss
            str = str.substring(0, 19);
        }
        if(str.trim().length() > 10 && str.trim().length() <= 19) {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if(str.trim().length() <= 7){
            format = new SimpleDateFormat("yyyy-MM");
        } else if(str.trim().length() <= 10){
            format = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            throw new Exception("String日期格式错误");
        }
        Date date = format.parse(str.trim());
        format = null;
        return date;
    }

    /**
     * 字符串转换成日期
     * @param str "yyyy-MM-dd HH:mm:ss" 格式的日期字符串
     * @return date
     * @throws Exception
     */
    public static Date StrToDateFromFtr(String str,String ftr) throws Exception {
        SimpleDateFormat format = null;
        if(isBlank(str)) {
            throw new Exception("String日期不能为空");
        }
        str = str.trim();
        if(str.length() > 19) {		//长度过长时要截取其中的yyyy-MM-dd HH:mm:ss
            str = str.substring(0, 19);
        }
        if(str.trim().length() <= 19) {
            format = new SimpleDateFormat(ftr);
        } else {
            throw new Exception("String日期格式错误");
        }
        Date date = format.parse(str.trim());
        format = null;
        return date;
    }

    /**
     * 按照传入的格式取得时间(格式含义: "yyyy-MM-dd HH:mm:ss" -- 对应 "年-月-日 时:分:秒")
     * @return 字符串格式的时间
     */
    public static String getDateFrmFormat(String format) {
        DateFormat df  = new SimpleDateFormat(format);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        String time = df.format(calendar.getTime());
        df = null;
        calendar = null;
        return time;
    }

    /**
     * 根据传入的date获取当月天数
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getDaysOfMonth(Object date) throws Exception {
        Date dd = ObjToDate(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dd);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static String toUtf8String(String s){
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if (c >= 0 && c <= 255){sb.append(c);}
            else{
                byte[] b;
                try { b = Character.toString(c).getBytes("utf-8");}
                catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            Date dd = new Date();
            System.out.println(CommonUtils.ObjToDateStr(dd,"yyyy-MM-dd HH:mm:ss"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
