package com.topcheer.httpNetty;

import com.topcheer.httpNetty.pojo.ChannelUser;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yyx on 2020/7/19.
 */
@Slf4j
@Component
public class NioWebSocketChannelPool {

   // private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private final Map<String,ChannelUser> channelUsers=new ConcurrentHashMap();

    /**
     * 新增一个客户端通道
     *
     * @param channel
     */
   /* public void addChannel(Channel channel) {
        channels.add(channel);
    }*/

    public void addChannel(ChannelUser channelUser){
        channelUsers.put(channelUser.getChannelId(),channelUser);
    }

    /**
     * 移除一个客户端连接通道
     *
     * @param channel
     */
    /*public void removeChannel(Channel channel) {
        channels.remove(channel);
    }*/
    public void removeChannel(ChannelUser channelUser){
        channelUsers.remove(channelUser);
    }


    public ChannelUser getChannelUser(String id){
        return channelUsers.get(id);
    }
}
