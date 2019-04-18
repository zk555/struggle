package com.gy.struggle.system.mapper;

import com.gy.struggle.system.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 09:45:11
 */
@Mapper
public interface UserMapper {

	UserDO get(Long userId);

}
