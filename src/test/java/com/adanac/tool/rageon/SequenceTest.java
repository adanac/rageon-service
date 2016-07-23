package com.adanac.tool.rageon;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.adanac.tool.rageon.service.common.SkuCodeGenerator;

public class SequenceTest extends BaseTest {

	@Autowired
	private SkuCodeGenerator skuCodeGenerator;

	@Test
	public void test_add() {
		System.out.println(skuCodeGenerator.getOuterSequence());
	}

}
