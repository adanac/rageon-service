package com.adanac.tool.rageon.utils.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;

/**
 * Excel 导出写入的工具类
 * 
 */
public class ExportExcelUtil<T> {

	private static final MyLogger log = MyLoggerFactory.getLogger(ExportExcelUtil.class);

	private String format;// 时间格式

	private String title;

	private String[] headers = {};

	private String[] objFields = {};

	private HSSFCellStyle titleStyle;

	private HSSFFont titleFont;

	private HSSFCellStyle dataStyle;

	private HSSFFont dataFont;

	private HSSFWorkbook workbook = new HSSFWorkbook();

	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public HSSFCellStyle getTitleStyle() {
		return titleStyle;
	}

	public void setTitleStyle(HSSFCellStyle titleStyle) {
		this.titleStyle = titleStyle;
	}

	public HSSFFont getTitleFont() {
		return titleFont;
	}

	public void setTitleFont(HSSFFont titleFont) {
		this.titleFont = titleFont;
	}

	public HSSFCellStyle getDataStyle() {
		return dataStyle;
	}

	public void setDataStyle(HSSFCellStyle dataStyle) {
		this.dataStyle = dataStyle;
	}

	public HSSFFont getDataFont() {
		return dataFont;
	}

	public void setDataFont(HSSFFont dataFont) {
		this.dataFont = dataFont;
	}

	public interface ExportTransIntface {
		public String transformValue(Object obj);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	public String[] getObjFields() {
		return objFields;
	}

	public void setObjFields(String[] objFields) {
		this.objFields = objFields;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void exportExcel(Collection<T> dataset, OutputStream out) {

		ExportExcelUtil.ExportTransIntface transIntf = new ExportExcelUtil.ExportTransIntface() {

			@Override
			public String transformValue(Object obj) {
				String textValue = null;
				Object value = obj;
				if (value instanceof Boolean) {
					boolean bValue = (Boolean) value;
					textValue = "是";
					if (!bValue) {
						textValue = "否";
					}
				} else if (value instanceof Date) {
					Date date = (Date) value;
					SimpleDateFormat sdf = new SimpleDateFormat(format);
					textValue = sdf.format(date);
				} else {
					// 其它数据类型都当作字符串简单处理
					textValue = value == null ? "" : value.toString();
				}
				return textValue;
			}
		};
		exportExcel(title, headers, objFields, dataset, out, format, transIntf);
	}

	public void exportExcel(Collection<T> dataset, OutputStream out, ExportTransIntface transIntf) {
		exportExcel(title, headers, objFields, dataset, out, format, transIntf);
	}

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public void exportExcel(String title, String[] headers, String[] objFields, Collection<T> dataset, OutputStream out,
			String pattern, ExportTransIntface transIntf) {

		// 声明一个工作薄
		// HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为30个字节
		// sheet.setColumnWidth(0, 300 * 256);
		sheet.autoSizeColumn((short) 0); // 调整第一列宽度
		sheet.autoSizeColumn((short) 1); // 调整第二列宽度
		sheet.autoSizeColumn((short) 2); // 调整第三列宽度
		sheet.autoSizeColumn((short) 3); // 调整第四列宽度
		sheet.autoSizeColumn((short) 4); // 调整第五列宽度
		sheet.autoSizeColumn((short) 5); // 调整第六列宽度
		sheet.autoSizeColumn((short) 6); // 调整第七列宽度
		sheet.autoSizeColumn((short) 8); // 调整第八列宽度
		sheet.autoSizeColumn((short) 9); // 调整第九列宽度
		sheet.autoSizeColumn((short) 10); // 调整第十列宽度

		// 标题样式
		if (null != titleStyle && titleFont != null) {
			titleStyle.setFont(titleFont);
		}
		// 数据样式
		if (null != dataStyle && dataFont != null) {
			dataStyle.setFont(dataFont);
		}
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		// HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
		// 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		// comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		// comment.setAuthor("leno");

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			// cell.setCellStyle(style);
			// 标题行样式设置
			if (null != titleStyle) {
				cell.setCellStyle(titleStyle);
			}
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++) {

				Field field = fields[i];
				String fieldName = field.getName();

				// 判读该属性是否需要被导出
				if (objFields != null && StringUtils.join(objFields, ",").indexOf(fieldName) == -1) {
					continue;
				}

				int indexCell = 0;
				for (int j = 0; j < objFields.length; j++) {
					if (objFields[j] == fieldName) {
						indexCell = j;
					}
				}
				HSSFCell cell = row.createCell(indexCell);
				// 数据行样式设置
				if (null != dataStyle) {
					cell.setCellStyle(dataStyle);
				}
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = transIntf.transformValue(value);

					HSSFRichTextString richString = new HSSFRichTextString(textValue);
					// HSSFFont font3 = workbook.createFont();
					// font3.setColor(HSSFColor.BLUE.index);
					// richString.applyFont(font3);
					sheet.autoSizeColumn(indexCell);
					cell.setCellValue(richString);
				} catch (Exception e) {
					log.error("excel 文件导出失败", e);
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}

		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			log.error("excel 文件导出失败", e);
			e.printStackTrace();
		}

	}

}