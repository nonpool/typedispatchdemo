package com.nonpool.server.customhandler;

import com.google.protobuf.MessageLite;
import com.nonpool.server.customhandler.buffer.InnerQueueBuffer;
import com.nonpool.server.customhandler.buffer.MessageBuffer;
import com.nonpool.util.HandlerUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 开启线程池处理接收到的数据
 * @author nonpool
 * @version 1.0
 * @since 2018/3/16
 */
public class HandlerPool {

    private int THREAD_POOL_SIZE = 4;

    private final ExecutorService executorService;

    public HandlerPool() {
        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE, new ThreadFactory() {
            private AtomicInteger i = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                //自定义线程池中的线程名
                thread.setName("hand-data-thread-" + i.addAndGet(1));
                return thread;
            }
        });
    }


    public void doHandler() {
        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            executorService.execute(new Runnable() {
                private final MessageBuffer buffer = InnerQueueBuffer.getInstance();
                @Override
                public void run() {
                    while (!Thread.currentThread().isInterrupted()) {
                        handler();
                    }
                }

                @SuppressWarnings("unchecked")
                private void handler() {
                    HandlerDataModal handlerDataModal = buffer.poll();
                    if (handlerDataModal != null) {
                        MessageLite messageLite = handlerDataModal.getMessageLite();
                        HandlerUtil.getHandlerInstance(messageLite.getClass().getSimpleName())
                                .handler(messageLite, handlerDataModal.getCtx());
                    }
                }

            });
        }

    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
