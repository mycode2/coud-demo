package com.zc.study.config.init;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @version v1.0
 * @ProjectName: cloud-demo
 * @ClassName: InitCMDAAnnotation
 * @Description: TODO(初始化)
 * @Author: zhouchao
 * @Date: 2020/2/9 15:47
 */

@Component
public class InitCMDAAnnotation implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(InitCMDAAnnotation.class);

    @Value("${cmdb.isCMDB.cmdbFlag}")
    private String cmdbFlag;

    @Autowired
    private InitConfigFromRemote initConfigFromRemote;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        logger.info("开始CMDA配置初始化！..........................................");
        if (!StringUtils.equals("Y",cmdbFlag) && !StringUtils.equals("N",cmdbFlag)){
           logger.info("从远程获取配置的开关配置错误，开关取值为[Y,N],默认从本地获取....................................");
        }
        if (StringUtils.equals("N",cmdbFlag)){
            logger.info("从远程获取配置开关取值为[Y,N],当前配置为【N】，从本地获取....................................");
            return;
        }
        initConfigFromRemote.writeConstant();
        logger.info("结束CMDA配置初始化！..........................................");
    }
}
