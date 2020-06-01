package com.rabbitmq.manager.factory;

import com.rabbitmq.manager.netty_yumi.RequestHandler;
import com.rabbitmq.manager.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class BlenderReceiver implements CafeReceiver{
    @Autowired
    private RequestHandler requestHandler;

    @Override
    public void make(Message message) {

        requestHandler.request(message);
    }
}
