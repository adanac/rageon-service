package com.adanac.tool.rageon.service.file.dom4j;

import com.adanac.tool.rageon.file.dom4j.WeatherInfo;

public class MakeMessage {
	WeatherInfo weather;

	public MakeMessage(WeatherInfo weather) {
		this.weather = weather;
	}

	public StringBuilder showMessage() {
		StringBuilder ans = new StringBuilder();
		ans.append("城市:" + weather.getCity() + "\n" + "当前温度：" + weather.getTemperature() + "℃\n" + "风向："
				+ weather.getWindDirection() + "  风力：" + weather.getWindForce() + "\n" + weather.getDate() + " "
				+ weather.getWeek() + "\n");
		ans.append("明天：\n白天:" + weather.getForecasttype(1, 0) + "\n" + "夜间:" + weather.getForecasttype(1, 1) + "\n"
				+ "温度:" + weather.getForecastLow(1) + "℃~" + weather.getForecastHigh(1) + "℃\n");
		return ans;
	}
}
