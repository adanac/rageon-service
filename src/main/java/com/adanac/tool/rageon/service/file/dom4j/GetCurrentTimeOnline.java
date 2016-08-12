package com.adanac.tool.rageon.service.file.dom4j;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.adanac.tool.rageon.file.dom4j.WeatherInfo;

/**获取网络时间*/
public class GetCurrentTimeOnline {
	WeatherInfo weather;

	public GetCurrentTimeOnline(WeatherInfo weather) {
		this.weather = weather;
		URL url;
		try {
			url = new URL("http://www.bjtime.cn");
			// 生成连接对象
			URLConnection uc = url.openConnection();
			// 发出连接
			uc.connect();
			long time = uc.getDate();
			// System.out.println("long time:"+time);
			Date date = new Date(time);
			// System.out.println("date:"+date.toString());
			// System.out.println(new SimpleDateFormat("yyyy-MM-dd-E
			// hh-mm-ss-").format(date));
			weather.setWeek(new SimpleDateFormat("E").format(date));
			weather.setDate(new SimpleDateFormat("MM-dd").format(date));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
