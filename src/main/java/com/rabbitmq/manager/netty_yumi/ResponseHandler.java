package com.rabbitmq.manager.netty_yumi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.manager.send.MessageSend;
import com.rabbitmq.manager.vo.Message;
import org.springframework.amqp.core.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandler {
    ObjectMapper objectMapper = new ObjectMapper();
    private MessageSend messageSend;
    @Autowired
    private Exchange exchange;

    void response(String msg) throws JsonProcessingException {
        Message message = objectMapper.readValue(msg, Message.class);
        messageSend.sendMessage(exchange,"result",message);
        //결과 큐에 넣음
    }
}
