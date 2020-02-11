package com.zc.study.config.helper;

import com.ctrip.framework.apollo.Config;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @version v1.0
 * @ProjectName: cloud-config
 * @ClassName: WirteConfigHelp
 * @Description: TODO(将配置写入本地配置文件辅助工具)
 * @Author: zhouchao
 * @Date: 2020/2/9 22:07
 */
public class WirteConfigHelper {

    public static void writeConfigHelper(Config config,String className) throws Exception{
        Class<?> clazz = Class.forName(className);
        Object object = clazz.newInstance();
        Set<String> propertyNames = config.getPropertyNames();
        for (String propertyName : propertyNames) {
            Field field = clazz.getField(propertyName);
            Object type = field.getType();
            if (type instanceof String){
//                field.set(field,config.get);
            }
        }
    }
}
