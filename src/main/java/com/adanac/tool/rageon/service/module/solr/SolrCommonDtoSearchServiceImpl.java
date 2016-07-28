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
import org.springframework.stereotype.Service;

import com.adanac.framework.exception.SysException;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.page.Pager;
import com.adanac.framework.page.PagerParam;
import com.adanac.framework.uniconfig.client.UniconfigClient;
import com.adanac.framework.uniconfig.client.UniconfigClientImpl;
import com.adanac.framework.uniconfig.client.UniconfigNode;
import com.adanac.framework.utils.StringUtils;
import com.adanac.tool.rageon.intf.common.entity.CommonDto;
import com.adanac.tool.rageon.intf.module.solr.service.SolrCommonSearchService;
import com.adanac.tool.rageon.utils.UrlBuilder;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service("SolrCommonDtoSearchServiceImpl")
public class SolrCommonDtoSearchServiceImpl implements SolrCommonSearchService, InitializingBean {

	private MyLogger logger = MyLoggerFactory.getLogger(SolrCommonDtoSearchServiceImpl.class);

	@Override
	public Pager<CommonDto> queryDtos(CommonDto dto, PagerParam pagerParam) {
		Pager<CommonDto> dtos = null;
		try {
			PagerParam param = new PagerParam();
			param.setPageNumber(pagerParam.getPageNumber());
			param.setPageSize(pagerParam.getPageSize());
			param.setOrderBy("age");
			param.setOrderAsc(false);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", dto.getId());
			map.put("username", dto.getUsername());
			map.put("passwd", dto.getPasswd());
			map.put("age", dto.getAge());

			Pager<CommonDto> list = searchCommonDtos(map, param);
			if (list.getDatas() == null) {
				List<CommonDto> listData = new ArrayList<CommonDto>();
				list.setDatas(listData);
			}
			dtos = new Pager<CommonDto>(list.getTotalDataCount(), pagerParam.getPageSize(), pagerParam.getPageNumber());
		} catch (Exception e) {
			dtos = null;
			e.printStackTrace();
		}
		return dtos;
	}

	private Properties prop;

	@Override
	public Pager<CommonDto> searchCommonDtos(Map<String, Object> map, PagerParam param) {
		Pager<CommonDto> pager = new Pager<CommonDto>();
		try {
			// 组装solr查询链接
			String requestUrl = assmbleSearchParams(map, param, pager);

			List<CommonDto> sList = new ArrayList<CommonDto>();
			logger.info("searchDtos url {}", requestUrl);

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
				sList.add(JSONObject.toJavaObject(json, CommonDto.class));
			}
			for (CommonDto dto : sList) {
				logger.info(dto.toString());
			}
			// logger.info("queryBytitle result : {}", result);
			pager.setDatas(sList);
			return pager;
		} catch (Exception e) {
			logger.error("searchDtos fail", e);
			return pager;
		}
	}

	@Override
	public CommonDto getCommonDtoById(String id) {
		Map<String, Object> map = new HashMap<>();
		PagerParam param = new PagerParam();
		param.setPageNumber(1);
		param.setPageSize(10);
		map.put("userId", id);
		Pager<CommonDto> page = searchCommonDtos(map, param);
		List<CommonDto> datas = page.getDatas();
		if (datas != null && !datas.isEmpty()) {
			return datas.get(0);
		}
		return null;
	}

	@Override
	public CommonDto getCommonDtoByEntity(CommonDto dto) {
		Map<String, Object> map = new HashMap<>();
		PagerParam param = new PagerParam();
		param.setPageNumber(1);
		param.setPageSize(10);
		map.put("username", dto.getUsername());
		map.put("passwd", dto.getPasswd());
		Pager<CommonDto> page = searchCommonDtos(map, param);
		List<CommonDto> datas = page.getDatas();
		if (datas != null && !datas.isEmpty()) {
			return datas.get(0);
		}
		return null;
	}

	/**
	 * 组装查询的链接
	 * 
	 * @param map
	 * @param param
	 * @param pager
	 * @return
	 */
	private String assmbleSearchParams(Map<String, Object> map, PagerParam param, Pager<CommonDto> pager) {
		String id = map.get("id") == null ? "" : map.get("id").toString();
		String username = map.get("username") == null ? "" : map.get("username").toString();
		String passwd = map.get("passwd") == null ? "" : map.get("passwd").toString();

		StringBuffer sb = new StringBuffer();
		if (StringUtils.isEmpty(username)) {
			username = "*";
		}
		if (!StringUtils.isEmpty(id)) {
			sb.append(" AND id:" + id);
		}
		if (!StringUtils.isEmpty(passwd)) {
			sb.append(" AND passwd:" + passwd);
		}

		int pageSize = param.getPageSize();
		int pageNumber = param.getPageNumber();

		pager.setPageNumber(pageNumber);
		pager.setPageSize(pageSize);

		pageNumber = pageNumber - 1;

		// 排序通过sort参数传递，格式为“字段名”+“空格”+ “ASC或者DESC”，多个排序字段通过“,”连接
		String sort = "";
		if (!StringUtils.isEmpty(param.getOrderBy())) {
			sort = param.getOrderBy() + " " + (param.isOrderAsc() == true ? "ASC" : "DESC");
			sort += ",age DESC";
		} else {
			sort = "age DESC";
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
		// prop = getpro();
		logger.info("afterPropertiesSet prop {}", prop);

	}

	public Properties getpro() {
		UniconfigClient client = UniconfigClientImpl.getInstance();
		UniconfigNode node = client.getConfig("rageon_solr");
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

}
