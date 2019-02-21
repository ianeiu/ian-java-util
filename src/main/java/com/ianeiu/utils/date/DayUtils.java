package com.ianeiu.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 获取Date集合工具类
 * @author admin
 *
 */
public class DayUtils {
	
	public static void main(String[] args) throws ParseException {
		Date date1 = new SimpleDateFormat("yyyyMMdd").parse("20180401");
		Date date2 = new SimpleDateFormat("yyyyMMdd").parse("20180420");
		List<Date> date = getDatesByWeekNum(date1,date2, "1,6", true, true);
		
		for (Date d : date) {
			System.out.println(new SimpleDateFormat("yyyyMMdd").format(d));
		}
		
		System.out.println("--------------------------");
		

		Date date3 = new SimpleDateFormat("yyyyMMdd").parse("20180401");
		Date date4 = new SimpleDateFormat("yyyyMMdd").parse("20180420");
		List<Date> dates = getDatesByDayNum(date3,date4, "1,2,13,17,20", true, true);
		
		for (Date d : dates) {
			System.out.println(new SimpleDateFormat("yyyyMMdd").format(d));
		}
	}
	

	/**  
	 * 获取某一时间段特定几号的日期  
	 * @param dateFrom 开始时间  
	 * @param dateEnd 结束时间  
	 * @param dayNum 日期 格式  1,2,16,28
	 * @param isNoFirstDay 是否排除第一天
	 * @param boolean isAfterNow 是否只包含今天之后
	 * @return 返回时间list
	 */  
	public static List<Date> getDatesByDayNum(Date dateFrom, Date dateEnd, String dayNums,boolean isNoFirstDay,boolean isAfterNow) {
		long perDayMilSec = 24 * 60 * 60 * 1000; //一天 
		List<Date> dateList = new ArrayList<Date>();    
		
		List<String> chooseDay = Arrays.asList(dayNums.split(","));
		
		if(isAfterNow){
			Long nowTime = new Date().getTime();
			if(nowTime > dateFrom.getTime()){
				dateFrom = new Date(nowTime);
			}
		}
		
		if(!isNoFirstDay){
			dateFrom = new Date(dateFrom.getTime() - perDayMilSec);
		}
		
		while (true) {  
			dateFrom = new Date(dateFrom.getTime()  + perDayMilSec);  
			if (dateFrom.getTime()<= dateEnd.getTime()) {  
				
				Integer day = dayForDayN(dateFrom);
				for (String cday : chooseDay) {
					if(cday.equals(day.toString())){
						dateList.add(dateFrom);
					}
				}
			} else {  
				break;  
			}  
		}  
		
		return dateList;
	}
	
	
	/**  
	 * 获取某一时间段特定星期几的日期  
	 * @param dateFrom 开始时间  
	 * @param dateEnd 结束时间  
	 * @param strWeekNumber 星期  格式  1,2,3
	 * @param isNoFirstDay 是否排除第一天
	 * @param boolean isAfterNow 是否只包含今天之后
	 * @return 返回时间list
	 */  
	public static List<Date> getDatesByWeekNum(Date dateFrom, Date dateEnd, String strWeekNumber,boolean isNoFirstDay,boolean isAfterNow) {   
		long perDayMilSec = 24 * 60 * 60 * 1000; //一天 
		List<Date> dateList = new ArrayList<Date>();   
		
		if(isAfterNow){
			Long nowTime = new Date().getTime();
			if(nowTime > dateFrom.getTime()){
				dateFrom = new Date(nowTime);
			}
		}
		
		if(!isNoFirstDay){
			dateFrom = new Date(dateFrom.getTime() - perDayMilSec);
		}
		
		while (true) {  
			dateFrom = new Date(dateFrom.getTime()  + perDayMilSec);  
			if (dateFrom.getTime()<= dateEnd.getTime()) {  
				//查询的某一时间的星期系数 
				Integer weekDay = dayForWeekN(dateFrom);                      
				//判断当期日期的星期系数是否是需要查询的  
				if (strWeekNumber.indexOf(weekDay.toString())!=-1) {  
					dateList.add(dateFrom);  
				}  
			} else {  
				break;  
			}  
		}  
		
		return dateList;  
	}  
	
	//等到当期时间的日期  
	public static Integer dayForDayN(Date date) {  
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);  
		int w = calendar.get(Calendar.DAY_OF_MONTH);  
		
		return w;
	}  
	
	 //等到当期时间的周系数。星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7  
    public static Integer dayForWeekN(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        int w = calendar.get(Calendar.DAY_OF_WEEK);  
        if(w==1){
        	return 7;
        }else{
        	return w-1;
        }
    }  
	
	
	
	/**  
     * 获取某一时间段特定星期几的日期  
     * @param dateFrom 开始时间  
     * @param dateEnd 结束时间  
     * @param weekDays 星期  
     * @return 返回时间数组  
     */  
    public static String[] getDates(String dateFrom, String dateEnd, String weekDays) {  
        long time = 1l;  
        long perDayMilSec = 24 * 60 * 60 * 1000;  
        List<String> dateList = new ArrayList<String>();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        //需要查询的星期系数  
        String strWeekNumber = weekForNum(weekDays);  
        try {  
            dateFrom = sdf.format(sdf.parse(dateFrom).getTime() - perDayMilSec);  
            while (true) {  
                    time = sdf.parse(dateFrom).getTime();  
                    time = time + perDayMilSec;  
                    Date date = new Date(time);  
                    dateFrom = sdf.format(date);  
                    if (dateFrom.compareTo(dateEnd) <= 0) {  
                        //查询的某一时间的星期系数  
                        Integer weekDay = dayForWeek(date);                      
                        //判断当期日期的星期系数是否是需要查询的  
                        if (strWeekNumber.indexOf(weekDay.toString())!=-1) {  
                            System.out.println(dateFrom);  
                            dateList.add(dateFrom);  
                        }  
                    } else {  
                        break;  
                    }  
            }  
        } catch (ParseException e1) {  
            e1.printStackTrace();  
        }  
        String[] dateArray = new String[dateList.size()];  
        dateList.toArray(dateArray);  
        return dateArray;  
    }  
  
    //等到当期时间的周系数。星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7  
    public static Integer dayForWeek(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        return calendar.get(Calendar.DAY_OF_WEEK);  
    }  
      
    /**  
     * 得到对应星期的系数  星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7  
     * @param weekDays 星期格式  星期一|星期二  
     */  
    public static String weekForNum(String weekDays){  
        //返回结果为组合的星期系数  
        String weekNumber = "";  
        //解析传入的星期  
        if(weekDays.indexOf("|")!=-1){//多个星期数  
            String []strWeeks = weekDays.split("\\|");  
            for(int i=0;i<strWeeks.length;i++){  
                weekNumber = weekNumber + "" + getWeekCode(strWeeks[i]).toString();  
            }  
        }else{//一个星期数  
            weekNumber = getWeekCode(weekDays).toString();  
        }  
          
        return weekNumber;  
          
    }  
      
    /**
     * @Description: 将星期转换为对应的系数  星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7  
     * @author: wm
     * @date: 2018年9月25日 下午7:50:07
     * @version: 1.1
     * @param strWeek
     * @return
     */
    public static Integer getWeekCode(String strWeek){  
        Integer code = 1;
        
        switch (strWeek) {
		case "星期日":
			code = 1;
			break;
		case "星期一":
			code = 2;
			break;
		case "星期二":
			code = 3;
			break;
		case "星期三":
			code = 4;
			break;
		case "星期四":
			code = 5;
			break;
		case "星期五":
			code = 6;
			break;
		case "星期六":
			code = 7;
			break;
		default:
			break;
		}
       
        return code;  
    }  
}
