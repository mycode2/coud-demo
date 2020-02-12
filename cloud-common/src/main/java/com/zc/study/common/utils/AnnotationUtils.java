package com.zc.study.common.utils;

import com.zc.study.common.annotation.AppModule;
import com.zc.study.common.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @version v1.0
 * @ProjectName: cloud-demo
 * @ClassName: AnnotationUtils
 * @Description: TODO(一句话描述该类的功能)
 * @Author: zhouchao
 * @Date: 2020/2/10 17:26
 */
public class AnnotationUtils {

    private static Logger logger = LoggerFactory.getLogger(AnnotationUtils.class);

    public static void reSetAnnotationValues(Class aClass, String methodName, Class annotationClass, String valueKey,
                                             Object newValues) {
        logger.debug("Initialize the value of the {1} annotation" + annotationClass);
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            if (!method.getName().equals(methodName)) {
                continue;
            }
            if (method.isAnnotationPresent(annotationClass)) {
                Annotation[] annotations = method.getAnnotations();
                Annotation annotation = annotations[0];
                try {
                    InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
                    Field declaredField = invocationHandler.getClass().getDeclaredField("memberValues");
                    declaredField.setAccessible(true);
                    Map<String, Object> memberValues = (Map<String, Object>) declaredField.get(invocationHandler);
                    memberValues.put(valueKey, newValues);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取有AppModule的注解值集合
     * 【在basePackages下面的】
     *
     * @param basePackages
     * @return
     */
    public static Set<String> getAppModuleSet(List<String> basePackages) {
        Set<String> appModuleSet = new HashSet<>();
        appModuleSet.add("application");//默认application必不可少
        for (String basePackage : ListUtils.nvlList(basePackages)) {
            try {
                List<String> classNames = ClazzUtils.findClassName(basePackage, Constants.TAIL_A);
                for (String className : classNames) {
                    Class<?> aClass = Class.forName(className);
                    Annotation[] annotations = aClass.getAnnotations();
                    if (annotations == null || annotations.length == 0) {
                        continue;
                    }
                    for (int i = 0; i < annotations.length; i++) {

                        if (annotations[i].annotationType() == AppModule.class) {
                            AppModule appModule = (AppModule) annotations[i];
                            appModuleSet.add(appModule.moduleName());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return appModuleSet;
    }

    public static String getAPPModuleNameByClassName(String className) throws ClassNotFoundException {
        Class<?> aClass = Class.forName(className);
        Annotation[] annotations = aClass.getDeclaredAnnotations();
        if (annotations==null || annotations.length==0){
            return null;
        }
        for (int i = 0 ; i < annotations.length ; i++) {
            if (annotations[i].annotationType()==AppModule.class){
                AppModule appModule = (AppModule) annotations[i];
                String moduleName = appModule.moduleName();
                Assert.notNull(moduleName,"AppModule注解moduleName属性不能为空,位于"+className);
                return moduleName;
            }
        }
        return null;
    }
}
