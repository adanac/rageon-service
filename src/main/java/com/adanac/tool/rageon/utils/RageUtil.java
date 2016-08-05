package com.adanac.tool.rageon.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.adanac.framework.utils.DateUtils;
import com.adanac.framework.utils.MD5;
import com.adanac.framework.utils.StringUtils;

public class RageUtil {
	private static final int DEFAULT_SCALE = 2;
	private static final int DEFAULT_PWD_LEN = 8;
	private final static String DEFAULT_DATE_FORMAT = "yyyyMMdd";

	private RageUtil() {
	}

	public static String md5(String secretKey, String... args) {
		if (null != args && args.length > 0) {
			StringBuilder sb = new StringBuilder(secretKey);
			for (String arg : args) {
				if (!StringUtils.isEmpty(arg)) {
					sb.append(arg);
				}
			}
			return MD5.encode(sb.toString());
		}
		return "";
	}

	/**
	 * 时间戳格式转换换为指定的格式
	 * 
	 * @param dateTime
	 * @return
	 * @see String
	 */
	public static String format(String dateTime) {
		return format(dateTime, DEFAULT_DATE_FORMAT);
	}

	public static String format(String dateTime, String pattern) {
		String result = "";
		Date date = DateUtils.parse(dateTime, DateUtils.DEFAULT_DATE_PATTERN);
		if (null != date) {
			result = DateUtils.format(date, pattern);
		}
		return result;
	}

	public static String getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return DateUtils.format(cal.getTime(), "yyyy-MM-dd");
	}

	public static String getToday() {
		Calendar cal = Calendar.getInstance();
		return DateUtils.format(cal.getTime(), "yyyy-MM-dd");
	}

	public static Double div(Double dividend, Double divisor) {
		return div(dividend, divisor, DEFAULT_SCALE);
	}

	public static Double div(Double dividend, Double divisor, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = BigDecimal.valueOf(dividend);
		BigDecimal b2 = BigDecimal.valueOf(divisor);
		return Double.valueOf(b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue());
	}

	/**
	 * 随机种子
	 */
	private static final String[] E = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A",
			"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	/**
	 * 规则范围
	 */
	private static final int[] SECTION = { 0, 10, 36, 62 };

	/**
	 * 随机生成8位密码(包含大小写字母和数字)
	 * @return
	 * @see String
	 */
	public static String randomPassword() {
		return randomPassword(DEFAULT_PWD_LEN);
	}

	/**
	 * 随机生成指定位数密码(包含大小写字母和数字)
	 * @param length 指定长度
	 * @return
	 * @see String
	 */
	public static String randomPassword(int length) {
		if (length < SECTION.length) {
			return "";
		}
		List<String> result = new ArrayList<>();
		for (int i = 1; i < SECTION.length; i++) {
			result.add(E[getRandom(SECTION[i - 1], SECTION[i])]);
		}
		int len = length - result.size();
		int max = SECTION[SECTION.length - 1];
		Random random = new Random();
		while (len-- > 0) {
			result.add(E[random.nextInt(max)]);
		}
		Collections.shuffle(result);
		return result.stream().collect(Collectors.joining());
	}

	/**
	 * 生成随机整型数值
	 * @param min 最小值(小于0时为0, 大于最大值时为0)
	 * @param max 最大值(小于1时为1)
	 * @return
	 * @see int
	 */
	public static int getRandom(int min, int max) {
		if (max < 1) {
			max = 1;
		}
		if (min < 0 || min > max) {
			min = 0;
		}
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;

	}
}
