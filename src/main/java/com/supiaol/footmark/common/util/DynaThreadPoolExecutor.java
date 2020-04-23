package com.supiaol.footmark.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author supiaol
 * @version 1.0.0
 * @date 2020/4/20 13:58
 */
public class DynaThreadPoolExecutor {

    private static Logger logger = LoggerFactory.getLogger(DynaThreadPoolExecutor.class);

    private final static ReentrantLock lock = new ReentrantLock();

    private static ThreadPoolExecutor footmarkThreadPool;

    private static final RejectedExecutionHandler defaultHandler =
            new ThreadPoolExecutor.AbortPolicy();


    private DynaThreadPoolExecutor() {
    }

    public static ThreadPoolExecutor build() {
        return footmarkThreadPool = new ThreadPoolExecutor(2,
                5,
                10,
                TimeUnit.SECONDS,
                new FootmarkLinkBlockQueue<>(100),
                new FootmarkThreadFactory("footmark"), defaultHandler);
    }

    public static ThreadPoolExecutor build(int corePoolSize,
                                           int maximumPoolSize,
                                           long keepAliveTime,
                                           TimeUnit unit,
                                           int capacity,
                                           ThreadFactory threadFactory) {
        return footmarkThreadPool = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                new FootmarkLinkBlockQueue<>(capacity)
                , threadFactory, defaultHandler);
    }

    public static ThreadPoolExecutor build(int corePoolSize,
                                           int maximumPoolSize,
                                           TimeUnit unit,
                                           long keepAliveTime,
                                           int capacity
    ) {
        return footmarkThreadPool = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                new FootmarkLinkBlockQueue<>(capacity)
                , Executors.defaultThreadFactory(), defaultHandler);
    }

    private static void dynaModifyExecutor(Integer corePoolSize, Integer maxPoolSize, Integer capacity) {
        if (footmarkThreadPool == null) {
            throw new NullPointerException("Footmark thread poll init failure");
        }
        threadPoolStatus(footmarkThreadPool, "改变之前");
        lock.lock();
        try {
            if (corePoolSize != null && corePoolSize > 0) {
                footmarkThreadPool.setCorePoolSize(corePoolSize);
            }
            if (maxPoolSize != null && maxPoolSize > 0) {
                int cps = footmarkThreadPool.getCorePoolSize();
                if (maxPoolSize > cps) {
                    footmarkThreadPool.setMaximumPoolSize(maxPoolSize);
                }
            }
            if (capacity != null && capacity > 0) {
                FootmarkLinkBlockQueue queue = (FootmarkLinkBlockQueue) footmarkThreadPool.getQueue();
                queue.setCapacity(capacity);
            }
        } finally {
            lock.unlock();
        }
        threadPoolStatus(footmarkThreadPool, "改变之后");

    }

    public static void threadPoolStatus(ThreadPoolExecutor executor, String desc) {
        if (executor == null) {
            throw new NullPointerException();
        }
        FootmarkLinkBlockQueue queue = (FootmarkLinkBlockQueue) executor.getQueue();
        logger.info(desc + ":" +
                "核心线程数:" + executor.getCorePoolSize() +
                " 活跃线程数:" + executor.getActiveCount() +
                " 最大线程数:" + executor.getMaximumPoolSize() +
                " 线程池活跃度:" + divide(executor.getActiveCount(), executor.getMaximumPoolSize()) +
                " 队列完成数:" + executor.getCompletedTaskCount() +
                " 队列长度:" + (queue.size() + queue.remainingCapacity()) +
                " 当前排队线程数:" + queue.size() +
                " 队列剩余长度:" + queue.remainingCapacity() +
                " 队列使用度:" + divide(queue.size(), queue.size() + queue.remainingCapacity()));


    }

    private static String divide(int num1, int num2) {
        return String.format("%1.2f%%", Double.parseDouble(num1 + "") / Double.parseDouble(num2 + "") * 100);
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = DynaThreadPoolExecutor.build();
        for (int i = 0; i < 15; i++) {
            assert executor != null;
            executor.execute(() -> {
                threadPoolStatus(executor, "创建线程");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        dynaModifyExecutor(5, 10, 200);
    }
}
