package com.adanac.tool.rageon.service.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adanac.framework.page.Pager;
import com.adanac.framework.page.PagerParam;
import com.adanac.framework.utils.TimeUtil;
import com.adanac.tool.rageon.intf.common.service.BaseDao;
import com.adanac.tool.rageon.intf.front.entity.ActivityDto;
import com.adanac.tool.rageon.intf.front.service.ActivityBaseService;

/**
 * 活动基础业务实现类
 */
@Service("activityBaseService")
public class ActivityBaseServiceImpl implements ActivityBaseService {

	@Autowired
	private BaseDao baseDao;

	@Override
	@Transactional
	public int addActivity(ActivityDto dto) {
		// 添加制单日期和最后修改日期
		dto.setCreateDttm(TimeUtil.getCurrentTimestamp());// 创建时间
		dto.setLastDttm(TimeUtil.getCurrentTimestamp());// 最后修改时间
		return baseDao.execute("activity.insert", dto);
	}

	@Override
	public int modActivity(ActivityDto dto) {
		// 给ID赋值
		dto.setCreateDttm(TimeUtil.getCurrentTimestamp());// 创建时间
		dto.setLastDttm(TimeUtil.getCurrentTimestamp());// 最后修改时间
		return baseDao.execute("activity.updateById", dto);
	}

	@Override
	public int stop(ActivityDto dto) {
		return baseDao.execute("activity.stopById", dto);
	}

	@Override
	public ActivityDto queryByActivityId(String id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		return baseDao.queryForObject("activity.select", param, ActivityDto.class);
	}

	@Override
	public Pager<ActivityDto> queryForPage(Map<String, Object> paramMap, PagerParam pageParam) {
		return baseDao.queryForPage("activity.select", paramMap, ActivityDto.class, pageParam.getPageSize(),
				pageParam.getPageNumber());
	}

	@Override
	public List<ActivityDto> queryRepeatActivityList(Map<String, Object> paramMap) {
		return baseDao.queryForList("activity.queryRepeatActivityList", paramMap, ActivityDto.class);
	}

}
