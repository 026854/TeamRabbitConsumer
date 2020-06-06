package com.rabbitmq.manager.factory;

import com.rabbitmq.manager.exception.CommunicationFailException;
import com.rabbitmq.manager.netty_yumi.RequestHandler;
import com.rabbitmq.manager.vo.QueueMessage;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CoffeeReceiver extends CafeReceiver {
    @Autowired
    private RequestHandler requestHandler;

    @Override
    public void make(Message message) throws IOException {
        System.out.println("message processed");
    }
}
