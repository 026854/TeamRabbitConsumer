package com.rabbitmq.manager.rabbitmq_jieun;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.manager.exception.JsonFailException;
import com.rabbitmq.manager.vo.QueueMessage;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageConvert {
    QueueMessage queueMessage = new QueueMessage();
    public MessageConvert() {
    }

    public QueueMessage getQueueMessage(Message message) throws Exception {
        try{
        byte[] b = message.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        String qm = objectMapper.readValue(new String(b),String.class);
        queueMessage = objectMapper.readValue(qm,  QueueMessage.class);
       }
       catch (Exception e){
            throw  new JsonFailException();
     }
        return queueMessage;
    }
}
