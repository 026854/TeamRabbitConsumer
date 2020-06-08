package com.rabbitmq.manager.netty_yumi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.manager.exception.JsonFailException;
import com.rabbitmq.manager.rabbitmq_jieun.MessageConvert;
import com.rabbitmq.manager.send.MessageSend;
import com.rabbitmq.manager.vo.QueueMessage;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandler {
    ObjectMapper objectMapper = new ObjectMapper();
    private MessageSend messageSend;
    private String message;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    private Exchange exchange;

    void response(String msg) throws Exception {
        try {
            QueueMessage queueMessage = objectMapper.readValue(msg, QueueMessage.class);
            message = objectMapper.writeValueAsString(queueMessage);
        }catch (Exception e){
            throw new JsonFailException();
        }
        rabbitTemplate.convertAndSend(exchange.getName(),"result",message);
        //messageSend.sendMessage(exchange,"result", message);
        //결과 큐에 넣음
    }
}
