package com.adanac.tool.rageon.service.common;

import org.springframework.stereotype.Service;

import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.bn.disconf.client.common.annotations.DisconfItem;

/**
 * 统一配置服务
 */
@Service
public class RageonUniConfig {

	public MyLogger log = MyLoggerFactory.getLogger(RageonUniConfig.class);

	private String secretKey;// app server secrect key
	private String solrUrl;// solr url
	private String companyCode;// company code

	@DisconfItem(key = "mmcApp_secretKey")
	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		log.info("secretKey updated: old={}, new={}.", this.secretKey, secretKey);
		this.secretKey = secretKey;
	}

	@DisconfItem(key = "mmcApp_solrUrl")
	public String getSolrUrl() {
		return solrUrl;
	}

	public void setSolrUrl(String solrUrl) {
		log.info("solrUrl updated: old={}, new={}.", this.solrUrl, solrUrl);
		this.solrUrl = solrUrl;
	}

	@DisconfItem(key = "mmcApp_companyCode")
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		log.info("companyCode updated: old={}, new={}.", this.companyCode, companyCode);
		this.companyCode = companyCode;
	}

}
