package com.nonpool.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于映射消息处理handler的处理类型 本demo中其实可以去掉本注解 但是为了灵活性在演示中有使用
 *
 * @author nonpool
 * @version 1.0
 * @since 2018/1/29
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlerMapping {

    /**
     * 消息类型的名字 即protobuf idl文件里的message类型
     *
     * @return
     */
    String value();
}
