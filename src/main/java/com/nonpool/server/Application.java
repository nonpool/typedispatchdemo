package com.nonpool.server;

import com.nonpool.proto.Frame;
import com.nonpool.server.customhandler.HandlerPool;
import com.nonpool.server.customhandler.buffer.InnerQueueBuffer;
import com.nonpool.server.customhandler.buffer.MessageBuffer;
import com.nonpool.server.handler.DispatchHandler;
import com.nonpool.server.handler.SecondProtobufCodec;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.util.concurrent.ExecutorService;


/**
 * 服务端
 *
 * @author nonpool
 * @version 1.0
 * @since 2018/1/29
 */
public class Application {
    private int port;

    public Application(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {

        int port = Integer.parseInt(args[0]);
        Application application = new Application(port);
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ExecutorService executorService = application.startHandler();
        application.shutdownHook(executorService,bossGroup,workerGroup);
        application.run(bossGroup,workerGroup);
    }

    //优雅停机
    private void shutdownHook(ExecutorService executorService, EventLoopGroup bossGroup,
                              EventLoopGroup workerGroup) {
        //正常停止时
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            //先停止netty的线程
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();

            //等待队列中的数据被处理完再停机
            MessageBuffer instance = InnerQueueBuffer.getInstance();
            while (instance.size() != 0) {
                System.out.println("wait message insert :" + instance.size());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!executorService.isShutdown()) {
                executorService.shutdownNow();
            }
        }));
    }

    //启动数据处理线程池
    private ExecutorService startHandler() {
        HandlerPool handlerPool = new HandlerPool();
        handlerPool.doHandler();

        return handlerPool.getExecutorService();
    }

    //启动netty
    private void run(EventLoopGroup bossGroup, EventLoopGroup workerGroup) throws Exception {
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ProtobufVarint32FrameDecoder())
                                    .addLast(new ProtobufDecoder(Frame.getDefaultInstance()))
                                    .addLast(new ProtobufVarint32LengthFieldPrepender())
                                    .addLast(new ProtobufEncoder())
                                    .addLast(new SecondProtobufCodec())
                                    .addLast(new DispatchHandler())
                            ;
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);


            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
