package com.adanac.tool.rageon.module.mq;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.mq.activemq.MsgListener;
import com.adanac.framework.mq.activemq.consumer.MQConsumer;
import com.adanac.framework.mq.activemq.producer.MQProducer;
import com.adanac.framework.utils.JsonUtils;
import com.adanac.tool.rageon.BaseTest;

public class ActiveMqServiceImplTest extends BaseTest {

	@Autowired
	private MQProducer producer;
	@Autowired
	private MQConsumer consumer;
	private String queueName = "test_queue";
	private MyLogger log = MyLoggerFactory.getLogger(ActiveMqServiceImplTest.class);
	private String queueWebName = "queue_create_web";

	@Test
	public void testSyncMonitor() {
		fail("Not yet implemented");
	}

	@Test
	public void test_product() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "10000089");
		map.put("name", "allen001");
		String json = JsonUtils.bean2json(map);
		producer.sendQueue(queueName, json, null);
	}

	@Test
	public void test_consumer() {
		// fail("Not yet implemented");
		while (true) {
			consumer.listenQueue(queueName, new MsgListener() {
				public void onMsg(Message orginMsg, Object msgContent) {
					log.info("msgContent=======>" + msgContent.toString());
					Map<String, String> map = JsonUtils.getMapFromJson(msgContent.toString());
					String id = map.get("id");
					log.info("id========>" + id);
				}
			});
		}
	}

	@Test
	public void test_consumer_create_web() {
		// fail("Not yet implemented");
		while (true) {
			consumer.listenQueue(queueWebName, new MsgListener() {
				public void onMsg(Message orginMsg, Object msgContent) {
					log.info("msgContent=======>" + msgContent.toString());
					Map<String, String> map = JsonUtils.getMapFromJson(msgContent.toString());
					String id = map.get("id");
					log.info("id========>" + id);
				}
			});
		}
	}

	@Test
	public void test_product_create_web() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "22222200001");
		map.put("name", "allen002");
		String json = JsonUtils.bean2json(map);
		producer.sendQueue(queueWebName, json, null);
	}
}
