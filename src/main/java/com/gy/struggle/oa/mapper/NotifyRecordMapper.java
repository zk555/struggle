package com.gy.struggle.oa.mapper;

import com.gy.struggle.oa.domain.NotifyRecordDO;

import java.util.List;
import java.util.Map;

/**
 * 通知通告发送记录
 *
 */
public interface NotifyRecordMapper {

	NotifyRecordDO get(Long id);

	List<NotifyRecordDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(NotifyRecordDO notifyRecord);

	int update(NotifyRecordDO notifyRecord);

	int remove(Long id);

	int batchRemove(Long[] ids);

	int batchSave(List<NotifyRecordDO> records);

	Long[] listNotifyIds(Map<String, Object> map);

	int removeByNotifbyId(Long notifyId);

	int batchRemoveByNotifbyId(Long[] notifyIds);

	int changeRead(NotifyRecordDO notifyRecord);
}
