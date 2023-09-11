package nacos.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Client;
import feign.Feign;
import lombok.extern.slf4j.Slf4j;
import nacos.demo.feigns.TraceClient;
import nacos.demo.interceptors.FeignHeaderRequestInterceptor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class Controller {

    @Resource
    NamingService namingService;

    @Resource
    Registration registration;

    @Resource
    ApplicationContext applicationContext;

    @GetMapping("/api/services")
    public Object service(
        @RequestParam("page") int page,
        @RequestParam("perPage") int perPage
    ) {
        var url = "http://console.nacos.io/nacos/v1/ns/catalog/services?hasIpCount=true&withInstances=false" +
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

        var instance = instances.stream()
            .filter(i -> i.getIp().equals(ip) && port == i.getPort())
            .findFirst()
            .orElseThrow(() -> new RuntimeException("找不到实例"));

        var body = "serviceName=" + instance.getServiceName()
            + "&clusterName=" + instance.getClusterName()
            + "&ip=" + instance.getIp()
            + "&port=" + instance.getPort()
            + "&weight=" + instance.getWeight()
            + "&enabled=true"
            + "&metadata=gray=false";

        try (
            var response = HttpRequest.of("http://console.nacos.io/nacos/v1/ns/instance")
                .setMethod(Method.PUT)
                .body(body)
                .execute()
        ) {
            log.info("修改节点元数据返回: {}", response.body());
        }
    }

    @PostMapping("/api/trace")
    public void trace(@RequestBody List<String> services) {
        if (CollUtil.isEmpty(services)) return;

        var service = services.remove(0);

        var client = applicationContext.getBean(Client.class);
        var interceptor = applicationContext.getBean(FeignHeaderRequestInterceptor.class);

        var jsonConverter = new MappingJackson2HttpMessageConverter(new ObjectMapper());
        ObjectFactory<HttpMessageConverters> converter = () -> new HttpMessageConverters(jsonConverter);

        var traceClient = Feign.builder()
            .client(client)
            .contract(new SpringMvcContract())
            .encoder(new SpringEncoder(converter))
            .decoder(new SpringDecoder(converter))
            .requestInterceptor(interceptor)
            .target(TraceClient.class, "http://" + service);

        traceClient.trace(services);
    }
}
