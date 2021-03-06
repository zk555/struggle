package com.gy.struggle.IOTGateConsole.rometing;

import com.gy.struggle.IOTGateConsole.codec.RpcDecoder;
import com.gy.struggle.IOTGateConsole.codec.RpcEncoder;
import com.gy.struggle.IOTGateConsole.databridge.RequestData;
import com.gy.struggle.IOTGateConsole.databridge.ResponseData;
import com.gy.struggle.common.utils.ThreadFactoryImpl;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.CountDownLatch;

/**
 * class_name: RemoteClient
 * package: com.gy.struggle.IOTGateConsole.rometing
 * describe: TODO  rpc调用服务
 * creat_user: zhaokai@
 * creat_date: 2019/6/11
 * creat_time: 17:33
 **/
public class RemoteClient {

    private final Bootstrap bootstrap = new Bootstrap();
    private final EventLoopGroup eventLoopGroupWorker;
    private final Object obj = new Object();
    private CountDownLatch lock = new CountDownLatch(1);
    private ResponseData responseData = null;

    public RemoteClient(){
        eventLoopGroupWorker = new NioEventLoopGroup(1, new ThreadFactoryImpl("netty_rpc_client_", false));
    }

    public ResponseData start(String ip ,int port ,RequestData requestData)throws InterruptedException {
        try{
            Bootstrap handler = this.bootstrap.group(this.eventLoopGroupWorker).channel(NioSocketChannel.class)//
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, false)
                    .option(ChannelOption.SO_SNDBUF, 65535)
                    .option(ChannelOption.SO_RCVBUF, 65535)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(//
                                    new RpcEncoder(), //
                                    new RpcDecoder(),
                                    new IdleStateHandler(0, 0, 120),//
                                    new NettyConnetManageHandler(), //
                                    new NettyClientHandler(lock));//获取数据
                        }
                    });
            ChannelFuture channelFuture=handler.connect(ip, port).sync();
            channelFuture.channel().writeAndFlush(requestData).sync();
            System.out.println("rpc调用成功等待数据响应......");
            lock.await();
            if (responseData != null) {
                channelFuture.channel().close();
            }
            return responseData;
        }finally{
            eventLoopGroupWorker.shutdownGracefully();
        }
    }

    class NettyConnetManageHandler extends ChannelDuplexHandler {
        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            super.channelRegistered(ctx);
        }


        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            super.channelUnregistered(ctx);
        }


        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);

        }


        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        }


        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent evnet = (IdleStateEvent) evt;
                if (evnet.state().equals(IdleState.ALL_IDLE)) {
                    ctx.channel().close();
                }
            }

            ctx.fireUserEventTriggered(evt);
        }


        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.channel().close();
        }
    }

    class NettyClientHandler extends SimpleChannelInboundHandler<ResponseData> {
        private CountDownLatch locks;
        public NettyClientHandler(CountDownLatch lock) {
            // TODO Auto-generated constructor stub
            this.locks = lock;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ResponseData msg) throws Exception {
            /**
             * 调用的处理响应数据的方法
             */
            RemoteClient.this.responseData = msg;
            locks.countDown();

        }
    }
}
