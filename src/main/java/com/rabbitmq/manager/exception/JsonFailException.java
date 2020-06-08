package com.rabbitmq.manager.exception;

public class JsonFailException extends CustomException{
    public JsonFailException(){
        super();
    }
    public JsonFailException(String errorMessage){
        super(errorMessage);
    }

}
