package com.demo.service;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yyx on 2020/7/19.
 */
@Component
public class NioWebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private WebSocketProperties webSocketProperties;
    @Autowired
    private NioWebSocketHandler nioWebSocketHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline()
                     .addLast(new HttpServerCodec())//解码器
                     .addLast(new ChunkedWriteHandler())//保证消息的完整性
                     .addLast(new HttpObjectAggregator(8192))//消息转对象
                     .addLast(nioWebSocketHandler)//自定义处理器
                     .addLast(new WebSocketServerProtocolHandler(webSocketProperties.getPath(),
                             null, true, 65536));//升级到ws
    }
}

