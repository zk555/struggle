package com.gy.struggle.system.service;

import com.gy.struggle.common.domain.Tree;
import com.gy.struggle.system.domain.DeptDO;
import com.gy.struggle.system.domain.UserDO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface UserService {

	UserDO get(Long id);

}
