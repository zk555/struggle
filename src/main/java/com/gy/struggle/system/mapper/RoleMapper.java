package com.gy.struggle.system.mapper;

import com.gy.struggle.system.domain.RoleDO;

import java.util.List;
import java.util.Map;

/**
 * 角色mapper
 */
public interface RoleMapper {

	RoleDO get(Long roleId);

	List<RoleDO> list(Map<String, Object> map);

	int save(RoleDO role);

	int remove(Long roleId);

	int batchRemove(Long[] roleIds);

	int update(RoleDO role);

}
