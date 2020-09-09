package com.ianeiu.utils.date;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间工具类
 * @author wm
 *
 */
public class DateUtil2 {
	
	private static final String ymd = "yyyy-MM-dd";
	private static final String Hms = "HH:mm:ss";
	private static final String ymdHms = "yyyy-MM-dd HH:mm:ss";
	private static final String ymdHmsS = "yyyy-MM-dd HH:mm:ss.SSS";
	
	/**
	 * for java.util.Date
	 */
	private static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat(ymdHms);
	
	/**
	 * for java.sql.Timestamp
	 */
	private static final SimpleDateFormat yyyyMMddHHmmssSSS = new SimpleDateFormat(ymdHmsS);
	
	/**
	 * for java.sql.Date
	 */
	private static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat(ymd);
	
	/**
	 * for java.sql.Time
	 */
	private static final SimpleDateFormat HHmmss = new SimpleDateFormat(Hms);
	
	/**
	 * 将字符串转化成时间类型
	 * @see 支持以下格式的字符串
	 * @see yyyy-MM-dd
	 * @see HH:mm:ss
	 * @see yyyy-MM-dd HH:mm:ss
	 * @see yyyy-MM-dd HH:mm:ss.SSS
	 * @param st 字符串
	 * @param toClass 时间类型
	 * @return 
	 */
	public static Date convertToDate(String st, Class toClass) {
		
		if(st != null){
			Date v = null;
			try{
				if(st.length() == ymd.length()){
					v = new SimpleDateFormat(ymd).parse(st);
				}
				if(st.length() == Hms.length()){
					v = new SimpleDateFormat(Hms).parse(st);
				}
				if(st.length() == ymdHms.length()){
					v = new SimpleDateFormat(ymdHms).parse(st);
				}
				if(st.length() == ymdHmsS.length()){
					v = new SimpleDateFormat(ymdHmsS).parse(st);
				}
			}catch(Exception e){
				return null;
			}
			
			if(v == null){
				return v;
			}
			
			// 日期
			if(Date.class.equals(toClass)){
				return v;
			}
			if(Time.class.equals(toClass)){
				return new Time(v.getTime());
			}
			if(java.sql.Date.class.equals(toClass)){
				return new java.sql.Date(v.getTime());
			}
			if(java.sql.Timestamp.class.equals(toClass)){
				return new java.sql.Timestamp(v.getTime());
			}
		}
		return null;
	}
	
	/**
	 * 将时间对象转化成字符串
	 * @see o适用以下类型
	 * @see Time
	 * @see java.sql.Timestamp
	 * @see java.sql.Date
	 * @see Date
	 * @param o 时间对象
	 * @return 字符串
	 */
	public static String convertToString(Object o) {
		if(o != null && o instanceof Date){
			if(o instanceof Time){
				return HHmmss.format((Date)o);
			}
			if(o instanceof java.sql.Timestamp){
				return yyyyMMddHHmmssSSS.format((Date)o);
			}
			if(o instanceof java.sql.Date){
				return yyyyMMdd.format((Date)o);
			}
			if(o instanceof Date){
				return yyyyMMddHHmmss.format((Date)o);
			}
		}
		
		return "";
	}
	
	/**
	 * 获得pattern格式的date的字符串
	 * @param date
	 * @param pattern 例：yyyy-MM-dd
	 * @return
	 */
	public static String convertToString(Date date,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 两个时间相差的天数
	 * @param smdate 较小的时间
	 * @param bdate	 较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	 public static int betweenDays(Date smdate,Date bdate){    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        try {
			smdate=sdf.parse(sdf.format(smdate));
			bdate=sdf.parse(sdf.format(bdate));  
		} catch (ParseException e) {
			e.printStackTrace();
		}  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
       return Integer.parseInt(String.valueOf(between_days));           
    } 
	 
	 /**
	 * 两个时间相差的分钟数
	 * @param smdate 较小的时间
	 * @param bdate	 较大的时间
	 * @return 相差分钟数
	 * @throws ParseException
	 */
	public static int betweenMins(Date smdate, Date bdate) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_mins = (time2 - time1) / (1000 * 60);
		return Integer.parseInt(String.valueOf(between_mins));
	} 
	 
	 
	/**
	 * 与当前时间比较
	 * 
	 * @param date 时间对象
	 * @return -1：date小；1：date大
	 */
	public static int compareToCurrentDate(Date date) {
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String currentDateStr = formatter.format(currentDate);
		String dateStr = formatter.format(date);
		return dateStr.compareTo(currentDateStr);
	}
	
	/**
	 * 与当前时间比较
	 * @param dateStr 时间字符串
	 * @param pattern 例：yyyy-MM-dd
	 * @return -1：date小；1：date大
	 * @throws Exception
	 */
	public static int compareToCurrentDate(String dateStr, String pattern)
			throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		Date date = formatter.parse(dateStr);
		return compareToCurrentDate(date);
	}
	
	/**
	 * 获取date 的sqlDate对象
	 * @param date
	 * @return
	 */
	public static java.sql.Date getSqlDateFromUtilDate(Date date){
		return new java.sql.Date(date.getTime());
	}
	
	/**
	 * @Description: 判断时间是否在时间段内
	 * @author: wm
	 * @date: 2018年9月25日 下午7:52:24
	 * @version: 1.1
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isBelongHour(String start,String end) {

		SimpleDateFormat df = new SimpleDateFormat("HH:mm");// 设置日期格式
		Date now = null;
		Date beginTime = null;
		Date endTime = null;
		try {
			now = df.parse(df.format(new Date()));
			beginTime = df.parse(start);
			endTime = df.parse(end);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Boolean flag = belongCalendar(now, beginTime, endTime);
		return flag;
	}

	/**
	 * 判断时间是否在时间段内
	 * @param nowTime
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		return nowTime.getTime() >= beginTime.getTime() && nowTime.getTime() <= endTime.getTime(); 
	}
	
	/**
	 * 获取当年的第一天
	 * @return
	 */
	public static Date getCurrYearFirst(){
		Calendar currCal=Calendar.getInstance();  
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}
	
	/**
	 * 获取当年的最后一天
	 * @return
	 */
	public static Date getCurrYearLast(){
		Calendar currCal=Calendar.getInstance();  
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}
	
	/**
	 * 获取某年第一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearFirst(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}
	
	/**
	 * 获取某年最后一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearLast(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		
		return currYearLast;
	}

	/**
	 * 获得当前月第一天，格式为2010-08-01
	 *
	 * @return
	 * @throws java.text.ParseException
	 */
	public static Date getFirstDayOfMonth() throws java.text.ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);
		return lastDate.getTime();
	}

	/**
	 * 获得当前月最后一天，格式为2010-08-31
	 *
	 * @return
	 * @throws java.text.ParseException
	 */
	public static Date getLastDayOfMonth() throws java.text.ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);
		lastDate.add(Calendar.MONTH, 1);
		lastDate.add(Calendar.DATE, -1);
		return lastDate.getTime();
	}

	/**
	 * 获得上个月第一天，格式为2010-07-01
	 *
	 * @return
	 * @throws java.text.ParseException
	 */
	public static Date getPreviousMonthFirst() throws java.text.ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);
		lastDate.add(Calendar.MONTH, -1);
		return lastDate.getTime();
	}

	/**
	 * 获得上个月最后一天，格式为2010-07-31
	 *
	 * @return
	 * @throws java.text.ParseException
	 */
	public static Date getPreviousMonthEnd() throws java.text.ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);
		lastDate.set(Calendar.DATE, 1);
		lastDate.roll(Calendar.DATE, -1);
		return lastDate.getTime();
	}

}
