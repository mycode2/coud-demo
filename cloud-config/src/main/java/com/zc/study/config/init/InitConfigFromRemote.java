package com.zc.study.config.init;

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

    public void writeConstant(Set<String> changedKeys,String changeNamespace);

}
