package com.rabbitmq.manager.factory;

import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;


@Component
public abstract class CafeReceiver {
    public abstract void make(String cup, String straw,Message message) throws Exception;


}
