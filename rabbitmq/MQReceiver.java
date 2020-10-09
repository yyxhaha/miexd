package com.topcheer.ytp_print.service.rabbitmq;


import com.rabbitmq.client.Channel;
import com.topcheer.ytp_print.mapper.PrintTaskDetailMapper;
import com.topcheer.ytp_print.mapper.PrintTaskMapper;
import com.topcheer.ytp_print.service.DocumentConvertService;
import com.topcheer.ytp_print.service.pojo.BackResult;
import com.topcheer.ytp_print.service.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import static com.topcheer.ytp_print.service.util.ConvertState.转换失败;
import static com.topcheer.ytp_print.service.util.ConvertState.转换完成;


@Component
@Slf4j
public class MQReceiver {
    @RabbitListener(queues = "topic.message",containerFactory = "rabbitListenerContainerFactory")
    public void process(String  content, Channel channel, @Headers Map<String,Object> map) {
        if (map.get("error") != null) {
            log.error("错误的消息:{}", map);
            try {
                //TODO 可能会导致队列被错误消息占满，如果遇到了这种情况，可以考虑抛弃该消息
                channel.basicNack((Long) map.get(AmqpHeaders.DELIVERY_TAG), false, true);      //否认消息
                return;
            } catch (IOException e) {
                e.printStackTrace();
                log.error("否认消息失败");
            }
        }
        //确认消息
        channel.basicAck((Long)map.get(AmqpHeaders.DELIVERY_TAG),false);

        //不确认消息，返回队列重新推送
        channel.basicAck((Long)map.get(AmqpHeaders.DELIVERY_TAG),true);

    }

    private String getValueFromMap(Map map,String key){
        return Optional.ofNullable(map).map(p->p.get(key)).map(Object::toString).orElse(null);
    }

    //使用不同的exchange 获取消息 这个应该是订阅模式
    @RabbitListener(queues = "topic.print",containerFactory = "rabbitListenerContainerFactory2")
    public void process2(String  hello, Channel channel, @Headers Map<String,Object> map)  {
        *//*try {
            channel.basicAck((Long) map.get(AmqpHeaders.DELIVERY_TAG), false);
        }
        catch (Exception e){

        }*//*
        System.out.println("Receiver2  : " + hello);
    }
}
