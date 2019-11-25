package com.ianeiu.demo.hutool.datetime;

import cn.hutool.core.date.*;
import cn.hutool.core.lang.Console;

import java.util.Calendar;
import java.util.Date;

/**
 * @author: wuweimian
 * @date: 2019/11/1 17:23
 * @description:
 */
public class Demo01DateUtil {

    public static void main(String[] args) {
        //Date、long、Calendar之间的相互转换
        //currentDate();

        //字符串转日期、格式化日期输出
        //parseAndFormat();

        //获取Date对象的某个部分
        //dateBody();

        //开始和结束时间、日期时间偏移、日期时间差、格式化时间差
        //dateSite();
        
        //计时器
        //recordTime();

        //年龄、是否闰年
        oldAndLeap();
    }

    private static void currentDate() {
        //当前时间
        Date date = DateUtil.date();
        //当前时间
        Date date2 = DateUtil.date(Calendar.getInstance());
        //当前时间
        Date date3 = DateUtil.date(System.currentTimeMillis());
        //当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        String now = DateUtil.now();
        //当前日期字符串，格式：yyyy-MM-dd
        String today = DateUtil.today();
    }

    private static void parseAndFormat() {
        String dateStr = "2019-11-01 17:27:30";

        Date date = DateUtil.parse(dateStr);
        Date date2 = DateUtil.parse(dateStr, "yyyy-MM-dd");

        //结果 2019/11/01
        String format = DateUtil.format(date, "yyyy/MM/dd");
        //常用格式的格式化，结果：2019-11-01
        String formatDate = DateUtil.formatDate(date);
        //结果：2019-11-01 17:27:30
        String formatDateTime = DateUtil.formatDateTime(date);
        //结果：17:27:30
        String formatTime = DateUtil.formatTime(date);
    }

    private static void dateBody() {
        Date date = DateUtil.date();
        //获得年的部分
        DateUtil.year(date);
        //获得月份，从0开始计数
        DateUtil.month(date);
        //获得月份枚举
        DateUtil.monthEnum(date);
    }

    private static void dateSite() {
        String dateStr = "2017-03-01 22:33:23";
        Date date = DateUtil.parse(dateStr);

        //一天的开始，结果：2017-03-01 00:00:00
        Date beginOfDay = DateUtil.beginOfDay(date);
        //一天的结束，结果：2017-03-01 23:59:59
        Date endOfDay = DateUtil.endOfDay(date);

        //结果：2017-03-03 22:33:23
        Date newDate = DateUtil.offset(date, DateField.DAY_OF_MONTH, 2);
        //常用偏移，结果：2017-03-04 22:33:23
        DateTime newDate2 = DateUtil.offsetDay(date, 3);
        //常用偏移，结果：2017-03-01 19:33:23
        DateTime newDate3 = DateUtil.offsetHour(date, -3);

        //昨天
        DateUtil.yesterday();
        //明天
        DateUtil.tomorrow();
        //上周
        DateUtil.lastWeek();
        //下周
        DateUtil.nextWeek();
        //上个月
        DateUtil.lastMonth();
        //下个月
        DateUtil.nextMonth();

        String dateStr1 = "2017-03-01 22:33:23";
        Date date1 = DateUtil.parse(dateStr1);

        String dateStr2 = "2017-04-01 23:33:23";
        Date date2 = DateUtil.parse(dateStr2);

        //相差一个月，31天
        long betweenDay = DateUtil.between(date1, date2, DateUnit.DAY);

        //Level.MINUTE表示精确到分
        String formatBetween = DateUtil.formatBetween(date1, date2, BetweenFormater.Level.MINUTE);
        //输出：31天1小时
        Console.log(formatBetween);

    }


    private static void recordTime() {
        TimeInterval timer = DateUtil.timer();

        for(int i = 0; i<100 ; i++){
            System.out.print(i+" ");
        }
        Console.log();

        //花费毫秒数
        Console.log(timer.interval());
        //返回花费时间，并重置开始时间
        Console.log(timer.intervalMinute());
        //花费分钟数
        Console.log(timer.intervalMinute());
    }

    private static void oldAndLeap() {
        //年龄
        Console.log(DateUtil.ageOfNow("1994-10-01"));

        //是否闰年
        Console.log(DateUtil.isLeapYear(2017));

    }
}
