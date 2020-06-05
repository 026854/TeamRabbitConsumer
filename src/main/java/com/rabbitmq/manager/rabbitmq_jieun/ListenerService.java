package com.rabbitmq.manager.rabbitmq_jieun;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.manager.factory.CafeReceiver;
import com.rabbitmq.manager.factory.CafeReceiverFactory;
import com.rabbitmq.manager.netty_yumi.RequestHandler;
import com.rabbitmq.manager.vo.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
public class ListenerService {



    @Autowired
    private CafeReceiverFactory factory;

    private CafeReceiver cafeReceiver;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    RequestHandler requestHandler;


    public void receive(String in, String receiver) throws
            InterruptedException, JsonMappingException, JsonProcessingException {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println(in.getClass());
        Message message = objectMapper.readValue(in, Message.class);
        System.out.println("instance " + receiver + " [x] Received '"
                + message.getId() + "'");
        //doWork(message.getMenu());

        cafeReceiver = factory.getCafeReceiver(message.getBeverageType());
        cafeReceiver.make(message);

        watch.stop();
        //System.out.println("instance " + receiver + " [x] Done in "
        //        + watch.getTotalTimeSeconds() + "s");

        //requestHandler.request(message);
    }
    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
