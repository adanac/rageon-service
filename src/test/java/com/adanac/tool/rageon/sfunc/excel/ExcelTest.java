package com.adanac.tool.rageon.sfunc.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelTest {
	private static final int CONSTANT_NUM = 10;

	/**
	 * 导出失败卡号
	 */
	public static Map<String, Object> exportExcel(List<String> cardList) {
		Map<String, Object> resultMap = new HashMap<String, Object>();// 返回
		// 创建一个工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 新建工作表
		XSSFSheet sheet = workbook.createSheet("失败信息");
		// 设置样式
		XSSFCellStyle style = workbook.createCellStyle();
		// 居中显示
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		if (cardList != null && cardList.size() > 0) {
			// 创建excel数据
			for (int i = -1; i < cardList.size(); i++) {
				if (i == -1) {
					// 创建行
					XSSFRow row = sheet.createRow(0);
					// 新建一列
					XSSFCell cell = row.createCell(0);
					// 定义单元格为字符串类型
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(style);
					cell.setCellValue("卡号");
				} else {
					// 列宽
					sheet.setColumnWidth(i, 8000);
					// 创建行
					XSSFRow row = sheet.createRow((short) i + 1);
					// 新建一列
					XSSFCell cell = row.createCell(0);
					// 定义单元格为字符串类型
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(style);
					cell.setCellValue(cardList.get(i));
				}
			}
			resultMap = FileUtil.createExcel(workbook, "", "");

		}
		return resultMap;

	}

	@SuppressWarnings("null")
	public static void main(String[] args) {
		List<String> cardList = new ArrayList<String>();
		for (int i = 0; i < CONSTANT_NUM; i++) {
			cardList.add(i + "-0000000" + i);
		}
		Map<String, Object> map = exportExcel(cardList);
		System.out.println(map.size());
	}
}
