package com.rabbitmq.manager.netty_yumi;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<NettyMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf out) throws Exception {
        if(msg == null){
            throw new Exception("message is null");
        }
        out.writeByte(msg.getMessageType());
        out.writeByte(msg.getLength());
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getBody().getBytes());

    }
}
