package com.gy.struggle.common.service;

import com.gy.struggle.common.domain.TaskDO;
import org.quartz.SchedulerException;

import java.util.List;
import java.util.Map;

/**
 * 任务管理系统
 *
 */
public interface JobService {
	
	TaskDO get(Long id);
	
	List<TaskDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	void initSchedule() throws SchedulerException;

	int update(TaskDO taskScheduleJob);

	int remove(Long id);

	int batchRemove(Long[] ids);

	int save(TaskDO taskScheduleJob);

	void changeStatus(Long jobId, String cmd) throws SchedulerException;

}
