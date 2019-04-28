package com.gy.struggle.common.lock.zookeeperLock.zkclient.constant;

public class LockConstant {

    //状态 1： 成功  2：注销
    public static final String STATUS_1 = "1";
    public static final String STATUS_2 = "2";
    /**分布式锁根节点*/
    public static final String LOCK_ROOT = "/root_locks";

    /**分布式锁系统key*/
    public static final String LOCK_KEY = "/jftAppService_master";
}
