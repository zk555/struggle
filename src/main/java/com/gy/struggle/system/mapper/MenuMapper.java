package com.gy.struggle.system.mapper;


import java.util.List;
import java.util.Map;

import com.gy.struggle.system.domain.MenuDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单管理
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 09:45:09
 */
@Mapper
public interface MenuMapper {

	List<MenuDO> listMenuByUserId(Long id);

	List<String> listUserPerms(Long id);

	List<MenuDO> list(Map<String,Object> map);

	MenuDO get(Long menuId);

	int save(MenuDO menu);

	int remove(Long menuId);

	int update(MenuDO menu);
}
