package com.adanac.tool.rageon.service.file.dom4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

public class DownloadXML {
	/**
	 * 获取xml文件
	 * @param weburl  输入的xml文件地址
	 * */
	StringBuffer buffer;

	public DownloadXML(String weburl, String savePath) {
		HttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(weburl);
		get.setHeader("Content-Type", "application/xml");
		HttpResponse response;
		try {
			response = client.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream is = entity.getContent();
				BufferedReader in = new BufferedReader(new InputStreamReader(is));
				// 获取文本
				buffer = new StringBuffer();
				String line = "";
				while ((line = in.readLine()) != null)
					buffer.append(line + "\n");
				// System.out.println(buffer);
				writeXMLFile(savePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**写获取的xml到文件，默认写到temp.xml
	 * @throws IOException */
	private void writeXMLFile(String savePath) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(savePath)));
		out.write(buffer.toString());
		out.close();

	}
}
