package com.rabbitmq.manager.netty_yumi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.manager.config.BeanUtils;
import com.rabbitmq.manager.send.MessageSend;
import com.rabbitmq.manager.vo.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

//@Component
//@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<NettyMessage> {


    HashMap<String, String> ResponseMap;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    //final AttributeKey<Byte> taskType = AttributeKey.newInstance("taskType");
    ChannelGroup ChannelList;
  //  private final ChannelGroup channelList;
   RequestHandler requestHandler;
  ResponseSync responseSync;

  public MessageHandler() {
    logger =  LoggerFactory.getLogger(this.getClass());
    ResponseMap = (HashMap<String, String>) BeanUtils.getBean("ResponseMap");
    ChannelList =(ChannelGroup) BeanUtils.getBean("ChannelList");
    requestHandler =(RequestHandler) BeanUtils.getBean("requestHandler");
    responseSync=(ResponseSync) BeanUtils.getBean("responseSync");
  }

  @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
      if(ChannelList == null) logger.info("channel is null");
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
       responseSync.setResult(msg.getBody(),"내용물");
      // ResponseMap.notifyAll();
       logger.info("key 넣음"+ResponseMap.get("key"));
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
