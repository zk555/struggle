package com.gy.struggle.common.mapper;

import com.gy.struggle.common.domain.DictDO;

import java.util.List;
import java.util.Map;

/**
 * 字典表
 *
 */
public interface DictMapper {

	DictDO get(Long id);

	List<DictDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(DictDO dict);

	int update(DictDO dict);

	int remove(Long id);

	int batchRemove(Long[] ids);

	List<DictDO> listType();
}
