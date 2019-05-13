package com.gy.struggle.activiti.service.impl;

import com.gy.struggle.activiti.config.ActivitiConstant;
import com.gy.struggle.activiti.domain.SalaryDO;
import com.gy.struggle.activiti.mapper.SalaryMapper;
import com.gy.struggle.activiti.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class SalaryServiceImpl implements SalaryService {
	@Autowired
	private SalaryMapper salaryMapper;
	@Autowired
	private ActTaskServiceImpl actTaskService;
	
	@Override
	public SalaryDO get(String id){
		return salaryMapper.get(id);
	}
	
	@Override
	public List<SalaryDO> list(Map<String, Object> map){
		return salaryMapper.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return salaryMapper.count(map);
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public int save(SalaryDO salary){
			salary.setId(UUID.randomUUID().toString().replace("-",""));
			actTaskService.startProcess(ActivitiConstant.ACTIVITI_SALARY[0],ActivitiConstant.ACTIVITI_SALARY[1],salary.getId(),salary.getContent(),new HashMap<>());
			return salaryMapper.save(salary);
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public int update(SalaryDO salary){
		Map<String,Object> vars = new HashMap<>(16);
		vars.put("pass",  salary.getTaskPass() );
		vars.put("title","");
		actTaskService.complete(salary.getTaskId(),vars);
		return salaryMapper.update(salary);
	}
	
	@Override
	public int remove(String id){
		return salaryMapper.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return salaryMapper.batchRemove(ids);
	}
	
}
