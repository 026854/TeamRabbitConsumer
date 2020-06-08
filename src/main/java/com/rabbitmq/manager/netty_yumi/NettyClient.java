package com.rabbitmq.manager.netty_yumi;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.ietf.jgss.Oid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
public class NettyClient {

    Bootstrap clientBootstrap;
    @Autowired
    ChannelGroup ChannelList;

    public void start(){
        clientBootstrap = new Bootstrap();
        clientBootstrap.group(new NioEventLoopGroup(3))
                .channel(NioSocketChannel.class)//서버 소켓 입출력 모드를 NIO로 설정
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
        connect();
        connect();
    }

    public ChannelFuture connect(){
        ChannelFuture channelFuture = clientBootstrap.connect("localhost", 8080);//channel
        return channelFuture;
    }
}