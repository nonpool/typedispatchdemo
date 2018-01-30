package com.nonpool.server.handler;

import com.google.protobuf.MessageLite;
import com.nonpool.proto.Frame;
import com.nonpool.util.ParseFromUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * 二次Protobuf解码器 in:把playload里面的字节数组转换成对象 out:把对需要发送的对象包装成Frame
 *
 * @author nonpool
 * @version 1.0
 * @since 2018/1/29
 */
public class SecondProtobufCodec extends MessageToMessageCodec<Frame, MessageLite> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageLite msg, List<Object> out) throws Exception {

        out.add(Frame.newBuilder()
                .setMessageType(msg.getClass().getSimpleName())
                .setPayload(msg.toByteString())
                .build());

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Frame msg, List<Object> out) throws Exception {
        out.add(ParseFromUtil.parse(msg));
    }

}