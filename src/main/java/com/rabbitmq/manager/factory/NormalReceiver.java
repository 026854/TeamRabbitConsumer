package com.rabbitmq.manager.factory;

import com.rabbitmq.manager.send.MessageSend;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class NormalReceiver extends CafeReceiver{
    @Autowired
    private MessageSend messageSend;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Exchange exchange;

    @Override
    public void make(String cup, String straw,Message message){

        messageSend.sendMessage(exchange.getName(),"result", message);

    }
}
