package com.qm.omp.business.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.alibaba.fastjson.JSONArray;

public class DateTimeUtil {

	// 格式：2007年06月07日 12时12分12秒234毫秒
	private final static String[] FORMAT_CHINA = { "年", "月", "日", "时", "分",
			"秒", "毫秒" };
	// 格式：2007年06月07日
	private final static String[] FORMAT_DATE_CHINA = { "年", "月", "日" };
	// 格式：2007-06-07 12:12:12 234
	private final static String[] FORMAT_NORMAL = { "-", "-", "", ":", ":", "",
			"" };
	// 格式：2007/06/07 12:12:12 234
	private final static String[] FORMAT_DATATIME = { "/", "/", "", ":", ":",
			"", "" };
	// 格式：中文星期
	private final static String[] FORMAT_WEEK = { "星期日", "星期一", "星期二", "星期三",
			"星期四", "星期五", "星期六" };

	/**
	 * 获取上月信息
	 * 
	 * @Title: getLastMonth
	 * @Description: TODO
	 * @Param: @param formart 给定转换格式
	 * @Param: @return
	 * @Return: String
	 * @Comment:
	 * @Author: Administrator
	 * @CreateDate: Mar 29, 2012 8:54:59 PM
	 */
	public static String getLastMonth(String strFormart) {
		SimpleDateFormat df = new SimpleDateFormat(strFormart);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -1);
		return df.format(calendar.getTime()).toString();
	}

	/**
	 * 获取今日年份
	 * 
	 * @return yyyy
	 */
	public static String getTodayYear() {
		return DateFormatUtils.format(new Date(), "yyyy");
	}

	/**
	 * 获取今日月份
	 * 
	 * @return MM
	 */
	public static String getTodayMonth() {
		return DateFormatUtils.format(new Date(), "MM");
	}

	/**
	 * 获取上月信息 以yyyymm格式返回
	 * 
	 * @Title: getLastMonthChar6
	 * @Description: TODO
	 * @Param: @return
	 * @Return: String
	 * @Comment:
	 * @Author: Administrator
	 * @CreateDate: 2012-6-9 下午03:37:12
	 */
	public static String getLastMonthChar6() {
		GregorianCalendar gerCal = new GregorianCalendar();
		gerCal.setTime(new Date());
		gerCal.add(GregorianCalendar.MONTH, -1);// 在日期上减一个月
		SimpleDateFormat lastDf = new SimpleDateFormat("yyyyMM");
		return lastDf.format(gerCal.getTime()).toString();
	}

	/**
	 * 
	 * @Title: getLastMonthChar6ByMonth
	 * @Description: 根据穿入的月份获取相应的加减月份
	 * @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String getLastMonthChar6ByMonth(int month) {
		GregorianCalendar gerCal = new GregorianCalendar();
		gerCal.setTime(new Date());
		gerCal.add(GregorianCalendar.MONTH, month);// 在日期上减一个月
		SimpleDateFormat lastDf = new SimpleDateFormat("yyyyMM");
		return lastDf.format(gerCal.getTime()).toString();
	}

	/**
	 * 
	 * @Title: getDateChar8
	 * @Description: 根据穿入的年月日获取日期
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String getDateChar8(int year, int month, int day) {
		GregorianCalendar gerCal = new GregorianCalendar();
		gerCal.setTime(new Date());
		gerCal.add(GregorianCalendar.YEAR, year);// 在日期上减一个月
		gerCal.add(GregorianCalendar.MONTH, month);// 在日期上减一个月
		gerCal.add(GregorianCalendar.DATE, day);// 在日期上减1天
		SimpleDateFormat lastDf = new SimpleDateFormat("yyyyMMdd");
		return lastDf.format(gerCal.getTime()).toString();
	}

	/**
	 * 获取今日日期
	 * 
	 * @return dd
	 */
	public static String getTodayDay() {
		return DateFormatUtils.format(new Date(), "dd");
	}

	/**
	 * 获取短日月
	 * 
	 * @return MMdd
	 */
	public static String getTodayChar4() {
		return DateFormatUtils.format(new Date(), "MMdd");
	}

	/**
	 * 获取短日月
	 * 
	 * @return MMdd
	 */
	public static String getTodayChar4En() {
		return DateFormatUtils.format(new Date(), "MM-dd");
	}

	/**
	 * 返回年月
	 * 
	 * @return yyyyMM
	 */
	public static String getTodayChar6() {
		return DateFormatUtils.format(new Date(), "yyyyMM");
	}

	/**
	 * 返回年月
	 * 
	 * @return yyyyMM
	 */
	public static String getTodayChar6En() {
		return DateFormatUtils.format(new Date(), "yyyy-MM");
	}

	/**
	 * 返回年月日
	 * 
	 * @return yyyyMMdd
	 */
	public static String getTodayChar8() {
		return DateFormatUtils.format(new Date(), "yyyyMMdd");
	}

	/**
	 * 返回年月日
	 * 
	 * @return yyyyMMdd
	 */
	public static String getTodayChar8En() {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 获取昨天日期 格式 yyyy-MM-dd
	 * 
	 * @Title: getLastDayChar8En
	 * @Description: TODO
	 * @Param: @return
	 * @Return: String
	 * @Comment:
	 * @Author: Administrator
	 * @CreateDate: 2012-5-29 上午11:20:37
	 */
	public static String getLastDayChar8En() {
		GregorianCalendar gerCal = new GregorianCalendar();
		gerCal.setTime(new Date());
		gerCal.add(GregorianCalendar.DATE, -1);// 在日期上减1天
		SimpleDateFormat lastDf = new SimpleDateFormat("yyyy-MM-dd");
		return lastDf.format(gerCal.getTime());
	}

	/**
	 * 返回 年月日小时分
	 * 
	 * @return yyyyMMddHHmm
	 */
	public static String getTodayChar12() {
		return DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
	}

	/**
	 * 返回 年月日小时分
	 * 
	 * @return yyyyMMddHHmm
	 */
	public static String getTodayChar12En() {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm");
	}

	/**
	 * 返回 年月日小时分秒
	 * 
	 * @return
	 */
	public static String getTodayChar14() {
		return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
	}

	/**
	 * 
	 * @Title: getDayChar14ByDays
	 * @Description: 根据天数加减当前日期
	 * @param days
	 * @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String getDayChar14ByDays(int days) {
		GregorianCalendar gerCal = new GregorianCalendar();
		gerCal.setTime(new Date());
		gerCal.add(GregorianCalendar.DATE, days);// 在日期上加减天数
		SimpleDateFormat lastDf = new SimpleDateFormat("yyyyMMddHHmmss");
		return lastDf.format(gerCal.getTime());
	}

	/**
	 * 返回 年月日小时分秒
	 * 
	 * @return
	 */
	public static String getTodayChar14En() {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	// /**
	// * 返回 年月日小时分秒 毫秒
	// *
	// * @return
	// */
	// public static String getTodayChar17()
	// {
	// return DateFormatUtils.format(new Date(), "yyyyMMddHHmmssS");
	// }
	//

	/**
	 * 返回 年月日小时分秒 毫秒
	 * 
	 * @return yyyyMMddHHmmssS
	 */
	public static String getTodayChar17() {
		String dateString = DateFormatUtils.format(new Date(),
				"yyyyMMddHHmmssS");
		int length = dateString.length();

		if (length < 17) {
			String endStr = dateString.substring(14, length);
			int len = endStr.length();
			for (int i = 0; i < 3 - len; i++) {
				endStr = "0" + endStr;
			}
			dateString = dateString.substring(0, 14) + endStr;
		}
		return dateString;
	}

	/**
	 * 返回 年月日小时分秒 毫秒
	 * 
	 * @return
	 */
	public static String getTodayChar17En() {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss S");
	}

	/**
	 * 返回 年月日小时分秒 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTodayChar14_() {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 返回 年月日小时分秒 毫秒
	 * 
	 * @return
	 */
	public static String getToDateCn() {
		return DateFormatUtils.format(new Date(), "yyyy年MM月dd日");
	}

	/**
	 * 返回 年-月-日
	 * 
	 * @return
	 */
	public static String getToDateEn() {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 返回中文日期格式 支持4、6、8、12、14、17位转换
	 * 
	 * @param charDateTime
	 *            长整型 CHAR
	 * @return 2007年12月12日 12时12分12秒 234毫秒
	 */
	public static String getFormatChina(String charDateTime) {
		return getFormatDateTime(charDateTime, "FORMAT_CHINA");
	}

	/**
	 * 返回标准日期格式 支持4、6、8、12、14、17位转换
	 * 
	 * @param charDateTime
	 *            长整型 CHAR
	 * @return 2007-12-12 12:12:12 234
	 */
	public static String getFormatNormal(String charDateTime) {
		return getFormatDateTime(charDateTime, "FORMAT_NORMAL");
	}

	/**
	 * 返回标准日期格式 支持4、6、8、12、14、17位转换
	 * 
	 * @param charDateTime
	 *            长整型 CHAR
	 * @return 2007/12/12 12:12:12 234
	 */
	public static String getFormatDateTime(String charDateTime) {
		return getFormatDateTime(charDateTime, "FORMAT_DATATIME");
	}

	/**
	 * 把日期格式转换为长整型格式
	 * 
	 * @param inputDateTime
	 * @return
	 */
	public static String getDateTimeToChar(String inputDateTime) {
		String strResult = "";
		if (null == inputDateTime) {
			return strResult = "";
		}

		if (("".equals(inputDateTime.trim()))) {
			return strResult = "";
		}

		// 替换
		strResult = inputDateTime.replaceAll("年", "");
		strResult = strResult.replaceAll("月", "");
		strResult = strResult.replaceAll("日", "");
		strResult = strResult.replaceAll("时", "");
		strResult = strResult.replaceAll("分", "");
		strResult = strResult.replaceAll("秒", "");
		strResult = strResult.replaceAll("毫", "");
		strResult = strResult.replaceAll(" ", "");
		strResult = strResult.replaceAll("-", "");
		strResult = strResult.replaceAll("/", "");
		strResult = strResult.replaceAll(":", "");
		return strResult;

	}

	/**
	 * 对日期进行转换
	 * 
	 * @Title: getFormatDateTime
	 * @Description: TODO
	 * @Param: @param charDateTime
	 * @Param: @param formatType FORMAT_CHINA 中文 FORMAT_NORMAL以‘-’先分隔
	 *         FORMAT_DATATIME 以‘/’分隔
	 * @Param: @return
	 * @Return: String
	 * @Comment:
	 * @Author: Administrator
	 * @CreateDate: Mar 29, 2012 8:35:29 PM
	 */
	private static String getFormatDateTime(String charDateTime,
			String formatType) {
		String strResult = "";
		if (null == charDateTime) {
			return strResult = "";
		}

		if (("".equals(charDateTime.trim()))) {
			return strResult = "";
		}

		String[] FORMAT_CHAR = null;
		if (formatType.equals("FORMAT_CHINA")) {
			FORMAT_CHAR = FORMAT_CHINA;
		} else if (formatType.equals("FORMAT_NORMAL")) {
			FORMAT_CHAR = FORMAT_NORMAL;
		} else if (formatType.equals("FORMAT_DATATIME")) {
			FORMAT_CHAR = FORMAT_DATATIME;
		} else {
			return strResult = charDateTime;
		}

		// 去掉空格
		charDateTime = charDateTime.trim();

		if (charDateTime.length() == 4) {
			// MMdd 转换 MM月dd日
			strResult = charDateTime.substring(0, 2) + FORMAT_CHAR[1]
					+ charDateTime.substring(2, 4) + FORMAT_CHAR[2];
		} else if (charDateTime.length() == 6) {
			// yyyyMM 转换 yyyy年MM月
			strResult = charDateTime.substring(0, 4) + FORMAT_CHAR[0]
					+ charDateTime.substring(4, 6) + FORMAT_CHAR[1];
		} else if (charDateTime.length() == 8) {
			// yyyyMMdd
			strResult = charDateTime.substring(0, 4) + FORMAT_CHAR[0]
					+ charDateTime.substring(4, 6) + FORMAT_CHAR[1]
					+ charDateTime.substring(6, 8) + FORMAT_CHAR[2];
		} else if (charDateTime.length() == 12) {
			// yyyyMMddHHmm
			strResult = charDateTime.substring(0, 4) + FORMAT_CHAR[0]
					+ charDateTime.substring(4, 6) + FORMAT_CHAR[1]
					+ charDateTime.substring(6, 8) + FORMAT_CHAR[2] + " "
					+ charDateTime.substring(8, 10) + FORMAT_CHAR[3]
					+ charDateTime.substring(10, 12) + FORMAT_CHAR[4];
		} else if (charDateTime.length() == 14) {
			// yyyyMMddHHmmss
			strResult = charDateTime.substring(0, 4) + FORMAT_CHAR[0]
					+ charDateTime.substring(4, 6) + FORMAT_CHAR[1]
					+ charDateTime.substring(6, 8) + FORMAT_CHAR[2] + " "
					+ charDateTime.substring(8, 10) + FORMAT_CHAR[3]
					+ charDateTime.substring(10, 12) + FORMAT_CHAR[4]
					+ charDateTime.substring(12, 14) + FORMAT_CHAR[5];
		} else if (charDateTime.length() == 17) {
			// yyyyMMddHHmmssS
			strResult = charDateTime.substring(0, 4) + FORMAT_CHAR[0]
					+ charDateTime.substring(4, 6) + FORMAT_CHAR[1]
					+ charDateTime.substring(6, 8) + FORMAT_CHAR[2] + " "
					+ charDateTime.substring(8, 10) + FORMAT_CHAR[3]
					+ charDateTime.substring(10, 12) + FORMAT_CHAR[4]
					+ charDateTime.substring(12, 14) + FORMAT_CHAR[5] + " "
					+ charDateTime.substring(14, 17) + FORMAT_CHAR[6];
		} else {
			strResult = charDateTime;
		}

		return strResult;
	}

	/**
	 * 将指定Date类型参数转换为指定的Oracle日期时间格式字符串
	 * 
	 * @param inputDate
	 *            传入Date类型参数
	 * @return String
	 */
	public static String getOracleDate(Date inputDateTime)
			throws NullPointerException {
		if (null == inputDateTime) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(inputDateTime);
	}

	/**
	 * 比对两个时间间隔
	 * 
	 * @param startDateTime
	 *            开始时间
	 * @param endDateTime
	 *            结束时间
	 * @param distanceType
	 *            计算间隔类型 天d 小时h 分钟m 秒s
	 * @return
	 */
	public static String getDistanceDT(String startDateTime,
			String endDateTime, String distanceType) {
		String strResult = "";
		long lngDistancVal = 0;
		try {
			SimpleDateFormat tempDateFormat = new SimpleDateFormat(
					"yyyyMMddHHmmss");
			Date startDate = tempDateFormat.parse(startDateTime);
			Date endDate = tempDateFormat.parse(endDateTime);
			if (distanceType.equals("d")) {
				lngDistancVal = (endDate.getTime() - startDate.getTime())
						/ (1000 * 60 * 60 * 24);
			} else if (distanceType.equals("h")) {
				lngDistancVal = (endDate.getTime() - startDate.getTime())
						/ (1000 * 60 * 60);
			} else if (distanceType.equals("m")) {
				lngDistancVal = (endDate.getTime() - startDate.getTime())
						/ (1000 * 60);
			} else if (distanceType.equals("s")) {
				lngDistancVal = (endDate.getTime() - startDate.getTime()) / (1000);
			}
			strResult = String.valueOf(lngDistancVal);
		} catch (Exception e) {
			strResult = "0";
		}
		return strResult;
	}

	/**
	 * 日期差计算 例如在某个日期增加几天后的日期 返回几天后日期
	 * 
	 * @param startDate
	 * @param addDate
	 * @return
	 */
	public static String getIncreaseDT(String startDate, int addDate) {
		String strResult = "0000-00-00";

		try {
			Calendar localDate = new GregorianCalendar();
			// 把字符串型日期转换为日期
			Date tempDate = new Date();
			if (!"".equals(startDate)) {
				SimpleDateFormat tempDateFormat = new SimpleDateFormat(
						"yyyyMMdd");
				tempDate = tempDateFormat.parse(startDate);
			}
			localDate.setTime(tempDate);
			localDate.add(Calendar.DATE, addDate);
			String curyear = "" + localDate.get(Calendar.YEAR);
			int intmonth = localDate.get(Calendar.MONTH) + 1;
			String curmonth = "" + intmonth;
			String curday = "" + localDate.get(Calendar.DAY_OF_MONTH);

			if (curmonth.length() == 1) {
				curmonth = "0" + curmonth;
			}
			if (curday.length() == 1) {
				curday = "0" + curday;
			}
			strResult = curyear + "" + curmonth + "" + curday;
		} catch (Exception e) {
			strResult = "";
		}
		return strResult;
	}

	/**
	 * 获取本周的开始日期 （星期天的日期）20070201
	 * 
	 * @return
	 */
	public static String getWeekStartDate() {
		String strResult = "19000101";
		try {
			Calendar calendar = Calendar.getInstance();
			int intWeekNum = calendar.get(Calendar.DAY_OF_WEEK);
			intWeekNum = intWeekNum - 1;
			strResult = getIncreaseDT(getTodayChar8(), -intWeekNum);
		} catch (Exception ex) {
			strResult = "19000101";
		}
		return strResult;
	}

	/**
	 * 获取今天星期几 中文
	 * 
	 * @return
	 */
	public static String getWeekChina() {
		String strResult = " ";
		try {
			Calendar calendar = Calendar.getInstance();
			int intWeekNum = calendar.get(Calendar.DAY_OF_WEEK);
			intWeekNum = intWeekNum - 1;
			strResult = FORMAT_WEEK[intWeekNum];
		} catch (Exception ex) {
			strResult = " ";
		}
		return strResult;
	}

	public static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	public static SimpleDateFormat dateFormat_1 = new SimpleDateFormat("yyyyMM");
	public static SimpleDateFormat dateFormat_2 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat dateFormat_3 = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static SimpleDateFormat dateFormat_4 = new SimpleDateFormat(
			"yyyy年MM月");
	public static SimpleDateFormat dateFormat_5 = new SimpleDateFormat(
			"yyyy年MM月dd日");
	public static SimpleDateFormat dateFormat_6 = new SimpleDateFormat(
			"yyyyMMdd");
	public static SimpleDateFormat dateFormat_7 = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	public static String formatDateStrToOtherStr(String time) {
		String reTime = "";
		try {
			reTime = dateFormat_2.format(dateFormat.parse(time));
		} catch (ParseException e) {
		}
		return reTime;
	}

	public static String formatDateStrToOtherStr6ByDateFormat(String time) {
		if (StringUtils.isEmpty(time)) return null;
		String reTime = "";
		try {
			reTime = dateFormat_6.format(dateFormat.parse(time));
		} catch (ParseException e) {
		}
		return reTime;
	}

	public static String formatDateStrToOtherStr3(String time) {
		String reTime = "";
		try {
			reTime = dateFormat_3.format(dateFormat.parse(time));
		} catch (ParseException e) {
		}
		return reTime;
	}

	public static String formatDateStr2ToOtherStr(String time) {
		String reTime = "";
		try {
			reTime = dateFormat.format(dateFormat_2.parse(time));
		} catch (ParseException e) {
		}
		return reTime;
	}

	public static String formatDateStrToOtherStr4(String time) {
		String reTime = "";
		try {
			reTime = dateFormat_4.format(dateFormat_1.parse(time));
		} catch (ParseException e) {
		}
		return reTime;
	}

	public static String formatDateStrToOtherStr5(String time) {
		String reTime = "";
		try {
			reTime = dateFormat_5.format(dateFormat_6.parse(time));
		} catch (ParseException e) {
		}
		return reTime;
	}

	public static String formatDateStrToOtherStr6(String time) {
		String reTime = "";
		try {
			reTime = dateFormat_5.format(dateFormat_7.parse(time));
		} catch (ParseException e) {
		}
		return reTime;
	}

	public static String formatDateStrToOtherStr8(String time) {
		String reTime = "";
		try {
			reTime = dateFormat_6.format(dateFormat_6.parse(time));
		} catch (ParseException e) {
		}
		return reTime;
	}

	/**
	 * 将 "2012-5-1" 转换成 "20120501"
	 * 
	 * @Title: formatDateStrToOtherStr9
	 * @Description: TODO
	 * @Param: @param time
	 * @Param: @return
	 * @Return: String
	 * @Comment:
	 * @Author: Administrator
	 * @CreateDate: Apr 24, 2012 10:09:38 AM
	 */
	public static String formatDateStrToOtherStr9(String time) {
		String reTime = "";
		try {
			reTime = dateFormat_6.format(dateFormat_3.parse(time));
		} catch (ParseException e) {
		}
		return reTime;
	}

	/**
	 * 将 "20120501" 转换成 "2012-5-1"
	 * 
	 * @Title: formatDateStrToOtherStr10
	 * @Description: TODO
	 * @Param: @param time
	 * @Param: @return
	 * @Return: String
	 * @Comment:
	 * @Author: Administrator
	 * @CreateDate: Apr 29, 2012 3:45:54 PM
	 */
	public static String formatDateStrToOtherStr10(String time) {
		String reTime = "";
		try {
			reTime = dateFormat_3.format(dateFormat_6.parse(time));
		} catch (ParseException e) {
		}
		return reTime;
	}

	/**
	 * 将 yyyy-MM-dd HH:mm:ss 转换成 yyyy-MM-dd
	 * 
	 * @Title: formatDateStrToOtherStr11
	 * @Description: TODO
	 * @Param: @param time
	 * @Param: @return
	 * @Return: String
	 * @Comment:
	 * @Author: Administrator
	 * @CreateDate: May 4, 2012 10:52:59 AM
	 */
	public static String formatDateStrToOtherStr11(String time) {
		String reTime = "";
		try {
			reTime = dateFormat_3.format(dateFormat_2.parse(time));
		} catch (ParseException e) {
		}
		return reTime;
	}

	/**
	 * 
	 * @Title: getDayChar8EnByDays
	 * @Description: 根据传入的天数获取日期
	 * @param days
	 * @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String getDayChar8EnByDays(int days) {
		GregorianCalendar gerCal = new GregorianCalendar();
		gerCal.setTime(new Date());
		gerCal.add(GregorianCalendar.DATE, days);// 在日期上减1天
		SimpleDateFormat lastDf = new SimpleDateFormat("yyyy-MM-dd");
		return lastDf.format(gerCal.getTime());
	}

	/**
	 * 比较时间与当前时间先后
	 * 
	 * @Title: compareTime
	 * @Description: TODO
	 * @Param: @param startTime
	 * @Param: @return
	 * @Return: 1 晚于当前时间 2 与当前时间相等 3 早于当前时间
	 * @Comment:
	 * @Author: Administrator
	 * @CreateDate: Mar 29, 2012 8:34:11 PM
	 */
	public static String compareTime(String startTime) {
		Date date = new Date();
		startTime = startTime.substring(0, 4) + "-" + startTime.substring(4, 6)
				+ "-" + startTime.substring(6, 8);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(date);
			c2.setTime(df.parse(startTime));
		} catch (java.text.ParseException e) {
		}
		int result = c1.compareTo(c2);
		if (result == 0) {
			return "2";
		} else if (result < 0) {
			return "1";
		} else {
			return "3";
		}
	}

	/**
	 * 当月第一天
	 * 
	 * @return
	 */
	public static String getFirstDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		Date theDate = calendar.getTime();

		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first);
		return str.toString();

	}

	/**
	 * 当月最后一天
	 * 
	 * @return
	 */
	public static String getLastDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		Date theDate = calendar.getTime();
		String s = df.format(theDate);
		StringBuffer str = new StringBuffer().append(s);
		return str.toString();

	}

	/**
	 * @Title: getEndTime
	 * @Description: TODO(根据传入单位和数量，确定时间)
	 * @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String getEndTime(String unit, int scope, String beginTime) {
		String endTime = "";
		try {
			if ("MM".equals(unit)) {
				GregorianCalendar gerCal = new GregorianCalendar();
				gerCal.setTime(dateFormat.parse(beginTime));
				gerCal.add(GregorianCalendar.MONTH, -scope);// 在日期上减1天
				SimpleDateFormat lastDf = new SimpleDateFormat("yyyyMMddHHmmss");
				endTime = lastDf.format(gerCal.getTime());
			} else if ("dd".equals(unit)) {
				GregorianCalendar gerCal = new GregorianCalendar();
				gerCal.setTime(dateFormat.parse(beginTime));
				gerCal.add(GregorianCalendar.DATE, -scope);// 在日期上减1天
				SimpleDateFormat lastDf = new SimpleDateFormat("yyyyMMddHHmmss");
				endTime = lastDf.format(gerCal.getTime());
			} else if ("HH".equals(unit)) {
				GregorianCalendar gerCal = new GregorianCalendar();
				gerCal.setTime(dateFormat.parse(beginTime));
				gerCal.add(GregorianCalendar.HOUR, -scope);// 在日期上减1天
				SimpleDateFormat lastDf = new SimpleDateFormat("yyyyMMddHHmmss");
				endTime = lastDf.format(gerCal.getTime());
			} else if ("mm".equals(unit)) {
				GregorianCalendar gerCal = new GregorianCalendar();
				gerCal.setTime(dateFormat.parse(beginTime));
				gerCal.add(GregorianCalendar.MINUTE, -scope);// 在日期上减1天
				SimpleDateFormat lastDf = new SimpleDateFormat("yyyyMMddHHmmss");
				endTime = lastDf.format(gerCal.getTime());
			} else if ("ss".equals(unit)) {
				GregorianCalendar gerCal = new GregorianCalendar();
				gerCal.setTime(dateFormat.parse(beginTime));
				gerCal.add(GregorianCalendar.MILLISECOND, -scope);// 在日期上减1天
				SimpleDateFormat lastDf = new SimpleDateFormat("yyyyMMddHHmmss");
				endTime = lastDf.format(gerCal.getTime());
			}
		} catch (Exception e) {
		}

		return endTime;
	}

	/**
	 * 
	 * @Title: getWeekNumber
	 * @Description:获取今天是本周的第几天（数字）
	 * @return
	 * @return int 返回类型
	 * @throws
	 */
	public static int getWeekNumber() {
		int strResult = 0;
		try {
			Calendar calendar = Calendar.getInstance();
			int intWeekNum = calendar.get(Calendar.DAY_OF_WEEK);
			intWeekNum = intWeekNum - 1;
			if (intWeekNum == 0) {
				intWeekNum = 7;
			}
			strResult = intWeekNum;
		} catch (Exception ex) {
			strResult = 0;
		}
		return strResult;
	}

	/**
	 * 
	 * @Title: getTodayHours
	 * @Description:获取当前小时（0~23）
	 * @return
	 * @return int 返回类型
	 * @throws
	 */
	public static int getTodayHours() {
		int result = 0;
		Calendar calendar = Calendar.getInstance();
		result = calendar.get(Calendar.HOUR_OF_DAY);
		return result;
	}

	/**
	 * 
	 * @Title: getStartTimeAndEndTimeBetweenTime
	 * @Description: TODO(获取两个日期之间的所有日期)
	 * @param startTime
	 * @param endTime
	 * @return
	 * @return List<String> 返回类型
	 * @throws
	 */
	public static List<String> getStartTimeAndEndTimeBetweenDays(
			String startTime, String endTime, String format) {
		SimpleDateFormat formatTime = new SimpleDateFormat(format);
		List<String> list = new ArrayList<String>();
		if (startTime.equals(endTime))// 开始时间和结束时间相等 就返回开始时间
			list.add(startTime);
		else {
			try {
				list.add(startTime);
				String tmp = formatTime.format(formatTime.parse(startTime)
						.getTime() + 3600 * 24 * 1000);
				while (tmp.compareTo(endTime) <= 0) {
					list.add(tmp);
					tmp = formatTime
							.format(formatTime.parse(tmp).getTime() + 3600 * 24 * 1000);
				}
				// list.add(endTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 
	 * @Title: getStartTimeAndEndTimeBetweenMonths
	 * @Description: TODO(获取俩个时间之间所有的月份)
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 * @return List<String> 返回类型
	 * @throws
	 */
	public static List<String> getStartTimeAndEndTimeBetweenMonths(
			String startTime, String endTime, String format) {
		SimpleDateFormat formatTime = new SimpleDateFormat(format);
		List<String> list = new ArrayList<String>();
		try {
			Date startDate = formatTime.parse(startTime);
			Date endDate = formatTime.parse(endTime); // 结束日期
			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			startCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
			String result = null;
			while (startCalendar.compareTo(endCalendar) <= 0) {
				startDate = startCalendar.getTime();
				result = new SimpleDateFormat(format).format(startDate);
				// result = result.substring(0, result.length());
				list.add(result);
				// 开始日期加一个月直到等于结束日期为止
				startCalendar.add(Calendar.MONTH, 1);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		} // 开始日期

		return list;
	}

	/**
	 * 
	 * @Title: getStartTimeAndEndTimeNumbers
	 * @Description: TODO(获取两个时间段内月份只差)
	 * @return
	 * @return int 返回类型
	 * @throws
	 */
	public static int getStartTimeAndEndTimeNumbers(String startTime,
			String endTime, String format) {
		SimpleDateFormat formatTime = new SimpleDateFormat(format);
		try {
			Date startDate = formatTime.parse(startTime);
			Date endDate = formatTime.parse(endTime); // 结束日期
			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			startCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
			return endCalendar.get(Calendar.MONTH)
					- startCalendar.get(Calendar.MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * yyyy-MM-dd HH:mm:ss 转为 yyyyMMddHHmmss
	 * 
	 * @param fTime
	 * @author rh
	 * @return
	 */
	public static String get_yyyyMMddHHmmss(String fTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			if (fTime != null && !"".equals(fTime.trim())) {
				fTime = format1.format(format.parse(fTime));
			} else {
				fTime = "";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fTime;
	}

	/**
	 * yyyy-MM-dd 转为 yyyyMMdd
	 * 
	 * @param fTime
	 * @author rh
	 * @return
	 */
	public static String get_yyyyMMdd(String fTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		try {
			if (fTime != null && !"".equals(fTime.trim())) {
				fTime = format1.format(format.parse(fTime));
			} else {
				fTime = "";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fTime;
	}

	/**
	 * yyyyMMddHHmmss 转为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param fTime
	 * @author rh
	 * @return
	 */
	public static String get_yyyyMMddHHmmss_reverse(String fTime) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			if (fTime != null && !"".equals(fTime.trim())) {
				fTime = format1.format(format.parse(fTime));
			} else {
				fTime = "";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fTime;
	}

	/**
	 * 日期类型从一个类型转为另一个类型
	 * 
	 * @param fTime
	 * @param sourcePattern
	 *            原类型
	 * @param toPattern
	 *            转换后的类型
	 * @return
	 */
	public static String translateDateToDate(String fTime,
			String sourcePattern, String toPattern) {
		SimpleDateFormat format = new SimpleDateFormat(sourcePattern);
		SimpleDateFormat format1 = new SimpleDateFormat(toPattern);
		try {
			if (fTime != null && !"".equals(fTime.trim())) {
				fTime = format1.format(format.parse(fTime));
			} else {
				fTime = "";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fTime;
	}

	/**
	 * 获取最近一周日期，字符串
	 * 
	 * @param pattern
	 *            返回日期类型
	 * @param days
	 *            天数
	 * @return
	 */
	public static JSONArray getWeekStr(String pattern, int days) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		JSONArray json = new JSONArray();
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.DAY_OF_MONTH, -days);
		for (int i = 0; i < days; i++) {
			c.add(Calendar.DAY_OF_MONTH, 1);
			json.add(format.format(c.getTime()));
		}
		return json;
	}

	/**
	 * 获取当天0点0分0秒的时间，格式：yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getTodayStartZero() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String res = format.format(new Date());
		return res + "000000";
	}

	/**
	 * 获取多少分钟前的时间，格式：yyyyMMddHHmmss
	 * 
	 * @param a
	 *            几分钟
	 * @return
	 */
	public static String getTodayChar14_10MinAgo(int a) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String res = format.format(new Date().getTime() - a * 60 * 1000);
		return res;
	}

	/**
	 * 得到指定月的天数
	 * 
	 * @param yyyyMM
	 *            这种格式的日期
	 * @return 获取天数，获取失败则返回-1
	 */
	public static int getMonthLastDay(String yyyyMM) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		try {
			Date date = format.parse(yyyyMM);
			Calendar a = Calendar.getInstance();
			a.setTime(date);
			// a.set(Calendar.YEAR, year);
			// a.set(Calendar.MONTH, month - 1);
			a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
			a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
			int maxDate = a.get(Calendar.DATE);
			return maxDate;
		} catch (ParseException e) {

		}
		return -1;
	}

	public static void main(String[] args) {
		System.out.println(DateTimeUtil.getTodayChar14());
	}
}
