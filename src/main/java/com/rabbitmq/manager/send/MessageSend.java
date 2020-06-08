package com.rabbitmq.manager.send;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    public void sendMessage(Exchange exchange, String key, QueueMessage queueMessage) throws Exception {
        rabbitTemplate.convertAndSend(exchange.getName(),key, queueMessage);
    }
    public void sendMessage(Exchange exchange, String key, String queueMessage) throws Exception {
        rabbitTemplate.convertAndSend(exchange.getName(),key, queueMessage);
    }
    public void sendMessage(Exchange exchange, String key, Message message) throws Exception {
        rabbitTemplate.convertAndSend(exchange.getName(),key, "messagerkqslek");
    }
    public void sendMessage(String exchange, String key, Message message) throws Exception {
        rabbitTemplate.convertAndSend(exchange,key, "messagerkqslek");
    }
    public void sendMessage(Message message) throws Exception {
        rabbitTemplate.convertAndSend(message.getMessageProperties().getReceivedExchange(),message.getMessageProperties().getReceivedRoutingKey(), message);
    }
}
