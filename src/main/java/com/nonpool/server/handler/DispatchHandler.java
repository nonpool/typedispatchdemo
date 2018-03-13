package com.nonpool.server.handler;

import com.google.protobuf.MessageLite;
import com.nonpool.util.HandlerUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 消息分发器
 *
 * @author nonpool
 * @version 1.0
 * @since 2018/1/29
 */
public class DispatchHandler extends ChannelInboundHandlerAdapter {

    @Override
    @SuppressWarnings("unchecked")
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HandlerUtil.getHandlerInstance(msg.getClass().getSimpleName()).handler((MessageLite) msg,ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}