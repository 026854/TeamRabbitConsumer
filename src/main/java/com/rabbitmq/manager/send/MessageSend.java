package com.rabbitmq.manager.send;

import com.rabbitmq.manager.vo.QueueMessage;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageSend {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(Exchange exchange, String key, QueueMessage queueMessage){
        rabbitTemplate.convertAndSend(exchange.getName(),key, queueMessage);
    }
    public void sendMessage(Exchange exchange, String key, String queueMessage) {
        rabbitTemplate.convertAndSend(exchange.getName(),key, queueMessage);
    }
    public void sendMessage(Exchange exchange, String key, Message message) {
        rabbitTemplate.convertAndSend(exchange.getName(),key, message);
    }
    public void sendMessage(String exchange, String key, Message message){
        rabbitTemplate.convertAndSend(exchange,key, message);
    }
    public void sendMessage(Message message){
        rabbitTemplate.convertAndSend(message.getMessageProperties().getReceivedExchange(),message.getMessageProperties().getReceivedRoutingKey(), message);
    }
}
