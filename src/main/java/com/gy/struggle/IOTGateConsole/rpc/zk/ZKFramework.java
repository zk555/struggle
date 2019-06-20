package com.gy.struggle.IOTGateConsole.rpc.zk;

import com.gy.struggle.IOTGateConsole.cache.CommonLocalCache;
import com.gy.struggle.IOTGateConsole.rpc.proxy.RPCRequestProxy;
import com.gy.struggle.IOTGateConsole.rpc.service.RPCExportService;
import com.gy.struggle.IOTGateConsole.service.RpcService;
import com.gy.struggle.common.utils.ThreadFactoryImpl;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * zookeeper处理类
 */
@Component
@PropertySource(value = {"classpath:application.properties"},encoding="utf-8")
public class ZKFramework {

    @Autowired
    RpcService rpcService;

    @Value("${curator.connectString}")
    private String zkAddr;
    private CuratorFramework cf ;
    //初始化一个线程
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor(
            new ThreadFactoryImpl("zkNodeCacheUpdate_", true));
    //zookeeper根节点
    private final String PARENT_PATH = "/iotGate2RPC";

    /**
     * 服务器初始化调用
     */
    @PostConstruct
    public  void start(){
        if (isCluster()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (zkAddr == null){
                            //TODO 测试zk地址
                            zkAddr = "192.168.142.153:2181";
                        }
                        init(zkAddr);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },"zkFrameworkThread").start();
        }else {
            System.out.println("********当前前置被指定为单机版，无需注册节点信息到zookeeper********");
        }
    }

    /**
     * 初始化zk连接
     * @param zkAddr
     */
    private void init(String zkAddr) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        cf = CuratorFrameworkFactory.builder()
                .connectString(zkAddr)
                .sessionTimeoutMs(6000)
                .retryPolicy(retryPolicy)
                .build();
        System.out.println("zk连接中。。。。。。");
        //3 开启连接
        cf.start();
        while(cf.getState() != CuratorFrameworkState.STARTED){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("zk连接成功。。。。。");
        try {
            zNodeListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void zNodeListener() throws Exception {
        if(cf.checkExists().forPath(PARENT_PATH) == null){
            // 创建根节点
            cf.create().withMode(CreateMode.PERSISTENT).forPath(PARENT_PATH,"pastoralDog init".getBytes());
        }
        PathChildrenCache cache = new PathChildrenCache(cf, PARENT_PATH, true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework cf, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED :" + event.getData().getPath());
                        try {
                            String val = new String(event.getData().getData());
                            addNode2Cache(val);
                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }

                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED :" + event.getData().getPath());
                        System.out.println("DATA :" + new String(event.getData().getData()));
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED :" + event.getData().getPath());
//					System.out.println("DATA :" + new String(event.getData().getData()));
                        try {
                            delNode2Cache(new String(event.getData().getData()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    default:
                        break;
                }
            }
        });

    }

    /**
     * 新增节点
     * @param nodeIp
     */
    private void addNode2Cache(String nodeIp) throws Exception{
        if(!CommonLocalCache.rpcServerCache.contains(nodeIp)){
            CommonLocalCache.rpcServerCache.add(nodeIp);
        }
        //缓存 key : Value  ( ip : RPCExportService.proxy)
        if(!CommonLocalCache.rpcProxys.containsKey(nodeIp)){
            CommonLocalCache.rpcProxys.put(nodeIp, new RPCRequestProxy(nodeIp).create(RPCExportService.class));
        }
        //节点规约同步
        rpcService.synchonizeStrategy(nodeIp);
    }

    /**
     * 删除节点
     * @param nodeIp
     * @throws Exception
     */
    private void delNode2Cache(String nodeIp)throws Exception{
        if(!CommonLocalCache.rpcServerCache.contains(nodeIp)){
            return;
        }
        CommonLocalCache.rpcProxys.remove(nodeIp);
    }

    /**
     * 更新节点
     */
    private void updateNode2Cache(String nodeIp)throws Exception{
        //TODO 节点更新
    }

    private boolean isCluster(){

        return true;
    }
}
