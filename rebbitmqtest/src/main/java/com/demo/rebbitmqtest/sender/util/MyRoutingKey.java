package com.demo.rebbitmqtest.sender.util;

import lombok.Getter;

@Getter
public enum MyRoutingKey {
    /**
     * 路由键
     */
    topicPrint("topic.print");

    final private String name;

    MyRoutingKey(String name) {
        this.name = name;
    }
}
