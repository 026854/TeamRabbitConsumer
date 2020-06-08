package com.rabbitmq.manager.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.manager.vo.QueueMessage;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public abstract class CafeReceiver {
    public abstract void make(String cup, String straw,Message message) throws Exception;


}
