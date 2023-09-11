package nacos.demo.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("zzt1")
public interface UserClient {

    @RequestMapping("/user")
    String getUserById(@RequestParam("rollback") boolean rollback);
}
