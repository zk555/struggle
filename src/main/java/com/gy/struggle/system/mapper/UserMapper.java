package com.gy.struggle.system.mapper;

import com.gy.struggle.common.redis.shiro.RedisManager;
import com.gy.struggle.system.domain.UserDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 09:45:11
 */
public interface UserMapper {

	UserDO get(Long userId);

	List<UserDO> getUserByName(String username,@Param("ids") List ids);

	List<UserDO> list(Map<String,Object> map);

	int count(Map<String,Object> map);

	int save(UserDO user);

	int batchRemove(Long[] userIds);

	int remove(Long userId);

	int update(UserDO user);

	Long[] listAllDept();

}
