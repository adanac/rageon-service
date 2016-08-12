package com.adanac.tool.rageon.sfunc.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileUtil {

	/**
	 * 生成excle文件
	 * @author 陈荣祥
	 */
	public static Map<String, Object> createExcel(XSSFWorkbook workbook, String filePath, String fileName) {
		try {
			// 项目根目录
			// HttpServletRequest request = ((ServletRequestAttributes)
			// RequestContextHolder.getRequestAttributes())
			// .getRequest();
			// String basePath =
			// request.getSession().getServletContext().getRealPath("/");
			File directory = new File("");// 参数为空
			String basePath = directory.getCanonicalPath() + "\\";
			if ("".equals(filePath)) {
				filePath = Constants.ExcleFile.FILEPATH;
			}
			if ("".equals(fileName)) {
				fileName = TimeUtil.getDateFormat("yyyyMMddHHmmss", "") + ".xlxs";
			}
			// 将文件存于
			File file = new File(basePath + filePath);
			// 判断路径是否存在
			if (!file.exists()) {
				file.mkdir();
			}
			filePath = basePath + filePath + fileName;
			FileOutputStream fout = new FileOutputStream(filePath);
			workbook.write(fout);

			// 关闭
			fout.flush();
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("filePath", filePath);
		resultmap.put("fileName", fileName);
		return resultmap;
	}

}
