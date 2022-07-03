## 注意

`com.alibaba.cloud.nacos.balancer` 在 `com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery:2021.0.1.0` 版本才有

## 配置中心

### 默认命名空间

需要在配置中心添加配置文件 `test.yml`
```yml
spring:
  cloud:
    nacos:
      discovery:
        server-addr: 47.92.205.201:8848
        cluster-name: guangzhou

text: xixi
```

### dev命名空间
需要在配置中心`dev`命名空间下添加配置文件 `test.yml`
