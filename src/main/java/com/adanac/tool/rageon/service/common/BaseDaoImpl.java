package com.adanac.tool.rageon.service.common;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.adanac.framework.dac.client.IPaginationDacClient;
import com.adanac.framework.dac.pagination.PaginationResult;
import com.adanac.framework.page.Pager;
import com.adanac.tool.rageon.intf.common.service.BaseDao;

/**
 * BaseDao的实现类，对数据库进行操作的封装类
 * 
 */
@Repository
public class BaseDaoImpl implements BaseDao {

	@Autowired
	private IPaginationDacClient dalClient;

	@Override
	public Number persist(Object entity) {
		return dalClient.persist(entity);
	}

	@Override
	public <T> T persist(Object entity, Class<T> requiredType) {
		return dalClient.persist(entity, requiredType);
	}

	@Override
	public int merge(Object entity) {
		return dalClient.merge(entity);
	}

	@Override
	public int dynamicMerge(Object entity) {
		return dalClient.dynamicMerge(entity);
	}

	@Override
	public int remove(Object entity) {
		return dalClient.remove(entity);
	}

	@Override
	public <T> T find(Class<T> entityClass, Object entity) {
		return dalClient.find(entityClass, entity);
	}

	@Override
	public <T> T queryForObject(String sqlId, Map<String, Object> paramMap, Class<T> requiredType) {
		return dalClient.queryForObject(sqlId, paramMap, requiredType);
	}

	@Override
	public <T> T queryForObject(String sqlId, Object param, Class<T> requiredType) {
		return dalClient.queryForObject(sqlId, param, requiredType);
	}

	@Override
	public Map<String, Object> queryForMap(String sqlId, Map<String, Object> paramMap) {
		return dalClient.queryForMap(sqlId, paramMap);
	}

	@Override
	public Map<String, Object> queryForMap(String sqlId, Object param) {
		return dalClient.queryForMap(sqlId, param);
	}

	@Override
	public <T> List<T> queryForList(String sqlId, Map<String, Object> paramMap, Class<T> elementType) {
		return dalClient.queryForList(sqlId, paramMap, elementType);
	}

	@Override
	public <T> List<T> queryForList(String sqlId, Object param, Class<T> requiredType) {
		return dalClient.queryForList(sqlId, param, requiredType);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sqlId, Map<String, Object> paramMap) {
		return dalClient.queryForList(sqlId, paramMap);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sqlId, Object param) {
		return dalClient.queryForList(sqlId, param);
	}

	@Override
	public int execute(String sqlId, Map<String, Object> paramMap) {
		return dalClient.execute(sqlId, paramMap);
	}

	@Override
	public int execute(String sqlId, Object param) {
		return dalClient.execute(sqlId, param);
	}

	@Override
	public int[] batchUpdate(String sqlId, Map<String, Object>[] batchValues) {
		return dalClient.batchUpdate(sqlId, batchValues);
	}

	/**
	 * 分布查询，
	 * sqlCountId:总记录数的SQL语句id
	 * pageNumber从1开始
	 * paramMap:查询条件
	 */
	@Override
	public <T> Pager<T> queryForPage(String sqlId, Map<String, Object> paramMap, Class<T> elementType, int pageSize,
			int pageNumber) {

		Pager<T> result = new Pager<T>(pageSize, pageNumber);

		PaginationResult<T> pr = dalClient.queryForList(sqlId, paramMap, elementType, result.getIndexNumber(),
				pageSize);

		result.setDatas(pr.getResult());

		result.setTotalDataCount(pr.getCount());

		return result;
	}

	/**
	* 分布查询，
	* sqlCountId:总记录数的SQL语句id
	* pageNumber从1开始
	* paramMap:查询条件
	*/
	public <T> Pager<T> queryForPage(String sqlId, Map<String, Object> paramMap, RowMapper<T> rowMapper, int pageSize,
			int pageNumber) {

		Pager<T> result = new Pager<T>(pageSize, pageNumber);

		PaginationResult<T> pr = dalClient.queryForList(sqlId, paramMap, rowMapper, result.getIndexNumber(), pageSize);

		result.setDatas(pr.getResult());

		result.setTotalDataCount(pr.getCount());

		return result;
	}

	@Override
	public Number execute4PrimaryKey(String sqlId, Map<String, Object> paramMap) {
		return dalClient.execute4PrimaryKey(sqlId, paramMap);
	}
}
