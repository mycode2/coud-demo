package com.zc.study.cust.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

/**
 * @version v1.0
 * @ProjectName: cloud-demo
 * @ClassName: ConfigRefresher
 * @Description: TODO(一句话描述该类的功能)
 * @Author: zhouchao
 * @Date: 2020/2/8 13:31
 */
@SuppressWarnings("all")
@Component
public class ConfigRefresher implements ApplicationContextAware {


    private ApplicationContext applicationContext;

    @ApolloConfig
    private Config config;

    @Value("${config.files}")
    private List<String> namespaces;

    @PostConstruct
    private void initialize() {
        refresher(config.getPropertyNames());
    }
    @ApolloConfigChangeListener
    private void onChange(ConfigChangeEvent changeEvent) {
        refresher(changeEvent.changedKeys());
    }
    private void refresher(Set<String> changedKeys) {
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changedKeys));
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
