package com.adanac.tool.rageon.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by chucun on 2016/6/28.
 */
public class DateUtil {

	public static String utc2Gmt8(String utcStr) {

		SimpleDateFormat utcDateFormat = getUTCDateFormat();
		SimpleDateFormat localDateFormat = getLocalDateFormat();
		try {
			Date date = utcDateFormat.parse(utcStr);

			return localDateFormat.format(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

	}

	public static String gmt82utc(String gmtStr) {

		SimpleDateFormat localDateFormat = getLocalDateFormat();
		SimpleDateFormat utcDateFormat = getUTCDateFormat();

		try {
			Date date = localDateFormat.parse(gmtStr);

			return utcDateFormat.format(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * gmt8
	 *
	 * @return
	 */
	private static SimpleDateFormat getLocalDateFormat() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		return dateFormat;
	}

	/**
	 * utc
	 *
	 * @return
	 */
	private static SimpleDateFormat getUTCDateFormat() {
		SimpleDateFormat utcDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		utcDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return utcDateFormat;
	}

	public static void main(String[] args) {
		String utcStr = "2016-06-26T18:00:00Z";
		String gmt8 = DateUtil.utc2Gmt8(utcStr);
		System.out.println(gmt8);
		System.out.println(DateUtil.gmt82utc(gmt8));
	}
}
