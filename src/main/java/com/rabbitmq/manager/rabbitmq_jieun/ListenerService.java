package com.rabbitmq.manager.rabbitmq_jieun;

import com.rabbitmq.manager.exception.HandleException;
import com.rabbitmq.manager.exception.InvalidMessageException;
import com.rabbitmq.manager.factory.CafeReceiver;
import com.rabbitmq.manager.factory.CafeReceiverFactory;
import com.rabbitmq.manager.vo.QueueMessage;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
public class ListenerService {



    @Autowired
    private CafeReceiverFactory factory;

    private CafeReceiver cafeReceiver;

    private MessageConvert messageConvert = new MessageConvert();


    @HandleException
    public void receive(Message message, String receiver) throws
            Exception {
        StopWatch watch = new StopWatch();
        watch.start();

       QueueMessage queueMessage = messageConvert.getQueueMessage(message);

        if (queueMessage.getBeverageType() == null ||
        queueMessage.getId() ==null || queueMessage.getMenu() == null || queueMessage.getBase()==null ||queueMessage.getCore()==null||queueMessage.getDate()==null ) {
            throw new InvalidMessageException("value has null");
        }
        cafeReceiver = factory.getCafeReceiver(queueMessage.getBeverageType());
        cafeReceiver.make(message);

        watch.stop();


    }
    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
