package com.rabbitmq.manager;

import com.rabbitmq.manager.netty_yumi.NettyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ManagerApplication{

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ManagerApplication.class, args);

        NettyClient nettyClient = context.getBean(NettyClient.class);
        nettyClient.start();
    }


}
