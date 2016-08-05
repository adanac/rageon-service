package com.adanac.tool.rageon.service.module.mq;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

import javax.jms.Message;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.adanac.framework.exception.SysException;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.mq.activemq.MsgListener;
import com.adanac.framework.mq.activemq.consumer.MQConsumer;
import com.adanac.framework.mq.activemq.producer.MQProducer;
import com.adanac.framework.utils.JsonUtils;
import com.adanac.framework.utils.TimeUtil;
import com.adanac.tool.rageon.intf.module.mq.entity.MonitorDto;
import com.adanac.tool.rageon.intf.module.mq.service.ActiveMqService;
import com.adanac.tool.rageon.intf.module.mq.service.MonitorService;

@Service("activeMqService")
public class ActiveMqServiceImpl implements ActiveMqService, InitializingBean {

	@Autowired
	private MQConsumer monitorConsumer;
	@Autowired
	private MQProducer monitorProducer;
	@Autowired
	private MonitorService monitorService;
	private Properties prop;
	// private String nodeName;
	@Value(value = "${rageonMqName}")
	private String rageonMqName;

	private MyLogger log = MyLoggerFactory.getLogger(ActiveMqServiceImpl.class);

	// public String getNodeName() {
	// return nodeName;
	// }
	//
	// public void setNodeName(String nodeName) {
	// this.nodeName = nodeName;
	// }

	@Override
	public void syncMonitor(String id) {
		MonitorDto dto = monitorService.get(id);
		String json = JsonUtils.bean2json(dto);
		log.info("send mq queue {}", json);

		String mqName;
		if (prop != null) {
			mqName = prop.getProperty("mqName");// 前两种方式（统一配置、加载配置文件）
		} else {
			mqName = rageonMqName;// 第三种方式通过注解的方式
		}

		log.info("mqName {}", mqName);
		monitorProducer.sendQueue(mqName, json, null);
	}

	/**
	 * 统一配置的方式，利用中间件disconf
	 * @return
	 */
	// public Properties getProp() {
	// UniconfigClient client = UniconfigClientImpl.getInstance();
	// UniconfigNode node = client.getConfig(nodeName);
	// node.sync();
	// String configValue = node.getValue();
	//
	// Properties properties = new Properties();
	// InputStream in = new ByteArrayInputStream(configValue.getBytes());
	// try {
	// properties.load(in);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// // properties.load(configValue);
	// return properties;
	// }

	/**
	 * 加载配置文件的方式
	 * @return
	 */
	public Properties getPropLoadFile(String filePath) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(filePath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// String filePath = "conf/rageon-monitor.properties";
		// prop = getPropLoadFile(filePath);// file not found
		// prop = getProp();

		monitorConsumer.listenQueue(rageonMqName, new MsgListener() {
			public void onMsg(Message orginMsg, Object msgContent) {
				log.info("MQService=====>msgContent:{}", msgContent);
				try {
					Map<String, String> map = JsonUtils.getMapFromJson(msgContent.toString());
					String id = map.get("id");

					log.info("id========>" + id);
					// 具体的操作，这里设置处理时间
					MonitorDto monitorDto = monitorService.get(id);
					monitorDto.setProcessTime(TimeUtil.getCurrentTimestamp());
					monitorService.mod(monitorDto);
				} catch (Exception e) {
					log.error("MQ异常{}===>", new Object[] { e.getMessage() });
					throw new SysException("MQService", e);
				}
			}
		});

	}

}
