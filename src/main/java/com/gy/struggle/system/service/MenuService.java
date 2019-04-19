package com.gy.struggle.system.service;

import com.gy.struggle.common.domain.Tree;
import com.gy.struggle.system.domain.MenuDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface MenuService {


	List<Tree<MenuDO>> listMenuTree(Long id);

	Set<String> listPerms(Long userId);
}
