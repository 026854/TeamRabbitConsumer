package com.rabbitmq.manager.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.manager.rabbitmq_jieun.MessageConvert;
import com.rabbitmq.manager.send.MessageSend;
import com.rabbitmq.manager.vo.QueueMessage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionHandlerAspect {
    @Pointcut("@annotation(com.rabbitmq.manager.exception.HandleException)")
    public void handledMethods() {}

    @Autowired
    private RabbitTemplate template;
    @Autowired
    private MessageSend messageSend;

    @AfterThrowing(pointcut = "handledMethods()", throwing = "ex")
    public void handleTheException(Exception ex) throws JsonProcessingException {
        if (ex instanceof CommunicationFailException) {
            CommunicationFailException communicationFailException = (CommunicationFailException) ex;

            if (communicationFailException.getRabbitMqMessage() == null) {

                log.info("communucationFailException  === > RabbitMQ is null");
                log.error("con Error! :  report error to us",ex);
                return;
            }

            MessageProperties properties = communicationFailException.getRabbitMqMessage().getMessageProperties();
            Integer deathCnt = (Integer) properties.getHeaders().get("x-death");
            QueueMessage queueMessage = MessageConvert.getQueueMessage(communicationFailException.getRabbitMqMessage());



            if(deathCnt>3){
               log.error("ID : {}, deathCnt is full", queueMessage.getId(), ex);
                return;
            }
            properties.getHeaders().put("x-death",deathCnt+1);
            properties.setHeader("x-delay",(deathCnt+1)*5000);

            messageSend.sendMessage(properties.getReceivedExchange(),properties.getReceivedRoutingKey(),communicationFailException.getRabbitMqMessage());
        } else if (ex instanceof InvalidMessageException) {
            // do nothing
            InvalidMessageException invalidMessageException = (InvalidMessageException) ex;
            QueueMessage queueMessage = MessageConvert.getQueueMessage(invalidMessageException.getRabbitMqMessage());

            log.warn("queueMessage : {}, mqMessage: {}", queueMessage,invalidMessageException.getRabbitMqMessage(), ex);
        }
        else if(ex instanceof JsonParseException){
            log.info("json failed");
            return;
        }
        else {
            // report
            log.error("unexpected Error! :  report error to us",ex);
        }
    }
}