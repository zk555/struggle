package com.gy.struggle.common.service;

import com.gy.struggle.common.domain.FileDO;

import java.util.List;
import java.util.Map;

/**
 * 文件上传
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-19 16:02:20
 */
public interface FileService {
	
	FileDO get(Long id);

	/**
	 * 判断一个文件是否存在
	 * @param url FileDO中存的路径
	 * @return
	 */
	Boolean isExist(String url);

}
