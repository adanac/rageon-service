package com.adanac.tool.rageon.service.module.mq;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adanac.framework.exception.SysException;
import com.adanac.framework.utils.TimeUtil;
import com.adanac.tool.rageon.intf.common.service.BaseDao;
import com.adanac.tool.rageon.intf.module.mq.entity.MonitorDto;
import com.adanac.tool.rageon.intf.module.mq.service.MonitorService;
import com.adanac.tool.rageon.utils.uuid.DefaultSequenceGenerator;

@Service
public class MonitorServiceImpl implements MonitorService {

	@Autowired
	private BaseDao baseDao;

	/**
	 * 普通调用
	 */
	@Override
	public MonitorDto monitor(MonitorDto content) {
		String uuid = DefaultSequenceGenerator.getInstance().uuid();
		content.setId(uuid);
		content.setHappenTime(TimeUtil.getCurrentTimestamp());
		System.err.println(content.toString());
		return content;
	}

	@Override
	public String add(MonitorDto dto) {
		String res;
		try {
			String uuid = DefaultSequenceGenerator.getInstance().uuid();
			dto.setId(uuid);
			dto.setHappenTime(TimeUtil.getCurrentTimestamp());
			int count = baseDao.execute("tabMonitor.insert", dto);
			if (count > 0) {
				res = dto.getId();
			} else {
				res = null;
			}

		} catch (Exception e) {
			throw new SysException(e);
		}
		return res;
	}

	@Override
	public MonitorDto get(String id) {
		MonitorDto dto;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", id);
			dto = baseDao.queryForObject("tabMonitor.select", param, MonitorDto.class);
		} catch (Exception e) {
			throw new SysException(e);
		}
		return dto;
	}

	@Override
	public int mod(MonitorDto dto) {
		try {
			dto.setProcessTime(TimeUtil.getCurrentTimestamp());
			return baseDao.execute("tabMonitor.update", dto);
		} catch (Exception e) {
			throw new SysException(e);
		}
	}

}
