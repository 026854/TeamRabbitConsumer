package com.rabbitmq.manager.config;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class NettyConfiguration {

    @Bean
    public ChannelGroup channelList(){
        return new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    @Bean
    public ConcurrentHashMap<String, String> channelResponse(){
        return new ConcurrentHashMap<>();
    }

    @Bean
    public LinkedBlockingQueue<Channel> channelQueue(){
        LinkedBlockingQueue<Channel> channels = new LinkedBlockingQueue<>();
        return channels;
    }
}
