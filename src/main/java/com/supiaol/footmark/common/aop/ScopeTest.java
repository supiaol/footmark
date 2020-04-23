package com.supiaol.footmark.common.aop;

import com.google.common.collect.Maps;
import com.supiaol.footmark.common.util.DynaThreadPoolExecutor;
import com.supiaol.footmark.common.util.RocketMqConfig;
import com.supiaol.footmark.common.util.TraceRunnable;
import com.supiaol.footmark.common.util.TraceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author supiaol
 * @version 1.0.0
 * @date 2020/4/21 14:38
 */
@Component
@Slf4j
public class ScopeTest {


    @PostConstruct
    public void init() throws InterruptedException {
        // 注册自定义域
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getBeanFactory().registerScope(FootmarkBeanScope.SCOPE_REFRESH, FootmarkBeanScope.getInstance());
        context.register(RocketMqConfig.class);
        HashMap<String, Object> config = Maps.newHashMap();
        config.put("rocket.name.server", UUID.randomUUID());
        MapPropertySource mailPropertySource = new MapPropertySource("rocket", config);
        context.getEnvironment().getPropertySources().addFirst(mailPropertySource);
        context.refresh();
        // get
        RocketMqConfig bean = context.getBean(RocketMqConfig.class);
        log.info(bean.getNameServer());
        FootmarkBeanScope.clean();
        ThreadPoolExecutor executor = DynaThreadPoolExecutor.build();
        for (int i = 0; i < 150; i++) {
            executor.execute(new TraceRunnable(() -> {
                HashMap<String, Object> newConfig = Maps.newHashMap();
                newConfig.put("rocket.name.server", UUID.randomUUID());
                MapPropertySource newPropertySource = new MapPropertySource("rocket", newConfig);
                context.getEnvironment().getPropertySources().addFirst(newPropertySource);
                log.info(bean.getNameServer());
                FootmarkBeanScope.clean();
            }));
            DynaThreadPoolExecutor.threadPoolStatus(executor, "");
        }
    }

}
