package com.rabbitmq.manager.factory;

import com.rabbitmq.manager.netty_yumi.RequestHandler;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlenderReceiver extends CafeReceiver{
    @Autowired
    private RequestHandler requestHandler;

    @Override
    public void make(String cup, String straw,Message message) throws Exception {
        String value =null;
        value = requestHandler.request(message);

    }
}
