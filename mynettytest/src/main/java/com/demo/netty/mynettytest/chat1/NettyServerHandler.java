package com.demo.netty.mynettytest.chat1;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by yyx on 2020/7/12.
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //Buffer msg1 = (Buffer) msg;

        System.out.println("读取数据：" + msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("读取数据完毕");
        ctx.writeAndFlush(Unpooled.copiedBuffer("读取数据完毕", CharsetUtil.UTF_8));
    }
}
