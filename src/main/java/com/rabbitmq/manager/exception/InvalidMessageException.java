package com.rabbitmq.manager.exception;

import org.springframework.amqp.core.Message;

public class InvalidMessageException extends CustomException{
    public InvalidMessageException() {
        super();
    }

    public InvalidMessageException(String errorMessage) {
        super(errorMessage);
    }
    public InvalidMessageException(String errorMessage,Message message) {
        super(errorMessage,message);
    }

}
