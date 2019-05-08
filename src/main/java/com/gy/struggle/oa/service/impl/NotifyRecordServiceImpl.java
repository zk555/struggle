package com.gy.struggle.oa.service.impl;

import com.gy.struggle.oa.domain.NotifyRecordDO;
import com.gy.struggle.oa.mapper.NotifyRecordMapper;
import com.gy.struggle.oa.service.NotifyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class NotifyRecordServiceImpl implements NotifyRecordService {

	@Autowired
	private NotifyRecordMapper notifyRecordMapper;
	
	@Override
	public NotifyRecordDO get(Long id){
		return notifyRecordMapper.get(id);
	}
	
	@Override
	public List<NotifyRecordDO> list(Map<String, Object> map){
		return notifyRecordMapper.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return notifyRecordMapper.count(map);
	}
	
	@Override
	public int save(NotifyRecordDO notifyRecord){
		return notifyRecordMapper.save(notifyRecord);
	}
	
	@Override
	public int update(NotifyRecordDO notifyRecord){
		return notifyRecordMapper.update(notifyRecord);
	}
	
	@Override
	public int remove(Long id){
		return notifyRecordMapper.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return notifyRecordMapper.batchRemove(ids);
	}

	@Override
	public int changeRead(NotifyRecordDO notifyRecord) {
		return notifyRecordMapper.changeRead(notifyRecord);
	}

}
