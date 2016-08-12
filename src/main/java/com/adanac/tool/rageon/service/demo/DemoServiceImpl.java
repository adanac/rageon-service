package com.adanac.tool.rageon.service.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.adanac.framework.page.Pager;
import com.adanac.framework.page.PagerParam;
import com.adanac.tool.rageon.demo.entity.DemoDto;
import com.adanac.tool.rageon.demo.service.DemoService;

@Service("demoService")
public class DemoServiceImpl implements DemoService {

	private Map<Integer, DemoDto> cache = new ConcurrentHashMap<Integer, DemoDto>();
	private final static Integer INIT_DATA_NUM = 30;

	@Override
	public Pager<DemoDto> getDemoList(DemoDto demoDto, PagerParam pagerParam) {
		List<DemoDto> result = new ArrayList<DemoDto>();
		// int size = null == demoDto.getId() ? 0 : demoDto.getId();
		//
		// int totalPageNum = size / pagerParam.getPageSize();
		// int remainNum = size % pagerParam.getPageSize();
		// if (remainNum > 0) {
		// totalPageNum++;
		// }
		int pageNum = pagerParam.getPageNumber() < 1 ? 1 : pagerParam.getPageNumber();
		// if (pageNum > totalPageNum) {
		// pageNum = totalPageNum;
		// }
		int start = (pageNum - 1) * pagerParam.getPageSize();
		int end = start + pagerParam.getPageSize() - 1;
		// if (end > size) {
		// end = size;
		// }
		Collection<DemoDto> values = cache.values();
		if (!values.isEmpty()) {// 如果缓存中有数据
			// if (start >= values.size()) {
			// start = values.size() - 1;
			// end = values.size();
			// } else if (end >= values.size()) {
			// end = values.size();
			// }
			DemoDto[] demos = values.toArray(new DemoDto[values.size()]);
			for (int i = start; i < end; i++) {
				result.add(demos[i]);
			}
		} else {// 缓存数据为空
			initData(result);
		}
		Pager<DemoDto> pager = new Pager<DemoDto>(result.size(), pagerParam.getPageSize(), pageNum);
		pager.setDatas(result);
		return pager;
	}

	private void initData(List<DemoDto> result) {

		for (int i = 0; i < INIT_DATA_NUM; i++) {
			DemoDto demoDto = new DemoDto(i + 1, "name" + i + 1);
			result.add(demoDto);// 放到结果集中
			cache.put(demoDto.getId(), demoDto);// 同时放入到缓存中
		}
	}

	@Override
	public DemoDto getDemoById(Integer id) {
		return cache.get(id);
	}

	@Override
	public boolean delDemoById(Integer id) {
		return null != id && id.intValue() > 0;
	}

	@Override
	public boolean addDemo(DemoDto demoDto) {
		if (cache.containsKey(demoDto.getId())) {
			return false;
		}
		cache.put(demoDto.getId(), demoDto);
		return true;
	}

	@Override
	public boolean modDemo(DemoDto demoDto) {
		if (!cache.containsKey(demoDto.getId())) {
			return false;
		}
		cache.put(demoDto.getId(), demoDto);
		return true;
	}

}
