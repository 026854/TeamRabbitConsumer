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
    private String cup,straw;

    @HandleException
    public void receive(Message message, String receiver) throws Exception {
        StopWatch watch = new StopWatch();
        watch.start();

        //메세지를 voa로
        QueueMessage queueMessage = messageConvert.getQueueMessage(message);

        //invaild check
        if (queueMessage.getBeverageType() == null ||
        queueMessage.getId() ==null || queueMessage.getMenu() == null || queueMessage.getBase()==null ||queueMessage.getCore()==null||queueMessage.getDate()==null ) {
            throw new InvalidMessageException("value has null");
        }


        //공통 로직
        cup = getCup();
        straw = getStraw();

        //분기 로직
        cafeReceiver = factory.getCafeReceiver(queueMessage.getBeverageType());
        cafeReceiver.make(cup,straw,message);

        watch.stop();

       //여기서 콜백?

    }
    private String getCup(){
        return "Cup";
    }
    private String getStraw(){
        return "Straw";
    }
    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
