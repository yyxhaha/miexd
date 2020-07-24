package com.topcheer.netty.chat2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * Created by yyx on 2020/7/12.
 */
public class TestServerChannelHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ch, HttpObject httpObject) throws Exception {
        if(httpObject instanceof HttpRequest){

            HttpRequest request = (HttpRequest) httpObject;
            String url=request.uri();
            if(!"/favicon.ico".equals(url)){
                System.out.println(ch.channel().remoteAddress());
                ByteBuf byteBuf= Unpooled.copiedBuffer("你好", CharsetUtil.UTF_8);
                DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                        HttpVersion.HTTP_1_1,
                        HttpResponseStatus.OK, byteBuf);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
                ch.writeAndFlush(response);
            }
        }
    }
}
