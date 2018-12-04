package com.syx.sboot.common.utils.extend;

import com.google.common.base.Preconditions;
import com.syx.sboot.common.utils.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtil {
	public static final String simple = "yyyy-MM-dd HH:mm:ss";
	public static final String dtSimple = "yyyy-MM-dd";
	public static final String dtSimpleYm = "yyyy-MM";
	public static final String dtSimpleYm02 = "yyyyMM";
	public static final String dtSimpleSlash = "yyyy/MM/dd";
	public static final String dtSimpleChinese = "yyyy年MM月dd日";
	public static final String week = "EEEE";
	public static final String dtShort = "yyyyMMdd";
	public static final String dtLong = "yyyyMMddHHmmss";
	public static final String dtLongss = "yyyyMMddHHmmssSS";
	public static final String hmsFormat = "HH:mm:ss";
	public static final String hmsFormat2 = "HH:mm";
	public static final String hmsFormat1 = "HHmmss";
	public static final String simpleFormat = "yyyy-MM-dd HH:mm";
	public static final long MILL_SECOND_IN_DAY = 86400000L;
	public static Date MAX_DAY = null;

	public static final DateFormat getFormat(String format) {
		return new SimpleDateFormat(format);
	}

	public static final String simpleFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static final String simpleMillionFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat(dtLongss).format(date);
	}

	public static final String dtSimpleFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat("yyyy-MM-dd").format(date);
	}

	public static final String dtSimpleYmFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat("yyyy-MM").format(date);
	}
	
	public static final String dtSimpleYmFormat02(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat("yyyyMM").format(date);
	}

	public static final String dtSimpleYFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat("yyyy").format(date);
	}

	public static final String dtSimpleMFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat("MM").format(date);
	}

	public static final Date strToDtSimpleYmFormat(String strDate) {
		if (strDate == null)
			return null;
		try {
			return getFormat("yyyy-MM").parse(strDate);
		} catch (Exception e) {
		}
		return null;
	}

	public static final Date strToDtSimpleFormat(String strDate) {
		if (strDate == null)
			return null;
		try {
			return getFormat("yyyy-MM-dd").parse(strDate);
		} catch (Exception e) {
		}
		return null;
	}

	public static final String dtSimpleSlashFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat("yyyy/MM/dd").format(date);
	}

	public static final Date strToDtSimpleSlashFormat(String strDate) {
		if (strDate == null)
			return null;
		try {
			return getFormat("yyyy/MM/dd").parse(strDate);
		} catch (Exception e) {
		}
		return null;
	}

	public static final String getDiffDate(Date dt, int idiff) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(5, idiff);
		return dtSimpleFormat(c.getTime());
	}
	
	public static final String getAddMinutes(Date dt, int minutes) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.MINUTE, minutes);
		return simpleFormat(c.getTime());
	}
	
	

	public static final String getDiffMon(Date dt, int idiff) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(2, idiff);
		return dtSimpleFormat(c.getTime());
	}

	public static final String dtSimpleChineseFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat("yyyy年MM月dd日").format(date);
	}

	public static final String dtSimpleChineseFormatStr(String date) throws ParseException {
		if (date == null) {
			return "";
		}
		return getFormat("yyyy年MM月dd日").format(string2Date(date));
	}

	public static final Date string2Date(String stringDate){
		try{
			if (stringDate == null) {
				return null;
			}
			return getFormat("yyyy-MM-dd").parse(stringDate);
		}catch(Exception e){
			return null;
		}
	}

	public static final Date string2DateTime(String stringDate) throws ParseException {
		if (stringDate == null) {
			return null;
		}
		return getFormat("yyyy-MM-dd HH:mm:ss").parse(stringDate);
	}

	public static final Date string2DateTimeByAutoZero(String stringDate) throws ParseException {
		if (stringDate == null) {
			return null;
		}
		if (stringDate.length() == 11)
			stringDate = stringDate + "00:00:00";
		else if (stringDate.length() == 13)
			stringDate = stringDate + ":00:00";
		else if (stringDate.length() == 16)
			stringDate = stringDate + ":00";
		else if (stringDate.length() == 10)
			stringDate = stringDate + " 00:00:00";
		return getFormat("yyyy-MM-dd HH:mm:ss").parse(stringDate);
	}

	public static final Date string2DateTimeBy23(String stringDate) throws ParseException {
		if (stringDate == null) {
			return null;
		}
		if (stringDate.length() == 11)
			stringDate = stringDate + "23:59:59";
		else if (stringDate.length() == 13)
			stringDate = stringDate + ":59:59";
		else if (stringDate.length() == 16)
			stringDate = stringDate + ":59";
		else if (stringDate.length() == 10)
			stringDate = stringDate + " 23:59:59";
		return getFormat("yyyy-MM-dd HH:mm:ss").parse(stringDate);
	}

	public static final int calculateDecreaseDate(String beforDate, String afterDate) throws ParseException {
		Date date1 = getFormat("yyyy-MM-dd").parse(beforDate);
		Date date2 = getFormat("yyyy-MM-dd").parse(afterDate);
		long decrease = getDateBetween(date1, date2) / 1000L / 3600L / 24L;
		int dateDiff = (int) decrease;
		return dateDiff;
	}

	public static final long getDateBetween(Date dBefor, Date dAfter) {
		long lBefor = 0L;
		long lAfter = 0L;
		long lRtn = 0L;

		lBefor = dBefor.getTime();
		lAfter = dAfter.getTime();
		lRtn = lAfter - lBefor;
		return lRtn;
	}

	public static final Date shortstring2Date(String stringDate) throws ParseException {
		if (stringDate == null) {
			return null;
		}
		return getFormat("yyyyMMdd").parse(stringDate);
	}

	public static final String shortDate(Date Date) {
		if (Date == null) {
			return null;
		}
		return getFormat("yyyyMMdd").format(Date);
	}

	public static final String longDate(Date Date) {
		if (Date == null) {
			return null;
		}
		return getFormat("yyyyMMddHHmmss").format(Date);
	}

	public static final Long string2DateLong(String stringDate) throws ParseException {
		Date d = string2Date(stringDate);
		if (d == null) {
			return null;
		}
		return Long.valueOf(d.getTime());
	}

	public static final String hmsFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat("HH:mm:ss").format(date);
	}

	public static final String hmFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat("HH:mm").format(date);
	}

	public static final String simpleDate(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat("yyyy-MM-dd HH:mm").format(date);
	}

	public static final String simpleDate02(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat("yyyy-MM-dd").format(date);
	}

	public static final Date simpleFormatDate(String dateString) throws ParseException {
		if ((dateString == null) || (dateString.trim().length() == 0)) {
			return null;
		}

		return getFormat("yyyy-MM-dd HH:mm").parse(dateString.trim());
	}

	public static final String getDiffDate(int diff) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(5, diff);
		return dtSimpleFormat(c.getTime());
	}

	public static final Date getDiffDateTime(int diff) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(5, diff);
		return c.getTime();
	}

	public static final String getDiffDateTime(int diff, int hours) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(5, diff);
		c.add(10, hours);
		return dtSimpleFormat(c.getTime());
	}

	public static final Long dateToNumber(Date date) {
		if (date == null) {
			return null;
		}
		String formated = getFormat("yyyyMMdd").format(date);
		return Long.valueOf(formated);
	}

	public static String getNextMon(String StringDate) throws ParseException {
		Date tempDate = shortstring2Date(StringDate);
		Calendar cad = Calendar.getInstance();
		cad.setTime(tempDate);
		cad.add(2, 1);
		return shortDate(cad.getTime());
	}

	public static String getNextDay(String StringDate) throws ParseException {
		Date tempDate = string2Date(StringDate);
		Calendar cad = Calendar.getInstance();
		cad.setTime(tempDate);
		cad.add(5, 1);
		return dtSimpleFormat(cad.getTime());
	}

	public static String getWebNextDayString() {
		Calendar cad = Calendar.getInstance();
		cad.setTime(new Date());
		cad.add(5, 1);
		return dtSimpleFormat(cad.getTime());
	}

	public static String getNextDay(Date date) throws ParseException {
		Calendar cad = Calendar.getInstance();
		cad.setTime(date);
		cad.add(5, 1);
		return dtSimpleFormat(cad.getTime());
	}

	public static Date getNextDayDtShort(String StringDate) throws ParseException {
		Date tempDate = shortstring2Date(StringDate);
		Calendar cad = Calendar.getInstance();
		cad.setTime(tempDate);
		cad.add(5, 1);
		return cad.getTime();
	}

	public static long countDays(String startDate, String endDate) {
		Date tempDate1 = null;
		Date tempDate2 = null;
		long days = 0L;
		try {
			tempDate1 = string2Date(startDate);
			tempDate2 = string2Date(endDate);
			days = (tempDate2.getTime() - tempDate1.getTime()) / 86400000L;
		} catch (Exception e) {
			;
		}
		return days;
	}

	public static String countMinutes(Date dateStart, Date dateEnd) {
		int count;
		try {
			if ((dateStart == null) || (dateEnd == null)) {
				count = 0;
			} else {
				count = (int) ((dateEnd.getTime() - dateStart.getTime()) / 60000L);
			}
		} catch (Exception e) {
			count = 0;
		}
		return String.valueOf(count);
	}
	
	public static int countSeconds(Date dateStart, Date dateEnd) {
		int count;
		try {
			if ((dateStart == null) || (dateEnd == null)) {
				count = 0;
			} else {
				count = (int) ((dateEnd.getTime() - dateStart.getTime()) / 1000L);
			}
		} catch (Exception e) {
			count = 0;
		}
		return count<=0?0:count;
	}

	public static int countDays(Date dateStart, Date dateEnd) {
		if ((dateStart == null) || (dateEnd == null)) {
			return -1;
		}
		return (int) ((dateEnd.getTime() - dateStart.getTime()) / 86400000L);
	}

	public static int countDaysBetweenTwoDays(Date dateStart, Date dateEnd) {
		if ((dateStart == null) || (dateEnd == null)) {
			return -1;
		}
		dateEnd.setHours(0);
		dateEnd.setMinutes(0);
		dateEnd.setSeconds(0);
		dateStart.setHours(0);
		dateStart.setMinutes(0);
		dateStart.setSeconds(0);
		return (int) ((dateEnd.getTime() / 1000L - dateStart.getTime() / 1000L) / 86400L);
	}

	public static boolean checkDays(Date start, Date end, int days) {
		int g = countDays(start, end);
		return g <= days;
	}

	public static Date now() {
		return new Date();
	}

	public static String nowStr() {
		return shortDate(new Date());
	}

	public static Date tomorrow() {
		Calendar cad = Calendar.getInstance();
		cad.setTime(new Date());
		cad.add(5, 1);
		return cad.getTime();
	}

	public static String getDiffStringDate(Date dt, int diff) {
		Calendar ca = Calendar.getInstance();
		if (dt == null)
			ca.setTime(new Date());
		else {
			ca.setTime(dt);
		}
		ca.add(5, diff);
		return dtSimpleFormat(ca.getTime());
	}

	public static boolean checkTime(String statTime) {
		if (statTime.length() > 8) {
			return false;
		}
		String[] timeArray = statTime.split(":");
		if (timeArray.length != 3) {
			return false;
		}
		for (int i = 0; i < timeArray.length; i++) {
			String tmpStr = timeArray[i];
			try {
				Integer tmpInt = new Integer(tmpStr);
				if (i == 0) {
					if ((tmpInt.intValue() > 23) || (tmpInt.intValue() < 0)) {
						return false;
					}

				} else if ((tmpInt.intValue() > 59) || (tmpInt.intValue() < 0))
					return false;
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	public static final String stringToStringDate(String stringDate) {
		if (stringDate == null) {
			return null;
		}
		if (stringDate.length() != 8) {
			return null;
		}
		return stringDate.substring(0, 4) + stringDate.substring(4, 6) + stringDate.substring(6, 8);
	}

	public static Date string2Date(String str, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
		}
		return null;
	}

	public static final Date increaseDate(Date aDate, int days) {
		try{
			Calendar cal = Calendar.getInstance();
			cal.setTime(aDate);
			cal.add(5, days);
			return cal.getTime();
		}catch(Exception e){
			return null;
		}
	}

	public static final Date increaseMonth(Date aDate, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(aDate);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	public static final boolean isLeapYear(int year) {
		return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
	}

	public static final boolean isDefaultWorkingDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int week = calendar.get(7);
		return (week != 7) && (week != 1);
	}

	public static final String getWeekDay(Date date) {
		return getFormat("EEEE").format(date);
	}
	
	public static final int getWeek(Date date) {
		try{
			Calendar cal=Calendar.getInstance(); 
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			cal.setTime(date);
			return cal.get(Calendar.WEEK_OF_YEAR);
		}catch(Exception e)
		{
			;
		}
		return 0;
	}
	
	public static final String getWeekFirst(Date date){
		try{
			Calendar cal=Calendar.getInstance(); 
			cal.setTime(date);
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
			dayOfWeek = dayOfWeek == 0?7:dayOfWeek;
			int mondayPlus = 0;
			mondayPlus = 1 - dayOfWeek;
			GregorianCalendar currentDate = new GregorianCalendar();
			currentDate.setTime(date);
			currentDate.add(GregorianCalendar.DATE, mondayPlus);
			Date monday = currentDate.getTime();
			String preMonday = DateUtils.formatDate(monday, dtSimple);
			return preMonday;
		}catch(Exception e)
		{
			;
		}
		return "";
	}
	
	public static final String getWeekEnd(Date date){
		try{
			Calendar cal=Calendar.getInstance(); 
			cal.setTime(date);
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
			dayOfWeek = dayOfWeek == 0?7:dayOfWeek;
			int mondayPlus = 0;
			mondayPlus = 7 - dayOfWeek;
			
			GregorianCalendar currentDate = new GregorianCalendar();
			currentDate.setTime(date);
			currentDate.add(GregorianCalendar.DATE, mondayPlus);
			Date monday = currentDate.getTime();
			String preMonday = DateUtils.formatDate(monday, dtSimple);
			return preMonday;
		}catch(Exception e)
		{
			;
		}
		return "";
	}
	
	public static final String getMonthFirstDay(Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
			String preMonday = DateUtils.formatDate(c.getTime(), dtSimple);
			return preMonday;
		} catch (Exception e) {
			;
		}
		return "";
	}
	
	public static final String getMonthLastDay(Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH)); 
			String preMonday = DateUtils.formatDate(c.getTime(), dtSimple);
			return preMonday;
		} catch (Exception e) {
			;
		}
		return "";
	}

	public static Date parseDateNoTime(String sDate, String format) throws ParseException {
		if (StringUtils.isEmpty(format)) {
			throw new ParseException("Null format. ", 0);
		}
		DateFormat dateFormat = new SimpleDateFormat(format);
		if ((sDate == null) || (sDate.length() < format.length())) {
			throw new ParseException("length too little", 0);
		}
		return dateFormat.parse(sDate);
	}

	public static final String getNowDateForPageSelectAhead() {
		Calendar cal = Calendar.getInstance();
		if (cal.get(12) < 30)
			cal.set(12, 0);
		else {
			cal.set(12, 30);
		}
		return simpleDate(cal.getTime());
	}

	public static final boolean isIn(Date cur, Date start, Date end) {
		Preconditions.checkNotNull(cur, "cur不能为null");
		Preconditions.checkNotNull(start, "start不能为null");
		Preconditions.checkNotNull(end, "end不能为null");
		return (cur.after(start)) && (cur.before(end));
	}

	public static final String getNowDateForPageSelectBehind() {
		Calendar cal = Calendar.getInstance();
		if (cal.get(12) < 30) {
			cal.set(12, 30);
		} else {
			cal.set(11, cal.get(11) + 1);
			cal.set(12, 0);
		}
		return simpleDate(cal.getTime());
	}

	public static String getNewFormatDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return getDateString(date, dateFormat);
	}

	public static String getDateString(Date date, DateFormat dateFormat) {
		if ((date == null) || (dateFormat == null)) {
			return null;
		}
		return dateFormat.format(date);
	}

	public static Date getOneMonthBegin() {
		Calendar cal = Calendar.getInstance();
		cal.set(2, cal.get(2) - 1);

		return cal.getTime();
	}

	public static Date getStartTimeOfTheDate(Date date) {
		if (date == null) {
			return null;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(11, 0);
		c.set(12, 0);
		c.set(13, 0);
		c.set(14, 0);
		return c.getTime();
	}

	public static Date getEndTimeOfTheDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(11, 23);
		c.set(12, 59);
		c.set(13, 59);
		c.set(14, 0);
		return c.getTime();
	}

	public static Date getMaxDate() {
		return new Date(MAX_DAY.getTime());
	}

	public static boolean isMaxDate(Date date) {
		return MAX_DAY.equals(date);
	}

	static {
		try {
			MAX_DAY = string2DateTimeBy23("9999-12-31 23:59:59");
		} catch (ParseException e) {
		}
	}

	public static void main(String... anArgs) throws Exception {
		System.out.println(DateUtil.simpleFormat(DateUtil.string2DateTime("2018-4-17 09:51:51")));
		// System.out.println(DateUtil.dtSimpleMFormat(new Date()));
	}
}
