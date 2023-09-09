package nacos.demo.controller;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import lombok.extern.slf4j.Slf4j;
import nacos.demo.feigns.UserClient;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ListView<String> service(
        @RequestParam("page") int page,
        @RequestParam("perPage") int perPage
    ) throws NacosException {
        return namingService.getServicesOfServer(page, perPage);
    }

    @GetMapping("nodes")
    public List<Instance> nodes(@RequestParam("serviceName") String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
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
