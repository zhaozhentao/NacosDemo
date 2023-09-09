package nacos.demo.controller;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.NacosNamingService;
import lombok.extern.slf4j.Slf4j;
import nacos.demo.feigns.UserClient;
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

    @GetMapping("service")
    public List<String> service() {
        return discoveryClient.getServices();
    }

    @GetMapping("nodes")
    public List<Instance> nodes() throws NacosException {
        NamingService namingService1 = NamingFactory.createNamingService("console.nacos.io");

        return namingService1.getAllInstances("zzt");
    }

    @GetMapping("test")
    public void test1() {

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
