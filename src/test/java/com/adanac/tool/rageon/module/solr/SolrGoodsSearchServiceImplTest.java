package com.adanac.tool.rageon.module.solr;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.adanac.commclient.solr.dto.SolrGoodsDto;
import com.adanac.commclient.solr.intf.SolrGoodsSearchService;
import com.adanac.tool.rageon.BaseTest;

public class SolrGoodsSearchServiceImplTest extends BaseTest {

	@Autowired
	private SolrGoodsSearchService goodsSearchService;

	@Test
	public void testChkIsUsed() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryGoodsByBarCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckBarCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchGoodsStringStringStringStringStringPagerParam() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchGoodsMapOfStringObjectPagerParamInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchSuppGoods() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBySkuId() {
		String skuId = "123";
		SolrGoodsDto goodsDto = goodsSearchService.getBySkuId(skuId);
		Assert.assertNotNull(goodsDto);
	}

}
