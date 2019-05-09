package com.gy.struggle.common.controller;

import com.gy.struggle.common.utils.ShiroUtils;
import com.gy.struggle.system.domain.UserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public UserDO getUser() {
		return  ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
}