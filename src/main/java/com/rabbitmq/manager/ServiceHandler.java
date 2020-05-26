package com.rabbitmq.manager;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

@ChannelHandler.Sharable
public class ServiceHandler  extends ChannelInboundHandlerAdapter {
    //입력된 데이터를 처리하는 이벤트 핸들러 상속
    /**
     * The Logger.
     */
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The Channels.
     */
    private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * Channel active.
     *
     * @param ctx the ctx
     * @throws Exception the exception
     */
    //채널 활성화 ; 최초에 채널 연결할 때 실행
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
    }

    /**
     * Channel read.
     *
     * @param ctx the ctx
     * @param msg the msg
     * @throws Exception the exception
     */
    @Override
    //데이터 수신 이벤트 처리 메서드. 클라이언트로부터 데이터 수신이 이뤄졌을 때 실행
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        logger.debug("message : {} ",byteBuf.toString(Charset.defaultCharset()));
        //채널 파이프라인에 대한 이벤트 처리 writeAndFlush 데이터를 쓰고 버퍼를 전송
        //channels.writeAndFlush(msg);
        channels.write(msg);
        //channels.write(msg);
        channels.flush();
    }


}
