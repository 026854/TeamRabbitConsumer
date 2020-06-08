package com.rabbitmq.manager.factory;

import com.rabbitmq.manager.netty_yumi.RequestHandler;
import com.rabbitmq.manager.netty_yumi.ResponseSync;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CoffeeReceiver extends CafeReceiver {
    @Autowired
    private RequestHandler requestHandler;

    @Override
    public void make(Message message) throws Exception {
        System.out.println("동시에 되나..?");
        //System.out.println(message);
        String value =null;
        value = requestHandler.request(message);
        //Thread.sleep(5000);
    }
}
