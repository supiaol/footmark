package com.supiaol.footmark.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author supiaol
 * @version 1.0.0
 * @date 2020/4/21 11:10
 */
@Slf4j
//@Component
public class FootmarkProducer {

    @Resource
    private DefaultMQProducer producer;

    public FootmarkProducer() {
        //示例生产者
        producer = new DefaultMQProducer(RocketMqConfig.PRODUCER_GROUP);
        //不开启vip通道 开通口端口会减2
        producer.setVipChannelEnabled(false);
        //绑定name server
        producer.setNamesrvAddr(RocketMqConfig.NAME_SERVER);
        start();
    }

    /**
     * 对象在使用之前必须要调用一次，只能初始化一次
     */
    public void start() {
        try {
            this.producer.start();
        } catch (MQClientException e) {
            log.error(e.getErrorMessage());
        }
    }

    public DefaultMQProducer getProducer() {
        return this.producer;
    }

    /**
     * 一般在应用上下文，使用上下文监听器，进行关闭
     */
    public void shutdown() {
        this.producer.shutdown();
    }
}
