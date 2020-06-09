package com.rabbitmq.manager.rabbitmq_jieun;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.manager.vo.QueueMessage;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

public class MessageConvert {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static QueueMessage getQueueMessage(Message message) throws JsonProcessingException {
        byte[] b = message.getBody();
        String qm = objectMapper.readValue(new String(b),String.class);
        return objectMapper.readValue(qm,  QueueMessage.class);
    }

    public static QueueMessage getQueueMessage(String msg) throws Exception{
        return objectMapper.readValue(msg,QueueMessage.class);
    }
}
