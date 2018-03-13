package com.nonpool.util;

import com.nonpool.annotation.HandlerMapping;
import com.nonpool.server.customhandler.DataHandler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author nonpool
 * @version 1.0
 * @since 2018/1/29
 */
public abstract class HandlerUtil {

    private final static Map<String,DataHandler> instanceCache = new ConcurrentHashMap<>();

    // 初始化所有handler
    static {
        try {
            //扫描指定包下的所有DataHandler实现类
            List<Class> classes = ClassUtil.getAllClassBySubClass(DataHandler.class, true,"com.onescorpion");
            for (Class claz : classes) {
                HandlerMapping annotation = (HandlerMapping) claz.getAnnotation(HandlerMapping.class);
                //以其HandlerMapping的value为key handler实例为value缓存到map中
                instanceCache.put(annotation.value(), (DataHandler) claz.newInstance());
            }
            System.out.println("handler init success handler Map: " +  instanceCache);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static DataHandler getHandlerInstance(String name) {
        return instanceCache.get(name);
    }
}
