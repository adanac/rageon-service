package com.adanac.tool.rageon.service.ip;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.utils.StringUtils;
import com.adanac.tool.rageon.ip.service.WeatherService;
import com.adanac.tool.rageon.utils.WeatherUtils;

@Service
public class WeatherServiceImpl implements WeatherService {
	// 日志
	private MyLogger log = MyLoggerFactory.getLogger(WeatherServiceImpl.class);

	@Value("${queryWeather.key}")
	private String secretKey;

	@Value("${queryWeather.url}")
	private String queryUrl;

	// public String getSecretKey() {
	// return secretKey;
	// }
	//
	// public String getQueryUrl() {
	// return queryUrl;
	// }

	@Override
	public Map<String, String> qrWeather(String city, String day) {
		log.info("====qrWeather====city:" + city + "====day:" + day);
		String link = queryUrl + "?password=" + secretKey;
		Map<String, String> map = new HashMap<>();
		try {
			if (!StringUtils.isEmpty(city)) {
				link += "&city=" + URLEncoder.encode(city, "GBK");
			} else {
				link += "&city=" + URLEncoder.encode("南京", "GBK");
			}
			if (!StringUtils.isEmpty(day)) {
				link += "&day=" + day;
			} else {
				link += "&day=" + 0;
			}
			map = WeatherUtils.queryWeather(link);
		} catch (Exception e) {
			log.error("qrWeather error:" + e.getMessage());
		}
		return map;
	}

}
