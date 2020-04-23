package com.supiaol.footmark.common.util;

/**
 * @author supiaol
 * @version 1.0.0
 * @date 2020/4/20 17:20
 */
public class TraceUtil {

    public static final String REQUEST_HEADER_TRACE_ID = "com.footmark.header.trace.id";

    public static final String MDC_TRACE_ID = "trace_id";

    private static InheritableThreadLocal<Long> inheritableThreadLocal = new InheritableThreadLocal<>();

    public static Long get() {
        Long traceId = inheritableThreadLocal.get();
        if (traceId == null) {
            traceId = SnowflakeUtils.genId();
            inheritableThreadLocal.set(traceId);
        }
        return traceId;
    }

    public static void set(Long footmark) {
        inheritableThreadLocal.set(footmark);
    }

    public static void remove() {
        inheritableThreadLocal.remove();
    }

}
