package com.zc.study.config.init;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @version v1.0
 * @ProjectName: cloud-demo
 * @ClassName: InitConfigFromApollo
 * @Description: TODO(一句话描述该类的功能)
 * @Author: zhouchao
 * @Date: 2020/2/9 16:15
 */
public interface InitConfigFromRemote {

    public void writeConstant(Set<String> changedKeys);

}
