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
    private RabbitTemplate template;
   // private ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(Exchange exchange, String key, QueueMessage queueMessage) throws JsonProcessingException {
        template.convertAndSend(exchange.getName(),key, queueMessage);
    }
    public void sendMessage(Message message) throws JsonProcessingException {
        template.convertAndSend(message.getMessageProperties().getReceivedExchange(),message.getMessageProperties().getReceivedRoutingKey(), message);
    }
}
