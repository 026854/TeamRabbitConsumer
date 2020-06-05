package com.rabbitmq.manager.rabbitmq_jieun;


import com.rabbitmq.client.Channel;

import com.rabbitmq.manager.vo.Message;
import org.springframework.amqp.core.AmqpAdmin;
//import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

    @RabbitListener(queues = COFFEE_QUEUE_NAME, containerFactory = "prefetchTenRabbitListenerContainerFactory")
    public void coffeeReceiver(Message message) throws InterruptedException, IOException {

        //enqueued message count
        System.out.println(" coffee-message:" + amqpAdmin.getQueueProperties(COFFEE_QUEUE_NAME).get("QUEUE_MESSAGE_COUNT"));

        //System.out.println(message.getMessageProperties().getTimestamp());
        //AMQP.BasicProperties pro = new AMQP.BasicProperties();
        //pro.getTimestamp();
        //System.out.println("time" + pro.getTimestamp().toString());

        System.out.println(message.getDate());
        //listenerService.receive(message, "coffee");
        //Thread.sleep(5000);
    }

//    @RabbitListener(queues = NORMAL_QUEUE_NAME, concurrency = "3")
//    public void normalReceiver(Message message) throws InterruptedException, JsonMappingException, JsonProcessingException {
//
//        listenerService.receive(message, "normal");
//    }
//
//    @RabbitListener(queues = BLENDER_QUEUE_NAME)
//    public void blenderReceiver(Message message) throws InterruptedException, JsonMappingException, JsonProcessingException {
//        listenerService.receive(message, "blender");
//    }


}
