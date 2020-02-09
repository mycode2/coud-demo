package com.zc.study.common.utils;

import com.zc.study.common.annotation.AppModule;
import com.zc.study.common.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cloud-common
 * @ClassName: ClazzUtil
 * @Description: TODO(类的工具类)
 * @Author: zhouchao
 * @Date: 2020/2/9 17:55
 */
public class ClazzUtil  implements ResourceLoaderAware {

    private static Logger logger = LoggerFactory.getLogger(ClazzUtil.class);


    /**
     * 注入ResourceLoader
     */
    private static ResourceLoader resourceLoader;

    /**
     * 获取到指定包下的全部类名集合
     * @param basePackage
     * @param classPattern
     * @return
     * @throws IOException
     */

    public static List<String> findClassName (String basePackage,String classPattern) throws IOException {
        List<String> classNameList = new ArrayList<>();
        basePackage = basePackage.replace(Constants.SEPARATOR_IN_POINT,Constants.SEPARATOR_IN_SLASH);
        String basePackageWithResPath = ClassUtils.convertClassNameToResourcePath(basePackage);
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + basePackageWithResPath + "/**/" + classPattern;
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        MetadataReaderFactory metaReader = new CachingMetadataReaderFactory(resourceLoader);
        Resource[] resources = resolver.getResources(packageSearchPath);

        for (Resource resource : resources) {
            MetadataReader reader = metaReader.getMetadataReader(resource);
            String className = reader.getClassMetadata().getClassName();
            classNameList.add(className);
        }
        return classNameList;
    }

    public static String  getAppModule(String className) throws ClassNotFoundException {
        String moduleName=null;
        Class<?> aClass = Class.forName(className);
        if (aClass==null){
            return moduleName;
        }
        AppModule annotation = aClass.getAnnotation(AppModule.class);
        if (annotation == null){
            return moduleName;
        }
        return  annotation.moduleName();
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
