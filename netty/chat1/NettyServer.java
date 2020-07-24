package com.topcheer.netty.chat1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by yyx on 2020/7/12.
 */
public class NettyServer {
    public static void main(String[] args) throws Exception{
        EventLoopGroup bossGroup=new NioEventLoopGroup(1);
        EventLoopGroup workGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                     .channel(NioServerSocketChannel.class)
                     .option(ChannelOption.SO_BACKLOG, 120)
                     .childOption(ChannelOption.SO_KEEPALIVE, true)
                     .childHandler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         protected void initChannel(SocketChannel socketChannel) throws Exception {
                             socketChannel.pipeline().addLast(new NettyServerHandler());
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
