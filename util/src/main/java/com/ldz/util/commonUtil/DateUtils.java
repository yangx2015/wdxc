package com.ldz.util.commonUtil;

import org.apache.commons.lang.StringUtils;
import java.time.LocalDateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

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






//	/**
//	 * 考试年份编码
//	 * @return
//	 */
//	public static String getYearCode(){
//		Calendar cal = Calendar.getInstance();
//    	int year = cal.get(Calendar.YEAR);
//    	String yearStr = new Integer(year).toString();
//    	return yearStr.substring(2,4);//当前年份后两位
//	}
	
	public static void main(String[] args){
//		String dateStr = DateUtils.getDateStr(parseMills(1350994695000l), "yyyy-MM-dd HH:mm:ss");
//		System.out.println(dateStr);
		System.out.println(calculateTime(LocalDateTime.now(), "-", 60 * Integer.parseInt("1")));

		try {
			DateUtils.getDate("2018-04-10","yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
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

}
