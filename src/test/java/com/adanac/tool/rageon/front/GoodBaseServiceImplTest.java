package com.adanac.tool.rageon.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.adanac.tool.rageon.intf.front.entity.GoodDto;
import com.adanac.tool.rageon.intf.front.service.GoodBaseService;

import junit.framework.TestCase;

public class GoodBaseServiceImplTest extends TestCase {

	private static final int DATALENGTH = 10;

	@Autowired
	private GoodBaseService goodBaseService;

	@Test
	public void testBatchInsert() {

		List<GoodDto> goodList = new ArrayList<GoodDto>();
		for (int i = 0; i < DATALENGTH; i++) {
			GoodDto dto = new GoodDto();
			dto.setCategoryId(i + 1);
			dto.setFskuId("1000" + i);
			dto.setFspuId("200" + i);
			goodList.add(dto);
		}
		int index = 0;
		Map<String, Object>[] maps = new HashMap[goodList.size()];
		for (GoodDto dto : goodList) {
			Map<String, Object> map = dto.toMap(); // 封装map数据
			maps[index++] = map;
		}
		goodBaseService.batchInsert(maps);

	}

	@Test
	public void testDeleteById() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryByGoodId() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryForPage() {
		fail("Not yet implemented");
	}

}
