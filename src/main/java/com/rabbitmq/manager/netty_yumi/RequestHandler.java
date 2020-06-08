package com.rabbitmq.manager.netty_yumi;

import com.rabbitmq.manager.vo.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.Timer;

@Component
@RequiredArgsConstructor
public class RequestHandler {

    Logger logger =  LoggerFactory.getLogger(this.getClass());
    @Autowired final ChannelGroup ChannelList;

    public void request(Message msg) {

        Integer ans =null;

        //Random random = new Random();
        //int randomTask=random.nextInt(2);
        //logger.info("msg (random task:"+(byte)randomTask+") : "+msg);
        String testMsg = msg.toString();
        NettyMessage nettyMessage = new NettyMessage((byte)1,(byte)1,testMsg.getBytes().length,testMsg);
        while(ChannelList.isEmpty()){

        }
        logger.info("request to channel: "+msg.toString());
        ChannelList.writeAndFlush(nettyMessage);

    }


}


