package com.demo.netty.mynettytest.http.service;

import com.demo.pojo.ChannelUser;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by yyx on 2020/7/19.
 */
@Service
public class LogonService {
    @Autowired
    private NioWebSocketChannelPool nioWebSocketChannelPool;

    public boolean authenticate(String token, Channel channel) {
        ChannelUser channelUser = nioWebSocketChannelPool.getChannelUser(channel.id().asLongText());
        channelUser.setUserId(token);
        return true;
    }
}
