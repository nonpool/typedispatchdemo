package com.nonpool.server.customhandler;

import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author nonpool
 * @version 1.0
 * @since 2018/1/29
 */
public abstract class AbstractDataHandler<T extends MessageLite> implements DataHandler<T> {

    @Override
    public void handler(T t, ChannelHandlerContext ctx) {
        try {
            this.onEvent(t,ctx);
        } catch (Exception e) {
            exceptionCaught(e);
        }
    }

    abstract void onEvent(T t, ChannelHandlerContext ctx) throws Exception;

    void exceptionCaught(Throwable cause) {

    }
}
