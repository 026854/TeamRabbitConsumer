package com.rabbitmq.manager.config;

import com.rabbitmq.manager.netty_yumi.MessageData;
import com.rabbitmq.manager.netty_yumi.MessageDecoder;
import com.rabbitmq.manager.netty_yumi.MessageEncoder;
import com.rabbitmq.manager.netty_yumi.MessageHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class NettyConfiguration {

    @Bean
    public ChannelGroup ChannelList(){
        return new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    @Bean
    public ConcurrentHashMap<String, String> ResponseMap(){
        return new ConcurrentHashMap<>();
    }

}
