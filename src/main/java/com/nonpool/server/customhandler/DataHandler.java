package com.nonpool.server.customhandler;

import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author nonpool
 * @version 1.0
 * @since 2018/1/29
 */
public interface DataHandler<T extends MessageLite> {

    void handler(T t, ChannelHandlerContext ctx);
}
