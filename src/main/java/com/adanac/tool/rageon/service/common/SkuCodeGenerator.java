package com.adanac.tool.rageon.service.common;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import com.adanac.framework.exception.SysException;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.tool.rageon.intf.common.service.BaseDao;

@Service
public class SkuCodeGenerator {

	@Autowired
	private BaseDao baseDao;

	private MyLogger logger = MyLoggerFactory.getLogger(SkuCodeGenerator.class);

	private String formatSeq(Integer n, Long seq) {
		StringBuffer formatStyle = new StringBuffer();
		for (int i = 0; i < n; i++) {
			formatStyle.append('0');
		}
		DecimalFormat format = new DecimalFormat(formatStyle.toString());
		return format.format(seq);
	}

	public Long getOuterSequence() {
		String sequenceId = "localCode";
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Map<String, Object> retMap = null;
			paramMap.put("SEQ_NAME", sequenceId);
			SqlOutParameter outParameter = new SqlOutParameter("SEQ_VAL", java.sql.Types.BIGINT);
			SqlParameter inParameter = new SqlParameter("SEQ_NAME", java.sql.Types.VARCHAR);
			List<SqlParameter> sqlParameters = new ArrayList<SqlParameter>();
			sqlParameters.add(inParameter);
			sqlParameters.add(outParameter);
			retMap = baseDao.call("sequence.getSequence", paramMap, sqlParameters);
			return (Long) retMap.get("SEQ_VAL");
		} catch (Exception e) {
			logger.error("系统异常，异常信息:[{}]", e);
			throw new SysException();
		}
	}
}
