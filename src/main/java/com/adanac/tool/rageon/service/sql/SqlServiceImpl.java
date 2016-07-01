package com.adanac.tool.rageon.service.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adanac.framework.web.controller.BaseResult;
import com.adanac.tool.rageon.intf.common.entity.CommonDto;
import com.adanac.tool.rageon.intf.common.service.BaseDao;
import com.adanac.tool.rageon.intf.sql.SqlService;
import com.esotericsoftware.minlog.Log;

@Service
public class SqlServiceImpl implements SqlService {

	@Autowired
	BaseDao baseDao;

	@Override
	public BaseResult insertDB(String username, String pwd, Integer age) {
		Log.info("===insertDB==={username:" + username + ",pwd" + pwd + ",age" + age + "}");
		BaseResult br = new BaseResult();
		try {
			CommonDto dto = new CommonDto();
			dto.setUsername(username);
			dto.setPasswd(pwd);
			dto.setAge(age);
			int count = baseDao.execute("tabTest.insert", dto);
			br.setContent(count);
			br.setStatus(BaseResult.SUCCESS);
		} catch (Exception e) {
			Log.error("insertDB Error:" + e.getMessage());
			e.printStackTrace();
			br.setContent("insertDB error");
			br.setStatus(BaseResult.ERROR);
		}
		return br;
	}

	@Override
	public BaseResult queryDB(String username, String pwd) {
		Log.info("===queryDB==={username:" + username + ",pwd" + pwd + "}");
		BaseResult br = new BaseResult();
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("username", username);
			paramMap.put("pwd", pwd);
			List<Map<String, Object>> mapList = baseDao.queryForList("tabTest.select", paramMap);
			br.setContent(mapList);
			br.setStatus(BaseResult.SUCCESS);
		} catch (Exception e) {
			Log.error("queryDB Error===>", e.getMessage());
			br.setContent("queryDB error");
			br.setStatus(BaseResult.ERROR);
		}
		return br;
	}

	@Override
	public BaseResult queryList(String username) {
		Log.info("===queryList==={username:" + username + "}");
		BaseResult br = new BaseResult();
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("username", username);
			List<Map<String, Object>> mapList = baseDao.queryForList("tabTest.queryList", paramMap);
			br.setContent(mapList);
			br.setStatus(BaseResult.SUCCESS);
		} catch (Exception e) {
			Log.error("queryList Error===>", e.getMessage());
			br.setContent("queryList error");
			br.setStatus(BaseResult.ERROR);
		}
		return br;
	}

	@Override
	public BaseResult queryIn(String[] username) {
		Log.info("===queryIn==={username:" + username.toString() + "}");
		BaseResult br = new BaseResult();
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("username", username);
			List<Map<String, Object>> mapList = baseDao.queryForList("tabTest.queryIn", paramMap);
			br.setContent(mapList);
			br.setStatus(BaseResult.SUCCESS);
		} catch (Exception e) {
			Log.error("queryIn Error===>", e.getMessage());
			br.setContent("queryIn error");
			br.setStatus(BaseResult.ERROR);
		}
		return br;
	}

}
