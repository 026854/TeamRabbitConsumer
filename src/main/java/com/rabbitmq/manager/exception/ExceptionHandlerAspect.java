package com.rabbitmq.manager.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.manager.send.MessageSend;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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
    private MessageSend messageSend;

    @AfterThrowing(pointcut = "handledMethods()", throwing = "ex")
    public void handleTheException(Exception ex){
        if (ex instanceof CommunicationFailException) {
            CommunicationFailException communicationFailException = (CommunicationFailException) ex;

            if (communicationFailException.getRabbitMqMessage() == null) {
                return;
            }

            MessageProperties properties = communicationFailException.getRabbitMqMessage().getMessageProperties();
            Integer deathCnt = (Integer) properties.getHeaders().get("x-death");

            properties.getHeaders().put("x-delay",5000);
            if(deathCnt>3){
                System.out.println("deathCnt is full ----> dead :(");
                return;
            }
            properties.getHeaders().put("x-death",deathCnt+1);
           template.convertAndSend(properties.getReceivedExchange(),properties.getReceivedRoutingKey(),communicationFailException.getRabbitMqMessage());
        } else if (ex instanceof InvalidMessageException) {
            // do nothing
            System.out.println(ex.getMessage());
        }
        else if(ex instanceof JsonFailException){

            System.out.println("jsonfailed");
            return;
        }
        else {
            // report
            System.out.println("report error to us");
        }
    }
}