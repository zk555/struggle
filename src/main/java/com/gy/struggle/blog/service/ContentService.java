package com.gy.struggle.blog.service;


import com.gy.struggle.blog.domain.ContentDO;

import java.util.List;
import java.util.Map;

/**
 * 文章内容
 *
 */
public interface ContentService {
	
	ContentDO get(Long cid);
	
	List<ContentDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ContentDO bContent);
	
	int update(ContentDO bContent);
	
	int remove(Long cid);
	
	int batchRemove(Long[] cids);
}
