package com.adanac.tool.rageon.front;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.utils.JsonUtils;
import com.adanac.tool.rageon.BaseTest;
import com.adanac.tool.rageon.intf.front.entity.ActivityDto;
import com.adanac.tool.rageon.intf.front.service.ActivityService;

public class ActivityServiceImplTest extends BaseTest {
	private MyLogger log = MyLoggerFactory.getLogger(ActivityServiceImplTest.class);

	@Autowired
	private ActivityService activityService;

	@Test
	public void test_addActivity() {

		ActivityDto act = new ActivityDto();
		act.setName("活动1");
		// ActivityDto act =
		// activityService.getActivity("006624afc83f4865989b9717b1160f27");
		act.setName(act.getName() + "0523-7");
		log.info("before act {}", JsonUtils.bean2json(act));
		// act.setId(DefaultSequenceGenerator.getInstance().uuid());
		// act.setCreateDttm(TimeUtil.getCurrentTimestamp());
		log.info("after act {}", JsonUtils.bean2json(act));

		String uid = activityService.addActivity(act);
		log.info("success,uid=" + uid);

		Assert.assertNotNull(uid);

	}

}
