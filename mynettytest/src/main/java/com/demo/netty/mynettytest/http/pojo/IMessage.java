package com.demo.netty.mynettytest.http.pojo;

import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import java.util.UUID;

@Getter
@Setter

public class IMessage {

    private String id;
    private String senderUid;
    private String senderName;
    private String content;
    private Date creatTime;

    public IMessage() {
        this.id = UUID.randomUUID().toString();
        this.creatTime = new Date();
    }

    public IMessage(String senderUid, String senderName, String content) {
        this.id = UUID.randomUUID().toString();
        this.creatTime = new Date();
        this.content = senderUid;
        this.senderName = senderName;
        this.content = content;
    }
}
