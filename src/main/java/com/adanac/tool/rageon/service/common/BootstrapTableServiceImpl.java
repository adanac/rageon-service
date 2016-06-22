package com.adanac.tool.rageon.service.common;

import org.springframework.stereotype.Service;

import com.adanac.framework.page.Pager;
import com.adanac.framework.web.controller.BaseResult;
import com.adanac.tool.rageon.intf.common.entity.BootstrapPage;
import com.adanac.tool.rageon.intf.common.entity.CommonDto;
import com.adanac.tool.rageon.intf.common.service.BootstrapTableService;

@Service
public class BootstrapTableServiceImpl implements BootstrapTableService {

	@Override
	public Pager<CommonDto> queryCommonDtoList(CommonDto commonDto, BootstrapPage param) {

		return null;
	}

	@Override
	public BaseResult getCommonDto(String id) {
		return null;
	}

}
