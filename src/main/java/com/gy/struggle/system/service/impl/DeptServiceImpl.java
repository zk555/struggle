package com.gy.struggle.system.service.impl;

import com.gy.struggle.system.domain.DeptDO;
import com.gy.struggle.system.mapper.DeptMapper;
import com.gy.struggle.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper sysDeptMapper;

    @Override
    public DeptDO get(Long deptId) {
        return sysDeptMapper.get(deptId);
    }

    @Override
    public List<Long> listChildrenIds(Long parentId) {
        List<DeptDO> deptDOS = list(null);
        return treeMenuList(deptDOS, parentId);
    }
    @Override
    public List<DeptDO> list(Map<String, Object> map) {
        return sysDeptMapper.list(map);
    }

    List<Long> treeMenuList(List<DeptDO> menuList, long pid) {
        List<Long> childIds = new ArrayList<>();
        for (DeptDO mu : menuList) {
            //遍历出父id等于参数的id，add进子节点集合
            if (mu.getParentId() == pid) {
                //递归遍历下一级
                treeMenuList(menuList, mu.getDeptId());
                childIds.add(mu.getDeptId());
            }
        }
        return childIds;
    }
}
