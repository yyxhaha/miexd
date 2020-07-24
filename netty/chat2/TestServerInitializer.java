package com.topcheer.netty.chat2;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by yyx on 2020/7/12.
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());
        pipeline.addLast("TestServerChannelHandler",new TestServerChannelHandler());
    }
}
