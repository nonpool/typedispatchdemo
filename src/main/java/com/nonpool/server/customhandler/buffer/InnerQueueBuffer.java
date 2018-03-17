package com.nonpool.server.customhandler.buffer;

import com.nonpool.server.customhandler.HandlerDataModal;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * jvm内基于queue的缓存实现
 *
 * @author nonpool
 * @version 1.0
 * @since 2018/3/16
 */
public class InnerQueueBuffer implements MessageBuffer {

    private int MAX_LENGTH = 5000;

    private final BlockingQueue<HandlerDataModal> queue = new LinkedBlockingQueue<>();

    private static InnerQueueBuffer instance = new InnerQueueBuffer();

    private InnerQueueBuffer() {
    }

    public static InnerQueueBuffer getInstance() {
        return instance;
    }


    @Override
    public boolean offer(HandlerDataModal handlerDataModal) {
        if (queue.size() > MAX_LENGTH) {
            //todo 处理队列超过规定长度
            return false;
        }
        queue.offer(handlerDataModal);
        return false;
    }

    @Override
    public HandlerDataModal poll() {
        try {
            //使用阻塞方法防止消费者线程空转
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        return queue.size();
    }

}
