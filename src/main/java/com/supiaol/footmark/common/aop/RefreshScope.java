package com.supiaol.footmark.common.aop;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.lang.annotation.*;

/**
 * @author supiaol
 * @version 1.0.0
 * @date 2020/4/21 14:08
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Scope(FootmarkBeanScope.SCOPE_REFRESH)
@Documented
public @interface RefreshScope {

    ScopedProxyMode proxyMode() default ScopedProxyMode.TARGET_CLASS;

}
