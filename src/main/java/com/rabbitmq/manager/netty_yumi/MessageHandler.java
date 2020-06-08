package com.rabbitmq.manager.netty_yumi;

import com.rabbitmq.manager.config.BeanUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

//@Component
//@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<NettyMessage> {


    ConcurrentHashMap<String, String> ResponseMap;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    //final AttributeKey<Byte> taskType = AttributeKey.newInstance("taskType");
    ChannelGroup ChannelList;
    //  private final ChannelGroup channelList;
    RequestHandler requestHandler;
    ResponseSync responseSync;

    public MessageHandler() {
        logger =  LoggerFactory.getLogger(this.getClass());
        ResponseMap = (ConcurrentHashMap<String, String>) BeanUtils.getBean("ResponseMap");
        ChannelList =(ChannelGroup) BeanUtils.getBean("ChannelList");
        requestHandler =(RequestHandler) BeanUtils.getBean("requestHandler");
        responseSync=(ResponseSync) BeanUtils.getBean("responseSync");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
      ChannelList.add(ctx.channel());
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

       //ResponseMap.put(msg.getBody(), "내용물");
       responseSync.setResult(msg.getBody(),"water");
       logger.info("key 넣음"+ResponseMap.get(msg.getBody()));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }



    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }
}
