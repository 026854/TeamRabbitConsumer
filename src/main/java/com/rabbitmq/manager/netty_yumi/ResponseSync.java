package com.rabbitmq.manager.netty_yumi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ResponseSync {

    @Autowired
    ConcurrentHashMap<String, String> channelResponse;

    public String getResult(String key) throws InterruptedException {
        String res = null;
        channelResponse.put(key,"null");
        synchronized (channelResponse.get(key)) {
            channelResponse.get(key).wait();
            res = channelResponse.get(key);
            return res;
        }
    }

    public void setResult(String key, String value) throws InterruptedException {
        synchronized(channelResponse.get(key)) {
            channelResponse.get(key).notify();
            channelResponse.put(key, value);
        }
    }
}
