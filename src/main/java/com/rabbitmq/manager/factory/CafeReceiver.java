package com.rabbitmq.manager.factory;

import com.rabbitmq.manager.vo.Message;
import org.springframework.stereotype.Component;

@Component
public interface CafeReceiver {
    public void make(Message message);
}
