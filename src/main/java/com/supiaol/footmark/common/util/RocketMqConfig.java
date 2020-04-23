package com.supiaol.footmark.common.util;

import com.supiaol.footmark.common.aop.RefreshScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author supiaol
 * @version 1.0.0
 * @date 2020/4/21 11:04
 */
@RefreshScope
@Component
public class RocketMqConfig {

    public static String NAME_SERVER;

    public static String TOPIC;

    public static String TAGS;

    public static String PRODUCER_GROUP;

    public static String CONSUMER_GROUP;

    public String getNameServer() {
        return NAME_SERVER;
    }

    @Value("${rocket.name.server}")
    public void setNameServer(String nameServer) {
        NAME_SERVER = nameServer;
    }

    public String getTopic() {
        return TOPIC;
    }

    @Value("${rocket.topic}")
    public void setTopic(String topic) {
        RocketMqConfig.TOPIC = topic;
    }

    public String getTags() {
        return TAGS;
    }

    @Value("${rocket.tags}")
    public void setTags(String tags) {
        RocketMqConfig.TAGS = tags;
    }

    public String getProducerGroup() {
        return PRODUCER_GROUP;
    }

    @Value("${rocket.producer.group}")
    public void setProducerGroup(String producerGroup) {
        PRODUCER_GROUP = producerGroup;
    }

    public String getConsumerGroup() {
        return CONSUMER_GROUP;
    }

    @Value("${rocket.consumer.group}")
    public void setConsumerGroup(String consumerGroup) {
        CONSUMER_GROUP = consumerGroup;
    }
}
