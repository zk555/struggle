package com.gy.struggle.system.controller;

import com.gy.struggle.common.annotation.Log;
import com.gy.struggle.common.config.Constant;
import com.gy.struggle.common.controller.BaseController;
import com.gy.struggle.common.domain.Tree;
import com.gy.struggle.common.utils.MD5Utils;
import com.gy.struggle.common.utils.PageUtils;
import com.gy.struggle.common.utils.Query;
import com.gy.struggle.common.utils.R;
import com.gy.struggle.system.domain.DeptDO;
import com.gy.struggle.system.domain.RoleDO;
import com.gy.struggle.system.domain.UserDO;
import com.gy.struggle.system.service.RoleService;
import com.gy.struggle.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/sys/user")
@Controller
public class UserController extends BaseController {

	private String prefix="system/user"  ;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	@RequiresPermissions("sys:user:user")
	@GetMapping("")
	String user(Model model) {
		return prefix + "/user";
	}

	@GetMapping("/list")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<UserDO> sysUserList = userService.list(query);
		int total = userService.count(query);
		PageUtils pageUtil = new PageUtils(sysUserList, total);
		return pageUtil;
	}


}
