package com.demo.netty.mynettytest.http.service;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by yyx on 2020/7/19.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "chat.websocket")
public class WebSocketProperties {

    private Integer port = 8081; // 监听端口
    private String path = "/ws"; // 请求路径
    private Integer boss = 2; // bossGroup线程数
    private Integer work = 2; // workGroup线程数

}
