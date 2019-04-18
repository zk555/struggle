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

}
