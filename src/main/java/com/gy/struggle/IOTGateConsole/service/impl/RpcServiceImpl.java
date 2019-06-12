package com.gy.struggle.IOTGateConsole.service.impl;

import com.gy.struggle.IOTGateConsole.cache.CommonLocalCache;
import com.gy.struggle.IOTGateConsole.databridge.ReqWebData;
import com.gy.struggle.IOTGateConsole.databridge.ResponseData;
import com.gy.struggle.IOTGateConsole.databridge.RetData;
import com.gy.struggle.IOTGateConsole.domain.IotGateDB;
import com.gy.struggle.IOTGateConsole.mapper.IotGateMapper;
import com.gy.struggle.IOTGateConsole.rpc.service.RPCExportService;
import com.gy.struggle.IOTGateConsole.service.RpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * rpc调用类
 */
@Service
public class RpcServiceImpl implements RpcService {
    @Autowired
    private IotGateMapper iotGateMapper;

    @Override
    public RetData getAllGateInfo() {
        List<String> allNodes = CommonLocalCache.rpcServerCache;
        RetData ret = new RetData();
        List<Object> retData = new ArrayList<>();
        for (String  ip: allNodes) {
            Map<String, String> aStrategy = new HashMap<>();
            aStrategy.put("ip", ip);
            RPCExportService rpcExportService = CommonLocalCache.rpcProxys.get(ip);
            if (rpcExportService != null) {
                aStrategy.put("state", "ok");
                ResponseData responseData = rpcExportService.getAllProtocal(true);
                if (responseData.getData().size() > 0) {
                    Map retMap = (Map) responseData.getData().get(0);
                    Iterator<Map.Entry<String, String>> it = retMap.entrySet().iterator();
                    String str = "";
                    while (it.hasNext()) {
                        String[] arg = it.next().getValue().split("\\,");
                        ;
                        str += (arg[0] + ":" + arg[arg.length - 1] + "/ ");
                    }
                    if (!"".equals(str)) {
                        aStrategy.put("data", str.substring(0, str.length() - 1));
                    }
                }
            } else {
                aStrategy.put("stat", "error");
            }
            retData.add(aStrategy);
        }
            ret.setRetSig(200);
            ret.setData(retData);
            return ret;
    }

    @Transactional
    @Override
    public RetData addOneStrategy(IotGateDB iotGateDB) {
        RetData ret = new RetData();
        //{bigdian=1, leftLen=1, len2=1, lenOffset=1, lenrange=1, pid=1, port=1}

        //写数据库
        iotGateDB.setId(UUID.randomUUID().toString());
        iotGateDB.setBeginHexVal("-1");
        iotGateDB.setHighControll(0);//高级功能位
        iotGateMapper.insert(iotGateDB);


        List<Integer> strategy = new ArrayList<>();
        strategy.add(iotGateDB.getHighControll());
        strategy.add(iotGateDB.getIsBigEndian());
        strategy.add(-1);//起始字符
        strategy.add(iotGateDB.getLengthFieldOffset());
        strategy.add(iotGateDB.getLengthFieldLength());
        strategy.add(iotGateDB.getExceptDataLenth());
        strategy.add(iotGateDB.getpId());
        strategy.add(iotGateDB.getPort());

        List<String> allNodes = CommonLocalCache.rpcServerCache;
        /**
         * 所有节点添加新规约--并不启动
         */
        for (String ip : allNodes) {
            RPCExportService rpcExportService = CommonLocalCache.rpcProxys.get(ip);
            if(rpcExportService != null){
                rpcExportService.addNewProtocal(Integer.toString(iotGateDB.getpId()), strategy, false);
            }
        }

        ret.setRetSig(200);
        return ret;
    }

    @Override
    public RetData getAllStrategeFromDB() {
        return null;
    }

    @Override
    public RetData getAllStrategyAllInfo() {
        RetData ret = new RetData();
        List<IotGateDB>  retList= iotGateMapper.getAllStrategy();
        List<Object> retData = new ArrayList<Object>();
        retData.add(retList);
        ret.setData(retData);
        ret.setRetSig(200);
        return ret;
    }

    @Override
    public RetData updateStrategy2Node(ReqWebData reqWebData) {
        return null;
    }

    @Override
    public RetData delOneStrategyByPID(String pId){
        RetData ret = new RetData();
        //清除节点数据
        List<String> allNodes = CommonLocalCache.rpcServerCache;
        for (String ip : allNodes) {
            RPCExportService rpcExportService = CommonLocalCache.rpcProxys.get(ip);
            if(rpcExportService != null){
                rpcExportService.delProtocalByPid(pId);
            }
        }
        //删除数据库对应数据
        IotGateDB iotgate = new IotGateDB();
        iotgate.setpId(Integer.parseInt(pId));
        iotGateMapper.delOneStrategyByPID(iotgate);
        ret.setRetSig(200);
        return ret;
    }

    @Override
    public void synchonizeStrategy(String nodeIp) {
        RPCExportService rpcExportService = CommonLocalCache.rpcProxys.get(nodeIp);
        if(rpcExportService != null){

            List<IotGateDB>  retList= iotGateMapper.getAllStrategy();
            for (IotGateDB iotGateDB : retList) {

                if(iotGateDB.getHighControll() == 0){
                    //非高级功能
                    int localPID = iotGateDB.getpId();
                    //如果已经存在则不会重复添加
                    rpcExportService.addNewProtocal(localPID+"", iotGateDB.toList(), false);
                }else{
                    //TODO 高级功能
                }
            }

        }
    }
}
