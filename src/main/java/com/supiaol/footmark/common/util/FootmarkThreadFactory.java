package com.supiaol.footmark.common.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author supiaol
 * @version 1.0.0
 * @date 2020/4/21 9:48
 */
public class FootmarkThreadFactory implements ThreadFactory {

    private final AtomicInteger atomicInteger = new AtomicInteger(1);

    private String namePrefix;

    public FootmarkThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix + "-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, namePrefix + atomicInteger.getAndIncrement());
        if (t.isDaemon()) {
            t.setDaemon(true);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
