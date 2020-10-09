package com.topcheer.ypt_business_handler.service.rabbit;

import com.topcheer.ypt_business_handler.service.rabbit.util.MyExchange;
import com.topcheer.ypt_business_handler.service.rabbit.util.MyRoutingKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class RabbitSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 	这里就是确认消息的回调监听接口，用于确认消息是否被broker所收到
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        /**
         * 	@param ack broker 是否落盘成功
         * 	@param cause 失败的一些异常信息
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            log.info("消息ACK结果: {} , correlationData: {},cause {}" ,ack , correlationData.getId());
        }
    };



    /**
     * 	对外发送消息的方法
     * @param id 业务id
     * @param message 	具体的消息内容
     * @param properties	额外的附加属性
     * @throws Exception
     */
    public void send(String id,Object message, Map<String, Object> properties,MyExchange myExchange,MyRoutingKey myRoutingKey) throws Exception {

        MessageHeaders mhs = new MessageHeaders(properties);
        Message<?> msg = MessageBuilder.createMessage(message, mhs);

        rabbitTemplate.setConfirmCallback(confirmCallback);

        // 	指定业务唯一的iD
        CorrelationData correlationData = new CorrelationData(id);

        MessagePostProcessor mpp = message1 -> {
            //System.err.println("---> post to do: " + message1);
            log.debug("send message: {}"+message1);
            return message1;
        };
        rabbitTemplate.convertAndSend(myExchange.getName(), myRoutingKey.getName(), msg, mpp, correlationData);
    }

    public void send(Object message, Map<String, Object> properties,MyExchange myExchange,MyRoutingKey myRoutingKey) throws Exception {
        send(UUID.randomUUID().toString(),message,properties,myExchange,myRoutingKey);
    }

    public void send(String key,Object message){
        //默认direct，直接投递
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.convertAndSend(key,message);
    }
}
