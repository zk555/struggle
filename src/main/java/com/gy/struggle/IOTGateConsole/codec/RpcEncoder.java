package com.gy.struggle.IOTGateConsole.codec;

import com.gy.struggle.IOTGateConsole.databridge.RequestData;
import com.gy.struggle.common.utils.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 */
public class RpcEncoder extends MessageToByteEncoder<RequestData> {

	@Override
	protected void encode(ChannelHandlerContext ctx, RequestData msg, ByteBuf out) throws Exception {
		byte[] data = SerializationUtil.serialize(msg);
		out.writeShort(data.length);
		out.writeBytes(data);
	}

}
