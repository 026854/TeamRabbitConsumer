package com.rabbitmq.manager.netty_yumi;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageHandler extends SimpleChannelInboundHandler<NettyMessage> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        logger.info("netty Message : "+ msg.toString());
    }


}
