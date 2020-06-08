package com.rabbitmq.manager.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.manager.send.MessageSend;
import com.rabbitmq.manager.vo.QueueMessage;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NormalReceiver extends CafeReceiver{

    private MessageSend messageSend = new MessageSend();
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Exchange exchange;
    @Override
    public void make(String cup, String straw,Message message) throws Exception {
        rabbitTemplate.convertAndSend(exchange.getName(),"result",message);
        //messageSend.sendMessage(exchange.getName(),"result", message);

    }
}
