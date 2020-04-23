package com.supiaol.footmark.common.util;

import org.apache.logging.log4j.Logger;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author supiaol
 * @version 1.0.0
 * @date 2020/4/20 22:37
 */
public class Footmark {

    private static ThreadPoolExecutor executor = DynaThreadPoolExecutor.build();

    @Resource
    private static FootmarkProducer footmarkProducer;

    public static boolean info(String log) {
        final String infoLog = "INFO " + log;
        executor.execute(new TraceRunnable(() -> {
            Message message = new Message(RocketMqConfig.TOPIC, RocketMqConfig.TAGS, infoLog.getBytes());
            try {
                // mq send
                footmarkProducer.getProducer().send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        return Boolean.TRUE;
    }

}
