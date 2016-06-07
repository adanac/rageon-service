package com.adanac.tool.rageon.service.file.dom4j;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import com.adanac.tool.rageon.intf.file.dom4j.Pollution;
import com.adanac.tool.rageon.intf.file.dom4j.WeatherInfo;
import com.adanac.tool.rageon.intf.file.dom4j.Zhishu;

/**@author find
 * 解析获取的xml，提取出里面的信息
 * */
public class ResolveXML {

	static final int MAXLAYER = 100;

	InputSource in;
	SAXReader reader;
	Document doc;
	Element root, tempElement;
	WeatherInfo weather;

	/**内层元素遍历*/
	@SuppressWarnings("unchecked")
	public ResolveXML(String filename, WeatherInfo weather) {
		// initialize
		in = new InputSource(filename);
		reader = new SAXReader();
		reader.setEncoding("utf-8");
		this.weather = weather;

		try {
			doc = reader.read(in);
			// 根
			root = doc.getRootElement();
			System.out.println(root.getName());
			// 第一层
			Iterator<Element> item1 = root.elementIterator();
			int i = 0;
			while (item1.hasNext()) {
				tempElement = (Element) item1.next();
				// 此部分内容请参考dev.md，xml文档的各项参数说明
				switch (i) {
				case 0:
					weather.setCity(tempElement.getText());
					break;
				case 1:
					weather.setUpdateTime(tempElement.getText());
					break;
				case 2:
					weather.setTemperature(Integer.parseInt(tempElement.getText()));
					break;
				case 3:
					weather.setWindForce(tempElement.getText());
					break;
				case 4:
					weather.setHumidity(
							Integer.parseInt(tempElement.getText().substring(0, tempElement.getText().length() - 1)));
					break;
				case 5:
					weather.setWindDirection(tempElement.getText());
					break;
				// 这里的warning不太懂。不知道如何解决
				case 10:
					setEnvironment(tempElement.elementIterator(), weather);
					break;
				case 12:
					setForecast(tempElement.elementIterator(), weather);
					break;
				case 13:
					setZhishus(tempElement.elementIterator(), weather);
					break;
				default:
					break;
				}
				// System.out.println("The layer1's
				// son:"+tempElement.getName()+"\t num:"+i);
				i++;
			}
		} catch (DocumentException e) {
			System.out.println("Here's error in reading xml.");
			e.printStackTrace();
		}
	}

	/**处理environment这个分支*/
	public void setEnvironment(Iterator<Element> tempit, WeatherInfo tempweather) {
		Element tempelement;
		Pollution tempPolllution = new Pollution();
		int i = 0;
		while (tempit.hasNext()) {
			tempelement = (Element) tempit.next();
			switch (i) {
			case 0:
				tempweather.setQualityNum(Integer.parseInt(tempelement.getText()));
				break;
			case 1:
				tempweather.setPM2_5(Integer.parseInt(tempelement.getText()));
				// System.out.println(tempelement.getText());
				break;
			case 2:
				tempweather.setSuggest(tempelement.getText());
				break;
			case 3:
				tempweather.setQuality(tempelement.getText());
				break;
			case 4:
				tempPolllution.setMajorPollutants(tempelement.getText());
				break;
			case 5:
				tempPolllution.setO3(Integer.parseInt(tempelement.getText()));
				break;
			case 6:
				tempPolllution.setCO(Integer.parseInt(tempelement.getText()));
				break;
			case 7:
				tempPolllution.setPM10(Integer.parseInt(tempelement.getText()));
				break;
			case 8:
				tempPolllution.setSO2(Integer.parseInt(tempelement.getText()));
				break;
			case 9:
				tempPolllution.setNO2(Integer.parseInt(tempelement.getText()));
				break;
			// case 10:tempweather.setUpdateTime(tempelement.getText());break;
			default:
				break;
			}
			i++;
		}
		tempweather.setPollution(tempPolllution);
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public void setForecast(Iterator<Element> tempit, WeatherInfo tempweather) {
		Element tempelementRoot, tempElement;
		/**tempit2: 每一个weather块<br>tempit3：day块和night块*/
		Iterator<Element> tempit2, tempit3;
		String temp;
		int i = 0, j;
		while (tempit.hasNext()) {
			tempelementRoot = (Element) tempit.next();
			tempit2 = tempelementRoot.elementIterator();
			j = 0;
			while (tempit2.hasNext()) {
				tempElement = tempit2.next();
				temp = tempElement.getText();
				switch (j) {
				case 0:
					tempweather.setForecastDate(i, temp);
					break;
				case 1:
					tempweather.setForecastHign(i,
							Integer.parseInt(temp.substring(temp.indexOf(' ') + 1, temp.indexOf("℃"))));
					break;
				case 2:
					tempweather.setForecastLow(i,
							Integer.parseInt(temp.substring(temp.indexOf(' ') + 1, temp.indexOf("℃"))));
					break;
				case 3:
					setForecastDayAndNight(i, tempElement.elementIterator(), tempweather, 0);
					break;
				case 4:
					setForecastDayAndNight(i, tempElement.elementIterator(), tempweather, 1);
					break;

				}
				j++;
			}

			i++;
		}
	}

	/**@param isNight 0:不是night，是day 1：Night*/
	public void setForecastDayAndNight(int aftertoday, Iterator<Element> tempit, WeatherInfo tempweather, int isNight) {
		Element tempElement;
		int i = 0;
		while (tempit.hasNext()) {
			tempElement = tempit.next();
			switch (i) {
			case 0:
				tempweather.setForecasttype(aftertoday, tempElement.getText(), isNight);
				break;
			case 1:
				tempweather.setForecastWindDirection(aftertoday, tempElement.getText(), isNight);
				break;
			case 2:
				tempweather.setForecastWindForce(aftertoday, tempElement.getText(), isNight);
				break;
			default:
				break;
			}
			i++;
		}
	}

	/**各项指数*/
	@SuppressWarnings("unchecked")
	public void setZhishus(Iterator<Element> tempit, WeatherInfo tempweather) {
		Element tempElement, tempElement2;
		Iterator<Element> tempit2;
		Zhishu zhishu = new Zhishu();
		int i = 0, j;
		while (tempit.hasNext()) {
			tempElement = tempit.next();
			tempit2 = tempElement.elementIterator();
			j = 0;
			while (tempit2.hasNext()) {
				tempElement2 = tempit2.next();
				switch (j) {
				case 0:
					zhishu.setName(tempElement2.getText());
					break;
				case 1:
					zhishu.setValue(tempElement2.getText());
					break;
				case 2:
					zhishu.setDetail(tempElement2.getText());
					break;
				default:
					break;
				}
				j++;
			}
			tempweather.setZhishus(i, zhishu);
			i++;
		}
	}
}
