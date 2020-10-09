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
    @Autowired
    private DocumentConvertService documentConvertService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private PrintTaskMapper printTaskMapper;
    @Autowired
    private PrintTaskDetailMapper printTaskDetailMapper;
    private final long expireSeconds=60*10;
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
        JSONObject object;
        String applyid = null;
        try {
            object = new JSONObject(content);
            applyid = Optional.ofNullable(object.get("payload")).map(Object::toString).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error new JSONObject from rabbitContent :{}", content);
        }
        try {
            if (applyid == null) {
                log.error("rabbitmq missing applyid,message:{}", content);
                //TODO 回写转换结果 error，applyid丢失
                redisUtil.hset(applyid, "state", 转换失败.getState(),expireSeconds);
                redisUtil.hset(applyid, "memo", "rabbitmq missing applyid",expireSeconds);
                channel.basicAck((Long) map.get(AmqpHeaders.DELIVERY_TAG), false);
            }

            else {
                if(!redisUtil.hasKey(applyid)){
                    log.error("redis missing obj,applyid:{}", applyid);
                    //TODO 回写转换结果 error，applyid丢失
                    redisUtil.hset(applyid, "state", 转换失败.getState(),expireSeconds);
                    redisUtil.hset(applyid, "memo", "redis 未找到对应applyid",expireSeconds);
                    channel.basicAck((Long) map.get(AmqpHeaders.DELIVERY_TAG), false);
                    return;
                }
                log.info("获取数据{}，开始转换",applyid);
                Map convertInfo=redisUtil.hmget(applyid);
                log.debug("获取数据{}:{}，开始转换",applyid,convertInfo);
                Map newInfo=new HashMap();
                newInfo.putAll(convertInfo);
                newInfo.put("startTime",new Date());
                //TODO 开始转换数据
                String id=(String) convertInfo.get("id");
                if(id==null){
                    //redisUtil.hmset(applyid,newInfo,expireSeconds);
                    channel.basicAck((Long)map.get(AmqpHeaders.DELIVERY_TAG),false);
                }
                BackResult backResult= documentConvertService.convert(id,
                        (HashMap<String,String>) convertInfo.get("detail"),
                        getValueFromMap(convertInfo,"modelRelativePath"),
                        getValueFromMap(convertInfo,"ext"),
                        getValueFromMap(convertInfo,"relativeSavePath"),
                        getValueFromMap(convertInfo,"fileName"),
                        getValueFromMap(convertInfo,"water"),getValueFromMap(convertInfo,"waterJsonObj"));
                //log.info("applyid:{},转换结果:{}",applyid,backResult.isSuccess());
                Thread.sleep(500);
                if(backResult.isSuccess()){
                    newInfo.put("endTime",new Date());
                    newInfo.put("state",转换完成.getState());
                    newInfo.put("downloadUrl",backResult.getObj());
                    redisUtil.hmset(applyid,newInfo,expireSeconds);
                    printTaskMapper.updateTaskState(
                            id,
                            转换完成.getState(),
                            new Date(),
                            backResult.getMsg(),
                           String.valueOf(backResult.getObj()));
                    printTaskDetailMapper.updatePrintDetailSate(转换完成.getState(),id);
                    //log.info("id:{},applyid:{},文档地址{}",id,applyid,backResult.getObj());
                    channel.basicAck((Long)map.get(AmqpHeaders.DELIVERY_TAG),false);
                }
                else {
                    newInfo.put("endTime",new Date());
                    newInfo.put("state",转换失败.getState());
                    newInfo.put("memo",backResult.getMsg());
                    redisUtil.hmset(applyid,newInfo,expireSeconds);
                    log.error("转换失败{}:{}",id,backResult.getMsg());
                    printTaskMapper.updateTaskState(id,转换失败.getState(),new Date(), backResult.getMsg(),null);
                    printTaskDetailMapper.updatePrintDetailSate(转换失败.getState(),id);
                    channel.basicAck((Long)map.get(AmqpHeaders.DELIVERY_TAG),true);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            log.error("applyid:{},IOException:{}",applyid,e.getMessage());
        }
    }

    //
    private String getValueFromMap(Map map,String key){
        return Optional.ofNullable(map).map(p->p.get(key)).map(Object::toString).orElse(null);
    }
/*    @RabbitListener(queues = "topic.print",containerFactory = "rabbitListenerContainerFactory2")
    public void process2(String  hello, Channel channel, @Headers Map<String,Object> map)  {
        *//*try {
            channel.basicAck((Long) map.get(AmqpHeaders.DELIVERY_TAG), false);
        }
        catch (Exception e){

        }*//*
        System.out.println("Receiver2  : " + hello);
    }*/
}
