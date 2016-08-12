package com.adanac.tool.rageon.service.common;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.tool.rageon.common.service.CacheService;

/**
 */
// @Service(value = "appAddrCacheService")
public class AppAddrCacheService implements CacheService<String>, InitializingBean, DisposableBean {

	private MyLogger logger = MyLoggerFactory.getLogger(AppAddrCacheService.class);

	private Map<String, String> addrMap = new ConcurrentHashMap<>();

	private static final String APP_ADDR_PREFIX = "appserver_addr_";// 前缀
	private static final long interval = 30 * 60 * 1000;// 每隔30分钟

	private Timer timer = new Timer(true);// 定时器

	@Value(value = "${appServerWebRoot}")
	private String appServerWebRoot;

	/**
	 * 根据CompanyId获取AppServer地址
	 */
	@Override
	public String get(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		String value = addrMap.get(APP_ADDR_PREFIX + key);
		if (null == value) {

			try {

			} catch (Exception e) {
				logger.error("Get app server address failed, companyId: {}, cause: {}.", key, e.getMessage());
			}

			value = "https://" + appServerWebRoot;
			addrMap.put(APP_ADDR_PREFIX + key, value);

		}
		return value;
	}

	/**
	 * 没有具体实现
	 */
	@Override
	public void set(String key, String t) {
		throw new NotImplementedException("Can not be implemented!");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				addrMap.clear();// 清空
			}
		}, 0, interval);
	}

	@Override
	public void destroy() throws Exception {
		timer.cancel();
	}

}
