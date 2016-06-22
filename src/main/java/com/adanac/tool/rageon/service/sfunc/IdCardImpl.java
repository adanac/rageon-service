package com.adanac.tool.rageon.service.sfunc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.adanac.tool.rageon.intf.sfunc.IdCard;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 查询身份信息
 */
@Service
public class IdCardImpl implements IdCard {

	@Value("${baiDU_apiKey}")
	private String apiKey;

	/**
	 * 将查询结果写入到文件中
	 * @param src
	 * @param dest
	 * @param flag
	 * @throws IOException
	 */
	public void rwTxtFile(String src, String dest, Integer flag) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(src));
		BufferedWriter bw = new BufferedWriter(new FileWriter(dest));
		String line;
		while ((line = br.readLine()) != null) {
			if (flag == 0) {// 根据name和cardno查询
				String[] split = line.split("-");
				String name = split[0];
				String cardno = split[1];
				JSONObject jsonObj = queryByNameAndCardno(name, cardno);
				String content = jsonObj.toJSONString();
				bw.write(line + "--" + content);// 把原有内容和查询结果写入目标文件
			} else if (flag == 1) {// 根据cardno查询
				String cardno = line.trim();
				JSONObject paramMap = queryByCardno(cardno);
				if (paramMap.getInteger("errNum") != -1) {

					JSONObject jsonObj = (JSONObject) paramMap.get("retData");
					bw.write("id:" + line.trim() + "," + "sex:" + jsonObj.getString("sex") + "," + "address:"
							+ jsonObj.getString("address") + "," + "birthday:" + jsonObj.getString("birthday") + ","
							+ "retMsg:" + paramMap.getString("retMsg"));// 把原有内容和查询结果写入目标文件
				} else {
					bw.write("id:" + line.trim() + "," + "retMsg:" + paramMap.getString("retMsg"));
				}
			}
			bw.newLine();
		}
		br.close();
		bw.close();
	}

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public String request(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", apiKey);
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject queryByCardno(String cardno) {
		String httpUrl = "http://apis.baidu.com/apistore/idservice/id";
		String httpArg = "id=" + cardno;
		String jsonResult = request(httpUrl, httpArg);
		System.out.println(jsonResult);
		JSONObject jsonObj = JSON.parseObject(jsonResult);
		return jsonObj;
	}

	@SuppressWarnings("deprecation")
	@Override
	public JSONObject queryByNameAndCardno(String name, String cardno) {
		String ename = URLEncoder.encode(name);
		String httpUrl = "http://apis.baidu.com/apix/idauth/idauth";
		String httpArg = "name=" + ename + "&cardno=" + cardno;
		String jsonResult = request(httpUrl, httpArg);
		System.out.println(jsonResult);
		JSONObject paramMap = JSON.parseObject(jsonResult);
		JSONObject jsonObj = (JSONObject) paramMap.get("data");
		return jsonObj;
	}

}
