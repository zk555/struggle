package com.gy.struggle.system.mapper;


import com.gy.struggle.system.domain.UserRoleDO;

import java.util.List;
import java.util.Map;

/**
 * 用户与角色对应关系
 */
public interface UserRoleMapper {

	UserRoleDO get(Long id);

	List<UserRoleDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(UserRoleDO userRole);

	List<Long> listRoleId(Long id);

	int removeByUserId(Long userId);

	int batchSave(List<UserRoleDO> list);

	int batchRemoveByUserId(Long[] ids);

	int removeByRoleId(Long roleId);
}
