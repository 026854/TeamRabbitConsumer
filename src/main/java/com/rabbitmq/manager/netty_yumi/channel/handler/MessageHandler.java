package com.rabbitmq.manager.netty_yumi.channel.handler;

import com.rabbitmq.manager.config.BeanUtils;
import com.rabbitmq.manager.netty_yumi.NettyMessage;
import com.rabbitmq.manager.netty_yumi.RequestHandler;
import com.rabbitmq.manager.netty_yumi.ResponseSync;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

//@Component
//@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<NettyMessage> {


    ConcurrentHashMap<String, String> channelResponse;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    //final AttributeKey<Byte> taskType = AttributeKey.newInstance("taskType");
    ChannelGroup channelList;
    RequestHandler requestHandler;
    ResponseSync responseSync;
    LinkedBlockingQueue<Channel> channelQueue;

    public MessageHandler() {
        logger =  LoggerFactory.getLogger(this.getClass());
        channelResponse = (ConcurrentHashMap<String, String>) BeanUtils.getBean("channelResponse");
        channelList =(ChannelGroup) BeanUtils.getBean("channelList");
        requestHandler =(RequestHandler) BeanUtils.getBean("requestHandler");
        responseSync=(ResponseSync) BeanUtils.getBean("responseSync");
        channelQueue= (LinkedBlockingQueue<Channel>)BeanUtils.getBean("channelQueue");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelList.add(ctx.channel());
        //channelQueue.offer(ctx.channel());
        //offer vs add vs put
        channelQueue.put(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {

        /*
        // 요청이면 요청 핸들러,, 응답이면 응답 핸들러를 fire 하는 식으로??
        if(msg.getMessageType() == 1){ //요청 메세지 처리
            logger.info("MessageHandler: channelRead0() : request : "+msg.getTaskType());
            ctx.channel().attr(taskType).set(msg.getTaskType());
        }else{ //응답 메세지 처리
            logger.info("MessageHandler: channelRead0() : response : "+msg.getBody());
            //responseHandler.response(msg.getBody());
        }
        */

       responseSync.setResult(msg.getBody(),"water");
       //logger.info("key :"+msg.getBody()+"에 재료 주입" );
    }

}
