package com.nonpool.server.customhandler;

import com.nonpool.annotation.HandlerMapping;
import com.nonpool.proto.TextMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author nonpool
 * @version 1.0
 * @since 2018/1/29
 */
@HandlerMapping("TextMessage")
public class TextMessageHandler extends AbstractDataHandler<TextMessage> {
    @Override
    public void onEvent(TextMessage textMessage, ChannelHandlerContext ctx) throws Exception {
        System.out.println(Thread.currentThread().getName() + "------" + textMessage.getText());
    }
}