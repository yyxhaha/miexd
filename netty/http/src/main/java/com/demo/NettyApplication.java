package com.topcheer;

import com.topcheer.netty.chat3.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyApplication {


    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class, args);
        try {
            new NettyServer().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
