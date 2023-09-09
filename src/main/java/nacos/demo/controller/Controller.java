package nacos.demo.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import nacos.demo.interceptors.HttpContextUtil;
import nacos.demo.feigns.UserClient;
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
    NamingService namingService;

    @GetMapping("api/services")
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

    @GetMapping("api/nodes")
    public List<Instance> nodes(
        @RequestParam("serviceName") String serviceName
    ) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

    @GetMapping("metadata")
    public String gray(@RequestParam("who") String who) throws NacosException {
        var instances = namingService.getAllInstances("zzt");

        for (var instance : instances) {
            HttpRequest httpRequest = HttpRequest.of("http://console.nacos.io/nacos/v1/ns/instance");
            httpRequest.setMethod(Method.PUT);

            String body = String.format(
                "serviceName=%s&clusterName=%s&ip=%s&port=%d&weight=%f&enabled=true&metadata=who=%s",
                instance.getServiceName(),
                instance.getClusterName(),
                instance.getIp(),
                instance.getPort(),
                instance.getWeight(),
                who
            );
            httpRequest.body(body);

            try (var response = httpRequest.execute()) {
                log.info("response {}", response.body());
            }
        }

        return "qq";
    }

    @GetMapping("/api/qw")
    public Object test() {
        String result = userClient.getUserById(false);
        return result;
    }

    @GetMapping("/user")
    public String user() {
        log.info("header {}", HttpContextUtil.getHttpServletRequest().getHeader("PARK_CODE"));
        return "hao";
    }
}
