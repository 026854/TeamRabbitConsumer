package com.rabbitmq.manager.exception;

import org.springframework.amqp.core.Message;

public class CommunicationFailException extends CustomException{
    public CommunicationFailException() {
        super();
    }

    public CommunicationFailException(String errorMessage) {
        super(errorMessage);
    }

    public CommunicationFailException(String errorMessage, Message message) {
        super(errorMessage, message);
    }
}
