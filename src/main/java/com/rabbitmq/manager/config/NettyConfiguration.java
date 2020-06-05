package com.rabbitmq.manager.config;

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

@Configuration
public class NettyConfiguration {

    @Bean
    public ChannelGroup ChannelList(){
        return new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    @Bean
    public Bootstrap bootstrap(){
        Bootstrap b = new Bootstrap();
        b.group(new OioEventLoopGroup())
                .channel(OioSocketChannel.class)//서버 소켓 입출력 모드를 NIO로 설정
                .handler(new ChannelInitializer<SocketChannel>() { //클라이언트 소켓 채널로 송수신 되는 데이터 가공 핸들러
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                        //코덱
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(2024,2,4));
                        pipeline.addLast(new MessageDecoder());;
                        pipeline.addLast(new MessageEncoder());
                        //핸들러 추가
                        pipeline.addLast(new MessageHandler());
                    }
                });
        return b;
    }
}
