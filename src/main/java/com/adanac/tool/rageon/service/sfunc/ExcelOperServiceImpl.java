package com.adanac.tool.rageon.service.sfunc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.utils.StringUtils;
import com.adanac.framework.web.controller.BaseResult;
import com.adanac.tool.rageon.constant.RageonConstant;
import com.adanac.tool.rageon.intf.common.entity.CommonDto;
import com.adanac.tool.rageon.intf.sfunc.intf.ExcelOperService;
import com.adanac.tool.rageon.utils.excel.ExcelCheckUtil;
import com.adanac.tool.rageon.utils.excel.ExcelUtil;
import com.alibaba.fastjson.JSONObject;

@Service("excelOperService")
public class ExcelOperServiceImpl implements ExcelOperService {

	private static final MyLogger logger = MyLoggerFactory.getLogger(ExcelOperServiceImpl.class);

	@Override
	public BaseResult readXlsNew(String filePath, String type, String userId) {

		BaseResult result = new BaseResult();
		try {
			logger.info("解析文件数据");
			// 解析文件数据 TODO
			List<Map<Integer, Object>> list = new ExcelUtil().readObjFromXlsx(filePath, 10, 3);
			if (list.isEmpty()) {
				logger.info("文件无内容");
				result.setStatus(RageonConstant.strNum.STR_2);
			} else if (list.size() > 500) {
				logger.info("excel有效数据超过500行");
				result.setStatus(RageonConstant.strNum.STR_6);
			} else {
				logger.info("验证数据格式是否正确");
				// TODO 校验excel
				BaseResult cheResult = this.checkExcelNew(list, userId);
				if (cheResult.getStatus().equals(BaseResult.SUCCESS)) {
					logger.info("验证通过，验证数据库工号是否有重复");
					result.setStatus(BaseResult.SUCCESS);
					result.setContent(cheResult.getContent());
					// BaseResult base =
					// userService.checkWorkNumber((List<EmployeeInfoDto>)cheResult.getContent());
					// if(base.getStatus().equals(BaseResult.SUCCESS)){
					// logger.info("验证通过");
					// result.setStatus(BaseResult.SUCCESS);
					// result.setContent((List<EmployeeInfoDto>)cheResult.getContent());
					// }else{
					// logger.info("验证不通过");
					// result.setStatus(RageonConstant.strNum.STR_3);
					// result.setContent(base.getContent());//失败信息
					// }
				} else {
					logger.info("验证不通过");
					result.setStatus(RageonConstant.strNum.STR_3);
					result.setContent(cheResult.getContent());// 失败信息
				}
			}
		} catch (Exception e) {
			logger.info("解析excel失败", e);
			result.setStatus(RageonConstant.strNum.STR_1);
		}
		return result;
	}

	/**
	 * 验证数据格式2.0
	 * @param List<Map<Integer, Object>> excelList
	 * @return status(1数据格式正确，0数据格式失败)
	 * @return content 验证通过返回数据List<UserInfo>
	 * @return content验证失败返回失败列表信息
	 */
	public BaseResult checkExcelNew(List<Map<Integer, Object>> excelList, String userId) {
		BaseResult result = new BaseResult();
		boolean b = true;// 验证是否通过
		List<CommonDto> userList = new ArrayList<CommonDto>();
		List<String> failList = new ArrayList<String>();// 失败信息
		logger.info("执行checkExcel 参数[" + JSONObject.toJSONString(excelList) + "]");

		if (!excelList.isEmpty()) {
			for (int i = 0; i < excelList.size(); i++) {
				CommonDto user = new CommonDto();
				logger.info("验证第[" + (i + 4) + "]行数据");
				Map<Integer, Object> rowMap = excelList.get(i);
				for (int j = 0; j < rowMap.size(); j++) {
					logger.info("第[" + (i + 4) + "]行，第[" + (j + 1) + "]列，参数[" + rowMap.get(j).toString() + "]");
					// 验证数据是否为空
					if (StringUtils.isEmpty(rowMap.get(j).toString())) {
						if (j == 0) {
							logger.info("第[" + (i + 4) + "]行,第[1]列工号为空");
							failList.add("第[" + (i + 4) + "]行第[1]列工号不能为空");
							b = false;
						}
						if (j == 1) {
							logger.info("第[" + (i + 4) + "]行,第[2]列真实姓名为空");
							failList.add("第[" + (i + 4) + "]行第[2]列真实姓名不能为空");
							b = false;
						}

						if (j == 2) {
							logger.info("第[" + (i + 4) + "]行,第[3]列性别为空");
							user.setSex(RageonConstant.NUM_0);// 默认为男
						}

						if (j == 8) {
							logger.info("第[" + (i + 4) + "]行,第[9]列E_Mail为空");
							failList.add("第[" + (i + 4) + "]行第[9]列E_Mail不能为空");
							b = false;
						}
					}

					// 验证数据长度已格式
					if (j == 0) {
						logger.info("第[" + (i + 4) + "]行,第[1]列工号是否重复");
						for (int m = 0; m < excelList.size(); m++) {
							Map<Integer, Object> queryName = excelList.get(m);
							if (!queryName.isEmpty() && m != i) {
								String oneValue = rowMap.get(0).toString();
								String twoValue = queryName.get(0).toString();
								if (oneValue.equals(twoValue)) {
									// 判断是否有重复提示信息
									logger.info("第[" + (i + 4) + "]行第[1]列和第[" + (m + 4) + "]行第[1]列工号重复");
									failList.add("第[" + (i + 4) + "]行第[1]列和第[" + (m + 4) + "]行第[1]列工号重复");
									for (int t = 0; t < failList.size(); t++) {
										// 错误信息中是否已包含这两行工号重复的提示
										if (failList.get(t)
												.equals("第[" + (m + 4) + "]行第[1]列和第[" + (i + 4) + "]行第[1]列工号重复")) {
											failList.remove(t);
										}
									}
									b = false;
								}
							}
						}
						// 判断工号长度不能超过20
						logger.info("验证第[" + (i + 4) + "]行,第[1]列工号格式是否正确");
						if (!StringUtils.isEmpty(rowMap.get(0).toString())) {
							if (ExcelCheckUtil.isOverLong(rowMap.get(0).toString(), 20)) {
								logger.info("第[" + (i + 4) + "]行，第[1]列工号格式错误");
								failList.add("第[" + (i + 4) + "]行第[1]列工号格式不正确");
								b = false;
							}
						}
					}
					if (j == 1) {
						logger.info("验证第[" + (i + 4) + "]行,第[2]列真实姓名格式是否正确");
						if (!StringUtils.isEmpty(rowMap.get(1).toString())) {
							if (ExcelCheckUtil.isOverLong(rowMap.get(1).toString(), 20)) {
								logger.info("第[" + (i + 4) + "]行，第[2]列真实姓名格式错误");
								failList.add("第[" + (i + 4) + "]行第[2]列真实姓名格式不正确");
								b = false;
							}
						}
					}

					if (j == 2) {
						logger.info("验证第[" + (i + 4) + "]行,第[3]列性别格式是否正确");
						if (!StringUtils.isEmpty(rowMap.get(j).toString())) {
							if (!ExcelCheckUtil.chkSex(rowMap.get(j).toString())) {
								logger.info("第[" + (i + 4) + "]行，第[3]列性别格式不正确");
								failList.add("第[" + (i + 4) + "]行第[3]列性别格式不正确，只能是0或1");
								b = false;
							}
						}
					}
					// 职位
					if (j == 3) {
						logger.info("验证第[" + (i + 4) + "]行,第[4]列职位格式是否正确");
						if (!StringUtils.isEmpty(rowMap.get(j).toString())) {
							if (ExcelCheckUtil.isOverLong(rowMap.get(j).toString(), 20)) {
								logger.info("第[" + (i + 4) + "]行，第[4]职位格式错误");
								failList.add("第[" + (i + 4) + "]行第[4]职位长度过长");
								b = false;
							}
						}
					}
					// 职称
					if (j == 4) {
						logger.info("验证第[" + (i + 4) + "]行,第[5]列职称格式是否正确");
						if (!StringUtils.isEmpty(rowMap.get(j).toString())) {
							if (ExcelCheckUtil.isOverLong(rowMap.get(j).toString(), 20)) {
								logger.info("第[" + (i + 4) + "]行，第[5]职称格式错误");
								failList.add("第[" + (i + 4) + "]行第[5]职称长度过长");
								b = false;
							}
						}
					}

					if (j == 5) {
						logger.info("验证第[" + (i + 4) + "]行,第[6]列联系电话格式是否正确");
						if (!StringUtils.isEmpty(rowMap.get(j).toString())) {
							if (ExcelCheckUtil.isOverLong(rowMap.get(j).toString(), 20)
									|| !ExcelCheckUtil.chkPhone(rowMap.get(j).toString())) {
								logger.info("第[" + (i + 4) + "]行，第[6]列联系电话格式错误");
								failList.add("第[" + (i + 4) + "]行第[6]列联系电话格式不正确");
								b = false;
							}
						}
					}
					// QQ
					if (j == 6) {
						logger.info("验证第[" + (i + 4) + "]行,第[7]列QQ格式是否正确");
						if (!StringUtils.isEmpty(rowMap.get(j).toString())) {
							if (ExcelCheckUtil.isOverLong(rowMap.get(j).toString(), 20)) {
								logger.info("第[" + (i + 4) + "]行，第[7]QQ格式错误");
								failList.add("第[" + (i + 4) + "]行第[7]QQ长度过长");
								b = false;
							}
						}
					}

					// 身份证号码
					if (j == 7) {
						logger.info("验证第[" + (i + 4) + "]行,第[8]列身份证号码格式是否正确");
						if (!StringUtils.isEmpty(rowMap.get(j).toString())) {
							if (ExcelCheckUtil.isOverLong(rowMap.get(j).toString(), 18)) {
								logger.info("第[" + (i + 4) + "]行，第[8]身份证号码格式错误");
								failList.add("第[" + (i + 4) + "]行第[8]身份证号码长度过长");
								b = false;
							}
						}
					}
					// E_Mail
					if (j == 8) {
						logger.info("验证第[" + (i + 4) + "]行,第[9]列邮箱格式是否正确");
						if (!StringUtils.isEmpty(rowMap.get(j).toString())) {
							if (!ExcelCheckUtil.chkEmail(rowMap.get(j).toString())
									|| ExcelCheckUtil.isOverLong(rowMap.get(j).toString(), 20)) {
								logger.info("第[" + (i + 4) + "]行，第[9]列邮箱格式错误");
								failList.add("第[" + (i + 4) + "]行第[9]列邮箱格式不正确");
								b = false;
							}
						}
					}
					// 地址
					if (j == 9) {
						logger.info("验证第[" + (i + 4) + "]行,第[10]列地址是否正确");
						if (!StringUtils.isEmpty(rowMap.get(j).toString())) {
							if (ExcelCheckUtil.isOverLong(rowMap.get(j).toString(), 100)) {
								logger.info("第[" + (i + 4) + "]行，第[10]地址格式错误");
								failList.add("第[" + (i + 4) + "]行第[10]地址长度过长");
								b = false;
							}
						}
					}
					// 备注
					if (j == 10) {
						logger.info("验证第[" + (i + 4) + "]行,第[11]备注是否正确");
						if (!StringUtils.isEmpty(rowMap.get(j).toString())) {
							if (ExcelCheckUtil.isOverLong(rowMap.get(j).toString(), 200)) {
								logger.info("第[" + (i + 4) + "]行，第[11]备注格式错误");
								failList.add("第[" + (i + 4) + "]行第[11]备注长度过长");
								b = false;
							}
						}
					}

				}
				// 验证通过组装数据
				if (b) {
					user.setId(rowMap.get(0).toString());// id
					user.setUsername(rowMap.get(0).toString());// 用户名
					// 性别
					if ("".equals(rowMap.get(2).toString())) {
						user.setSex(RageonConstant.NUM_0);// 默认为女
					} else {
						user.setSex(Integer.parseInt(rowMap.get(2).toString()));
					}

					user.setAge((Integer) rowMap.get(3));// 年龄
					user.setPasswd(rowMap.get(4).toString());// 密码
					userList.add(user);

				}
			}
		} else {
			logger.info("文件无内容");
			failList.add("文件无内容，请添加内容后再导入");
		}
		if (failList.isEmpty()) {
			logger.info("验证通过");
			result.setStatus(BaseResult.SUCCESS);
			result.setContent(userList);// 用户列表
		} else {
			logger.info("验证不通过");
			result.setStatus(BaseResult.ERROR);
			result.setContent(failList);// 失败信息列表
		}

		return result;
	}
}
