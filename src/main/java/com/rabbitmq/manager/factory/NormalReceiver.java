package com.rabbitmq.manager.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.manager.send.MessageSend;
import com.rabbitmq.manager.vo.QueueMessage;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NormalReceiver extends CafeReceiver{
    @Autowired
    private MessageSend messageSend;

    @Autowired
    private Exchange exchange;
    @Override
    public void make(Message message) throws IOException {
        //messageSend.sendMessage(exchange,"result", getQueueMessage(message));

    }
}
