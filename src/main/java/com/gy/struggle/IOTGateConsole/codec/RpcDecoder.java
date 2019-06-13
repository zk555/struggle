package com.gy.struggle.IOTGateConsole.codec;



import com.gy.struggle.IOTGateConsole.databridge.ResponseData;
import com.gy.struggle.IOTGateConsole.rpc.rpcProcessor.RPCClientProcessor;
import com.gy.struggle.IOTGateConsole.rpc.rpcProcessor.RPCClientProcessorImpl;
import com.gy.struggle.common.utils.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteBuffer;

/**
 *  解码器
 */
public class RpcDecoder extends LengthFieldBasedFrameDecoder{

	RPCClientProcessor processor = new RPCClientProcessorImpl();
	
	public RpcDecoder() {
		super(10240, 0, 2, 0, 0);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		ByteBuf buff =  (ByteBuf) super.decode(ctx, in);
		if(buff == null){
			return null;
		}
		ByteBuffer byteBuffer = buff.nioBuffer();
		int dataAllLen = byteBuffer.limit();
		int lenArea = byteBuffer.getShort();
		int dataLen = dataAllLen - 2;
		byte[] contentData = new byte[dataLen];
        byteBuffer.get(contentData);//报头数据
        ResponseData requestData = SerializationUtil.deserialize(contentData, ResponseData.class);
        return requestData;
	}

	
}
