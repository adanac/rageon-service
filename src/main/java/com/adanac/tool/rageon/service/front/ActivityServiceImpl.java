package com.adanac.tool.rageon.service.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adanac.framework.exception.SysException;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.page.Pager;
import com.adanac.framework.utils.UUIDGenerator;
import com.adanac.tool.rageon.intf.front.entity.ActivityDto;
import com.adanac.tool.rageon.intf.front.entity.ActivityQueryDto;
import com.adanac.tool.rageon.intf.front.service.ActivityBaseService;
import com.adanac.tool.rageon.intf.front.service.ActivityService;

/**
 * 限时活动实现类
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityBaseService activityBaseService;

	private final MyLogger log = MyLoggerFactory.getLogger(GoodServiceImpl.class);

	@Override
	public String addActivity(ActivityDto activityDto) {
		log.info("XSActivityServiceImpl-->addActivity-->suppActivityDto:" + activityDto.toString());
		try {
			String uuid = UUIDGenerator.getInstance().get64BitUUID();
			activityDto.setId(uuid);

			if (activityBaseService.addActivity(activityDto) > 0) {
				// TODO 使用MQ 进行同步操作
				// syncAct(activityDto, RageonConstant.OPER_TYPE_ADD);
			}

		} catch (Exception e) {
			log.error("新增限时促销活动失败{}", e);
			throw new SysException("新增限时促销活动失败");
		}
		return activityDto.getId();
	}

	@Override
	public Pager<ActivityDto> queryActivityList(ActivityQueryDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
