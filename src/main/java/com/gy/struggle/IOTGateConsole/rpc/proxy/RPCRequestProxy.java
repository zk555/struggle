package com.gy.struggle.IOTGateConsole.rpc.proxy;


import com.gy.struggle.IOTGateConsole.databridge.RequestData;
import com.gy.struggle.IOTGateConsole.databridge.ResponseData;
import com.gy.struggle.IOTGateConsole.rometing.RemoteClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * 请求代理类
 */
public class RPCRequestProxy {
	
	//TODO 缓存所有rpc服务节点信息
	private String rpcServerIP;
	
	public RPCRequestProxy(String rpcServerIP) {
		super();
		this.rpcServerIP = rpcServerIP;
	}

	@SuppressWarnings("unchecked")
	public <T> T create(Class<?> clazz){
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class<?>[]{clazz}, new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						// TODO Auto-generated method stub
						RequestData requestData = new RequestData();
						requestData.setRequestNum(UUID.randomUUID().toString());
						requestData.setClassName(method.getDeclaringClass().getSimpleName());//获取方法所在类名称
						requestData.setMethodName(method.getName());
						requestData.setParamTyps(method.getParameterTypes());
						requestData.setArgs(args);
						
						RemoteClient remoteClient = new RemoteClient();
						//TODO  改为从zookeeper获取rpc数据
						ResponseData responseData = remoteClient.start(rpcServerIP, 10916, requestData);
						return responseData;
					}
				});
	}

}
