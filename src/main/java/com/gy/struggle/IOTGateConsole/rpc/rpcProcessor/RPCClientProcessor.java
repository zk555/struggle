package com.gy.struggle.IOTGateConsole.rpc.rpcProcessor;

import com.gy.struggle.IOTGateConsole.databridge.RequestData;
import com.gy.struggle.IOTGateConsole.databridge.ResponseData;

/**
 * RPC服务接口
 */
public interface RPCClientProcessor {

    /**
     * 调用rpc服务
     * @param requestData
     * @return
     */
    ResponseData callServiceService(RequestData requestData);

}
