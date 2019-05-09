package com.gy.struggle.system.mapper;

import com.gy.struggle.system.domain.DeptDO;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 */
public interface DeptMapper {

	DeptDO get(Long deptId);

	List<DeptDO> list(Map<String,Object> map);

	int save(DeptDO dept);

	int count(Map<String,Object> map);

	int getDeptUserNumber(Long deptId);

	int remove(Long deptId);

	int batchRemove(Long[] deptIds);

	int update(DeptDO dept);

	Long[] listParentDept();
}
