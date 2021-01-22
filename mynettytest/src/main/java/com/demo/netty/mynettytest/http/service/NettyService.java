package com.demo.netty.mynettytest.http.service;

import com.demo.pojo.ChannelUser;
import com.demo.pojo.IMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public interface NettyService {
    //给对应的通道发送消息
    void sendMessage(Channel channel, String str);

    void sendMessage(Channel channel, IMessage msg);

    /**
     * @param ctx   获取用户对应通道
     * @param frame 获取用户发送的消息
     *              将消息转发给其他用户
     */
    void receiveMsgEventHandler(ChannelHandlerContext ctx, TextWebSocketFrame frame);

    /**
     * @param token   用户身份信息
     * @param channel
     * @return true/false 解析 成功/失败
     * 如果解析成功，创建channelUser对象，保存到NioWebSocketChannelPool
     */
    boolean authenticate(String token, Channel channel);

    void addChannelHandler(Channel myChannel);

    void addChannelHandler(ChannelUser myChannel);

    void removeChannelHandler(Channel myChannel);

    void removeChannelHandler(ChannelUser myChannel);

    /**
     * @param ctx
     * @param evt
     * @throws Exception 心跳检测，读空闲、写空闲，读写空闲
     */
    void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception;
}
