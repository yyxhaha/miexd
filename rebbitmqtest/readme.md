发布订阅模式：  
1.生产者(produce)把消息发送给 交换机(exchange)  
2.交换机与多个队列（queue）绑定，消费者(consumer)监听自己的队列   
3.交换机得到消息后，根据路由(routekey)发送消息给绑定的队列，符合规则的队列都会得到消息，在有效得这去消费  
4.同一消息在一个队列下只能被消费一次

直接交换中心：（Direct exchange）  
扇形交换中心：（Fanout exchange）  
主题交换中心：（Topic exchange）  
首部交换中心：（Headers exchange）  