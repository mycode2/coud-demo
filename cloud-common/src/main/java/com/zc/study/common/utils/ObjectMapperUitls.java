package com.zc.study.common.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.Map;

/**
 * ObjectMapper工具类
 */
public abstract class ObjectMapperUitls {

    public static JavaType getJavaType(ObjectMapper mapper, GenericClass genericClass) {
        JavaType javaType = null;
        Class<?> clazz = genericClass.getRawClass();
        GenericClass[] genericClasses = genericClass.getGenericClasses();
        JavaType[] jts = null;
        if (genericClasses != null) {
            jts = new JavaType[genericClasses.length];
            for (int i = 0; i < genericClasses.length; i++) {
                jts[i] = getJavaType(mapper,genericClasses[i]);
            }
        }else{
            jts = new JavaType[]{ mapper.getTypeFactory().constructType(Object.class),
                    mapper.getTypeFactory().constructType(Object.class)};
        }
        if(clazz.isArray()){
            javaType = mapper.getTypeFactory().constructArrayType(clazz.getComponentType());
        }else if(Collection.class.isAssignableFrom(clazz) ){
            javaType = mapper.getTypeFactory().constructCollectionType(Collection.class, jts[0]);
        }else if(Map.class.isAssignableFrom(clazz) ){
            javaType = mapper.getTypeFactory().constructMapType(Map.class,jts[0],jts[1]);
        }else{
            javaType = mapper.getTypeFactory().constructType(clazz);
        }
        return javaType;
    }
}
