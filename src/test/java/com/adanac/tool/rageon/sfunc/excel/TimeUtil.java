package com.adanac.tool.rageon.sfunc.excel;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	/**
	 * 获取当前日期
	 * 
	 * @return YYYYMMDD
	 */
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currDate = sdf.format(new Date().getTime());
		return currDate;
	}

	/**
	 * 获取当前日期
	 * @param dateFormat 日期格式
	 * @return
	 */
	public static String getCurrentDate(String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat("" + dateFormat + "");
		String currDate = sdf.format(new Date().getTime());
		return currDate;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getCurrentTimestamp() {
		return new Timestamp(new Date().getTime()).toString();
	}

	/**
	 * 获取指定格式类型的时间
	 * @param dateFormat（时间格式：yyyy-MM-dd HH:mm:ss(默认)）
	 * @param datetime 毫秒数
	 * @return
	 */
	public static String getDateFormat(String dateFormat, String datetime) {
		if (dateFormat == null || "".equals(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		long timeMillis = 0l;
		if (datetime == null || "".equals(datetime)) {
			timeMillis = new Date().getTime();
		} else {
			timeMillis = Long.parseLong(datetime);
		}

		DateFormat df = new SimpleDateFormat(dateFormat);
		return df.format(timeMillis);
	}
}
