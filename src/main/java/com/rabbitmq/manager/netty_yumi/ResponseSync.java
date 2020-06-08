package com.rabbitmq.manager.netty_yumi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ResponseSync {

    @Autowired
    HashMap<String, String> ResponseMap;

    public synchronized String getResult(String key) throws InterruptedException {
        String res = null;
        //ResponseMap.put(key,"test");
        while(true){
            if( (res =ResponseMap.get(key)) != null){
                return res;
            }else{
                wait();
            }
        }
    }

    public synchronized void setResult(String key, String value)  {
        ResponseMap.put(key,value);
        notifyAll();

    }
}
