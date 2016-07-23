package com.adanac.tool.rageon;

import javax.jms.Message;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.adanac.framework.exception.SysException;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.mq.activemq.MsgListener;
import com.adanac.framework.mq.activemq.consumer.MQConsumer;
import com.adanac.tool.rageon.intf.module.mq.entity.MonitorDto;
import com.adanac.tool.rageon.intf.module.mq.service.MonitorService;
import com.alibaba.fastjson.JSONObject;

/**
 * 此文件时，利用统一配置disconf时会用到
 * Created by allen
 */
public class ActiveMqService implements InitializingBean {

	@Autowired
	private MQConsumer consumer;

	@Autowired
	private MonitorService monitorService;

	/**
	 * 分组名称
	 */
	public static final String GRP_NAME = "rage_monitor";

	/**
	 * 主题
	 */
	public static final String TOPIC_MONITRO_WEBSITE = "monitor_website";

	private MyLogger log = MyLoggerFactory.getLogger(ActiveMqService.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		consumer.listenVirtualTopic(TOPIC_MONITRO_WEBSITE, GRP_NAME, new MsgListener() {

			@Override
			public void onMsg(Message orginMsg, Object msgContent) {
				log.info("get msg from monitor {}", msgContent);
				JSONObject jsonObject = JSONObject.parseObject(msgContent.toString());
				JSONObject monitor = jsonObject.getJSONObject("monitor");
				String id = monitor.getString("eventId");
				System.err.println(id);
				try {
					MonitorDto monitorDto = monitorService.get(id);
					int mod = monitorService.mod(monitorDto);

					log.info("mod:" + mod + "," + (mod > 0 ? "MQ mod成功" : "MQ mod失败"));
				} catch (Exception e) {
					log.info("monitor fail", e);
					throw new SysException("monitor fail", e);
				}
			}
		});
	}

}
