package com.gy.struggle.common.mapper;


import java.util.List;
import java.util.Map;

import com.gy.struggle.common.domain.FileDO;

/**
 * 文件上传
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 15:45:42
 */

public interface FileMapper {

	FileDO get(Long id);
	
	List<FileDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(FileDO file);
	
	int update(FileDO file);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
