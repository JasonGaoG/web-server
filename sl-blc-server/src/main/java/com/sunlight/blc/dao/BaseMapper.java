package com.sunlight.blc.dao;

import java.util.List;

public interface BaseMapper<T> {
	
	int delete(T t);
	
	int count(T t);
	
	List<T> selectMany(T t);
	
	T selectOne(T t);
	
	int update(T t);
	
	int insertBatch(List<T> list);

	int updateBatch(List<T> list);
	
	int logicDelete(T t);
	
	int deleteBatch(List<T> list);
	
	Object excuteCustomSql(String sql);
	
}
