package com.zc.study.cust.controller;

import com.zc.study.cust.common.CommonMDA;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version v1.0
 * @ProjectName: cloud-demo
 * @ClassName: CustController
 * @Description: TODO(一句话描述该类的功能)
 * @Author: zhouchao
 * @Date: 2020/2/10 19:24
 */
@RestController
@RequestMapping(value = "/cust")
public class CustController {

    @Value("${basePackage}")
    private String basePackage;
    @GetMapping("/getCustInfo")
    public String getCustInfo(){
        System.out.println(CommonMDA.CERTNUMFIVE_REGIONID);
        System.out.println(CommonMDA.GROUP_4G_SRC_APP_KEY);
        System.out.println(CommonMDA.GROUP_DST_SYS_ID);
        System.out.println(CommonMDA.PROD_SERVICE_OFFER_ID_COMP_PROD);
        return "this is a test\t"+basePackage;
    }
}
