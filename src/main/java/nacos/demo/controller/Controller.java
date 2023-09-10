package nacos.demo.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class Controller {

    @Resource
    NamingService namingService;

    @GetMapping("/api/services")
    public Object service(
        @RequestParam("page") int page,
        @RequestParam("perPage") int perPage
    ) {
        String url = "http://console.nacos.io/nacos/v1/ns/catalog/services?hasIpCount=true&withInstances=false" +
            "&pageNo=" + page + "&pageSize=" + perPage + "&serviceNameParam=&groupNameParam=&namespaceId=";

        try (var response = HttpRequest.of(url).execute()) {
            return response.body();
        }
    }

    @GetMapping("/api/nodes")
    public List<Instance> nodes(
        @RequestParam("serviceName") String serviceName
    ) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

    @PostMapping("/api/removeGray")
    public void gray(
        @RequestParam("ip") String ip,
        @RequestParam("port") Integer port,
        @RequestParam("serviceName") String serviceName
    ) throws NacosException {
        var instances = namingService.getAllInstances(serviceName);

        for (var instance : instances) {
            if (!ip.equals(instance.getIp()) || port != instance.getPort()) {
                continue;
            }

            HttpRequest httpRequest = HttpRequest.of("http://console.nacos.io/nacos/v1/ns/instance");
            httpRequest.setMethod(Method.PUT);

            String body = "serviceName=" + instance.getServiceName()
                + "&clusterName=" + instance.getClusterName()
                + "&ip=" + instance.getIp()
                + "&port=" + instance.getPort()
                + "&weight=" + instance.getWeight()
                + "&enabled=true"
                + "&metadata=gray=false";

            httpRequest.body(body);

            try (var response = httpRequest.execute()) {
                log.info("response {}", response.body());
            }
        }
    }
}
