package com.rabbitmq.manager.exception;

import lombok.Data;
import org.springframework.amqp.core.Message;

@Data
public abstract class CustomException extends RuntimeException{
    private Message rabbitMqMessage;

    public CustomException() {
        super();
    }

    public CustomException(String errorMessage) {
        super(errorMessage);
    }

    public CustomException(String errorMessage, Message rabbitMqMessage) {
        super(errorMessage);
        this.rabbitMqMessage = rabbitMqMessage;
    }
}
