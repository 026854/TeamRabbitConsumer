package com.rabbitmq.manager.rabbitmq_jieun;

import ch.qos.logback.core.net.server.Client;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.manager.factory.CafeReceiver;
import com.rabbitmq.manager.factory.CafeReceiverFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannelImpl;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.IOException;


public class Manager {
    private static final String COFFEE_QUEUE_NAME = "coffee-queue";
    private static final String BLENDER_QUEUE_NAME = "blender-queue";
    private static final String NORMAL_QUEUE_NAME = "normal-queue";
    private static final String DIRECT_EXCHANGE_NAME = "direct-exchange";
    
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private ListenerService listenerService;



    @Autowired
    private RabbitAdmin rabbitAdmin;
    Channel channel;


    //@RabbitHandler // 메세지 타입에 따라 메서드 매핑
    @RabbitListener(queues =COFFEE_QUEUE_NAME,containerFactory = "prefetchTenRabbitListenerContainerFactory")
    public void highReceiver(String message) throws InterruptedException, IOException {
    	//RabbitAdmin admin = new RabbitAdmin(template);
//        AMQP.Queue.DeclareOk declareOk = amqpAdmin.getRabbitTemplate().execute(new ChannelCallback<AMQP.Queue.DeclareOk>() {
//            @Override
//            public AMQP.Queue.DeclareOk doInRabbit(Channel channel) throws Exception {
//                return channel.queueDeclarePassive(HIGH_QUEUE_NAME);
//            }
//        });



        //ch.write()

       // System.out.println("message: "+amqpAdmin.getQueueInfo("high-queue").getMessageCount());
        //System.out.println("message: "+channel.queueDeclarePassive("high-queue").getMessageCount());

        System.out.println(" coffee-message:" + amqpAdmin.getQueueProperties(COFFEE_QUEUE_NAME).get("QUEUE_MESSAGE_COUNT"));


        listenerService.receive(message, "coffee");
        //Thread.sleep(5000);
    }

    @RabbitListener(queues=NORMAL_QUEUE_NAME,concurrency = "3")
    public void normalReceiver(String message) throws InterruptedException, JsonMappingException, JsonProcessingException {
        listenerService.receive(message, "normal");
    }
    @RabbitListener(queues =BLENDER_QUEUE_NAME)
    public void blenderReceiver(String message) throws InterruptedException, JsonMappingException, JsonProcessingException {
        listenerService.receive(message, "blender");
    }


}
