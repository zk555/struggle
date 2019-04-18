package com.gy.struggle.system.service.impl;

import com.gy.struggle.system.domain.RoleDO;
import com.gy.struggle.system.domain.RoleMenuDO;
import com.gy.struggle.system.mapper.RoleMapper;
import com.gy.struggle.system.mapper.RoleMenuMapper;
import com.gy.struggle.system.mapper.UserMapper;
import com.gy.struggle.system.mapper.UserRoleMapper;
import com.gy.struggle.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Service
public class RoleServiceImpl implements RoleService {

    public static final String ROLE_ALL_KEY = "\"role_all\"";

    public static final String DEMO_CACHE_NAME = "role";


}
