package com.adanac.tool.rageon.service.front;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adanac.framework.page.Pager;
import com.adanac.framework.page.PagerParam;
import com.adanac.tool.rageon.intf.common.service.BaseDao;
import com.adanac.tool.rageon.intf.front.entity.GoodDto;
import com.adanac.tool.rageon.intf.front.service.GoodBaseService;

@Service("goodBaseService")
public class GoodBaseServiceImpl implements GoodBaseService {
	@Autowired
	private BaseDao baseDao;

	@Override
	public int[] batchInsert(Map<String, Object>[] maps) {
		return baseDao.batchUpdate("good.insert", maps);
	}

	@Override
	public int deleteById(Map<String, Object> paramMap) {
		return baseDao.execute("good.deleteById", paramMap);
	}

	@Override
	public GoodDto queryByGoodId(String id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		return baseDao.queryForObject("activity.select", param, GoodDto.class);
	}

	@Override
	public Pager<GoodDto> queryForPage(Map<String, Object> paramMap, PagerParam pageParam) {
		return baseDao.queryForPage("good.select", paramMap, GoodDto.class, pageParam.getPageSize(),
				pageParam.getPageNumber());
	}

}
