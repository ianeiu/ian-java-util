package com.ianeiu.demo.hutool.datetime;

import cn.hutool.core.date.*;
import cn.hutool.core.lang.Console;

import java.util.Date;

/**
 * @author: wuweimian
 * @date: 2019/11/1 17:45
 * @description:
 */
public class Demo02DateTime {

    public static void main(String[] args) {
        //=====================================新建对象=====================================
        Date date = new Date();

        //new方式创建
        DateTime time = new DateTime(date);
        Console.log(time);
        //of方式创建
        DateTime now = DateTime.now();
        DateTime dt = DateTime.of(date);

        DateTime dateTime = new DateTime("2017-01-05 12:34:23", DatePattern.NORM_DATETIME_FORMAT);
        //=====================================使用对象=====================================
        //年，结果：2017
        int year = dateTime.year();
        //季度（非季节），结果：Season.SPRING
        Season season = dateTime.seasonEnum();
        //月份，结果：Month.JANUARY
        Month month = dateTime.monthEnum();
        //日，结果：5
        int day = dateTime.dayOfMonth();

        //=====================================对象的可变性=====================================
        //默认情况下DateTime为可变对象，此时offset == dateTime
        DateTime offset = dateTime.offset(DateField.YEAR, 0);
        //设置为不可变对象后变动将返回新对象，此时offset != dateTime
        dateTime.setMutable(false);
        offset = dateTime.offset(DateField.YEAR, 0);

        //=====================================格式化为字符串=====================================
        String dateStr = dateTime.toString();
        String dateStr2 = dateTime.toString(DatePattern.NORM_DATETIME_FORMAT);
    }
}
