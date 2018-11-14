package com.ldz.util.commonUtil;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
	/**
	 * 返回格式：2007-08-14
	 * @return
	 */
	public static String getToday(){
		String time = "";
		time = getToday("yyyy-MM-dd");
		return time;
	}
	/**
	 * 
	 * @param format 根据指定的格式时间类型返回当前时间
	 * @return
	 */
	public static String getToday(String format){
		return getDateStr(Calendar.getInstance().getTime(),format);
	}
	
	/**
	 * 日期转字符
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateStr(Date date,String format){
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	public static Date getDate(String date,String format) throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.parse(date);
	}
	
	/**
	 * @param millis
	 * @return
	 */
	public static Date parseMills(long millis){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
	
	public static Date getDateToday(String format) throws ParseException{
		String str = getDateStr(Calendar.getInstance().getTime(),format);
		return getDate(str,format);
	}
	
	public static long getTimes(){
		return new Date().getTime();
	}


	/**
	 * 根据所有传入的参数进行时间计算
	 * @param time 时间
	 * @param operator 运算符
	 * @param second 计算参数    秒
	 */
	public static String calculateTime(LocalDateTime time,String operator,int second){

		if(StringUtils.equals(operator,"+")){
			time = time.plusSeconds(second);
		}else if(StringUtils.equals(operator, "-")){
			time = time.minusSeconds(second);
		}
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return time.format(df);

	}

	public static long get_D_Plaus_1(Calendar c) {
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
		return c.getTimeInMillis();
	}



	/**
	 * 根据传入的开始时间和结束时间，获取时间段
	 * @param kssj
	 * @param jssj
	 * @return
	 */
	public static List<Date> createDateList(String kssj,String jssj) throws ParseException {
		List<Date> dates = new ArrayList<>();


		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(kssj));

		for (long d = cal.getTimeInMillis(); d <= sdf.parse(jssj).getTime(); d = get_D_Plaus_1(cal)) {
			String format = sdf.format(d);
			Date parse = sdf.parse(format);
			dates.add(parse);
		}

		return dates;
	}


	public static void main(String[] args) throws ParseException {
//		System.out.println(createDateList("2018-09-08","2018-09-08"));
//		System.out.println(getNowTime().substring(11)+"1");
//		System.out.println(DateUtils.getDate("07:00:00","HH:mm:ss"));
		System.out.println(hourMinuteBetween("06:00:00","07:00:00","22:00:00"));

	}
	public static String getNowTime() {
		return getToday("yyyy-MM-dd HH:mm:ss");
	}
	public static boolean checkTxnTime(String startTime, String endTime) {
		String nowDate = getDateStr(new Date(), "HH:mm:ss");
		if(nowDate.compareTo(startTime) >= 0 && nowDate.compareTo(endTime) <= 0){
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param nowDate   要比较的时间
	 * @param startDate   开始时间
	 * @param endDate   结束时间
	 * @return   true在时间段内，false不在时间段内
	 * @throws Exception
	 */
	public static boolean hourMinuteBetween(String nowDate, String startDate, String endDate)  {
	try {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

			Date now = format.parse(nowDate);
			Date start = format.parse(startDate);
			Date end = format.parse(endDate);

			long nowTime = now.getTime();
			long startTime = start.getTime();
			long endTime = end.getTime();

			return nowTime >= startTime && nowTime <= endTime;
		}catch (Exception e){}
		return false;
	}
}
