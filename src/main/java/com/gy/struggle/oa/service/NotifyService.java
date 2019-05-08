package com.gy.struggle.oa.service;


import com.gy.struggle.common.utils.PageUtils;
import com.gy.struggle.oa.domain.NotifyDO;

import java.util.List;
import java.util.Map;

/**
 * 通知通告
 *
 */
public interface NotifyService {

	NotifyDO get(Long id);

	List<NotifyDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(NotifyDO notify);

	int remove(Long id);

	int batchRemove(Long[] ids);

	int update(NotifyDO notify);

	PageUtils selfList(Map<String, Object> map);
}
