package com.rabbitmq.manager.netty_yumi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.manager.send.MessageSend;
import com.rabbitmq.manager.vo.Message;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<NettyMessage> {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    final AttributeKey<Byte> taskType = AttributeKey.newInstance("taskType");

    private final ChannelGroup channelList;
    private final ResponseHandler responseHandler;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        channelList.add(ctx.channel());
        //던져줘
        String str ="hihi";
        NettyMessage nettyMessage = new NettyMessage((byte)1, (byte)1,str.getBytes().length,str );
        ChannelFuture channelFuture = ctx.channel().writeAndFlush(nettyMessage);

        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()){

                    String str1 ="byebye";
                    NettyMessage nettyMessage1 = new NettyMessage((byte)1, (byte)1,str1.getBytes().length,str1 );
                    ctx.channel().writeAndFlush(nettyMessage1);
                }
            }
        });
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        logger.info("chennel Read :"+msg.getBody());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
