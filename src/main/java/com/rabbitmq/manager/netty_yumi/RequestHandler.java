package com.rabbitmq.manager.netty_yumi;

import com.rabbitmq.manager.vo.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.group.ChannelGroup;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class RequestHandler {

    Logger logger =  LoggerFactory.getLogger(this.getClass());
    @Autowired
    Bootstrap bootstrap;
    public void request(Message msg){
        logger.info("request: "+msg.toString());
        //Random random = new Random();
        //int randomTask=random.nextInt(2);
        //logger.info("msg (random task:"+(byte)randomTask+") : "+msg);
        String testMsg = msg.toString();
        NettyMessage nettyMessage = new NettyMessage((byte)1,(byte)1,testMsg.getBytes().length,testMsg);

        try {
            ChannelFuture f= bootstrap.connect("localhost", 8080).sync();
            f.channel().writeAndFlush(nettyMessage);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("샷 추출 완료");

    }


}


