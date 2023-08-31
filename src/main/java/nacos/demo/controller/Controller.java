package nacos.demo.controller;

import nacos.demo.feigns.UserClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class Controller {

    @Resource
    UserClient userClient;

    @Resource
    LoadBalancerClient loadBalancerClient;

    @GetMapping("/qw")
    public Object test() {
        String result = userClient.getUserById(false);

        ServiceInstance serviceInstance = loadBalancerClient.choose("zzt");

        return serviceInstance;
    }

    @GetMapping("/user")
    public String user() {
        return "hao";
    }
}
