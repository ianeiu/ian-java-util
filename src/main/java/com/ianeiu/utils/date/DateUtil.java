package com.ianeiu.utils.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static String DATETIME_FORMAT1 = "yyyy-MM-dd HH:mm";
	public static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String DATE_ZEROTIME_FORMAT = "yyyy-MM-dd 00:00:00";
	public static String DATE_ZEROTIME_FORMAT2 = "yyyyMMdd000000";

	public static String DATE_FULLTIME_FORMAT = "yyyy-MM-dd 23:59:59";
	public static String DATE_FULLTIME_FORMAT2 = "yyyyMMdd235959";

	public static String DATETIME12_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String DATETIME12_FORMAT2 = "yyyyMMddhhmmss";
	public static String DATETIME24_FORMAT = "yyyyMMddHHmmss";

	public static String DATE_FORMAT = "yyyy-MM-dd";
	public static String DATE_FORMAT2 = "yyyyMMdd";

	public static String YEAR_MONTH_FORMAT = "yyyy-MM";
	public static String YEAR_MONTH_FORMAT2 = "yyyyMM";

	public static String YEAR_MONTH_FIRSTDAY = "yyyy-MM-01";

	public static String YEAR_FORMAT = "yyyy";
	public static String MONTH_FORMAT = "MM";
	public static String DAY_FORMAT = "dd";

	public static String TIME_FORMAT = "HH:mm:ss";
	public static String TIME_FORMAT2 = "HHmmss";

	public static String TIME12_FORMAT = "HH:mm:ss";
	public static String TIME12_FORMAT2 = "HHmmss";

	public static String DATETIME_SLASH_FORMAT = "yyyy/MM/dd HH:mm:ss";
	public static String DATE_SLASH_FORMAT = "yyyy/MM/dd";

	public static Timestamp convertStrToTimestamp(String timeStampStr) {
		Timestamp returnT = null;
		if ((timeStampStr != null) && (!timeStampStr.trim().equals(""))) {
			returnT = new Timestamp(parseDate(timeStampStr).getTime());
		}
		return returnT;
	}

	public static String formatDate(Date date, String format) {
		if (date == null) {
			return "";
		}
		if (format.indexOf("h") > 0) {
			format = format.replace('h', 'H');
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}

	public static Date parseDate(String str) {
		if ((str == null) || (str.trim().length() == 0)) {
			return null;
		}
		if (str.length() == 10)
			return parseDate(str, "yyyy-MM-dd");
		if (str.length() == 13)
			return parseDate(str, "yyyy-MM-dd HH");
		if (str.length() == 16)
			return parseDate(str, "yyyy-MM-dd HH:mm");
		if (str.length() == 19) {
			return parseDate(str, "yyyy-MM-dd HH:mm:ss");
		}
		if (str.length() >= 21) {
			return parseDate(str, "yyyy-MM-dd HH:mm:ss.S");
		}
		return null;
	}

	public static Date parseDate(String str, String format) {
		try {
			if ((str == null) || (str.equals(""))) {
				return null;
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			return simpleDateFormat.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Date();
	}

	public static String formatDateByFormat(Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String formatDate(Date date) {
		return formatDateByFormat(date, DATE_FORMAT);
	}

	public static Date formatStringToDate(String str) throws ParseException {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

		date = sdf.parse(str);
		return date;
	}

	public static Date formatStringToDate(String str, String format) throws ParseException {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		date = sdf.parse(str);
		return date;
	}

	public static Date now() {
		Calendar now = Calendar.getInstance();
		return now.getTime();
	}

	public static String getCurrDate() {
		Calendar now = Calendar.getInstance();
		return getDateStr(now);
	}

	public static String getCurrDateTime() {
		Calendar now = Calendar.getInstance();

		return getDateStr(now) + " " + getHour(now) + ":" + getMinute(now) + ":" + getSecond(now);
	}

	public static String getCurrDateTimeBegin() {
		Calendar now = Calendar.getInstance();
		return getDateStr(now) + " 00:00:00";
	}

	public static String getCurrDateTimeEnd() {
		Calendar now = Calendar.getInstance();
		return getDateStr(now) + " 23:59:59";
	}

	public static String getFirstDayDate() {
		Calendar now = Calendar.getInstance();
		return getYear(now) + "-" + getMonth(now) + "-" + "01";
	}

	public static String getFirstDayDateTime() {
		return getFirstDayDate() + " 00:00:00";
	}

	public static String getDateStr(Calendar cal) {
		return getYear(cal) + "-" + getMonth(cal) + "-" + getDay(cal);
	}

	public static String getYear(Calendar cal) {
		return String.valueOf(cal.get(1));
	}

	public static String getMonth(Calendar cal) {
		return strLen(String.valueOf(cal.get(2) + 1), 2);
	}

	public static String getDay(Calendar cal) {
		return strLen(String.valueOf(cal.get(5)), 2);
	}

	public static String getHour(Calendar cal) {
		return strLen(String.valueOf(cal.get(11)), 2);
	}

	public static String getMinute(Calendar cal) {
		return strLen(String.valueOf(cal.get(12)), 2);
	}

	public static String getSecond(Calendar cal) {
		return strLen(String.valueOf(cal.get(13)), 2);
	}

	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(1);
	}

	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(2) + 1;
	}

	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(5);
	}

	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(11);
	}

	public static int getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(12);
	}

	public static int getSecond(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(13);
	}

	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	public static Date addDate(Date date, int day) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + day * 24L * 3600L * 1000L);
		return c.getTime();
	}

	public static Date addHour(Date date, int hour) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + hour * 3600L * 1000L);
		return c.getTime();
	}

	public static Date addMinis(Date date, int minis) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + minis * 60L * 1000L);
		return c.getTime();
	}

	public static Date diffDate(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) - day * 24L * 3600L * 1000L);
		return c.getTime();
	}

	public static Date addMonth(Date date, int month) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(c.get(1), c.get(2) + month, c.get(5));

		return c.getTime();
	}

	public static int diffDate(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / 86400000L);
	}

	public static int getWeekFirstDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return 1;
	}

	public static Date getMaxDate(Date date1, Date date2) {
		if ((date1 == null) && (date2 == null)) {
			return null;
		}
		if (date1 == null) {
			return date2;
		}
		if (date2 == null) {
			return date1;
		}
		if (date1.before(date2)) {
			return date2;
		}
		return date1;
	}

	public static Date getMinDate(Date date1, Date date2) {
		if ((date1 == null) && (date2 == null)) {
			return null;
		}
		if (date1 == null) {
			return date2;
		}
		if (date2 == null) {
			return date1;
		}
		if (date1.before(date2)) {
			return date1;
		}
		return date2;
	}

	public static String cvtToTimeStr(Timestamp dtDate) {
		return cvtFormattedDate(dtDate, TIME_FORMAT);
	}

	public static String cvtFormattedDate(Timestamp dtDate, String strFormatTo, String defaultValue) {
		String dateString = cvtFormattedDate(dtDate, strFormatTo);
		if ((dateString == null) || ("".equals(dateString))) {
			return defaultValue;
		}

		return dateString;
	}

	public static String cvtFormattedDate(Timestamp dtDate, String strFormatTo) {
		if (dtDate == null) {
			return "";
		}
		strFormatTo = strFormatTo.replace('/', '-');
		try {
			if ((dtDate.getTime() == getZeroDate().getTime()) || (dtDate.getTime() == 0L)
					|| (dtDate.getTime() == -62170185600000L)) {
				String ret = "";
				if (strFormatTo.equals("yyyy-MM-dd"))
					ret = "0000-00-00";
				else if (strFormatTo.equals("yyyy-MM"))
					ret = "0000-00";
				else if (strFormatTo.equals("yyyyMM"))
					ret = "000000";
				else if (strFormatTo.equals("HH:mm:ss"))
					ret = "00:00:00";
				else {
					ret = "0000-00-00 00:00:00";
				}
				return ret;
			}

			SimpleDateFormat formatter = new SimpleDateFormat(strFormatTo);
			return formatter.format(dtDate);
		} catch (Exception e) {
			System.out.println("转换日期字符串格式时出错;" + e.getMessage());
		}
		return "";
	}

	public static java.sql.Date parseDate2SqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}

	public static Timestamp getZeroDate() {
		return getTimeStamp("2000-01-01 00:00:00");
	}

	public static Timestamp getTimeStamp(String dateDesc) {
		Date date = parseDate(dateDesc);
		Timestamp ts = new Timestamp(date.getTime());
		return ts;
	}

	public static String strLen(String s, int len) {
		if (s == null)
			s = "";
		else
			s = s.trim();
		int strLen = s.length();
		for (int i = 0; i < len - strLen; i++) {
			s = "0" + s;
		}
		return s;
	}
}
