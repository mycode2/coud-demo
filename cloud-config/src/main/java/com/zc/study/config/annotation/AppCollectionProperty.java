package com.zc.study.config.annotation;

import java.lang.annotation.*;

/**
 * 用于标识常量类中的一个Collection类型的属性。
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AppCollectionProperty {
	String objType();
}