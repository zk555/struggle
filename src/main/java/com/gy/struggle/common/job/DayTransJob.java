package com.gy.struggle.common.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;


/**
* @ClassName: DayTransJob 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 
* @date 2018年8月6日 下午3:06:56 
*  
*/
@Component
public class DayTransJob implements Runnable {

	 	Logger logger = LoggerFactory.getLogger(this.getClass());

	    private Boolean isRunning = Boolean.FALSE;
	    
//	    @Autowired
//		OrgService orgService ;
	    
	    
	    @Override
	    public void run() {
	        isRunning = Boolean.TRUE;
	        logger.info(MessageFormat.format("定时任务，任务执行状态设置为:{0}，任务启动。", isRunning));
	        try {
//	        	String isResult = orgService.getTradeHistory();
//	        	if ("F".equals(isResult)) {
//	        		logger.info("【每日跑批汇总】汇总数据失败,时间为：" + new Date());
//				}else {
//					logger.info("【每日跑批汇总】汇总数据完成,时间为：" + new Date());
//				}
	        } finally {
	            isRunning = Boolean.FALSE;
	        }
	    }

	    public Boolean getIsRunning() {
	        return isRunning;
	    }

}
