package com.rabbitmq.manager.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.manager.send.MessageSend;
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
public class ExceptionHandlerAspect {
    @Pointcut("@annotation(com.rabbitmq.manager.exception.HandleException)")
    public void handledMethods() {}

    @Autowired
    private RabbitTemplate template;
    @Autowired
    private MessageSend messageSend;

    @AfterThrowing(pointcut = "handledMethods()", throwing = "ex")
    public void handleTheException(Exception ex){
        Logger logger =  LoggerFactory.getLogger(this.getClass());
        if (ex instanceof CommunicationFailException) {
            CommunicationFailException communicationFailException = (CommunicationFailException) ex;

            if (communicationFailException.getRabbitMqMessage() == null) {

                logger.info("communucationFailException  === > RabbitMQ is null");
                return;
            }

            MessageProperties properties = communicationFailException.getRabbitMqMessage().getMessageProperties();
            Integer deathCnt = (Integer) properties.getHeaders().get("x-death");


            if(deathCnt>3){
               logger.info("deathCnt is full ----> dead :(");
                return;
            }
            properties.getHeaders().put("x-death",deathCnt+1);
            properties.setHeader("x-delay",(deathCnt+1)*5000);

            messageSend.sendMessage(properties.getReceivedExchange(),properties.getReceivedRoutingKey(),communicationFailException.getRabbitMqMessage());
        } else if (ex instanceof InvalidMessageException) {
            // do nothing
            logger.info(ex.getMessage());
        }
        else if(ex instanceof JsonParseException){
            logger.info("json failed");
            return;
        }
        else {
            // report
            System.out.println("report error to us");
        }
    }
}