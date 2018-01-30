package com.nonpool.util;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLite;
import com.nonpool.proto.Frame;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * @author nonpool
 * @version 1.0
 * @since 2018/1/29
 */
public abstract class ParseFromUtil {

    private final static ConcurrentMap<String, Method> methodCache = new ConcurrentHashMap<>();

    static {
        //找到指定包下所有protobuf实体类
        List<Class> classes = ClassUtil.getAllClassBySubClass(MessageLite.class, true, "com.nonpool.proto");
        classes.stream()
                .filter(protoClass -> !Objects.equals(protoClass, Frame.class))
                .forEach(protoClass -> {
                    try {
                        //反射获取parseFrom方法并缓存到map
                        methodCache.put(protoClass.getSimpleName(), protoClass.getMethod("parseFrom", ByteString.class));
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    /**
     * 根据Frame类解析出其中的body
     *
     * @param msg
     * @return
     */
    public static MessageLite parse(Frame msg) throws InvocationTargetException, IllegalAccessException {
        String type = msg.getMessageType();
        ByteString body = msg.getPayload();

        Method method = methodCache.get(type);
        if (method == null) {
            throw new RuntimeException("unknown Message type :" + type);
        }
        return (MessageLite) method.invoke(null, body);
    }
}
