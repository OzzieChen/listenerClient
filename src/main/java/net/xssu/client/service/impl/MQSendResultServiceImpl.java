package net.xssu.client.service.impl;

import net.xssu.client.entity.ScanResult;
import net.xssu.client.service.IMQSendResultService;
import net.xssu.client.util.PropertiesUtil;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.File;
import java.util.Properties;

public class MQSendResultServiceImpl implements IMQSendResultService{
    private RabbitTemplate rabbit;
    private Queue queue;

    MQSendResultServiceImpl(RabbitTemplate rabbit, Queue queue) {
        this.rabbit = rabbit;
        this.queue = queue;
    }

    public boolean sendResult(Integer taskId, File outputFile) {
        if (outputFile == null) {
            // TODO: Tell queue that I failed
            return false;
        }
        Properties configProp = PropertiesUtil.loadProps("properties/config.properties");
        String clientId = configProp.getProperty("client-id");
        ScanResult result = new ScanResult(taskId, clientId, outputFile);
        try {
            rabbit.convertAndSend(queue.getName(), result);
            return true;
        } catch (AmqpException e) {
            e.printStackTrace();
        }
        return false;
    }
}
