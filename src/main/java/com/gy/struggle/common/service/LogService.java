package com.gy.struggle.common.service;

import com.gy.struggle.common.domain.LogDO;
import com.gy.struggle.common.domain.PageDO;
import com.gy.struggle.common.utils.Query;
import org.springframework.stereotype.Service;

@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
