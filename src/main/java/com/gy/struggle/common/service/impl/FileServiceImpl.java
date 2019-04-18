package com.gy.struggle.common.service.impl;

import com.gy.struggle.common.config.StruggleConfig;
import com.gy.struggle.common.domain.FileDO;
import com.gy.struggle.common.mapper.FileMapper;
import com.gy.struggle.common.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

import org.springframework.util.StringUtils;


@Service
public class FileServiceImpl implements FileService {
	@Autowired
	private FileMapper sysFileMapper;

	@Autowired
	private StruggleConfig struggleConfig;
	@Override
	public FileDO get(Long id){
		return sysFileMapper.get(id);
	}


	@Override
	public Boolean isExist(String url) {
		Boolean isExist = false;
		if (!StringUtils.isEmpty(url)) {
			String filePath = url.replace("/files/", "");
			filePath = struggleConfig.getUploadPath() + filePath;
			File file = new File(filePath);
			if (file.exists()) {
				isExist = true;
			}
		}
		return isExist;
	}
}
