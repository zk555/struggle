package com.gy.struggle.oa.mapper;

import com.gy.struggle.oa.domain.NotifyDO;
import com.gy.struggle.oa.domain.NotifyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 通知通告
 *
 */
@Mapper
public interface NotifyMapper {

	NotifyDO get(Long id);

	List<NotifyDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(NotifyDO notify);

	int remove(Long id);

	int batchRemove(Long[] ids);

	int update(NotifyDO notify);

	List<NotifyDTO> listDTO(Map<String, Object> map);

	int countDTO(Map<String, Object> map);

}
