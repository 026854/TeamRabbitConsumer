package com.rabbitmq.manager.netty_yumi;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageHandler extends SimpleChannelInboundHandler<NettyMessage> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ChannelGroup channelList;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        channelList.add(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        logger.info("netty Message : "+ msg.toString());
    }


}
