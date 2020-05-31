package com.rabbitmq.manager.netty_yumi;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class MessageDecoderTest {

    @Test
    public void 코덱_확인(){
        //given
        EmbeddedChannel channel = new EmbeddedChannel(new MessageDecoder(), new MessageEncoder());
        String str ="hello, world";
        //when
        NettyMessage msg = new NettyMessage((byte)2, (byte)1,str.getBytes().length,str);

        channel.writeOutbound(msg);
        channel.writeInbound((Object)channel.readOutbound()); //(object)를 캐스팅 해서넣어줘야함!

        //then
        NettyMessage returnMessage = (NettyMessage) channel.readInbound();
        assertNotNull(returnMessage);
        log.info("msg : "+msg.toString());
        log.info("return msg : "+returnMessage.toString());
        assertEquals(msg.getBody(), returnMessage.getBody());

    }

}