package com.sunlight.invest.dao;

import java.util.List;

public interface BaseMapper<T> {
	
	int delete(T t);
	
	int count(T t);
	
	List<T> selectMany(T t);
	
	T selectOne(T t);
	
	int update(T t);

	int insertBatch(List<T> ts);

	int updateBatch(List<T> ts);

	public Object executeCustomSql(String sql);
}
