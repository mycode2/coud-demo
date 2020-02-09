package com.zc.study.cust;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableApolloConfig
@ComponentScan(basePackages = {"com.zc.study"})
public class CustServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustServiceApplication.class, args);
    }

}
