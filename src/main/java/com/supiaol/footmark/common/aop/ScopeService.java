package com.supiaol.footmark.common.aop;

import com.supiaol.footmark.common.util.RocketMqConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author supiaol
 * @version 1.0.0
 * @date 2020/4/21 16:00
 */
@Component
public class ScopeService {

    @Resource
    private RocketMqConfig rocketMqConfig;

    @Override
    public String toString() {
        return "ScopeService{" +
                "rocketMqConfig=" + rocketMqConfig.getNameServer() +
                '}';
    }
}
