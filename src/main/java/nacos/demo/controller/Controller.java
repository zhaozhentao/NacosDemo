package nacos.demo.controller;

import lombok.extern.slf4j.Slf4j;
import nacos.demo.feigns.UserClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class Controller {

    @Resource
    UserClient userClient;

    @Resource
    LoadBalancerClient loadBalancerClient;

    @GetMapping("/qw")
    public Object test() {
        String result = userClient.getUserById(false);
        return result;
    }

    @GetMapping("/user")
    public String user() {
        log.info("here");
        return "hao";
    }
}
