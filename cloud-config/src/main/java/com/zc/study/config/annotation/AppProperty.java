package com.zc.study.config.annotation;

import java.lang.annotation.*;


/**
 * 用于将常量类中的一个Field标识成固定的，表示不会有变化。
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AppProperty {
	boolean readOnly();
}
