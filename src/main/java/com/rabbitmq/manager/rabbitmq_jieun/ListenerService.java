package com.rabbitmq.manager.rabbitmq_jieun;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.manager.exception.HandleException;
import com.rabbitmq.manager.factory.CafeReceiver;
import com.rabbitmq.manager.factory.CafeReceiverFactory;
//import com.rabbitmq.manager.vo.Message;
import com.rabbitmq.manager.vo.QueueMessage;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.IOException;

@Service
public class ListenerService {



    @Autowired
    private CafeReceiverFactory factory;

    private CafeReceiver cafeReceiver;

    private ObjectMapper objectMapper = new ObjectMapper();


    @HandleException
    public void receive(Message message, String receiver) throws
            Exception {
        StopWatch watch = new StopWatch();
        watch.start();
       QueueMessage queueMessage = objectMapper.readValue(new String(message.getBody()),  QueueMessage.class);
        System.out.println("instance " + receiver + " [x] Received '"
                + queueMessage.getId() + "'");
        //doWork(message.getMenu());

        cafeReceiver = factory.getCafeReceiver(queueMessage.getBeverageType());
        cafeReceiver.make(message);

        watch.stop();
        //System.out.println("instance " + receiver + " [x] Done in "
        //        + watch.getTotalTimeSeconds() + "s");
       //여기서 콜백?

    }
    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
