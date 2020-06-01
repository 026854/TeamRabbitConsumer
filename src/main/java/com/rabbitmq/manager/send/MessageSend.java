package com.rabbitmq.manager.send;

import com.rabbitmq.manager.vo.Message;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageSend {

    @Autowired
    private RabbitTemplate template;

    public void sendMessage(Exchange exchange, String key, Message message){
        template.convertAndSend(exchange.getName(),key,message);
    }
}
