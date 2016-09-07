package com.adanac.tool.rageon.service.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.tool.rageon.common.entity.CommonDto;
import com.adanac.tool.rageon.common.service.CommonService;
import com.adanac.tool.rageon.common.service.ExportService;

/**
 * 导入service接口的实现类
 * 
 */
@Service("exportService")
public class ExportServiceImpl implements ExportService {
	@Autowired
	CommonService commonService;

	// 日志
	private MyLogger log = MyLoggerFactory.getLogger(ExportServiceImpl.class);

	@Override
	public String exportFile(String basePath, List<CommonDto> list) {
		String path = "";
		// 读取配置文件，判断导出的文件格式，是excel还是cvs
		// String exportType = AppConstants.getProperties("export_type");
		String exportType = "excel";
		if ("excel".equals(exportType)) {
			path = this.wirteExcel(basePath, list);
		} else {
			path = this.wirteCvs(basePath, list);
		}
		return path;
	}

	private String wirteExcel(String basePath, List<CommonDto> list) {
		String filePath = "";

		try {
			CommonDto commonDto = new CommonDto();
			commonDto.setSex(0);
			// 根据所属参数查询该参数下的所有字段
			List<CommonDto> baseList = commonService.queryCommonDtoList(commonDto);
			// 把主键字段排序放在前面

			String filename = basePath + "excelTemplate" + File.separator + "excelTemp.xls";
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			// 按名引用excel工作表
			// HSSFSheet sheet = workbook.getSheet("JSP");
			// 也可以用以下方式来获取excel的工作表，采用工作表的索引值
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow firstRow = sheet.getRow(0); // 取第一行
			// 循环向第一行添加表头
			for (int icount = 0; icount < baseList.size(); icount++) {
				HSSFCell cell = firstRow.createCell(icount); // 第一行的第icount列
				cell.setCellValue(baseList.get(icount) + "[" + baseList.get(icount) + "]"); // 向单元格里写值
			}

			for (int i = 0; i < list.size(); i++) {
				CommonDto map = list.get(i);
				HSSFRow row = sheet.createRow(i + 1); // 从第二行开始，创建行

				for (int icount = 0; icount < baseList.size(); icount++) {
					// HSSFCell titleCell = firstRow.getCell(icount); //
					// 获取第一行的第icount列
					HSSFCell valueCell = row.createCell(icount); // 创建第i+1行的第icount列
					valueCell.setCellValue(map.getId()); // 向第i+1行的第icount列里写值
				}
			}

			// 新建一输出流
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeStr = sdf.format(new Date());

			basePath = basePath + "exportFile" + File.separator;
			// 如果文件夹不存在则创建
			File file = new File(basePath);
			if (!file.exists()) {
				file.mkdirs();
			}

			// 判断所属参数，生成对应的文件名

			filePath = basePath + "_" + timeStr + ".xls";
			FileOutputStream fout = new FileOutputStream(filePath); // tempPath是另存为的路径
			// 存盘
			workbook.write(fout);
			workbook.close();
			fout.flush();
			// 结束关闭
			fout.close();
		} catch (Exception e) {
			log.error("ExportServiceImpl==>wirteExcel", e);
		}
		return filePath;
	}

	private String wirteCvs(String basePath, List<CommonDto> list) {
		String filePath = "";

		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {

			CommonDto commonDto = new CommonDto();
			commonDto.setSex(0);
			// 根据所属参数查询该参数下的所有字段
			List<CommonDto> baseList = commonService.queryCommonDtoList(commonDto);

			String fileName = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeStr = sdf.format(new Date());
			basePath = basePath + "exportFile" + File.separator;
			// 如果文件夹不存在则创建
			File file = new File(basePath);
			if (!file.exists()) {
				file.mkdirs();
			}

			// 判断所属参数，生成对应的文件名

			fileName = fileName + "_" + timeStr;
			filePath = basePath + fileName + ".csv";

			// 定义文件名格式并创建
			// csvFile = File.createTempFile(fileName, ".csv",new
			// File(basePath));

			csvFile = new File(filePath);
			csvFile.createNewFile();

			filePath = csvFile.getPath();
			// UTF-8使正确读取分隔符","
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"),
					1024);
			// 写入文件头部
			for (int icount = 0; icount < baseList.size(); icount++) {
				csvFileOutputStream.write(baseList.get(icount) + "[" + baseList.get(icount) + "]" + ",");
			}
			csvFileOutputStream.newLine();
			// 写入文件内容
			for (int i = 0; i < list.size(); i++) {
				CommonDto map = list.get(i);

				for (int icount = 0; icount < baseList.size(); icount++) {
					String val = map.getId();
					csvFileOutputStream.write(val);
				}
				csvFileOutputStream.newLine();
			}

			csvFileOutputStream.flush();
		} catch (Exception e) {
			log.error("ExportServiceImpl==>wirteCvs", e);
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				log.error("ExportServiceImpl==>wirteCvs", e);
			}
		}
		return filePath;
	}

}
