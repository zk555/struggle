package com.gy.struggle.common.lock.zookeeperLock.zkclient.service.impl;

import com.gy.struggle.common.lock.zookeeperLock.zkclient.NodeMonitor;
import com.gy.struggle.common.lock.zookeeperLock.zkclient.service.ILockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LockServiceImpl implements ILockService {

    Logger logger = LoggerFactory.getLogger(LockServiceImpl.class);

    @Autowired
    NodeMonitor nodeMonitor;

    @Override
    public Boolean getLock() {
        Boolean ret = Boolean.FALSE;
        while (nodeMonitor.getIsMaster() == Boolean.TRUE || nodeMonitor.getConnected() == Boolean.FALSE) {
            try {
                ret = nodeMonitor.createMaster();
                break;
            } catch (Exception e) {
                logger.error("判断是否获取主节点锁，创建节点异常：", e);
                try {
                    long sleep = 10000L;
                    logger.error("Exception: Zookeeper无法连接，请检查！！！休眠：" + sleep + "(毫秒）后重试");
                    Thread.sleep(sleep);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                    logger.error("无法连接zookeeper，休眠异常：", ie);
                    break;
                }
            }
        }
        return ret;
    }
    @Override
    public void startLockMonitor() {
        nodeMonitor.startLockMonitor();
    }
}
