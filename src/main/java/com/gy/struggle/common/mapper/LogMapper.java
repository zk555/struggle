package com.gy.struggle.common.mapper;

import com.gy.struggle.common.domain.LogDO;

import java.util.List;
import java.util.Map;

/**
 * 系统日志
 */

public interface LogMapper {

	LogDO get(Long id);

	List<LogDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(LogDO log);
	
	int update(LogDO log);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
