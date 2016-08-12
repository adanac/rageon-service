package com.adanac.tool.rageon.service.module.solr;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.adanac.commonclient.solr.SolrGoodsSearchServiceImpl;
import com.adanac.framework.exception.SysException;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.page.Pager;
import com.adanac.framework.page.PagerParam;
import com.adanac.framework.uniconfig.client.UniconfigClient;
import com.adanac.framework.uniconfig.client.UniconfigClientImpl;
import com.adanac.framework.uniconfig.client.UniconfigNode;
import com.adanac.framework.utils.StringUtils;
import com.adanac.tool.rageon.constant.AnchoredTypeEnum;
import com.adanac.tool.rageon.constant.SkuStatusEnum;
import com.adanac.tool.rageon.module.solr.entity.SolrGoodsDto;
import com.adanac.tool.rageon.module.solr.service.SearchService;
import com.adanac.tool.rageon.utils.EscapeUtil;
import com.adanac.tool.rageon.utils.UrlBuilder;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 商品搜索的服务类，主要对接solr进行商品数据的搜索，并提供给前端检索商品使用
 * 
 */
@Service("searchService")
public class SearchServiceImpl implements SearchService, InitializingBean {

	// private SolrGoodsSearchService goodsSearchService;

	private MyLogger logger = MyLoggerFactory.getLogger(SolrGoodsSearchServiceImpl.class);

	private Properties prop;

	// private String nodeName;
	@Value(value = "${rageonSolrName}")
	private String rageonSolrName;

	// public String getNodeName() {
	// return nodeName;
	// }
	//
	// public void setNodeName(String nodeName) {
	// this.nodeName = nodeName;
	// }

	@Override
	public Pager<SolrGoodsDto> searchGoods(Map<String, Object> map, PagerParam param) {

		Pager<SolrGoodsDto> pager = new Pager<SolrGoodsDto>();
		try {
			// 组装solr查询链接
			String requestUrl = assmbleSearchParams(map, param, pager);

			List<SolrGoodsDto> sList = new ArrayList<SolrGoodsDto>();
			logger.info("searchGoods url {}", requestUrl);

			String result = this.execute(requestUrl);
			// 转换对象
			JSONObject jsonObject = JSONObject.parseObject(result);
			JSONObject responseHead = jsonObject.getJSONObject("responseHeader");
			String stat = responseHead.getString("status");
			if (!"0".equals(stat)) {
				logger.error("query fail stat {}", stat);
				return pager;
			}
			JSONObject response = jsonObject.getJSONObject("response");
			// 获取总记录条数
			int numFound = response.getIntValue("numFound");
			pager.setTotalDataCount(numFound);

			JSONArray docs = response.getJSONArray("docs");
			for (int i = 0; i < docs.size(); i++) {
				JSONObject json = docs.getJSONObject(i);
				sList.add(JSONObject.toJavaObject(json, SolrGoodsDto.class));
			}
			for (SolrGoodsDto dto : sList) {
				logger.info(dto.toString());
			}
			// logger.info("queryBytitle result : {}", result);
			pager.setDatas(sList);
			return pager;
		} catch (Exception e) {
			logger.error("searchGoods fail", e);
			return pager;
		}

	}

	public static void main(String[] args) {
		SolrGoodsSearchServiceImpl s = new SolrGoodsSearchServiceImpl();
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("status", "1,3");
		// map.put("cooPeratorType", 0);
		//
		// PagerParam param = new PagerParam();
		// Pager<SolrGoodsDto> pager = new Pager<SolrGoodsDto>();
		// String url = s.assmbleSearchParams(map, param, pager);
		// System.out.println("url=" + url);
		String companyId = "1042";
		String goodName = "apple";
		String desc = "true";
		PagerParam param = new PagerParam();
		param.setPageNumber(1);
		param.setPageSize(10);
		Pager<com.adanac.commonclient.solr.dto.SolrGoodsDto> datas = s.searchGoods(companyId, null, goodName, desc,
				null, param);
		System.out.println(datas.getDatas().size());
	}

	/**
	 * 测试solr-client
	 */
	@Override
	public Pager<com.adanac.commonclient.solr.dto.SolrGoodsDto> searchGoodsSolrClient(String companyId) {
		SolrGoodsSearchServiceImpl solrGood = new SolrGoodsSearchServiceImpl();
		PagerParam param = new PagerParam();
		param.setPageNumber(1);
		param.setPageSize(10);
		Pager<com.adanac.commonclient.solr.dto.SolrGoodsDto> datas = solrGood.searchGoods(companyId, null, null, null,
				null, param);
		return datas;
	}

	/**
	 * 组装查询的链接
	 * 
	 * @param map
	 * @param param
	 * @param pager
	 * @return
	 */
	private String assmbleSearchParams(Map<String, Object> map, PagerParam param, Pager<SolrGoodsDto> pager) {
		String title = map.get("title") == null ? "" : map.get("title").toString();
		String classId = map.get("classId") == null ? "" : map.get("classId").toString();
		String status = map.get("status") == null ? "" : map.get("status").toString();
		String localCode = map.get("localCode") == null ? "" : map.get("localCode").toString();

		String classOne = map.get("classOne") == null ? "" : map.get("classOne").toString();
		String classTwo = map.get("classTwo") == null ? "" : map.get("classTwo").toString();
		String classThree = map.get("classThree") == null ? "" : map.get("classThree").toString();
		String categoryId = map.get("categoryId") == null ? "" : map.get("categoryId").toString();
		String brand = map.get("brand") == null ? "" : map.get("brand").toString();
		String suppId = map.get("suppId") == null ? "" : map.get("suppId").toString();

		String barCode = map.get("barCode") == null ? "" : map.get("barCode").toString();

		// 单铺的查询
		String desc = map.get("desc") == null ? "" : map.get("desc").toString();
		String skuId = map.get("skuId") == null ? "" : map.get("skuId").toString();

		// 引进挂靠
		String anchored = map.get("anchored") == null ? "" : map.get("anchored").toString();

		String categoryName = map.get("categoryName") == null ? "" : map.get("categoryName").toString();
		String brandName = map.get("brandName") == null ? "" : map.get("brandName").toString();
		// status = SkuStatusEnum.STATUS_UP.getValue()+"";

		// 排除平台的
		String onlySupp = map.get("onlySupp") == null ? "" : map.get("onlySupp").toString();
		String cooPeratorType = map.get("cooPeratorType") == null ? "" : map.get("cooPeratorType").toString();

		StringBuffer sb = new StringBuffer();
		if (StringUtils.isEmpty(title)) {
			title = "*";
		}
		sb.append("titleSuggest:" + EscapeUtil.escapeQueryChars(title));
		// sb.append("titleSuggest:" + title);
		if (!StringUtils.isEmpty(classId)) {
			sb.append(" AND fclassId:" + classId);
		}
		if (!StringUtils.isEmpty(localCode)) {
			sb.append(" AND localCode:" + localCode);
		}

		if (!StringUtils.isEmpty(barCode)) {
			sb.append(" AND barCode:" + barCode);
		}

		if (!StringUtils.isEmpty(status)) {
			if (status.contains(",")) {
				String[] statusArr = status.split(",");
				sb.append(" AND (");
				for (int i = 0; i < statusArr.length; i++) {
					if (!StringUtils.isEmpty(statusArr[i])) {
						if (i == statusArr.length - 1) {
							sb.append("status:" + statusArr[i]);
						} else {
							sb.append("status:" + statusArr[i] + " OR ");
						}

					}
				}
				sb.append(")");
			} else {
				sb.append(" AND status:" + status);
			}

		}

		if (!StringUtils.isEmpty(classOne)) {
			sb.append(" AND classOne:" + classOne);
		}

		if (!StringUtils.isEmpty(classTwo)) {
			sb.append(" AND classTwo:" + classTwo);
		}

		if (!StringUtils.isEmpty(classThree)) {
			sb.append(" AND classThree:" + classThree);
		}

		if (!StringUtils.isEmpty(categoryId)) {
			sb.append(" AND categoryId:" + categoryId);
		}
		if (!StringUtils.isEmpty(brand)) {
			sb.append(" AND brand:" + brand);
		}
		if (!StringUtils.isEmpty(suppId)) {
			sb.append(" AND cooperatorId:" + suppId);
		}

		// 单铺的查询
		if (!StringUtils.isEmpty(desc)) {
			sb.append(" AND promotdesc:" + EscapeUtil.escapeQueryChars(desc));
		}
		if (!StringUtils.isEmpty(skuId)) {
			sb.append(" AND skuId:" + skuId);
		}

		// 查询是否关联-主要查询挂靠关系
		if (!StringUtils.isEmpty(anchored)) {
			if (Integer.parseInt(anchored) == AnchoredTypeEnum.HAS_REL.getValue()) {
				sb.append(" AND -anchoredId:\"\"");
			} else if (Integer.parseInt(anchored) == AnchoredTypeEnum.NO_REL.getValue()) {
				sb.append(" AND anchoredId:\"\"");
			} else if (Integer.parseInt(anchored) == AnchoredTypeEnum.HAS_INTR.getValue()) {
				sb.append(" AND -introductionId:\"\"");
			}
		}

		if (!StringUtils.isEmpty(categoryName)) {
			sb.append(" AND categoryName:" + EscapeUtil.escapeQueryChars(categoryName));
		}
		if (!StringUtils.isEmpty(brandName)) {
			sb.append(" AND brandName:" + EscapeUtil.escapeQueryChars(brandName));
		}

		if (!StringUtils.isEmpty(onlySupp)) {
			sb.append(" AND -cooperatorId:" + onlySupp);
		}
		if (!StringUtils.isEmpty(cooPeratorType)) {
			sb.append(" AND cooPeratorType:" + cooPeratorType);
		}

		// logger.info("need except delete sku start");
		// 排除删除状态的商品
		sb.append(" AND -status:" + String.valueOf(SkuStatusEnum.STATUS_DEL.getValue()));
		// logger.info("need except delete sku end");

		int pageSize = param.getPageSize();
		int pageNumber = param.getPageNumber();

		pager.setPageNumber(pageNumber);
		pager.setPageSize(pageSize);

		pageNumber = pageNumber - 1;

		// 排序通过sort参数传递，格式为“字段名”+“空格”+ “ASC或者DESC”，多个排序字段通过“,”连接
		String sort = "";
		if (!StringUtils.isEmpty(param.getOrderBy())) {
			sort = param.getOrderBy() + " " + (param.isOrderAsc() == true ? "ASC" : "DESC");
			sort += ",lastupDate DESC";
		} else {
			sort = "lastupDate DESC";
		}
		UrlBuilder urlBuilder = UrlBuilder.create();
		urlBuilder.addPrefix("/select?").appendWithoutPrefix("q", sb.toString()).append("wt", "json")
				.append("indent", "false").append("start", (pageNumber * pageSize) + "").append("rows", pageSize + "")
				.append("sort", sort);

		// 高亮配置
		// if (StringUtils.hasText(hlField)){
		// urlBuilder.append("hl","true")
		// .append("hl.fl",hlField)
		// .append("hl.simple.pre",hlPre)
		// .append("hl.simple.post",hlPost);
		// }

		String requestUrl = urlBuilder.build();
		return requestUrl;
	}

	/**
	 * http访问
	 * 
	 * @param url
	 * @return
	 */
	public String execute(String url) {
		// httpClientBuilder = HttpClientBuilder.create();
		// httpClientBuilder.build();
		HttpClient httpClient = new DefaultHttpClient();
		// CloseableHttpClient httpClient = httpClientBuilder.build();
		String coreUrl = prop.getProperty("coreUrl");

		logger.info("execute core {}", coreUrl + url);
		HttpGet httpGet = new HttpGet(coreUrl + url);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			return EntityUtils.toString(entity);
		} catch (IOException e) {
			logger.error("execute get error", e);
			throw new SysException("execute solr fail", e);
		} finally {

		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		prop = getpro();
		logger.info("afterPropertiesSet prop {}", prop);

	}

	public Properties getpro() {
		UniconfigClient client = UniconfigClientImpl.getInstance();
		// UniconfigNode node = client.getConfig(nodeName);
		UniconfigNode node = client.getConfig(rageonSolrName);
		node.sync();
		String configValue = node.getValue();

		Properties properties = new Properties();
		InputStream in = new ByteArrayInputStream(configValue.getBytes());
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// properties.load(configValue);
		return properties;
	}

	@Override
	public boolean chkIsUsed(Map<String, Object> map) {

		// map.put("status", SkuStatusEnum.STATUS_UP.getValue());
		PagerParam param = new PagerParam();
		param.setPageNumber(1);
		param.setPageSize(10);
		Pager<SolrGoodsDto> page = searchGoods(map, param);
		if (page != null) {
			List<SolrGoodsDto> list = page.getDatas();

			if (list == null || list.isEmpty()) {
				logger.info("data is no used map{}", map);
				return false;
			}
		}
		return true;
	}

	@Override
	public Pager<SolrGoodsDto> queryGoodsByBarCode(String barCode, String cooperatorId, PagerParam param) {
		if (StringUtils.isEmpty(barCode)) {
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("barCode", barCode);
		map.put("cooperatorId", cooperatorId);
		Pager<SolrGoodsDto> page = searchGoods(map, param);
		return page;
	}

	@Override
	public Boolean checkBarCode(String barCode, String cooperatorId) {
		if (StringUtils.isEmpty(barCode)) {
			return false;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("barCode", barCode);
		map.put("suppId", cooperatorId);

		PagerParam pager = new PagerParam();
		pager.setPageSize(10);
		pager.setPageNumber(1);
		Pager<SolrGoodsDto> page = searchGoods(map, pager);
		return page.getTotalDataCount() > 0 ? true : false;
	}

	@Override
	public Pager<SolrGoodsDto> searchGoods(String companyId, String barCode, String goodsName, String desc,
			String skuId, PagerParam param) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("suppId", companyId);
		map.put("barCode", barCode);
		map.put("title", goodsName);
		map.put("desc", desc);
		map.put("skuId", skuId);
		map.put("status", 1);

		Pager<SolrGoodsDto> page = searchGoods(map, param);
		return page;
	}

	@Override
	public Pager<SolrGoodsDto> searchGoods(Map<String, Object> map, PagerParam param, int anchoredType) {
		if (anchoredType != AnchoredTypeEnum.ALL.getValue()) {
			map.put("anchored", anchoredType);
		}
		Pager<SolrGoodsDto> page = searchGoods(map, param);
		return page;
	}

	@Override
	public Pager<SolrGoodsDto> searchSuppGoods(Map<String, Object> map, PagerParam param, int anchoredType) {
		if (anchoredType != AnchoredTypeEnum.ALL.getValue()) {
			map.put("anchored", anchoredType);
		}
		// map.put("onlySupp", "1000");
		Pager<SolrGoodsDto> page = searchGoods(map, param);
		return page;
	}

	@Override
	public SolrGoodsDto getBySkuId(String skuId) {
		Map<String, Object> map = new HashMap<>();
		PagerParam param = new PagerParam();
		param.setPageNumber(1);
		param.setPageSize(10);
		map.put("skuId", skuId);
		Pager<SolrGoodsDto> page = searchGoods(map, param);
		List<SolrGoodsDto> datas = page.getDatas();
		if (datas != null && !datas.isEmpty()) {
			return datas.get(0);
		}
		return null;
	}
}
