package com.adanac.tool.rageon.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.adanac.tool.rageon.BaseTest;
import com.adanac.tool.rageon.common.entity.CommonDto;
import com.adanac.tool.rageon.common.service.ExportService;

public class ExportServiceImplTest extends BaseTest {

	@Autowired
	private ExportService exportService;

	@Test
	public void testExportFile() throws IOException {
		File directory = new File("");// 参数为空
		String basePath = directory.getCanonicalPath();
		List<CommonDto> list = initData();
		exportService.exportFile(basePath, list);
	}

	private List<CommonDto> initData() {
		List<CommonDto> list = new ArrayList<CommonDto>();
		for (int i = 0; i < 100; i++) {
			CommonDto dto = new CommonDto();
			dto.setId(i + 1 + "");
			dto.setAge(20 + i);
			dto.setPasswd(UUID.randomUUID().toString());
			dto.setUsername("user" + i);
			list.add(dto);
		}
		return list;
	}

}
