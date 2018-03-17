package com.nonpool.server.customhandler.buffer;

import com.nonpool.server.customhandler.HandlerDataModal;

/**
 * MessageLite对象缓存接口
 * 为了让所有数据处理操作不阻塞netty的handler链
 *
 * @author nonpool
 * @version 1.0
 * @since 2018/3/16
 */
public interface MessageBuffer {

    /**
     * 放入一个handlerData
     */
    boolean offer(HandlerDataModal handlerDataModal);

    /**
     * 取出一个handlerData
     */
    HandlerDataModal poll();


    /**
     * 当前buffer中数据量
     * @return
     */
    int size();
}
