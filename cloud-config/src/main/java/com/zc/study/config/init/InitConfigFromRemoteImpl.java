package com.zc.study.config.init;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigFile;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.enums.ConfigFileFormat;
import com.zc.study.common.constant.Constants;
import com.zc.study.common.utils.ClazzUtil;
import com.zc.study.common.utils.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class InitConfigFromRemoteImpl implements InitConfigFromRemote{

    private Logger logger = LoggerFactory.getLogger(InitConfigFromRemoteImpl.class);

    @Value("${basePackage}")
    private String basePackageStr;

    /**
     * Spring容器注入
     */
    private ResourceLoader resourceLoader;

    @Override
    public void writeConstant(ApplicationContext applicationContext) {

        List<String> basePackageList = Arrays.asList(basePackageStr.split(Constants.SEPARATOR_IN_COMMA));
        
        logger.info("默认从远程Apollo配置中心拉取配置初始化本地配置文件......................");
        logger.info("如果需要从其他的配置中心获取配置，只需要新加InitConfigFromRemote的实现类，替换bean initConfigFromRemote 即可");
        logger.info("从注解中获取到Apollo的namespace，也就是本地配置类的类名");

        logger.info("接下来设置配置到本地类中");
        ClassLoader classLoader = applicationContext.getClassLoader();
        List<String> classNameList = new ArrayList<>();
        try {
            classNameList = ClazzUtil.findClassName(basePackageList.get(0),"*.class");
            for (String className : ListUtils.nvlList(classNameList)){
                String appModule = ClazzUtil.getAppModule(className);//appModule也就是Apollo中的namespace
                Config config = ConfigService.getConfig(appModule);
                System.out.println("nameSpace【commonMDA】的配置内容:\n"+config.toString());
//                WirteConfigHelper.writeConfig(content);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
