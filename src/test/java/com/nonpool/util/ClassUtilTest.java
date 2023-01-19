package com.nonpool.util;

import com.google.protobuf.MessageLite;
import com.nonpool.proto.Frame;
import com.nonpool.proto.TextMessage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ClassUtilTest {
    @Test
    void should_find_class_success() {
        List<Class> classes = ClassUtil.getAllClassBySubClass(MessageLite.class, true, "com.nonpool.proto");
        assertTrue(classes.contains(Frame.class));
        assertTrue(classes.contains(TextMessage.class));
    }
}