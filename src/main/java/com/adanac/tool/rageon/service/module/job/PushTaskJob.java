package com.adanac.tool.rageon.service.module.job;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adanac.framework.utils.DateUtils;
import com.adanac.tool.rageon.intf.common.entity.CommonDto;
import com.adanac.tool.rageon.intf.common.service.CommonService;
import com.alibaba.fastjson.JSON;

/**
 * 定时任务
 */
@Component
public class PushTaskJob {

	private static Logger logger = LoggerFactory.getLogger(PushTaskJob.class);

	@Autowired
	private CommonService commonService;

	public void excute() {

		logger.info("发送消息通知  start:" + DateUtils.format(new Date(), DateUtils.DEFAULT_YEAR_MONTH_DAY_HMS));
		Long start = System.currentTimeMillis();

		CommonDto commonDto = new CommonDto();
		commonDto.setUsername("");
		List<CommonDto> list = commonService.queryCommonDtoList(commonDto);
		logger.info("getAllPushingTask:" + JSON.toJSONString(list));

		for (CommonDto dto : list) {
			try {
				logger.info(dto.toString());

			} catch (Exception e) {
				logger.error("发送失败：{}", e);
				continue;
			}

		}

		Long end = System.currentTimeMillis();

		logger.info("发送消息通知  end,耗时：" + (end - start) + "毫秒");

	}

	public static void main(String[] args) throws Exception {
		// String content ="<html>\\n <head></head>\\n <body>\\n <p><img
		// src=\\\"http://mmcimg-10016961.image.myqcloud.com/e23a11d3-4a96-43aa-ba5f-98fcbb1a4dfa\\\">111</p>\\n
		// </body>\\n</html>";
		// System.out.println(dealArticleContent("wxdfec4bcf9f6a2423",
		// content));
		// uploadImgForUrl("wx37ba853fe28f9200",
		// "http://mmcimg-10016961.image.myqcloud.com/dab50d9e-54ce-434d-9bfb-94c95a4d63e2");

		/*
		 * Map<String, String> params= new HashMap<>(); params.put("massTuple",
		 * "{\"articles\":[{\"content\":\"<ol class=\\\" list-paddingleft-2\\\" style=\\\"list-style-type: decimal;\\\"><li><p>kkkk</p></li><li><p>dsda</p></li><li><p>addss</p></li><li><p>dasdas</p></li><li><p>adas</p></li><li><p>ads<br/></p></li></ol><p style=\\\"line-height: 16px;\\\"><img style=\\\"vertical-align: middle; margin-right: 2px;\\\" src=\\\"http://mmbiz.qpic.cn/mmbiz/rYdMqTvibW5GOicNv7PObY4dDUXYM5B1mKianr8c9LTwVDpWOMJmOqxJmxhRUoqaL55OXomvXwde6RKZ9qqhmjIxA/0\\\"/><a style=\\\"font-size:12px; color:#0066cc;\\\" href=\\\"http://oa.bizcent.com:5001/g1/M00/00/0D/wKgCR1dWWceAXm6dAAACl3jc_ZA832.txt\\\" title=\\\"Noname1.txt\\\">Noname1.txt</a></p><p style=\\\"line-height: 16px;\\\"><img style=\\\"vertical-align: middle; margin-right: 2px;\\\" src=\\\"http://192.168.1.23:8080/mmc-web/RES/assets/plugins/ueditor1_4_3_2-src/dialogs/attachment/fileTypeImages/icon_txt.gif\\\"/><a style=\\\"font-size:12px; color:#0066cc;\\\" href=\\\"http://oa.bizcent.com:5001/g1/M00/00/0D/wKgCR1dWWm2AFLr8AAEkuJN4Jcw05.xlsx\\\" title=\\\"role_bugs.xlsx\\\">role_bugs.xlsx</a></p><p style=\\\"line-height: 16px;\\\"><img style=\\\"vertical-align: middle; margin-right: 2px;\\\" src=\\\"http://192.168.1.23:8080/mmc-web/RES/assets/plugins/ueditor1_4_3_2-src/dialogs/attachment/fileTypeImages/icon_doc.gif\\\"/><a style=\\\"font-size:12px; color:#0066cc;\\\" href=\\\"http://oa.bizcent.com:5001/g1/M00/00/0D/wKgCR1dWXq6AaPAdAAraKCs3ZZg01.docx\\\" title=\\\"???????????????????.docx\\\">???????????????????.docx</a></p><p style=\\\"line-height: 16px;\\\"><a style=\\\"font-size:12px; color:#0066cc;\\\" href=\\\"http://oa.bizcent.com:5001/g1/M00/00/0D/wKgCR1dWWceAXm6dAAACl3jc_ZA832.txt\\\" title=\\\"Noname1.txt\\\"></a><br/></p><p><br/></p>\",\"show_cover_pic\":\"0\",\"thumb_media_id\":\"Sf9oUO4EnT8iR9OqUdE6tb357J0JLPOnGKoYHpCpC70kZ2hZfRNWKrBEM5P9Q7do\",\"title\":\"2222\"}],\"messageType\":\"mpnews\"}"
		 * ); params.put("weixin_id", "wxdfec4bcf9f6a2423");
		 * params.put("openId", "obECAtw6l-JhvoiZdVaEz3_xpgts"); String url=
		 * "http://www.bizb2b.com.cn/bn-weixin-test/weixin/mass/previewMassNews.do";
		 * String resp = HttpUtil.post(url, params);
		 * System.out.println("resp:"+resp);
		 */

		/*
		 * Map<String, String> params= new HashMap<>(); params.put("massTuple",
		 * "{\"content\":\"自动ceshi\",\"messageType\":\"text\"}");
		 * params.put("weixin_id", "wxdfec4bcf9f6a2423"); params.put("openId",
		 * "obECAtw6l-JhvoiZdVaEz3_xpgts"); String url=
		 * "http://www.bizb2b.com.cn/bn-weixin-test/weixin/mass/previewMassNews.do";
		 * String resp = HttpUtil.post(url, params); System.out.println(resp);
		 */
	}

}
