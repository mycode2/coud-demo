### 完成服务注册到consul
1. consul IP
spring.cloud.consul.host=192.168.0.194
2. consul端口
spring.cloud.consul.port=8500
3. 注册到consul的服务名称
spring.cloud.consul.discovery.serviceName=cust-service
4. 配置通过ip地址注册
spring.cloud.consul.discovery.prefer-ip-address=true

###