package com.gy.struggle.common.mapper;

import com.gy.struggle.common.domain.TaskDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * zk
 * 任务管理mapper
 */
@Mapper
public interface TaskMapper {

	TaskDO get(Long id);

	List<TaskDO> list(Map<String, Object> map);

	int count(Map<String,Object> map);

	int update(TaskDO task);

	int remove(Long id);

	int batchRemove(Long[] ids);

	int save(TaskDO task);

}
