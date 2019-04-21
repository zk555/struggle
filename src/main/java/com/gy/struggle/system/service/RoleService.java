package com.gy.struggle.system.service;

import com.gy.struggle.system.domain.RoleDO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    RoleDO get(Long id);

    List<RoleDO> list();

    List<RoleDO> list(Long userId);

    int save(RoleDO role);

    int remove(Long id);

    int batchremove(Long[] ids);

    int update(RoleDO role);
}
