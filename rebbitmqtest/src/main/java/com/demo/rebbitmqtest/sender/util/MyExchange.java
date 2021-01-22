package com.demo.rebbitmqtest.sender.util;

import lombok.Getter;

@Getter
public enum MyExchange {
    topicExchange("topicExchange"),
    directExchange("directExchange");
    final private String name;

    MyExchange(String name) {
        this.name = name;
    }
}
