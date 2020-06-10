package com.rabbitmq.manager.factory;

import com.rabbitmq.manager.exception.CommunicationFailException;
import com.rabbitmq.manager.netty_yumi.RequestHandler;
import com.rabbitmq.manager.send.MessageSend;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CoffeeReceiver extends CafeReceiver {
    @Autowired
    private RequestHandler requestHandler;
    @Autowired
    private Exchange exchange;
    @Autowired
    private MessageSend messageSend;

    @Override
    public void make(String cup, String straw,Message message) throws Exception {
        String value =null;
        value = requestHandler.request(message);

        messageSend.sendMessage(exchange.getName(),"result", message);

    }
}
