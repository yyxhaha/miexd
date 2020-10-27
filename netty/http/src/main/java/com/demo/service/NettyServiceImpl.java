package com.demo.service;

import com.demo.pojo.ChannelUser;
import com.demo.pojo.IMessage;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.netty.handler.timeout.IdleState.ALL_IDLE;

@Service
@Slf4j
public class NettyServiceImpl implements NettyService {
    @Autowired
    private NioWebSocketChannelPool nioWebSocketChannelPool;

    @Override
    public void sendMessage(Channel channel,String str) {
        TextWebSocketFrame webSocketFrame = new TextWebSocketFrame(Unpooled.copiedBuffer(str, CharsetUtil.UTF_8));
        channel.writeAndFlush(webSocketFrame.retain());
    }

    @Override
    public void sendMessage(Channel channel, IMessage msg) {

    }



    @Override
    public boolean authenticate(String token, Channel channel) {
        try {
            //todo 解密token
            //todo 创建channelUser对象，设置用户信息并且保存到map里面
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void addChannelHandler(Channel myChannel) {

    }

    @Override
    public void addChannelHandler(ChannelUser myChannel) {

    }

    @Override
    public void removeChannelHandler(Channel myChannel) {

    }


    @Override
    public void receiveMsgEventHandler(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
    }


    @Override
    public void removeChannelHandler(ChannelUser myChannel) {
        //todo 用户离线，业务处理
        nioWebSocketChannelPool.removeChannel(myChannel);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
        if(evt instanceof IdleStateEvent){
            IdleStateEvent e=(IdleStateEvent)evt;
            if(e.state().equals(ALL_IDLE)){
                log.info("读写空闲");
            }
        }
    }

}
