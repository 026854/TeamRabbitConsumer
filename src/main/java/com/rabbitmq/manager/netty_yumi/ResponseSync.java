package com.rabbitmq.manager.netty_yumi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ResponseSync {

    @Autowired
    HashMap<String, String> ResponseMap;
    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    public String getResult(String key) throws InterruptedException {
        String res = null;
        ResponseMap.put(key,"null");
        System.out.println(key+" - 대기");
        synchronized (ResponseMap) {
            ResponseMap.wait();
            return res = ResponseMap.get(key);
            /*
            while (true) {
                System.out.println("gg1");
                System.out.println(ResponseMap.get(key).getClass());
                if ((res = ResponseMap.get(key)) != "null") {
                    System.out.println("gg");
                    //ResponseMap.get(key).notifyAll();
                    return res;
                } else {
                    ResponseMap.get(key).wait();
                }
            }
            */
        }
    }

    public void setResult(String key, String value) throws InterruptedException {
        //System.out.println(key+" - "+value+" 넣어줌 ");
        //ResponseMap.get(key).notifyAll();
        synchronized(ResponseMap) { //다 대기함..;;
            ResponseMap.put(key, value);
            System.out.println(ResponseMap.get(key));
            Thread.sleep(5000);
            ResponseMap.notify();
            System.out.println(key+" - "+value+" 넣어줌!! ");
        }
    }
}
