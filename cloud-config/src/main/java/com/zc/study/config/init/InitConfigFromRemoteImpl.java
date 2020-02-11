package com.zc.study.config.init;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.zc.study.common.constant.Constants;
import com.zc.study.common.utils.ClazzUtil;
import com.zc.study.common.utils.ListUtils;
import com.zc.study.config.helper.WirteConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

    @Override
    public void writeConstant(Set<String> changedKeys) {

    }
}
