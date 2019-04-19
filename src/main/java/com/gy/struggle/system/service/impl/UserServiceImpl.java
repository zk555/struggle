package com.gy.struggle.system.service.impl;

import com.gy.struggle.common.config.StruggleConfig;
import com.gy.struggle.common.domain.FileDO;
import com.gy.struggle.common.service.FileService;
import com.gy.struggle.common.utils.BuildTree;
import com.gy.struggle.common.utils.FileUtil;
import com.gy.struggle.common.utils.MD5Utils;
import com.gy.struggle.common.utils.StringUtils;
import com.gy.struggle.system.domain.UserDO;
import com.gy.struggle.system.domain.UserRoleDO;
import com.gy.struggle.system.mapper.DeptMapper;
import com.gy.struggle.system.mapper.UserMapper;
import com.gy.struggle.system.mapper.UserRoleMapper;
import com.gy.struggle.system.service.DeptService;
import com.gy.struggle.system.service.UserService;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.*;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    DeptMapper deptMapper;
    @Autowired
    private FileService sysFileService;
    @Autowired
    private StruggleConfig struggleConfig;
    @Autowired
    DeptService deptService;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
//    @Cacheable(value = "user",key = "#id")
    public UserDO get(Long id) {
        List<Long> roleIds = userRoleMapper.listRoleId(id);
        UserDO user = userMapper.get(id);
        user.setDeptName(deptMapper.get(user.getDeptId()).getName());
        user.setRoleIds(roleIds);
        return user;
    }

    @Override
    public List<UserDO> list(Map<String, Object> map) {
        String deptId = map.get("deptId").toString();
        if (StringUtils.isNotBlank(deptId)) {
            Long deptIdl = Long.valueOf(deptId);
            List<Long> childIds = deptService.listChildrenIds(deptIdl);
            childIds.add(deptIdl);
            map.put("deptId", null);
            map.put("deptIds",childIds);
        }
        return userMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return userMapper.count(map);
    }
}
