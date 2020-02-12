package com.zc.study.config.init;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.zc.study.common.utils.AnnotationUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @version v1.0
 * @ProjectName: cloud-demo
 * @ClassName: InitCMDAAnnotation
 * @Description: TODO(初始化)
 * @Author: zhouchao
 * @Date: 2020/2/9 15:47
 */

@Component
public class InitMDAAnnotation implements ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(InitMDAAnnotation.class);

    private ApplicationContext applicationContext;

    @Value("${cmdb.isCMDB.cmdbFlag}")
    private String cmdbFlag;

    @Value("${basePackage}")
    private String basePackage;

    @Resource
    private InitConfigFromRemote initConfigFromRemote;

    @PostConstruct
    private void initialize() {
        Config appConfig = ConfigService.getAppConfig();
        refresher(appConfig.getPropertyNames(),null);
    }

    /**
     * 监听
     * @param changeEvent
     */
    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        String changeNamespace = changeEvent.getNamespace();//修改配置之后的namespace
        refresher(changeEvent.changedKeys(),changeNamespace);//改变过的配置key
    }

    private void refresher(Set<String> changedKeys,String changeNamespace) {
        // 设置配置内中的值
        logger.info("开始CMDA配置初始化！..........................................");
        if (!StringUtils.equals("Y",cmdbFlag) && !StringUtils.equals("N",cmdbFlag)){
            logger.info("从远程获取配置的开关配置错误，开关取值为[Y,N],默认从本地获取....................................");
        }
        if (StringUtils.equals("N",cmdbFlag)){
            logger.info("从远程获取配置开关取值为[Y,N],当前配置为【N】，从本地获取....................................");
            return;
        }
        initConfigFromRemote.writeConstant(changedKeys,changeNamespace);
        logger.info("结束CMDA配置初始化！..........................................");
    }

    /**
     * 1、ApolloConfigChangeListener；
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //初始化ApolloConfigChangeListener注解中value属性的默认值
        initApolloConfigChangeListenerAnnotationValueDefault();

        this.applicationContext = applicationContext;
    }

    /**
     * 初始化ApolloConfigChangeListener注解中value属性的默认值
     * 默认ApolloConfigChangeListener注解中value属性的默认值为application
     * 需要在项目启动的时候加入自定义的appModule名称，也就是Apollo中的namespace名称
     */
    private void initApolloConfigChangeListenerAnnotationValueDefault() {
        List<String> basePackageList = Arrays.asList(basePackage.split(","));
        Set<String> appModuleSet = AnnotationUtils.getAppModuleSet(basePackageList);
        String[] newValues = appModuleSet.toArray(new String[appModuleSet.size()]);
        AnnotationUtils.reSetAnnotationValues(this.getClass(),"onChange",
                ApolloConfigChangeListener.class,"value",newValues);
    }
}
