package com.nonpool.generator;

import com.google.protobuf.MessageLite;
import com.nonpool.proto.Frame;
import com.nonpool.util.ClassUtil;
import com.nonpool.util.StringUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * handler层代码生成器
 *
 * @author nonpool
 * @version 1.0
 * @since 2018/1/30
 */
public class HandlerGenerator {

    public static void main(String[] args) throws Exception {
        HandlerGenerator generator = new HandlerGenerator();
        generator.run("com.nonpool.server.customhandler", true);
    }

    private void run(String packagePath, boolean override) throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        URL resource = HandlerGenerator.class.getResource("/handler_template.ftl");
        cfg.setDirectoryForTemplateLoading(Paths.get(resource.toURI()).toFile().getParentFile());
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String, String> dataModal = new HashMap<>();
        String handlerFilePath = "src/main/java/" + packagePath.replace('.', '/') + "/";
        List<Class> classes = ClassUtil.getAllClassBySubClass(MessageLite.class, true, "com.nonpool.proto");
        classes.stream()
                .filter(claz -> !Objects.equals(claz, Frame.class))   //不包含Frame
                .forEach(claz -> {
                    try {
                        dataModal.put("className", claz.getSimpleName());
                        dataModal.put("lowerClassName", StringUtil.lowerFirst(claz.getSimpleName()));
                        dataModal.put("packagePath", packagePath);
                        Template handlerTemplate = cfg.getTemplate("handler_template.ftl");
                        File file = new File(handlerFilePath + claz.getSimpleName() + "Handler.java");
                        if (override || !file.exists()) {
                            Writer out = new OutputStreamWriter(new FileOutputStream(file));
                            handlerTemplate.process(dataModal, out);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }


}
