package com.rabbitmq.manager;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Manager {
    private static final String HIGH_QUEUE_NAME = "high-queue";
    private static final String NORMAL_QUEUE_NAME = "normal-queue";
    private static final String DIRECT_EXCHANGE_NAME = "direct-exchange";
    
    @Autowired
    private RabbitTemplate template;
    private ObjectMapper objectMapper = new ObjectMapper();
    
    //@RabbitHandler // 메세지 타입에 따라 메서드 매핑
    @RabbitListener(queues ="high-queue", concurrency = "3")
    public void highReceiver(String message) throws InterruptedException, JsonMappingException, JsonProcessingException {
    	RabbitAdmin admin = new RabbitAdmin(template);
    	System.out.println("message: "+admin.getQueueInfo("high-queue").getMessageCount());
        receive(message, "high");
    }

    @RabbitListener(queues ="normal-queue")
    public void normalReceiver(String message) throws InterruptedException, JsonMappingException, JsonProcessingException {
        receive(message, "normal");
    }

    public void receive(String in, String receiver) throws
            InterruptedException, JsonMappingException, JsonProcessingException {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println(in.getClass());
        Message message = objectMapper.readValue(in, Message.class);
        		
        System.out.println("instance " + receiver + " [x] Received '"
                + message.getId() + "'");
       
        doWork(message.getAfterType());
        watch.stop();
        System.out.println("instance " + receiver + " [x] Done in "
                + watch.getTotalTimeSeconds() + "s");
    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }

}
