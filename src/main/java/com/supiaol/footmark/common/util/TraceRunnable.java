package com.supiaol.footmark.common.util;

import org.jboss.logging.MDC;

/**
 * @author supiaol
 * @version 1.0.0
 * @date 2020/4/20 17:19
 */
public class TraceRunnable implements Runnable {

    /**
     * footmarkId
     */
    private Long footmarkId;
    /**
     *
     */
    private Runnable target;

    public TraceRunnable(Runnable target) {
        this.footmarkId = TraceUtil.get();
        this.target = target;
    }

    @Override
    public void run() {
        try {
            TraceUtil.set(this.footmarkId);
            MDC.put(TraceUtil.MDC_TRACE_ID, TraceUtil.get());
            this.target.run();
        } finally {
            MDC.remove(TraceUtil.MDC_TRACE_ID);
            TraceUtil.remove();
        }
    }

    public static Runnable trace(Runnable target) {
        return new TraceRunnable(target);
    }

    public Long getFootmarkId() {
        return footmarkId;
    }

    public void setFootmarkId(Long footmarkId) {
        this.footmarkId = footmarkId;
    }

    public Runnable getTarget() {
        return target;
    }

    public void setTarget(Runnable target) {
        this.target = target;
    }
}
