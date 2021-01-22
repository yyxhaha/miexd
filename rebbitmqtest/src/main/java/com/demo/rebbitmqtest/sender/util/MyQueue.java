package com.demo.rebbitmqtest.sender.util;


import lombok.Getter;

@Getter
public enum MyQueue {
    /***
     * 队列名称
     *
     */
    topic_print("topic.print"),
    topic_message("topic.message");

    final private String name;

    MyQueue(String name) {
        this.name = name;
    }

}
