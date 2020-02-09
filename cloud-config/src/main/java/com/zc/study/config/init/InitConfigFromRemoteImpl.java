package com.zc.study.config.init;

import com.ctrip.framework.apollo.ConfigFile;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.enums.ConfigFileFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @version v1.0
 * @ProjectName: cloud-demo
 * @ClassName: InitConfigFromApollo
 * @Description: TODO(从Apollo配置中心拉取配置初始化本地配置文件)
 * @Author: zhouchao
 * @Date: 2020/2/9 16:18
 */
@Service(value = "initConfigFromRemote")
public class InitConfigFromRemoteImpl implements InitConfigFromRemote{

    private Logger logger = LoggerFactory.getLogger(InitConfigFromRemoteImpl.class);

    @Override
    public void writeConstant() {
        logger.info("默认从远程Apollo配置中心拉取配置初始化本地配置文件......................");
        logger.info("如果需要从其他的配置中心获取配置，只需要新加InitConfigFromRemote的实现类，替换bean initConfigFromRemote 即可");
        logger.info("从注解中获取到Apollo的namespace，也就是本地配置类的类名");
        ConfigFile commonMDAConfigFile = ConfigService.getConfigFile("commonMDA", ConfigFileFormat.Properties);
        System.out.println("commonMDAConfigFile*****************\n"+commonMDAConfigFile);
        String content = commonMDAConfigFile.getContent();
        System.out.println("content*****************\n"+content);
        // TODO: 2020/2/9
        logger.info("接下来设置配置到本地类中");

    }
}
