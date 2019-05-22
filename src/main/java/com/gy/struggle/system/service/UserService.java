package com.gy.struggle.system.service;

import com.gy.struggle.common.domain.Tree;
import com.gy.struggle.system.domain.DeptDO;
import com.gy.struggle.system.domain.UserDO;
import com.gy.struggle.system.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface UserService {

	UserDO get(Long id);

	List<UserDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(UserDO user);

	int batchremove(Long[] userIds);

	int remove(Long userId);

	int update(UserDO user);

	int resetPwd(UserVO userVO,UserDO userDO) throws Exception;

	int adminResetPwd(UserVO userVO) throws Exception;

	Tree<DeptDO> getTree();

	/**
	 * 更新个人图片
	 * @param file 图片
	 * @param avatar_data 裁剪信息
	 * @param userId 用户ID
	 * @throws Exception
	 */
	Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception;

}
