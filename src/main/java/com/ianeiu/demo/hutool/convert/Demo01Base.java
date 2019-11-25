package com.ianeiu.demo.hutool.convert;

import cn.hutool.core.convert.Convert;

import java.util.Date;
import java.util.List;

/**
 * @author: wuweimian
 * @date: 2019/5/30 17:11
 * @description:
 * Convert类可以说是一个工具方法类，里面封装了针对Java常见类型的转换，用于简化类型转换。
 * Convert类中大部分方法为toXXX，参数为Object，可以实现将任意可能的类型转换为指定类型。
 * 同时支持第二个参数defaultValue用于在转换失败时返回一个默认值。
 */
public class Demo01Base {

    public static void main(String[] args) {

        toStr();

        toArray();

        //toDate
        String a = "2017-05-06";
        Date value = Convert.toDate(a);

        //toCollect
        Object[] b = {"a", "你", "好", "", 1};
        List<?> list = Convert.convert(List.class, b);
        //从4.1.11开始可以这么用
        List<?> list2 = Convert.toList(b);
    }

    private static void toArray() {
        String[] b = { "1", "2", "3", "4" };
        //结果为Integer数组
        Integer[] intArray = Convert.toIntArray(b);

        long[] c = {1,2,3,4,5};
        //结果为Integer数组
        Integer[] intArray2 = Convert.toIntArray(c);

        String str = "[1, 2, 3, 4, 5]";
        //结果为Integer数组
        Integer[] intArray3 = Convert.toIntArray(c);
    }

    private static void toStr() {
        int a = 1;
        //aStr为"1"
        String aStr = Convert.toStr(a);

        long[] b = {1,2,3,4,5};
        //bStr为："[1, 2, 3, 4, 5]"
        String bStr = Convert.toStr(b);
    }
}
