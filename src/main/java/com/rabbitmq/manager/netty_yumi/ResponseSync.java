package com.rabbitmq.manager.netty_yumi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ResponseSync {

    @Autowired
    ConcurrentHashMap<String, String> ResponseMap;

    public String getResult(String key) throws InterruptedException {
        String res = null;
        ResponseMap.put(key,"null");
        System.out.println(key+" - 대기");
        synchronized (ResponseMap.get(key)) {
            ResponseMap.get(key).wait();
            res = ResponseMap.get(key);
            return res;
        }
    }

    public void setResult(String key, String value) throws InterruptedException {
        synchronized(ResponseMap.get(key)) {
            ResponseMap.get(key).notify();
            ResponseMap.put(key, value);
        }
    }
}
