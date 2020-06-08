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
@PropertySource(value = "classpath:/application.properties")
public class NettyClient {

    @Value("${tcp.port}")
    private int tcpPort;
    @Value("${boss.thread.count}")
    private int bossCount;
    @Value("${worker.thread.count}")
    private int workerCount;
    private final int MAX_FRAME_SIZE =1024;

    @Autowired private MessageHandler messageHandler;
    @Autowired
    ChannelGroup ChannelList;
    public void start() {
        try {
            OioEventLoopGroup oioEventLoopGroup = new OioEventLoopGroup(1);
            //NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(1);
            Bootstrap b = new Bootstrap();
            b.group(oioEventLoopGroup)
                    .channel(OioSocketChannel.class)//서버 소켓 입출력 모드를 NIO로 설정
                    .handler(new ChannelInitializer<SocketChannel>() { //클라이언트 소켓 채널로 송수신 되는 데이터 가공 핸들러
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                            //디코더 추가
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(MAX_FRAME_SIZE,2,4));
                            //pipeline.addLast(new LineBasedFrameDecoder());
                            pipeline.addLast(new MessageDecoder());;
                            pipeline.addLast(new MessageEncoder());
                            //핸들러 추가
                            pipeline.addLast(messageHandler);
                            pipeline.addLast(new TestHandler());
                        }
                    }).option(ChannelOption.SO_TIMEOUT,1);
            //ChannelFuture : 비동기 방식의 작업 처리 후 결과를 제어
            //클라이언트의 요청이 들어올 수 있게 포트를 활성화 시킴
            ChannelFuture channelFuture = b.connect("localhost",8080).sync();
            //서버 소켓이 닫힐 때까지 대기

            //Thread.sleep(3000);
            //channelFuture.channel().close();
            //Thread.sleep(2000);
            //ChannelFuture channelFuture1 = b.connect("localhost",8080).sync();

            ChannelList.add(channelFuture.channel());
            //ChannelList.add(channelFuture1.channel());
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
    }

}
