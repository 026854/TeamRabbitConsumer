package com.rabbitmq.manager.netty_yumi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ResponseSync {

    @Autowired
    HashMap<String, String> ResponseMap;

    public String getResult(String key) throws InterruptedException {
        String res = null;
        ResponseMap.put(key,"null");
        while (true) {
            synchronized (ResponseMap.get(key)) {
                if ((res = ResponseMap.get(key)) != "null") {
                    return res;
                } else {
                    ResponseMap.get(key).wait();
                }
            }
        }
    }

    public void setResult(String key, String value)  {
        System.out.println(key+" - "+value+" 넣어줌 ");
        //synchronized() {
            ResponseMap.put(key, value);
            ResponseMap.get(key).notifyAll();
            System.out.println(key+" - "+value+" 넣어줌 ");
           // ResponseMap.get(key).notifyAll();
        //}
    }
}
