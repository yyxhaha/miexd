package com.topcheer.httpNetty.pojo;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by yyx on 2020/7/12.
 */
@Getter
@Setter
public class ChannelUser {
    private String name;
    private String uid;
    private Channel channel;
    private String channelId;
    private String targetUid;
    private String targetUid;
}
