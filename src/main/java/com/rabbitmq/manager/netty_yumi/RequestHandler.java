package com.rabbitmq.manager.netty_yumi;

import com.rabbitmq.manager.vo.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
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

    private final MessageHandler messageHandler;
    @Autowired
    ChannelGroup ChannelList;
    Logger logger =  LoggerFactory.getLogger(this.getClass());

    public void request(Message msg){
        String testMsg = msg.toString();
        Random random = new Random();
        int randomTask=random.nextInt(2);
        logger.info("msg (random task:"+(byte)randomTask+") : "+msg);
        NettyMessage nettyMessage = new NettyMessage((byte)1,(byte)randomTask,testMsg.getBytes().length,testMsg);

        ChannelList.writeAndFlush(nettyMessage);

    }


}


