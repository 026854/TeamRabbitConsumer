package com.rabbitmq.manager.factory;

import com.rabbitmq.manager.send.MessageSend;
import com.rabbitmq.manager.vo.Message;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class NormalReceiver implements CafeReceiver{
    @Autowired
    private MessageSend messageSend;

    @Autowired
    private Exchange exchange;
    @Override
    public void make(Message message) {
        messageSend.sendMessage(exchange,"result",message);

    }
}
