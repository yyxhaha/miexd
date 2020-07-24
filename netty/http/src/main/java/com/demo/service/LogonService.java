package com.topcheer.httpNetty.service;

import com.topcheer.httpNetty.NioWebSocketChannelPool;
import com.topcheer.httpNetty.pojo.ChannelUser;
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
    public boolean authenticate(String token, Channel channel){
        ChannelUser channelUser = nioWebSocketChannelPool.getChannelUser(channel.id().asLongText());
        channelUser.setUserId(token);
        return true;
    }
}
