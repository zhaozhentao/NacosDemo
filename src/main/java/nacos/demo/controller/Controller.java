package nacos.demo.controller;

import io.seata.spring.annotation.GlobalTransactional;
import nacos.demo.feigns.UserClient;
import nacos.demo.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
public class Controller {

    @Resource
    UserClient client;

    @Value("${text}")
    String text;

    @GlobalTransactional
    @GetMapping("/user")
    public Object show(@RequestParam("rollback") boolean rollback) {
        User user = new User("旺财", new Date());
        user.insert();

        if (rollback) throw new RuntimeException("我要回滚啦");
        return text;
    }

    @GlobalTransactional
    @GetMapping("/test")
    public Object test(@RequestParam("rollback") boolean rollback) {
        User user = new User("test", new Date());
        user.insert();
        return client.getUserById(rollback);
    }
}
