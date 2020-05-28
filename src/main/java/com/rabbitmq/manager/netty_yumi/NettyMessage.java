package com.rabbitmq.manager.netty_yumi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class NettyMessage {

    private byte messageType;
    private byte taskType;
    private int length;
    private String body;
}
