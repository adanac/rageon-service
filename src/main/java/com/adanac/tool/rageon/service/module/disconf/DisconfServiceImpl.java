package com.adanac.tool.rageon.service.module.disconf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adanac.tool.rageon.intf.module.disconf.service.DisconfService;
import com.adanac.tool.rageon.service.common.RageonUniConfig;

/**
 * 测试Disconf
 */
@Service("disconfService")
public class DisconfServiceImpl implements DisconfService {

	@Autowired
	private RageonUniConfig config;

	@Override
	public boolean validateCompanyCode(String companyCode) {
		return config.getCompanyCode().equals(companyCode);
	}

}
