package com.gy.struggle.common.lock.zookeeperLock.zkclient;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 监控节点接口
 */
@Component
public class NodeWatcher implements Watcher {

    Logger logger = LoggerFactory.getLogger(NodeWatcher.class);
    @Override
    public void process(WatchedEvent watchedEvent) {

    }
}
