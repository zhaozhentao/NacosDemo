package nacos.demo.controller;

import com.alibaba.nacos.api.naming.NamingService;
import lombok.extern.slf4j.Slf4j;
import nacos.demo.feigns.UserClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class Controller {

    @Resource
    UserClient userClient;

    @Resource
    LoadBalancerClient loadBalancerClient;

    @Resource
    DiscoveryClient discoveryClient;

    @Resource
    NamingService namingService;

    @GetMapping("service")
    public List<String> service() {
        return discoveryClient.getServices();
    }

    @GetMapping("nodes")
    public List<ServiceInstance> nodes() {
        return discoveryClient.getInstances("zzt");
    }

    @GetMapping("/api/qw")
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
