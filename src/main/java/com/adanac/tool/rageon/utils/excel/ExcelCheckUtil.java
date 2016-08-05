package com.adanac.tool.rageon.utils.excel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adanac.framework.utils.StringUtils;
import com.adanac.tool.rageon.constant.RageonConstant;

public class ExcelCheckUtil {

	static String reg_email = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	static String reg_date = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))$";
	// static String
	// reg_phone="1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}";
	static String reg_phone = "^[0-9\\-]{5,20}$";
	static String reg_card = "";

	/**
	 * 判断字符长度是否超长
	 * @param value
	 * @param size
	 * @return
	 */
	public static boolean isOverLong(String value, int size) {
		if (isEmpty(value)) {
			return false;
		}
		return value.length() > size;
	}

	/**
	 * 校验是否为空
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return StringUtils.isEmpty(value);
	}

	/**
	 * 校验邮箱
	 * @param value
	 * @return
	 */
	public static boolean chkEmail(String value) {
		return chk(value, reg_email);
	}

	/**
	 * 校验日期格式
	 * @param value
	 * @return
	 */
	public boolean chkDate(String value) {
		return chk(value, reg_date);
	}

	/**
	 * 校验手机号码格式
	 * @param value
	 * @return
	 */
	public static boolean chkPhone(String value) {
		return chk(value, reg_phone);
	}

	/**
	 * 校验手机号码格式
	 * @param value
	 * @return
	 */
	public boolean chkCard(String value) {
		return chk(value, reg_card);
	}

	/**
	 *  校验
	 * @param value
	 * @param reg
	 * @return
	 */
	public static boolean chk(String value, String reg) {
		if (StringUtils.isEmpty(value)) {
			return true;
		}
		return chkVal(reg, value);
	}

	/**
	 * 利用正则验证单元格里的值是否符合规范
	 * @param reg
	 * @param value
	 * @return
	 */
	public static boolean chkVal(String reg, String value) {
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(value);
		boolean flg = m.matches();
		return flg;
	}

	/**
	 * 校验状态值
	 * [0-使用中，1-已停用]
	 * @param value
	 * @return
	 */
	public static boolean chkStatus(String value) {
		if (RageonConstant.strNum.STR_0.equals(value)) {
			return true;
		}
		if (RageonConstant.strNum.STR_1.equals(value)) {
			return true;
		}

		return false;
	}

	/**
	 * 校验性别
	 * 0-管理员，1-店铺员工，2-财务人员，3-店铺老板，4-普通员工
	 * @param value
	 * @return
	 */
	public static boolean chkRole(String value) {
		if (null != value && value.length() == 1 && "01234".contains(value)) {
			return true;
		}
		return false;
	}

	/**
	 * 校验角色
	 * 1-男 0 女
	 * @param value
	 * @return
	 */
	public static boolean chkSex(String value) {
		if (RageonConstant.strNum.STR_0.equals(value)) {
			return true;
		}
		if (RageonConstant.strNum.STR_1.equals(value)) {
			return true;
		}
		return false;
	}

}
