package com.gy.struggle.common.lock.zookeeperLock.zkclient.service;

/**
 * class_name: ILockService
 * package: com.gy.struggle.common.lock.zookeeperLock.zkclient.service
 * describe: TODO
 * creat_user: zhaokai@
 * creat_date: 2019/4/26
 * creat_time: 16:56
 **/
public interface ILockService {
    /**
     * 判断系统当前是否获取分布式锁
     *
     * @return
     */
    Boolean getLock();

    /**
     * 启动分布式锁监听
     */
    void startLockMonitor();
}
