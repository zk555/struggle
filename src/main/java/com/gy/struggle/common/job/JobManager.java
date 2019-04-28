/**
 * JobManager.java
 */
package com.gy.struggle.common.job;

import com.gy.struggle.common.lock.zookeeperLock.zkclient.service.ILockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * 定时任务管理
 *
 * @author sean
 */
@Component
@EnableScheduling
public class JobManager {

    Logger logger = LoggerFactory.getLogger(JobManager.class);

    @Autowired
    private ILockService lockServiceImpl;

    @Autowired
    DayTransJob dayTransJob;
    /**
     * 每日交易统计处理 
     */
//    @Scheduled(cron="${jobs.dayTransJob}")
    public void dayTransJobInfo() {
        if (lockServiceImpl.getLock()) {
            logger.info(MessageFormat.format("当前任务每日终端数统计处理（dayTransJob）执行状态为:{0}，任务{1}执行",
            		dayTransJob.getIsRunning(),
            		dayTransJob.getIsRunning()?"不可以":"可以"));
            if (!dayTransJob.getIsRunning()) {
            	dayTransJob.run();
            }
        } else {
            logger.info("此节点未获得分布式锁，任务不启动执行。");
        }
    }
    
}
