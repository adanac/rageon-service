package com.adanac.tool.rageon.service.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.adanac.framework.exception.SysException;
import com.adanac.framework.utils.UUIDGenerator;
import com.adanac.tool.rageon.intf.front.entity.GoodDto;
import com.adanac.tool.rageon.intf.front.service.GoodBaseService;
import com.adanac.tool.rageon.intf.front.service.GoodService;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.esotericsoftware.minlog.Log;

public class GoodServiceImpl implements GoodService {

	@Autowired
	private GoodBaseService goodBaseService;

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	@Transactional
	@POST
	@Path("add")
	@Consumes({ MediaType.APPLICATION_JSON, ContentType.APPLICATION_JSON_UTF_8 })
	public Boolean addGoods(List<GoodDto> list) {
		if (list == null || list.isEmpty()) {
			Log.info("addGoods list is empty");
			return true;
		}
		int index = 0;
		Map<String, Object>[] maps = new HashMap[list.size()];

		try {
			String activityId = "";
			for (GoodDto dto : list) {
				String id = UUIDGenerator.getInstance().get64BitUUID();
				dto.setId(id);

				activityId = dto.getId(); // 设置活动id
				Map<String, Object> map = dto.toMap(); // 封装map数据
				maps[index++] = map;
			}
			goodBaseService.batchInsert(maps);
			return true;
		} catch (Exception e) {
			Log.error("addGoods fail", e);
			throw new SysException("addGoods fail");
		}
	}

}
