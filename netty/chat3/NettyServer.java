package com.topcheer.netty.chat3;

;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yyx on 2020/7/12.
 */
public class NettyServer {
    public static ChannelGroup group=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static Map<String,Channel> channelMap=new ConcurrentHashMap<>();
    public void run() throws Exception{
        EventLoopGroup bossGroup=new NioEventLoopGroup(1);
        EventLoopGroup workGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                     .channel(NioServerSocketChannel.class)
                     .handler(new LoggingHandler(LogLevel.INFO))
                       .childHandler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         protected void initChannel(SocketChannel socketChannel) throws Exception {
                             ChannelPipeline pipeline = socketChannel.pipeline();
                             pipeline.addLast(new HttpServerCodec());
                             //块方式写的处理器
                             pipeline.addLast(new ChunkedWriteHandler());
                             //聚合数据
                             pipeline.addLast(new HttpObjectAggregator(1024*10));//8192
                             //升级http请求ws
                             pipeline.addLast(new WebSocketServerProtocolHandler("/consult"));
                         }
                     });
            ChannelFuture cf = bootstrap.bind(6666).sync();
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println(("启动成功"));
                    } else {
                        System.out.println("启动失败");
                    }
                }
            });
            cf.channel().closeFuture().sync();
        }
    finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
