package com.adanac.tool.rageon.service.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adanac.tool.rageon.common.service.BaseDao;
import com.adanac.tool.rageon.menu.entity.MenuDto;
import com.adanac.tool.rageon.menu.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	BaseDao baseDao;

	@Override
	public List<MenuDto> listMenu(MenuDto menuDto) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", menuDto.getId());
		paramMap.put("pid", menuDto.getPId());
		paramMap.put("name", menuDto.getName());
		paramMap.put("createTime", menuDto.getCreateTime());
		paramMap.put("updateTime", menuDto.getUpdateTime());
		List<MenuDto> res = baseDao.queryForList("tb_Menu.queryCond", paramMap, MenuDto.class);
		return res;
	}

	@Override
	public Integer addMenu(MenuDto menu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateMenu(MenuDto menu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delMenu(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MenuDto getMenu(Integer id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		MenuDto menuDto = baseDao.queryForObject("tb_Menu.queryOne", paramMap, MenuDto.class);
		return menuDto;
	}

}
