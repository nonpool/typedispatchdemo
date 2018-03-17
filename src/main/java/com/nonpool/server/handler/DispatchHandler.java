package com.nonpool.server.handler;

import com.google.protobuf.MessageLite;
import com.nonpool.server.customhandler.HandlerDataModal;
import com.nonpool.server.customhandler.buffer.InnerQueueBuffer;
import com.nonpool.server.customhandler.buffer.MessageBuffer;
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

    private final MessageBuffer buffer = InnerQueueBuffer.getInstance();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //把解包完成的对象封装放入队列中异步处理
        buffer.offer(new HandlerDataModal((MessageLite) msg, ctx));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}