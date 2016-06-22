package com.adanac.tool.rageon.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

	/**
	 * 
	 * @param source
	 * @param dest
	 */
	public static void write2File(String content, String dest) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(dest));
			bw.write(content);// 把内容写入目标文件
			bw.newLine();// 换行
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void read4File(String source) {
		BufferedReader br;
		String line;
		try {
			br = new BufferedReader(new FileReader(source));
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
