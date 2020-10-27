package com.demo.pojo;

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
    private String userId;
    private Channel channel;
    private String channelId;
    private String sealManId;
}
