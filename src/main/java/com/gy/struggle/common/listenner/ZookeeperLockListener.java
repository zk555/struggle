package com.gy.struggle.common.listenner;

import com.gy.struggle.common.lock.zookeeperLock.zkclient.service.ILockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

@Order(1)
public class ZookeeperLockListener implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(ZookeeperLockListener.class);

    @Autowired
    ILockService lockServiceImpl;

    @Override
    public void run(String... args) throws Exception {
        logger.info("server is started");
        lockServiceImpl.startLockMonitor();
    }
}
