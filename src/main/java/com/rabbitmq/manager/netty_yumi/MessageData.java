package com.rabbitmq.manager.netty_yumi;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageData {

    private ConcurrentHashMap<String, Optional<String>> message =new ConcurrentHashMap<>();
   // private BlockingConcurrentHash
    public synchronized void getMessage(){

    }

}
