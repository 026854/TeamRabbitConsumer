package com.rabbitmq.manager;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StopWatch;


public class Manager {
    private static final String HIGH_QUEUE_NAME = "high-queue";
    private static final String NORMAL_QUEUE_NAME = "normal-queue";
    private static final String DIRECT_EXCHANGE_NAME = "direct-exchange";
    //@RabbitHandler // 메세지 타입에 따라 메서드 매핑
    @RabbitListener(queues ="#{highQueue.name}", concurrency = "3")
    public void highReceiver(String message) throws InterruptedException {
        receive(message, "high");
    }

    @RabbitListener(queues ="#{normalQueue.name}")
    public void normalReceiver(String message) throws InterruptedException {
        receive(message, "normal");
    }

    public void receive(String in, String receiver) throws
            InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("instance " + receiver + " [x] Received '"
                + in + "'");
        doWork(in);
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
