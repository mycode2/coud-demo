package com.zc.study.common.annotation;

import java.lang.annotation.*;


/**
 * 用于标识常量类中的一个Map类型的属性。
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AppMapProperty {
	String keyType();
	String valueType();
}
