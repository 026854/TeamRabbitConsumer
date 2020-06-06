package com.rabbitmq.manager.netty_yumi;

import com.rabbitmq.manager.vo.QueueMessage;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class RequestHandler {

    private final MessageHandler messageHandler;
    private final ChannelGroup channelList;
    Logger logger =  LoggerFactory.getLogger(this.getClass());

    public void request(QueueMessage msg){
        String testMsg = msg.toString();
        Random random = new Random();
        int randomTask=random.nextInt(2);
        logger.info("msg (random task:"+(byte)randomTask+") : "+msg);
        NettyMessage nettyMessage = new NettyMessage((byte)1,(byte)randomTask,testMsg.getBytes().length,testMsg);

        for(Channel channel : channelList){
           if( channel.attr(messageHandler.taskType).get() == (byte)randomTask){
               channel.writeAndFlush(nettyMessage);
           }
        }
    }


}


