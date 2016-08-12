package com.adanac.tool.rageon.utils.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;

import com.adanac.framework.exception.BizzException;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.utils.StringUtils;
import com.adanac.tool.rageon.constant.RageonConstant;
import com.adanac.tool.rageon.sfunc.entity.TradeDto;
import com.adanac.tool.rageon.utils.RageUtil;

public class ExcelUtil {

	private static final MyLogger log = MyLoggerFactory.getLogger(ExcelUtil.class);

	private final static DecimalFormat df = new DecimalFormat("0");

	/**
	 * 根据文件路径和行数读取文件内容，并返回Map集合对象
	 * @param filePath文件路径
	 * @param cellNumber列数
	 * @param rowNumber从哪一行开始读取
	 * @return 
	 */
	public List<Map<Integer, Object>> readObjFromXlsx(String filePath, int cellNumber, int rowNumber) {

		InputStream is = null;
		HSSFWorkbook hssfWorkbook = null;
		List<Map<Integer, Object>> listMap = new ArrayList<Map<Integer, Object>>();
		try {
			is = new FileInputStream(filePath);
			hssfWorkbook = new HSSFWorkbook(is);
			log.info("默认读取第一个工作表");
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);// 默认取第一个模板
			if (null == hssfSheet) {// 如果sheet为null则模板不匹配
				log.info("导入模板与所选的导入方案不匹配");
				throw new BizzException("导入模板与所选的导入方案不匹配");
			}

			log.info("读取行");
			Map<Integer, Object> map = null;
			// 获取最后一行工号和真实姓名都不为空的行数
			int effectiveRowNum = hssfSheet.getLastRowNum();
			for (int i = hssfSheet.getLastRowNum(); i > 0; i--) {
				HSSFRow hssfRow = hssfSheet.getRow(i);
				if (hssfRow == null) {
					continue;
				}
				HSSFCell cell = hssfRow.getCell(0);
				if (cell != null && !"".equals(getXlsValue(cell).trim())) {
					// 获得最后一行的非空行
					effectiveRowNum = i;
					break;
				}
			}
			// System.out.println("去空后的EXCEL有效行数:"+effectiveRowNum);
			for (int rowNum = rowNumber; rowNum <= effectiveRowNum; rowNum++) {
				// for (int rowNum = rowNumber; rowNum <=
				// hssfSheet.getLastRowNum(); rowNum++) {
				map = new HashMap<Integer, Object>();
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				for (int cellNum = 0; cellNum <= cellNumber; cellNum++) {
					log.info("读取第[" + rowNum + "]行,第[" + cellNum + "]列数据");
					HSSFCell cell = hssfRow.getCell(cellNum);
					if (cell == null) {
						map.put(cellNum, ""); // 存放第多少列的值
						continue;
					}
					// 具体读取单元格的值
					String value = this.getXlsValue(cell); // 当前行的第(cellNum+1)个单元格里面的值
					if (!StringUtils.isEmpty(value) && value.endsWith(".0")) {
						// 判断是否以.0结尾
						value = value.substring(0, value.lastIndexOf(".0"));
					}
					map.put(cellNum, value); // 存放第多少列的值
					log.info("正在读取第[" + rowNum + "]行数据，值[" + value + "]");
				}
				listMap.add(map);
			}
		} catch (Exception e) {
			log.error("excel 文件读取失败", e);
			throw new BizzException("excel 文件读取失败");
		} finally {
			try {
				hssfWorkbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return listMap;
	}

	/**
	 * 获取单元格里面的值
	 * 
	 * @param hssfCell
	 * @return
	 */
	private String getXlsValue(HSSFCell xssfCell) {
		String value = "";
		switch (xssfCell.getCellType()) {
		case Cell.CELL_TYPE_STRING:// excel数据是字符串类型
			value = xssfCell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA:// excel数据是公式生成的
			value = xssfCell.getCellFormula();
			break;
		case Cell.CELL_TYPE_NUMERIC:// excel数据是数字类型
			value = xssfCell.getNumericCellValue() + "";
			break;
		case Cell.CELL_TYPE_BLANK:// excel数据是空值
			value = "";
			break;
		case Cell.CELL_TYPE_ERROR:// excel数据类型读取出错
			value = "";
			break;
		}
		return value;
	}

	/**
	 * 设置导出excel导出样式
	 */
	public static HSSFCellStyle excelStyle(HSSFWorkbook workbook, int row) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 标题样式
		if (row == RageonConstant.NUM_0) {
			style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
			return style;
		}
		// 双行样式
		if (row % RageonConstant.NUM_2 == RageonConstant.NUM_0) {
			style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		} else {
			style.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		}
		return style;
	}

	/**
	 * 导出没列的值
	 */
	public static String cellValue(int cellNumber, TradeDto dto) {
		String cellValue = "";
		switch (cellNumber) {
		case 0: {
			cellValue = dto.getId().toString();
			break;
		}
		case 1: {
			cellValue = dto.getCompanyCode();
			break;
		}
		case 2: {
			cellValue = dto.getCompanyName();
			break;
		}
		case 3: {
			cellValue = dto.getPubnumId().toString();
			break;
		}
		case 4: {
			cellValue = dto.getOccurDate();
			break;
		}
		case 5: {
			cellValue = intercept(dto.getPayCount().toString());
			break;
		}
		case 6: {
			cellValue = RageUtil.div(dto.getPayMoney().doubleValue(), 100d, 2).toString();
			break;
		}
		case 7: {
			cellValue = dto.getUploadDate();
			break;
		}
		}
		return cellValue;
	}

	public static String formatScientific(double val) {
		return df.format(val);
	}

	public static String intercept(String val) {
		String str = val;
		String[] strs = str.split("[.]");
		return strs[0];
	}
}
