package com.rabbitmq.manager.rabbitmq_jieun;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;



public class Manager {
    private static final String COFFEE_QUEUE_NAME = "coffee-queue";
    private static final String BLENDER_QUEUE_NAME = "blender-queue";
    private static final String NORMAL_QUEUE_NAME = "normal-queue";


    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private ListenerService listenerService;


    @RabbitListener(queues = COFFEE_QUEUE_NAME)
    public void coffeeReceiver(Message message) throws Exception {
        System.out.println(" coffee-message:" + amqpAdmin.getQueueProperties(COFFEE_QUEUE_NAME).get("QUEUE_MESSAGE_COUNT"));
        listenerService.receive(message, "coffee");

    }

    @RabbitListener(queues = NORMAL_QUEUE_NAME, concurrency = "3")
    public void normalReceiver(Message message) throws Exception{


        listenerService.receive(message, "normal");
    }

    @RabbitListener(queues = BLENDER_QUEUE_NAME)
    public void blenderReceiver(Message message) throws Exception {
        listenerService.receive(message, "blender");
    }


}
