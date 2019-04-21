package com.gy.struggle.system.service;


import com.gy.struggle.common.domain.Tree;
import com.gy.struggle.system.domain.DeptDO;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-27 14:28:36
 */
public interface DeptService {
	
	DeptDO get(Long deptId);

	List<Long> listChildrenIds(Long parentId);

	List<DeptDO> list(Map<String, Object> map);

	Tree<DeptDO> getTree();

	int save(DeptDO sysDept);

	int count(Map<String, Object> map);

	boolean checkDeptHasUser(Long deptId);

	int remove(Long deptId);

	int batchRemove(Long[] deptIds);

	int update(DeptDO sysDept);
}
