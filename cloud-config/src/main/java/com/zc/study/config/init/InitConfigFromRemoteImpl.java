package com.zc.study.config.init;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.zc.study.common.constant.Constants;
import com.zc.study.common.utils.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @version v1.0
 * @ProjectName: cloud-demo
 * @ClassName: InitConfigFromApollo
 * @Description: TODO(从Apollo配置中心拉取配置初始化本地配置文件)
 * @Author: zhouchao
 * @Date: 2020/2/9 16:18
 */
@SuppressWarnings("all")
@Service(value = "initConfigFromRemote")
public class InitConfigFromRemoteImpl implements InitConfigFromRemote {

    private Logger logger = LoggerFactory.getLogger(InitConfigFromRemoteImpl.class);

    @Value("${basePackage}")
    private String basePackage;

    @Override
    public void writeConstant(Set<String> changedKeys, String changeNamespace) {
        List<String> basePackageList = Arrays.asList(basePackage.split(Constants.SEPARATOR_IN_COMMA));
        try {
            if (StringUtils.isEmpty(changeNamespace)) {
                //系统启动,需要拉取所有配置
                for (String basePackage : basePackageList) {
                    List<String> classNames = ClazzUtils.findClassName(basePackage, Constants.TAIL_A);
                    for (String className : classNames) {
                        String nameSpace = AnnotationUtils.getAPPModuleNameByClassName(className);
                        if (!StringUtils.isEmpty(nameSpace)) {
                            Config fileconfig = ConfigService.getConfig(nameSpace);
                            writeFileConfigToClass(fileconfig, className, new HashSet<>());
                        }
                    }
                }
            } else {
                //系统运行中，只需要修改更改过的配置
                for (String basePackage : basePackageList) {
                    List<String> classNames = ClazzUtils.findClassName(basePackage, Constants.TAIL_A);
                    for (String className : classNames) {
                        String nameSpace = AnnotationUtils.getAPPModuleNameByClassName(className);
                        if (StringUtils.equals(nameSpace, changeNamespace)) {
                            Config fileconfig = ConfigService.getConfig(nameSpace);
                            writeFileConfigToClass(fileconfig, className, changedKeys);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    /**
     * 将文件（nameSpace）配置写入类中
     *
     * @param fileconfig
     */
    private void writeFileConfigToClass(Config fileconfig, String className, Set<String> changedKeys) throws ClassNotFoundException, IOException, IllegalAccessException {
        long beginTime = System.currentTimeMillis();
        Field[] fields = com.zc.study.common.utils.ClazzUtils.getFieldsFromClass(className);
        Properties properties = new Properties();
        if (fields == null || fields.length == 0) {
            return;
        }
        //將 Config 转换为java.util.Properties
        if (fileconfig != null) {
            for (String key : fileconfig.getPropertyNames()) {
                if (changedKeys.isEmpty()) {
                    properties.setProperty(key, fileconfig.getProperty(key, null));
                } else if (!changedKeys.isEmpty() && changedKeys.contains(key)) {
                    properties.setProperty(key, fileconfig.getProperty(key, null));
                }
            }
        }
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            Class<?> clazz = field.getType();
            logger.info("clazz={},field={}", clazz.getName(), field.getName());
            String propValue = !properties.containsKey(fieldName) ? null : properties.getProperty(fieldName);
            if (!StringUtils.isEmpty(propValue)) {
                boolean isPrimitiveOrWrapper = ClassUtils.isPrimitiveOrWrapper(clazz);//判断clazz是基本类型或包装类
                Object value = null;
                if (clazz == String.class) {
                    value = propValue;
                } else if (!isPrimitiveOrWrapper) {
                    Type type = field.getGenericType();
                    value = JsonUtils.getJsonConverter().toBean(propValue, clazz, type);
                } else {
                    value = JsonUtils.getJsonConverter().toBean(propValue, clazz);
                }
                //赋值
                if (clazz.isAssignableFrom(List.class)) {
                    List oldList = (List) field.get(null);
                    oldList.clear();
                    oldList.addAll((List) value);
                } else {
                    //设置final修饰的成员变量,此方法有个判断 Modifier.isFinal(targetField.getModifiers())，非final成员也通过此接口重置value
                    FinalObjectUtil.setValue(field, value);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("Cost time {} seconds while search constant class in module 【{}】",
                (endTime - beginTime) / 1000, AnnotationUtils.getAPPModuleNameByClassName(className));
    }
}
