package com.ianeiu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description: 提供一些对象有效性校验的方法
 * @author wm
 * @date 2018/12/27 17:36
 * @version: 1.0
 */
public final class CheckUtil {
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		//System.out.println(org.apache.commons.collections.CollectionUtils.isEmpty(list));//true

        String dateStr = "2018-12-27";
        String format = "yyyy-MM-dd";
        String format2 = "yyyy-MM HH:mm:ss";
        System.out.println(isDate(dateStr,format));
        System.out.println(isDate(dateStr,format2));

        String testStr = "ada";
        String testStr2 = "  ";
        System.out.println(valid(testStr));
        System.out.println(valid(testStr2));

        StringBuffer sb = new StringBuffer();
        StringBuffer sb2 = null;
        System.out.println(valid(sb));
        System.out.println(valid(sb2));

        /*true
        false
        true
        false
        true
        false*/
	}

    /**
     * 判断字符串是否是符合指定格式的时间
     * @param date 时间字符串
     * @param format 时间格式
     * @return 是否符合
     */
    public final static boolean isDate(String date,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {

        }
        return false;
    }

    /**
     * 判断字符串有效性
     */
    public final static boolean valid(String src) {
        return !(src == null || "".equals(src.trim()));
    }

    /**
     * 判断一组字符串是否有效
     * @param src
     * @return
     */
    public final static boolean valid(String... src) {
        for (String s : src) {
            if (!valid(s)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 判断一个对象是否为空
     */
    public final static boolean valid(Object obj) {
        return !(null == obj);
    }

    /**
     * 判断一组对象是否有效
     * @param objs
     * @return
     */
    public final static boolean valid(Object... objs) {
        if (objs != null && objs.length != 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断集合的有效性
     */
    public final static boolean valid(Collection col) {
        return !(col == null || col.isEmpty());
    }

    /**
     * 判断一组集合是否有效
     * @param cols
     * @return
     */
    public final static boolean valid(Collection... cols) {
        for (Collection c : cols) {
            if (!valid(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断map是否有效
     * @param map
     * @return
     */
    public final static boolean valid(Map map) {
        return !(map == null || map.isEmpty());
    }

    /**
     * 判断一组map是否有效
     * @param maps 需要判断map
     * @return 是否全部有效
     */
    public final static boolean valid(Map... maps) {
        for (Map m : maps) {
            if (!valid(m)) {
                return false;
            }
        }
        return true;
    }
}
