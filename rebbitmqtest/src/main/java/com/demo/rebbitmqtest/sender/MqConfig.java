package com.demo.rebbitmqtest.sender;

import com.topcheer.ypt_business_handler.service.rabbit.util.MyExchange;
import com.topcheer.ypt_business_handler.service.rabbit.util.MyQueue;
import com.topcheer.ypt_business_handler.service.rabbit.util.MyRoutingKey;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MqConfig {

    /*    @Bean(name = "message")
        Queue queue(){
            return new Queue(MyQueue.topic_message.name());
        }*/
    @Bean(name = "print")
    Queue queues() {
        return new Queue(MyQueue.topic_print.getName());
    }

    @Bean(name = "topicExchange")
    TopicExchange topicExchange() {
        return new TopicExchange(MyExchange.topicExchange.getName());
    }

    @Bean(name = "directExchange")
    DirectExchange directExchange() {
        return new DirectExchange(MyExchange.directExchange.getName());
    }

    /**
     * BindingBuilder.bind(指定队列).to(交换机).with(路由键);
     */
/*    @Bean
    Binding binding(@Qualifier("message")Queue message,
                    @Qualifier("topicExchange") TopicExchange exchange){
        return BindingBuilder.bind(message).to(exchange).with("topic.#");
    }*/
    @Bean
    Binding binding2(@Qualifier("print") Queue message,
                     @Qualifier("topicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(message).to(exchange).with(MyRoutingKey.topicPrint.getName());
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
