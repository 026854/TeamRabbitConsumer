package com.rabbitmq.manager.netty_yumi;

import lombok.*;

import java.util.Map;


@Data
@AllArgsConstructor
public class NettyMessage {
    private byte messageType;
    private byte taskType;
    private int length;
    private String body;
}
