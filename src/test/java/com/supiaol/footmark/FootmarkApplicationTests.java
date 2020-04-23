package com.supiaol.footmark;

import com.google.common.collect.Maps;
import com.supiaol.footmark.common.aop.FootmarkBeanScope;
import com.supiaol.footmark.common.aop.RefreshScopeConfig;
import com.supiaol.footmark.common.aop.ScopeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.UUID;

@SpringBootTest
class FootmarkApplicationTests {


    @Test
    void contextLoads() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册自定义域
        context.getBeanFactory().registerScope(FootmarkBeanScope.SCOPE_REFRESH, FootmarkBeanScope.getInstance());
        context.register(RefreshScopeConfig.class);
        // 注入数据
        HashMap<String, Object> config = Maps.newHashMap();
        config.put("rocket.name.service", UUID.randomUUID());
        MapPropertySource mailPropertySource = new MapPropertySource("mail", config);
        context.getEnvironment().getPropertySources().addFirst(mailPropertySource);
        context.refresh();
        // get
        ScopeService bean = context.getBean(ScopeService.class);
        System.out.println(bean);
    }

}
