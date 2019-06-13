package com.gy.struggle.IOTGateConsole.cache;

import com.gy.struggle.IOTGateConsole.rpc.service.RPCExportService;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class CommonLocalCache {
	private CommonLocalCache(){
		throw new AssertionError();
	}
	/**
	 * 缓存所有rpcNode IP
	 */
	public static List<String> rpcServerCache = new CopyOnWriteArrayList<>();
	/**
	 * 缓存rpc代理---保持最新
	 */
	public static ConcurrentHashMap<String , RPCExportService> rpcProxys = new ConcurrentHashMap<>();
	
}
