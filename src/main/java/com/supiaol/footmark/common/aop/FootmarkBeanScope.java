package com.supiaol.footmark.common.aop;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author supiaol
 * @version 1.0.0
 * @date 2020/4/21 14:10
 */
public class FootmarkBeanScope implements Scope {

    public static final String SCOPE_REFRESH = "refresh";

    private static final FootmarkBeanScope INSTANCE = new FootmarkBeanScope();

    private ConcurrentHashMap<String, Object> beanMap = new ConcurrentHashMap<>(16);

    private FootmarkBeanScope() {
    }

    public static FootmarkBeanScope getInstance() {
        return INSTANCE;
    }

    /**
     * 清理当前
     */
    public static void clean() {
        INSTANCE.beanMap.clear();
    }

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Object bean = beanMap.get(name);
        if (bean == null) {
            bean = objectFactory.getObject();
            beanMap.put(name, bean);
        }
        return bean;
    }

    @Override
    public Object remove(String s) {
        return null;
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {
    }

    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }

}
