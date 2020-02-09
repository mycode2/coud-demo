package com.zc.study.config.annotation;

import java.lang.annotation.*;

/**
 * @version v1.0
 * @ProjectName: cloud-demo
 * @ClassName: AppModule
 * @Description: TODO(用于对常量类进行标识出归属哪个模块。（也就是Apollo上的namespace）)
 * @Author: zhouchao
 * @Date: 2020/2/8 16:15
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface AppModule {
    String moduleName();
}

