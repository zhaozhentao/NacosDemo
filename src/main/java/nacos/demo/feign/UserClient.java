package nacos.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("test")
public interface UserClient {

    @RequestMapping("/user/{id}")
    String getUserById(@PathVariable("id") int id);
}
