package com.nonpool.server.customhandler;

import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelHandlerContext;

/**
 * 处理器处理数据的封装
 *
 * @author nonpool
 * @version 1.0
 * @since 2018/3/16
 */
public class HandlerDataModal {

    public HandlerDataModal() {
    }

    public HandlerDataModal(MessageLite messageLite, ChannelHandlerContext ctx) {
        this.messageLite = messageLite;
        this.ctx = ctx;
    }

    private MessageLite messageLite;

    private ChannelHandlerContext ctx;

    public MessageLite getMessageLite() {
        return messageLite;
    }

    public void setMessageLite(MessageLite messageLite) {
        this.messageLite = messageLite;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public String toString() {
        return "HandlerDataModal{" +
                "messageLite=" + messageLite +
                ", ctx=" + ctx +
                '}';
    }
}
