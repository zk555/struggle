/**
 * 
 */
package com.gy.struggle.common.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 自动生成
 */
@Service
public interface GeneratorService {

	List<Map<String, Object>> list();

	byte[] generatorCode(String[] tableNames);
}
