
package com.adanac.tool.rageon.sfunc.excel;

/**
* 常量类
* @Title: Constants.java 
* @Package com.bn.b2c.CRM.util.stored 
* @Description: TODO(公共常量类) 
* @author KevinWu   
* @date 2015年7月2日 下午2:50:35 
* @version V1.0
 */
public class Constants {

	/**
	 * 调用接口 成功
	 */
	public static final String SUCESS = "0";

	/**
	 * 调用接口 失败
	 */
	public static final String FAILD = "1";

	/**
	 *查询需要打印的券 存入redisKey前缀   voucherCard_卡记账号 
	 */
	public static final String VOUCHER_CARD = "voucherCard_";

	/**
	 *券信息存入redisKey前缀   voucher_券号
	 */
	public static final String VOUCHER = "voucher_";

	/**
	 *  查询会员及卡号-01手机key   cardMobile_手机号
	 */
	public static final String MEM_CARD_MOBILE = "mem_card_mobile_";

	/**
	 * 查询会员及卡号-02卡号key   memberCardCode_卡号
	 */
	public static final String MEM_CARD_CODE = "mem_card_code_";

	/**
	 * 查询会员及卡号-03刷卡key   cardInNum_卡内号
	 */
	public static final String MEM_CARD_IN_NUM = "mem_card_in_num_";
	/**
	 * 查询会员及卡号-04身份证号key   cardIDCard_身份证号
	 */
	public static final String MEM_CARD_IDENTITY = "mem_card_identity_";

	/**
	 * 查询会员及卡号-05电话号码key    telPhone_电话号码
	 */
	public static final String MEM_CARD_TEL = "mem_card_tel_";

	/**
	 * 查询会员及卡号-05电话号码key    accountsCode_卡记账号
	 */
	public static final String MEM_CARD_ACCOUNTSCODE = "mem_card_accountsCode_";

	/**
	 * 查询会员及卡信息   CardCode_卡号
	 */
	public static final String MEM_CARD_INFO_CODE = "mem_card_info_code_";

	/**
	 * 会员关联券汇总查询  voucherCardId_会员账号ID
	 */
	public static final String VOUCHER_CARD_ID = "voucherCardId_";

	/**
	 * 维护卡月折扣表存入Redis对象Key  CardAgios_当前年月_记账号
	 */
	public static final String CARD_AGIOS = "card_Agios_";

	/**
	 * 券支付 redis异步调用LIST key
	 */
	public static final String VOUCHER_WATER_LIST = "voucherWaterList";

	/**
	 * 维护卡月折扣表 redis异步调用LIST key
	 */
	public static final String CARD_AGIOS_LIST = "cardAgiosList";

	/**
	 * 更新券打印次数 Redis异步调用LIST key
	 */
	public static final String VOUCHER_COUPON_LIST = "voucherCouponList";

	/**
	 * 积分返券 Redis单据大key
	 */
	public static final String INRETURNVOUCHERBILL = "inReturnVoucherBill";

	/**
	 * APP券生成成功 单据存入  Redis 大key
	 */
	public static final String VOUCHER_APP_BILL = "AppBill";

	/**
	 * 券信息存入redis失败Key前缀
	 */
	public static final String VOUCHER_EXCEPTION = "voucher_exception_list";

	/**
	 * APP券生成 单据  Redis失败LIST key 
	 */
	public static final String VOUCHER_APP_BILL_LIST_EXCEPTION = "AppBillList_Exce";

	/**
	 * 积分返券单据失败Redis LIST key 
	 */
	public static final String VOUCHER_BILL_LIST_EXCEPTION = "voucherBillList_exception";

	/**
	 * 积分返券的卡Redis 前缀
	 */
	public static final String CARD_VOUCHER = "cardVoucher_";

	/**
	 * 券返积分失败Redis LIST key 
	 */
	public static final String VOUCHER_INTE_LIST_EXCEPTION = "voucherInteList_exception";

	/**
	 * 满足券返积分的数据 redis List key前缀
	 */
	public static final String VOUCHERRETURNINTEGRAL_LIST = "voucherReturnIntegralList_";

	/**
	 * 会员个性券需要生成的券redis key
	 */
	public static final String VOUCHER_MEMGRAN = "voucher_memgran";

	/**
	 * 用户表的sqlMap命名空间
	 */
	public static final String NAMESPACE_USER = "user.";

	/**
	 * 登陆用户信息放入session
	 */
	public static final String SESSION_LOGINUSER = "loginuser";

	/**
	 * redis key 上次登录时间
	 */
	public static final String REDISKEY_LOGIN_TIME_BEFORE = "loginTime_before:{0}";

	/**
	 * redis key 本次登录时间
	 */
	public static final String REDISKEY_LOGIN_TIME_THIS = "loginTime_This:{0}";

	/**
	 * 会员表的sqlMap命名空间
	 */
	public static final String NAMESPACE_MEMBER = "member.";

	/**
	 * 表名前缀
	 */
	public static final String TABLENAME_MP_TB = "MP_TB";

	/**
	 * 卡蜂窝
	 */
	public static final String NODECODE = "NODECODE_";

	/**
	 * 卡蜂窝节点---
	 */
	public static final String KEY_CARDNESTNODE = "KEY_CARDNESTNODE_";

	/**
	 * 卡对应部门
	 */
	public static final String KEY_CARDTONODE = "KEY_CARDTONODE_";

	/**
	 * 卡蜂窝信息的大KEY值
	 */
	public static final String KEY_MEM_NODE = "KEY_MEM_NODE";

	/**
	 * 维护卡月折扣表大KEY值
	 */
	public static final String KEY_MAIN_DIST = "KEY_MAIN_DIST";

	/**
	 * 打印券信息大KEY值
	 */
	public static final String KEY_PRINT_TICKET = "KEY_PRINT_TICKET";

	/**
	 *微信会员信息大KEY值--手机
	 */
	public static final String KEY_WX_MEM_MOBILE = "KEY_WX_MEM_MOBILE";

	/**
	 * 微信卡信息大KEY值--卡号
	 */
	public static final String KEY_WX_CARD_CARDCODE = "KEY_WX_CARD_CARDCODE";
	/**
	 * 会员及卡号信息 大KEY值
	 */
	public static final String KEY_MEM_CARD_INFO = "KEY_MEM_CARD_INFO";

	/**
	 * 微信
	* @Title: Constants.java 
	* @Package com.bn.b2c.CRM.util.stored 
	* @Description: TODO() 
	* @author KevinWu   
	* @date 2015年8月11日 下午1:18:26 
	* @version V1.0
	 */
	public static class weiXin {
		/**
		 * 微信查询会员信息  手机号
		 */
		public static final String WEIXIN_MEMBER_MOBILE = "weixinMemberMobile_";

		/**
		 * 微信查询会员信息 会员编码
		 */
		public static final String WEIXIN_MEMBER_CARDCODE = "weixinMemberCardCode_";

		/**
		 * 微信查询卡信息 卡号
		 */
		public static final String WEIXIN_CARD_CARDCODE = "weixinCardCardCode_";

	}

	/**
	 * Int类型常用字符串
	* @Title: Constants.java 
	* @Package com.bn.b2c.CRM.util.stored 
	* @Description: TODO() 
	* @author KevinWu   
	* @date 2015年7月2日 下午4:26:35 
	* @version V1.0
	 */
	public static class intNumbers {
		/**
		 * APP类型-1常规
		 */
		public static final int NUM = -1;

		public static final int NUM_0 = 0;

		public static final int NUM_1 = 1;

		public static final int NUM_2 = 2;

		public static final int NUM_3 = 3;

		public static final int NUM_4 = 4;

		public static final int NUM_5 = 5;

		public static final int NUM_6 = 6;

		public static final int NUM_7 = 7;

		public static final int NUM_8 = 8;

		public static final int NUM_9 = 9;

		/**
		 * 生成券线程数
		 */
		public static final int NUM_10 = 10;

	}

	/**
	 * String类型常用字符串
	* @Title: Constants.java 
	* @Package com.bn.b2c.CRM.util.stored 
	* @Description: TODO() 
	* @author KevinWu   
	* @date 2015年7月2日 下午4:26:35 
	* @version V1.0
	 */
	public static class strNumers {

		public static final String NUM_0 = "0";

		public static final String NUM_1 = "1";

		public static final String NUM_2 = "2";

		public static final String NUM_3 = "3";

		public static final String NUM_4 = "4";

		public static final String NUM_5 = "5";

		public static final String NUM_6 = "6";

		public static final String NUM_7 = "7";

		public static final String NUM_8 = "8";

		public static final String NUM_9 = "9";

	}

	/**
	 * 
	 * @description 券信息常量
	 * @author xuneng
	 * @date 2015-07-01
	 */
	public static class Voucher {

		/**
		 * 最小的中心交易流水号
		 */
		public static final String MIN_CENTERWATERNO = "000000";

		/**
		 * 券信息的校验字符串后缀
		 */
		public static final String VOUCHER_CHECKCODE_SUFFIX = "p89cj3edkgzs4uioHUhJwaEW7ZQRX*0K";

		/**
		 * sqlMap命名空间
		 */
		public static final String NAMESPACE_VOUCHER = "voucher.";
	}

	/**
	 * 文件常量
	 */
	public static class ExcleFile {
		/**
		 * 模板路径
		 */
		public static final String EXCEL_FILEPATH = "excelFile/卡号模板.xlsx";

		/**
		 * 文件名称
		 */
		public static final String EXCEL_FILENAME = "卡号模板.xlsx";

		/**
		 * 导出文件名称
		 */
		public static final String EXCEL_EXPORT = "FailedCard.xlsx";

		/**
		 * 文件存放路径
		 */
		public static final String FILEPATH = "excelFile\\";
	}

	/**
	 * 线程常量
	 */
	public static class threadConstants {
		/**
		 * 分页查询记录数
		 */
		public static final int PAGE_SIZE = 1000;

	}
}
