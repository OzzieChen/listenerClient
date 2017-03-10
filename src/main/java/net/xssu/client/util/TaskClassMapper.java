package net.xssu.client.util;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.ClassMapper;

public class TaskClassMapper implements ClassMapper {
    private String scanTaskClassName;

    public TaskClassMapper(String className) {
        this.scanTaskClassName = className;
    }

    public void fromClass(Class<?> clazz, MessageProperties properties) {
        throw new UnsupportedOperationException("this mapper is only for inbound, do not use for receive message");
    }

    public Class<?> toClass(MessageProperties properties) {
        try {
            return Class.forName(scanTaskClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
