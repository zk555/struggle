package com.gy.struggle.common.lock.zookeeperLock.zkclient;

import com.gy.struggle.common.lock.zookeeperLock.zkclient.constant.LockConstant;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;

/**
 * 分布式锁类
 */
@Component
public class NodeMonitor {
    Logger logger = LoggerFactory.getLogger(NodeMonitor.class);

    private Boolean isMaster = Boolean.FALSE;
    private Boolean isConnected = Boolean.FALSE;

    @Value("${lock.server.ip}")
    private String zkUrl;

    @Value("${lock.server.session.timeout}")
    private int sessionTimeout;

    @Value("${lock.server.sleep.time}")
    private long lockSleepTime;

    private ZooKeeper zooKeeper;

    @Autowired
    private NodeWatcher nodeWatcher;

    public synchronized Boolean createMaster() throws IOException, KeeperException, InterruptedException {
        if (zooKeeper == null) {
            zooKeeper = new ZooKeeper(zkUrl, sessionTimeout, nodeWatcher);
        }
        // root node
        Stat stat = zooKeeper.exists(LockConstant.LOCK_ROOT, true);
        if (stat == null) {
            zooKeeper.create(LockConstant.LOCK_ROOT, LockConstant.LOCK_ROOT.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        logger.info("root node stat:" + stat);
        // master node
        Stat masterStat = zooKeeper.exists(LockConstant.LOCK_ROOT + LockConstant.LOCK_KEY, nodeWatcher);
        if (masterStat == null) {
            String ip = InetAddress.getLocalHost().getHostAddress();
            long sessionId = zooKeeper.getSessionId();
            String master = zooKeeper.create(LockConstant.LOCK_ROOT + LockConstant.LOCK_KEY, (ip + sessionId).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL);
            logger.info("master node stat:" + master);
            isMaster = Boolean.TRUE;
        } else {
            logger.info("masterStat:" + masterStat.toString());
        }
        isConnected = Boolean.TRUE;
        return isMaster;
    }

    public void startLockMonitor() {
        Thread zookeeperMonitor = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (zooKeeper == null) {  //连接为空
                            logger.info("zooKeeper object is null");
                            isMaster = Boolean.FALSE;
                            isConnected = Boolean.FALSE;
                            createMaster();
                            logger.info("isMaster:" + isMaster  + " isConnected:" + isConnected);
                        } else if (!zooKeeper.getState().isAlive()) { //连接不可以
                            logger.info("zooKeeper object stat:" + zooKeeper.getState() + "isMaster:" + isMaster + " isConnected:" + isConnected);
                            close();
                        } else {
                            logger.info("zooKeeper object stat:" + zooKeeper.getState() + "isMaster:" + isMaster   + " isConnected:" + isConnected);
                        }
                        Thread.sleep(lockSleepTime);
                    } catch (Exception e) {
                        logger.error("zookeeper exception:" + e.getMessage() + "isMaster:" + isMaster + " isConnected:" + isConnected,e);
                        close();
                        try {
                            Thread.sleep(lockSleepTime);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        zookeeperMonitor.setName("thread-zookeeper node monitor");
        zookeeperMonitor.start();
    }

    public synchronized void close() {
        if (zooKeeper != null) {
            try {
                isMaster = Boolean.FALSE;
                isConnected = Boolean.FALSE;
                zooKeeper.close();
                zooKeeper = null;
            } catch (Exception e) {
                logger.error("zookeeper关闭异常",e);
            }
        }
    }

    /**
     * @return 当前系统是否为主，即是否获得分布式锁
     */
    public Boolean getIsMaster() {
        return isMaster;
    }

    /**
     * @return 当前系统是否为已连接状态，即是否获得分布式锁
     */
    public Boolean getConnected() {
        return isConnected;
    }
}
