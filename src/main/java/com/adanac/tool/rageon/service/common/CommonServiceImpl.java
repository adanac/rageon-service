package com.adanac.tool.rageon.service.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;
import com.adanac.framework.page.Pager;
import com.adanac.framework.web.controller.BaseResult;
import com.adanac.tool.rageon.constant.RageonConstant;
import com.adanac.tool.rageon.intf.common.entity.BootstrapPage;
import com.adanac.tool.rageon.intf.common.entity.CommonDto;
import com.adanac.tool.rageon.intf.common.service.BaseDao;
import com.adanac.tool.rageon.intf.common.service.CommonService;
import com.esotericsoftware.minlog.Log;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	BaseDao baseDao;

	private MyLogger log = MyLoggerFactory.getLogger(CommonServiceImpl.class);

	@Override
	public Pager<CommonDto> queryCommonDtoPage(CommonDto commonDto, BootstrapPage param, Integer flag) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("passwd", commonDto.getPasswd());
		paramMap.put("age", commonDto.getAge());
		if (flag == RageonConstant.NUM_0) {
			paramMap.put("id", commonDto.getId());
			paramMap.put("username", commonDto.getUsername());
			log.info("queryPage {}", paramMap);
			return baseDao.queryForPage("tabCommon.select", paramMap, CommonDto.class, param.getPageSize(),
					param.getPageNumber());
		} else if (flag == RageonConstant.NUM_1) {
			paramMap.put("id", commonDto.getId() == null ? "" : commonDto.getId() + "%");
			paramMap.put("username", commonDto.getUsername() == null ? "" : "%" + commonDto.getUsername() + "%");
			return baseDao.queryForPage("tabCommon.queryCond", paramMap, CommonDto.class, param.getPageSize(),
					param.getPageNumber());
		}
		return null;
	}

	@Override
	public List<CommonDto> queryCommonDtoList(CommonDto commonDto) {
		log.info("====queryCommonDtoList===={commonDto:" + commonDto.toString() + "}");
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", commonDto.getId());
			paramMap.put("username", commonDto.getUsername());
			paramMap.put("passwd", commonDto.getPasswd());
			paramMap.put("age", commonDto.getAge());
			List<CommonDto> commonDtoList = baseDao.queryForList("tabCommon.select", paramMap, CommonDto.class);
			return commonDtoList;
		} catch (Exception e) {
			log.error("====queryCommonDtoList====error:" + e.getMessage());
		}
		return null;
	}

	@Override
	public BaseResult addCommonDto(CommonDto commonDto) {
		Log.info("===addCommonDto==={commonDto:" + commonDto.toString() + "}");
		BaseResult br = new BaseResult();
		try {
			int count = baseDao.execute("tabTest.insert", commonDto);
			br.setContent(count);
			br.setStatus(BaseResult.SUCCESS);
		} catch (Exception e) {
			Log.error("addCommonDto Error:" + e.getMessage());
			e.printStackTrace();
			br.setContent("addCommonDto error");
			br.setStatus(BaseResult.ERROR);
		}
		return br;
	}

	@Override
	public BaseResult addCommonDto(List<CommonDto> commonDtoList) {
		Log.info("===addCommonDto==={commonDtoList:" + commonDtoList.size() + "}");
		BaseResult br = new BaseResult();
		Map<String, Object>[] maps = new HashMap[commonDtoList.size()];
		try {
			int index = 0;
			for (CommonDto dto : commonDtoList) {
				Map<String, Object> map = dto.toMap(); // 封装map数据
				maps[index++] = map;
			}
			int[] res = baseDao.batchUpdate("tabCommon.insert", maps);
			br.setContent(res);
			br.setStatus(BaseResult.SUCCESS);
		} catch (Exception e) {
			Log.error("addCommonDto Error:" + e.getMessage());
			e.printStackTrace();
			br.setContent("addCommonDto error");
			br.setStatus(BaseResult.ERROR);
		}
		return br;
	}

	@Override
	public BaseResult getCommonDtoByID(String id) {
		log.info("====getCommonDtoByID===={id:" + id + "}");
		BaseResult br = new BaseResult();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", id);
			log.info("getFromDB {} ", paramMap);
			CommonDto commonDto = baseDao.queryForObject("tabCommon.select", paramMap, CommonDto.class);
			br.setContent(commonDto);
			br.setStatus(BaseResult.SUCCESS);
		} catch (Exception e) {
			log.info("====getCommonDtoByID====error:" + e.getMessage());
			br.setStatus(BaseResult.ERROR);
		}
		return br;
	}

	@Override
	public BaseResult modCommonDto(String id) {
		BaseResult br = new BaseResult();
		try {
			log.info("====modCommonDto===={id:" + id + "}");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", id);
			CommonDto commonDto = baseDao.queryForObject("tabCommon.select", paramMap, CommonDto.class);
			if (commonDto != null) {
				int count = baseDao.execute("tabCommon.update", commonDto);
				br.setContent(count);
				br.setStatus(count > 0 ? BaseResult.SUCCESS : BaseResult.ERROR);
			}
		} catch (Exception e) {
			log.error("====modCommonDto====error:" + e.getMessage());
			br.setStatus(BaseResult.ERROR);
		}
		return br;
	}

	@Override
	public BaseResult delCommonDto(String id) {
		log.info("====delCommonDto===={id:" + id + "}");
		BaseResult br = new BaseResult();
		try {
			Map<String, Object> pmap = new HashMap<String, Object>();
			pmap.put("id", id);
			int res = baseDao.execute("tabCommon.delete", pmap);
			br.setContent(res);
			br.setStatus(BaseResult.SUCCESS);
		} catch (Exception e) {
			log.error("====delCommonDto====error:" + e.getMessage());
			br.setStatus(BaseResult.ERROR);
		}
		return br;
	}

}
