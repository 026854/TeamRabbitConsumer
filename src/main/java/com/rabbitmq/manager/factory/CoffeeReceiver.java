package com.rabbitmq.manager.factory;

import com.rabbitmq.manager.netty_yumi.RequestHandler;
import com.rabbitmq.manager.netty_yumi.ResponseSync;
import com.rabbitmq.manager.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CoffeeReceiver implements CafeReceiver {
    @Autowired
    private RequestHandler requestHandler;

    @Autowired
    ResponseSync responseSync;
    @Override
    public void make(Message message) {
        String value =null;
        requestHandler.request(message);
        System.out.println("block되니?");
        String tempkey = "key";
        try {
            value = responseSync.getResult(tempkey);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("chk value"+value);
    }
}
