package com.gy.struggle.IOTGateConsole.service;

import com.gy.struggle.IOTGateConsole.databridge.ReqWebData;
import com.gy.struggle.IOTGateConsole.databridge.RetData;
import com.gy.struggle.IOTGateConsole.domain.IotGateDB;

public interface RpcService {
    /**
     * 获取网关节点信息
     * @return
     */
    RetData getAllGateInfo();
    /**
     * 添加一个规约
     * @param iotGateDB
     * @return
     */
    RetData addOneStrategy(IotGateDB iotGateDB);
    /**
     * 获取所有创建过的规约
     * @return
     */
    RetData getAllStrategeFromDB();

    /**
     * 获取所有规约的所有信息
     * @return
     */
    RetData getAllStrategyAllInfo();
    /**
     * 更新网关节点所所开启得规约
     * @param reqWebData
     * @return
     */
    RetData updateStrategy2Node(ReqWebData reqWebData);

    /**
     * 根据id删除规约，同时删除所有网关节点关联的当前规约
     * @param reqWebData
     * @return
     */
    RetData delOneStrategyByPID(String pid);

    /**
     * 节点规约数据同步--防止节点缓存的规约策略与本地数据库不同步
     * @param nodeIp
     */
    void synchonizeStrategy(String nodeIp);
}
